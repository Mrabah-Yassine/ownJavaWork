package fr.edf.epr2.rgn.transform.externals.find.impl;

import com.dassault_systemes.ontology_itfs.OntoImpossibleCreationException;
import fr.edf.epr2.rgn.transform.externals.find.itf.IFilePathFinder;
import matrix.db.Context;
import matrix.util.MatrixException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FindFilePathToCheckout implements IFilePathFinder {


    private static final Logger logger = LoggerFactory.getLogger(FindFilePathToCheckout.class);

    private final Context context;
    private final String fileNameToCheckout;


    private boolean isSuccess = false;

    private String path;

    public FindFilePathToCheckout(Context context, String fileNameToCheckout) {
        this.context = context;
        this.fileNameToCheckout = fileNameToCheckout;
    }


    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public String getFilePath() throws UnableToGetFilePathException {
        try {
            prepareTemporaryFolderForCheckOut();
            isSuccess = true;
        } catch (MatrixException e) {
            throw new UnableToGetFilePathException(String.format("Unable to get full path of file %s ", fileNameToCheckout), e);
        }
        return path;
    }


    private void prepareTemporaryFolderForCheckOut() throws MatrixException {

        //-----Prepare checkout directory---------------------
        String workSpacePath = createWorkSpaceAndGetItsPath();
        logger.debug("Working directory is :{}", workSpacePath);

        //------Set Access RWE inside working directory--------
        File workingDirFile = createFileFromPath(workSpacePath);
        setFileProperties(workingDirFile);

        //-------Creation du dossier tmp ----------------------
        String tmp = "tmp";
        workSpacePath = workSpacePath + File.separator + tmp + File.separator;
        checkDirectoryExistenceAndCreateIfAbsent(workSpacePath);

        //------Valuate path var and go for checkout----------
        this.path = workSpacePath;
        logger.debug("WORKING PATH:::::::::::::{}", this.path);

    }


    private void checkDirectoryExistenceAndCreateIfAbsent(String workSpacePath) throws OntoImpossibleCreationException {
        File dirToCreate = createFileFromPath(workSpacePath);
        boolean directoryCreationStatus = false;
        if (!dirToCreate.exists()) {
            directoryCreationStatus = createDirectoryFromFileAndCheckCreationStatus(dirToCreate);
        }

        if (!directoryCreationStatus) {
            throw new OntoImpossibleCreationException(String.format("Unable to create directory from path %s ", workSpacePath));
        }
    }


    private String createWorkSpaceAndGetItsPath() throws MatrixException {
        return context.createWorkspace();
    }

    private File createFileFromPath(String workSpacePath) {
        return new File(workSpacePath);
    }

    private void setFileProperties(File file) {
        boolean isWritable = file.setWritable(true);
        logger.info("write access for working directory is set to {}", isWritable);
        boolean isReadable = file.setReadable(true);
        logger.info("read access for working directory is set to {}", isReadable);
        boolean isExecutable = file.setExecutable(true);
        logger.info("execution access for working directory is set to {}", isExecutable);
    }

    private boolean createDirectoryFromFileAndCheckCreationStatus(File directoryToCreate) {
        return directoryToCreate.mkdir();
    }


}
