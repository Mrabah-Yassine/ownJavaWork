package fr.edf.epr2.rgn.transform.externals.jpo;

import com.matrixone.apps.common.CommonDocument;
import com.matrixone.apps.domain.DomainObject;
import fr.edf.epr2.rgn.transform.domain.read.factory.DocumentFactory;
import fr.edf.epr2.rgn.transform.domain.read.entity.itf.IReport;
import fr.edf.epr2.rgn.transform.domain.read.factory.ReportStyle;
import fr.edf.epr2.rgn.transform.domain.read.entity.impl.XMLRGNReport;
import fr.edf.epr2.rgn.transform.domain.read.xml.XMLParser;
import fr.edf.epr2.rgn.transform.domain.read.xml.XMLStyleParser;
import fr.edf.epr2.rgn.transform.domain.read.entity.itf.ICell;
import fr.edf.epr2.rgn.transform.exception.GenerateRGNReportException;
import fr.edf.epr2.rgn.transform.externals.find.impl.FindFilePathToCheckout;
import fr.edf.epr2.rgn.transform.externals.find.impl.FindRgnStyleFile;
import fr.edf.epr2.rgn.transform.externals.find.impl.LastActiveVersionFileFinder;
import fr.edf.epr2.rgn.transform.externals.find.itf.IFileFinder;
import fr.edf.epr2.rgn.transform.externals.find.itf.IFilePathFinder;
import matrix.db.Context;
import matrix.db.FcsSupport;
import matrix.db.JPO;
import matrix.util.MatrixException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.List;
import java.util.Map;

public class SWT_ReportsGenerator_mxJPO {

    private static final Logger logger = LoggerFactory.getLogger(SWT_ReportsGenerator_mxJPO.class);

    private static final String RGN_STYLE_FILE_PATH = "SWT_SPACE_BATCH_RGN_STYLE_XML_PATH";

    private String fileNameToCheckout;

    private String checkOutFilePath;

    private String rgnStyleFilePath;

    private String fullFilePath;

    public int swtGenerateReport(Context context, String[] args) throws MatrixException, XPathExpressionException, GenerateRGNReportException {

        /*Unpacking input args*/
        Map<String, String> programMap = unpackInputArgs(args);
        String strReportDocumentId = getInfoFromReportDocument(programMap, "objectId"); //id du doc
        String reportGenerationMode = getInfoFromReportDocument(programMap, "mode"); //mode : oneSheet Vs MultipleSheet
        String inputFileFormat = "xml";
        String outputFileFormat = "xlsx";

        DomainObject reportDocDomainObject = DomainObject.newInstance(context, strReportDocumentId);
        CommonDocument reportDocumentHost = getCommonDocument(strReportDocumentId);

        //Get the input xml file name to check-out from Active Version
        IFileFinder iFileToCheckOutFinder = new LastActiveVersionFileFinder(context, reportDocDomainObject, inputFileFormat);
        fileNameToCheckout = iFileToCheckOutFinder.getFileName();

        //Get the temporary input xml file path where it is going to be checked-out
        IFilePathFinder iFilePathToCheckOutFinder = new FindFilePathToCheckout(context, fileNameToCheckout);
        checkOutFilePath = iFilePathToCheckOutFinder.getFilePath();

        //Get the input rgn style xml file name from source files through env variable
        /*loading env variable of the style file*/
        IFilePathFinder iStyleFilePathFinder = new FindRgnStyleFile(RGN_STYLE_FILE_PATH);
        rgnStyleFilePath = iStyleFilePathFinder.getFilePath();

        /*Checking out full path*/
        fullFilePath = checkOutFilePath + File.separator + fileNameToCheckout;

        //checkout xml file
        checkoutFileFromHostToTemporaryDir(context, reportDocumentHost, fileNameToCheckout, checkOutFilePath);

        ///////////////////

        Document checkedOutDocument = DocumentFactory.createDocumentFromFile(fullFilePath);

        XPathFactory xpathfactory = XPathFactory.newInstance();

        XMLParser xmlParser = new XMLParser(checkedOutDocument, xpathfactory, "//section[1]/title/text()");

        IReport iXmlRgnReport = new XMLRGNReport(xmlParser);

        ReportStyle reportStyle = iXmlRgnReport.getReportStyle();

        XMLStyleParser styleParser = new XMLStyleParser(checkedOutDocument, xpathfactory, "//" + reportStyle.name());

        List<ICell> cellList = styleParser.getCellsStyleList();


        //construct excel file name
        String excelFileName;
        String inputFileNameWithoutExtension = getFileNameWithoutExtension(fileNameToCheckout, inputFileFormat);
        if(reportGenerationMode.equalsIgnoreCase("one_sheet")){
            excelFileName = "OneSheet_" +  inputFileNameWithoutExtension + "." + outputFileFormat;
            IReport iActualRGNReport = new XMLRGNReport(fullFilePath);
            ReportType reportType = iActualRgnReport.getReportType();
            String excelSheetName = ReportType.TechnicalReportType.valueOf(reportType) + "_Report";
        }else if (reportGenerationMode.equalsIgnoreCase("multiple_sheet")){
            excelFileName = "MultipleSheet_" +  inputFileNameWithoutExtension + "." + outputFileFormat;
        }else{
            throw new UnhandledRgnModeException(String.format("This generation mode %s is unknown"));
        }








        //TODO
        ISearchEngine iSearchForAssociatedXml = new Searchengine();
        iSearchForAssociatedXml.launchSearch();

        /*Loading the input brut file to be transformed from report document*/
        IReader IInputFileReader = new InputCheckedOutFileReader(context, reportDocDomainObject, inputFormat);
        if(!IInputFileReader.isSucess()){
            throw new UnableToHandleInputForRgnTransformationException(String.
                    format("Unable to handle loading of file with format %s from %s document",
                            inputFormat, strReportDocumentId));
        }




        /*Loading style file*/
        IReader IStyleFileReader = new InputConfigStyleFileReader(pathToTheXmlStyleFile);

        if(!IStyleFileReader.isSuccess()){
            throw new UnableToHandleInputForRgnTransformationException(String.
                    format("Unable to correctly load the style file %s",
                            pathToTheXmlStyleFile));
        }

        /*Making the transformation*/
        ITransformer IReportTransformer = new TransformForRgn(context, IInputFileReader, IStyleFileReader, toFormat);
        if(!IReportTransformer.isSuccess()){
            throw new UnableToTransformException(String.format("Unable to transform from %s to %s", inputFormat, toFormat));
        }

        /*Writing and checking in the transformed file into the report document*/
        IWriter IOutputFileWriter = new OutputCheckOutFileWriter(context, IReportTransformer, strReportDocumentId);
        if(!IOutputFileWriter.isSuccess()){
            throw new FailedToWriteFinalTransformedReportException(String.format("Failed to make check-in of the newly " +
                    "generated excel file"));
        }
        return 0;
    }


