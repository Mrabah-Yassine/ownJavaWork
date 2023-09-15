package fr.edf.epr2.rgn.transform.externals.find.impl;

import fr.edf.epr2.rgn.transform.externals.find.itf.IFilePathFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindRgnStyleFile implements IFilePathFinder {

    private static final Logger logger = LoggerFactory.getLogger(FindRgnStyleFile.class);

    private final String envVariableName;


    private boolean isSuccess = false;

    public FindRgnStyleFile(String envVariableName) {
        this.envVariableName = envVariableName;
    }


    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public String getFilePath() throws UnableToGetFilePathException {
        String pathToTheXmlStyleFile = System.getenv(this.envVariableName);

        if (pathToTheXmlStyleFile == null) {
            throw new UnableToGetFilePathException(String.format("Env variable %s is not defined or missing " +
                    "and returning a null value..", this.envVariableName));
        }

        isSuccess = true;
        return pathToTheXmlStyleFile;
    }
}
