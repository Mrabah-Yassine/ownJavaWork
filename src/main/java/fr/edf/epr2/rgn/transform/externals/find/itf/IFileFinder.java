package fr.edf.epr2.rgn.transform.externals.find.itf;

import fr.edf.epr2.rgn.transform.IResult;
import fr.edf.epr2.rgn.transform.exception.GenerateRGNReportException;

public interface IFileFinder extends IResult {

    String getFileName() throws UnableToFindFileException;

    class UnableToFindFileException extends GenerateRGNReportException {

        public UnableToFindFileException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
