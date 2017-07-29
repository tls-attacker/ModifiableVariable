/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The goal of this class is to pretty print byte array text nodes so that text
 * contents and closing XML tags are correctly aligned.
 *
 * Juraj Somorovsky <juraj.somorovsky@rub.de>
 */
public class XMLPrettyPrinter {

    public static int IDENT_AMOUNT = 4;

    public static String prettyPrintXML(String input) throws TransformerConfigurationException,
            ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException,
            XPathFactoryConfigurationException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(IDENT_AMOUNT));
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        StreamResult result = new StreamResult(new StringWriter());
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(input)));

        // XPath for selecting all text contents
        XPathExpression xpath = XPathFactory.newInstance().newXPath().compile("//*[text()]/*");
        // this is better but it does not work, because the default java xpath
        // transformer does not support XPath 2.0
        // XPathExpression xpath =
        // XPathFactory.newInstance().newXPath().compile("//*[text()[matches(.,'^[0-9A-F ]*$')]]");
        // XPath for counting the number of ancestors of a current element
        XPathExpression xpathDepth = XPathFactory.newInstance().newXPath().compile("count(ancestor-or-self::*)");

        NodeList textNodes = (NodeList) xpath.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < textNodes.getLength(); i++) {
            String content = textNodes.item(i).getTextContent();
            System.out.println(textNodes.item(i).getTextContent());
            double doubleDepth = (Double) xpathDepth.evaluate(textNodes.item(i), XPathConstants.NUMBER);
            int depth = (int) doubleDepth;
            String emptyString = createEmptyString(depth);
            System.out.println(depth);
            String newContent = content.replaceAll("\n", ("\n" + emptyString));
            // remove last white space elements from the text content to align
            // the closing tag
            if (newContent.length() > content.length()) {
                newContent = newContent.substring(0, newContent.length() - IDENT_AMOUNT);
            }
            textNodes.item(i).setTextContent(newContent);
        }

        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        return result.getWriter().toString();
    }

    private static String createEmptyString(int depth) {
        char[] charArray = new char[depth * IDENT_AMOUNT];
        Arrays.fill(charArray, ' ');
        return new String(charArray);
    }

}
