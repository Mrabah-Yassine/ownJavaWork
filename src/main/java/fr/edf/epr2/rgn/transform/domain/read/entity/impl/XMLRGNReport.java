package fr.edf.epr2.rgn.transform.domain.read.entity.impl;

import fr.edf.epr2.rgn.transform.domain.read.factory.ReportStyle;
import fr.edf.epr2.rgn.transform.domain.read.factory.ReportStylePerType;
import fr.edf.epr2.rgn.transform.domain.read.factory.ReportType;
import fr.edf.epr2.rgn.transform.domain.read.entity.itf.IBusinessObject;
import fr.edf.epr2.rgn.transform.domain.read.entity.itf.IReport;
import fr.edf.epr2.rgn.transform.domain.read.xml.XMLParser;

import java.util.List;

public class XMLRGNReport implements IReport {

    private final XMLParser xmlRGNParser;

    private ReportType actualReportType;

    public XMLRGNReport(XMLParser xmlRGNParser) {
        this.xmlRGNParser = xmlRGNParser;
    }


    @Override
    public ReportType getReportType() throws NoReportTypeIdentifiedException{
        String reportCategory = getReportCategory();
        for (ReportType reportType : ReportType.values()) {
            String reportTypeAsString = reportType.name().replace("_", " ");
            if (reportCategory.toLowerCase().contains(reportTypeAsString.toLowerCase())) {
                actualReportType = reportType;
                return actualReportType;
            }
        }
        throw new NoReportTypeIdentifiedException("Unable to identify the type of the report (Article, Room etc...)");
    }

    @Override
    public ReportStyle getReportStyle() {
        return ReportStylePerType.getReportStyle().get(actualReportType);
    }


    @Override
    public List<IBusinessObject> getReportBusinessObjects() {
        return null;
    }


    private String getReportCategory() {
        return this.xmlRGNParser.getTextContentOfXmlNodeWithIndex(0);
    }

}