    private CommonDocument getCommonDocument(String strReportDocumentId) throws MatrixException {
        CommonDocument reportDocumentHost;
        try{
            reportDocumentHost = new CommonDocument(strReportDocumentId);
        }catch(Exception e){
            throw new MatrixException(String.format("Unable to initialize Common Document from document id %s : ", strReportDocumentId), e);
        }
        return reportDocumentHost;
    }


    private void checkoutFileFromHostToTemporaryDir(Context context, CommonDocument hostDocument, String fileNameToCheckout,
                                                    String temporaryDir) throws MatrixException {
        FcsSupport.fcsCheckout(hostDocument.getObjectId(context), context, false, "generic", fileNameToCheckout, temporaryDir);
        logger.debug("== File {} was created in the temporary directory {} ==", fileNameToCheckout, temporaryDir);
    }


    private Map<String, String> unpackInputArgs(String[] args) throws UnpackInputArgsException {
        try{
            return JPO.unpackArgs(args);
        }catch(Exception e){
            throw new UnpackInputArgsException(String.format("Unable to unpack input args %s", args), e);
        }
    }


    private String getInfoFromReportDocument(Map<String, String> unpackedArgsMap, String infoKey){
        return unpackedArgsMap.get(infoKey);
    }


    private String getFileNameWithoutExtension(String fileName, String fileExtension) {
        return fileName.replaceAll("." + fileExtension, "");
    }



    /*Exception definitions*/

    private static class UnableToHandleInputForRgnTransformationException extends GenerateRGNReportException {
        public UnableToHandleInputForRgnTransformationException(String message) {
            super(message);
        }
    }

    private static class UnpackInputArgsException extends GenerateRGNReportException {
        public UnpackInputArgsException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class UnableToTransformException extends GenerateRGNReportException {
        public UnableToTransformException(String message) {
            super(message);
        }
    }

    private static class FailedToWriteFinalTransformedReportException extends GenerateRGNReportException {
        public FailedToWriteFinalTransformedReportException(String message) {
            super(message);
        }
    }


    private static class UnhandledRgnModeException extends GenerateRGNReportException {
        public UnhandledRgnModeException(String message) {
            super(message);
        }
    }
}
