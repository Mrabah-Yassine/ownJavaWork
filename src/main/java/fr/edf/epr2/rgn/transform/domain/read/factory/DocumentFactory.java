package fr.edf.epr2.rgn.transform.domain.read.factory;

import fr.edf.epr2.rgn.transform.exception.GenerateRGNReportException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DocumentFactory {

    private DocumentFactory() {
    }

    public static Document createDocumentFromFile(String filePath) throws GenerateRGNReportException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            //---
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader inputReader = new BufferedReader(isr);
            InputSource is = new InputSource(inputReader);
            is.setEncoding("UTF-8");

            doc = builder.parse(is);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new GenerateRGNReportException(String.format("Failed to continue the process of report generation : %s", e.getMessage()), e);
        }
        return doc;
    }
}
