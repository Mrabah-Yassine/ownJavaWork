package fr.edf.epr2.rgn.transform.domain.read.entity.itf;

import fr.edf.epr2.rgn.transform.domain.read.factory.ReportStyle;
import fr.edf.epr2.rgn.transform.domain.read.factory.ReportType;

import java.util.List;

public interface IReport {

    ReportType getReportType() throws NoReportTypeIdentifiedException;

    ReportStyle getReportStyle();

    List<IBusinessObject> getReportBusinessObjects();

    class NoReportTypeIdentifiedException extends Exception {
        public NoReportTypeIdentifiedException(String message, Throwable cause) {
            super(message, cause);
        }

        public NoReportTypeIdentifiedException(String message) {super(message);}
    }
}
