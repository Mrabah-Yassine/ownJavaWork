package fr.edf.epr2.rgn.transform.domain.read.xml;

import fr.edf.epr2.rgn.transform.domain.read.factory.CellsFactory;
import fr.edf.epr2.rgn.transform.domain.read.entity.itf.ICell;
import fr.edf.epr2.rgn.transform.exception.GenerateRGNReportException;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class XMLStyleParser extends XMLParser {

    List<ICell> cellsStyleList = new LinkedList<>();


    public XMLStyleParser(Document checkedOutXmlAsDocument, XPathFactory xpathFactory, String xpathExpression) throws XPathExpressionException {
        super(checkedOutXmlAsDocument, xpathFactory, xpathExpression);
    }

    public List<ICell> getCellsStyleList() throws IllegalNumberOfRGBShadesException {
        parseHighLevelOfStyleXml();
        return cellsStyleList;
    }


    private void parseHighLevelOfStyleXml() throws IllegalNumberOfRGBShadesException {
        Node parentNode = this.getNodeWithIndexFromList(0, this.xmlNodeListFromXpathExpression);
        NodeList childrenNodes = this.getXmlChildrenNodeListFromParentXmlNode(parentNode);
        for (int childIndex = 0; childIndex < childrenNodes.getLength(); childIndex++) {
            Node currentChildNode = this.getNodeWithIndexFromList(childIndex, childrenNodes);
            if (this.isNodeReallyAnXmlNode(currentChildNode)) {
                processForStylesExtraction(currentChildNode);
            }
        }
    }


    private void processForStylesExtraction(Node headersOrAttributesNode) throws IllegalNumberOfRGBShadesException {
        String nodeName = headersOrAttributesNode.getNodeName();
        NodeList styleNodeList = this.getXmlChildrenNodeListFromParentXmlNode(headersOrAttributesNode);
        if (!this.isNodeListEmpty(styleNodeList)) {
            for (int childNodeIndex = 0; childNodeIndex < styleNodeList.getLength(); childNodeIndex++) {
                Node styleNode = this.getNodeWithIndexFromList(childNodeIndex, styleNodeList);
                extractStylePropertiesAndCreateCellsFromNode(styleNode, nodeName);
            }
        }
    }


    private void extractStylePropertiesAndCreateCellsFromNode(Node styleNode, String cellType) throws IllegalNumberOfRGBShadesException {
        if (this.isNodeReallyAnXmlNode(styleNode)) {
            NamedNodeMap xmlNodeAttributesList = this.getAttributesOfXmlNode(styleNode);
            Node rgbShadesXmlNode = this.getAttributeWithIndexFromAttributesList(0, xmlNodeAttributesList);
            String[] shadesOfRGBArray = extractShadesOfRGBFromNode(rgbShadesXmlNode);
            validateShadesRGBDefinition(shadesOfRGBArray);
            XSSFColor color = initializeColorDefinitionFromRGBShades(shadesOfRGBArray);
            String[] concernedCellsNamesArray = extractHeadersAndAttributeNamesFromXmlNode(styleNode);
            for (String cellName : concernedCellsNamesArray) {
                ICell cell = CellsFactory.createCellWithProperties(cellType, cellName, color);
                cellsStyleList.add(cell);
            }
        }
    }


    private String[] extractShadesOfRGBFromNode(Node attributeNode) {
        return attributeNode.getNodeValue().split(",");
    }

    private String[] extractHeadersAndAttributeNamesFromXmlNode(Node xmlNode) {
        return xmlNode.getTextContent().split("\\|");
    }


    private void validateShadesRGBDefinition(String[] shadesOfRGBArray) throws IllegalNumberOfRGBShadesException {
        if (shadesOfRGBArray.length != 3) {
            throw new IllegalNumberOfRGBShadesException("Number of RGB shades is different than 3 which is not possible..." +
                    "Please verify your input styles xml file");
        }
    }


    private XSSFColor initializeColorDefinitionFromRGBShades(String[] shadesOfRGBArray) {
        int shadesOfRed = Integer.parseInt(shadesOfRGBArray[0].trim());
        int shadesOfGrey = Integer.parseInt(shadesOfRGBArray[1].trim());
        int shadesOfBlue = Integer.parseInt(shadesOfRGBArray[2].trim());
        return new XSSFColor(new Color(shadesOfRed, shadesOfGrey, shadesOfBlue));
    }


    private static class IllegalNumberOfRGBShadesException extends GenerateRGNReportException {
        public IllegalNumberOfRGBShadesException(String message) {
            super(message);
        }
    }
}
