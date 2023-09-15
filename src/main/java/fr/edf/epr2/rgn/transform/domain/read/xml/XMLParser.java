package fr.edf.epr2.rgn.transform.domain.read.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

public class XMLParser {

    private final Document checkedOutXmlAsDocument;
    private final XPathFactory xpathFactory;


    protected NodeList xmlNodeListFromXpathExpression;



    public XMLParser(Document checkedOutXmlAsDocument, XPathFactory xpathFactory, String xpathExpression) throws XPathExpressionException {
        this.checkedOutXmlAsDocument = checkedOutXmlAsDocument;
        this.xpathFactory = xpathFactory;
        this.compileAndEvaluateXpath(xpathExpression);
    }


    public NodeList getXmlChildrenNodeListFromParentXmlNode(Node parentNode){
        return parentNode.getChildNodes();
    }

    protected boolean isXmlNodeNameEqualTo(Node xmlNode, String name){
        return xmlNode.getNodeName().equals(name);
    }

    protected boolean isNodeReallyAnXmlNode(Node nodeToCheck){
        return nodeToCheck.getNodeType() == Node.ELEMENT_NODE;
    }

    protected boolean isNodeListEmpty(NodeList nodeList){
        return nodeList.getLength() == 0;
    }

    protected NamedNodeMap getAttributesOfXmlNode(Node xmlNode){
        return xmlNode.getAttributes();
    }

    protected Node getAttributeWithIndexFromAttributesList(int attributeIndex, NamedNodeMap attributesList){
        return attributesList.item(attributeIndex);
    }


    public String getTextContentOfXmlNodeWithIndex(int nodeIndex) {
        return getNodeWithIndexFromList(nodeIndex, xmlNodeListFromXpathExpression).getNodeValue();
    }

    protected Node getNodeWithIndexFromList(int nodeIndex, NodeList nodeList){
        return nodeList.item(nodeIndex);
    }


    private void compileAndEvaluateXpath(String xpathExpression) throws XPathExpressionException {
        XPath xpath = this.xpathFactory.newXPath();
        XPathExpression exprItem = xpath.compile(xpathExpression);
        xmlNodeListFromXpathExpression = (NodeList) exprItem.evaluate(checkedOutXmlAsDocument, XPathConstants.NODESET);
    }
}
