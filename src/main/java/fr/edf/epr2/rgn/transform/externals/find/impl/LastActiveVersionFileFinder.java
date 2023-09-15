package fr.edf.epr2.rgn.transform.externals.find.impl;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.util.FrameworkException;
import com.matrixone.apps.domain.util.MapList;
import fr.edf.epr2.rgn.transform.externals.find.itf.IFileFinder;
import matrix.db.Context;
import matrix.util.StringList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LastActiveVersionFileFinder implements IFileFinder {

    private static final Logger logger = LoggerFactory.getLogger(LastActiveVersionFileFinder.class);

    private final Context context;
    private final DomainObject reportDocumentDomObj;
    private final String checkedOutFileFormat;

    private boolean isSuccess = false;

    private MapList associatedActiveVersionObjects;

    public LastActiveVersionFileFinder(Context context, DomainObject reportDocumentDomObj, String checkedOutFileFormat) {
        this.context = context;
        this.reportDocumentDomObj = reportDocumentDomObj;
        this.checkedOutFileFormat = checkedOutFileFormat;
    }


    @Override
    public boolean isSuccess() {
        return isSuccess;
    }


    @Override
    public String getFileName() throws UnableToFindFileException {
        String fileNameToProcess = DomainConstants.EMPTY_STRING;
        try {
            fileNameToProcess = getAllAssociatedActiveVersions().doesSomeExist().sortByDescendingDate().getLastActiveVersionFileName();
            isSuccess = true;
        } catch (ActiveVersionNotFoundException e) {
            throw new UnableToFindFileException(String.format("Unable to find the file with extension %s to be checked-out...", this.checkedOutFileFormat), e);
        }
        return fileNameToProcess;
    }


    private LastActiveVersionFileFinder getAllAssociatedActiveVersions() throws ActiveVersionNotFoundException {
        StringList busSelector = new StringList();
        busSelector.add(DomainConstants.SELECT_NAME);
        busSelector.add(DomainConstants.SELECT_MODIFIED);
        busSelector.add(DomainConstants.SELECT_ATTRIBUTE_TITLE);
        try {
            associatedActiveVersionObjects = this.reportDocumentDomObj.getRelatedObjects(context, "Active Version", "Report Document", busSelector,
                    new StringList(), false, true, (short) 1, "attribute[Title] match '*" + checkedOutFileFormat + "'", null, (short) 0);
        } catch (FrameworkException e) {
            throw new ActiveVersionNotFoundException(String.format("No Active Versions were found associated to document with id %s", this.reportDocumentDomObj.getObjectId()), e);
        }

        return this;
    }


    private LastActiveVersionFileFinder doesSomeExist() throws ActiveVersionNotFoundException {
        if (associatedActiveVersionObjects.isEmpty()) {
            throw new ActiveVersionNotFoundException(String.format("No %s file considered as Active Version was found to be checked out...", this.checkedOutFileFormat));
        }
        return this;
    }


    private LastActiveVersionFileFinder sortByDescendingDate() {
        if (doesMoreThanOneActiveVersionExist()) {
            logger.info("More than one {} were found as candidates..", this.checkedOutFileFormat);
            // Sorting these associated objects by date of modification
            associatedActiveVersionObjects.addSortKey(DomainConstants.SELECT_MODIFIED, "descending", "date");
            associatedActiveVersionObjects.sort();
        }
        return this;
    }


    private boolean doesMoreThanOneActiveVersionExist() {
        return associatedActiveVersionObjects.size() > 1;
    }


    private String getLastActiveVersionFileName() {
        String fileName = ((Map<String, String>) associatedActiveVersionObjects.get(0)).get(DomainConstants.SELECT_ATTRIBUTE_TITLE);
        if (!isFileNameWellDefined(fileName)) {
            throw new UnsupportedOperationException("The name of the extracted Active Version object cannot be processed");
        }
        return fileName;
    }

    private boolean isFileNameWellDefined(String fileName) {
        return fileName != null && !fileName.isEmpty();
    }


    private static class ActiveVersionNotFoundException extends Exception {
        private final String message;

        public ActiveVersionNotFoundException(String message) {
            this.message = message;
        }

        public ActiveVersionNotFoundException(String message, Throwable cause) {
            super(cause);
            this.message = message;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }
}
