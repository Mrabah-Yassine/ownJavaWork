package fr.edf.epr2.rgn.transform.externals.find.itf;

import fr.edf.epr2.rgn.transform.IResult;
import fr.edf.epr2.rgn.transform.exception.GenerateRGNReportException;

public interface IFilePathFinder extends IResult {

    String getFilePath() throws UnableToGetFilePathException;

    class UnableToGetFilePathException extends GenerateRGNReportException {
        public UnableToGetFilePathException(String message, Throwable cause) {
            super(message, cause);
        }

        public UnableToGetFilePathException(String message) {
            super(message);
        }
    }
}
