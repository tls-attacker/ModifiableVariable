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
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The goal of this class is to pretty print byte array text nodes so that text
 * contents and closing XML tags are correctly aligned.
 *
 */
public class XMLPrettyPrinter {

    public static int IDENT_AMOUNT = 4;

    /**
     * This functions autoformats the text content of the child nodes of the
     * node which have the {@code autoformat="true"} attribute set. The
     * autoformatting results in having the textContent of the node aligned to
     * the depth of indentation of its parent node. The
     * {@code autoformat="true"} is also removed from the tree.
     *
     * <br>
     * <br>
     *
     * <b>Note:</b> the function also removes all blank text from nodes which
     * may merge {@code <a></a>} into {@code <a/>}.
     *
     * <br>
     * <br>
     *
     * <b>Caution:</b> the function literally takes the textContent of the child
     * nodes of the autoformat node. This removes any other nodes from that
     * child node and leaves only the textContent left.
     *
     * <br>
     * <br>
     *
     * <h3>Example</h3>
     *
     * Using this XML as input:
     *
     * <pre>
     * {@code
     * <root autoformat="true">
     *     <textnode>
     * this text should
     * be aligned
     *     </textnode>
     * </root>
     * }
     * </pre>
     *
     * Results in this output XML:
     *
     * <pre>
     * {@code
     * <root>
     *     <textnode>
     *         this text sould
     *         be aligned
     *     </textnode>
     * </root>
     * }
     * </pre>
     *
     *
     * @param input
     * @return
     * @throws TransformerConfigurationException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws TransformerException
     */
    public static String prettyPrintXML(String input) throws TransformerConfigurationException,
            ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException {
        return input;
        // Transformer transformer =
        // TransformerFactory.newInstance().newTransformer();
        // transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
        // Integer.toString(IDENT_AMOUNT));
        // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
        // "yes");
        // StreamResult result = new StreamResult(new StringWriter());
        // Document doc =
        // DocumentBuilderFactory.newInstance().newDocumentBuilder()
        // .parse(new InputSource(new StringReader(input)));
        // XPathExpression xpathDepth =
        // XPathFactory.newInstance().newXPath().compile("count(ancestor-or-self::*)");
        // XPathExpression toBeFormatted =
        // XPathFactory.newInstance().newXPath().compile("//*[@autoformat = \'true\']/*");
        // NodeList textNodes = (NodeList) toBeFormatted.evaluate(doc,
        // XPathConstants.NODESET);
        // for (int i = 0; i < textNodes.getLength(); i++) {
        // Node node = textNodes.item(i);
        // String content = node.getTextContent();
        // double doubleDepth = (Double) xpathDepth.evaluate(textNodes.item(i),
        // XPathConstants.NUMBER);
        // int depth = (int) doubleDepth;
        // String emptyString = createEmptyString(depth);
        // String newContent = content.replaceAll("\n", ("\n" + emptyString));
        // if (newContent.length() > content.length()
        // && newContent.substring(newContent.length() - IDENT_AMOUNT,
        // newContent.length()).trim().equals("")) {
        // newContent = newContent.substring(0, newContent.length() -
        // IDENT_AMOUNT);
        // }
        // node.setTextContent(newContent);
        // Element element = (Element) node.getParentNode();
        // element.removeAttribute("autoformat");
        //
        // }
        // DOMSource source = new DOMSource(doc);
        // transformer.transform(source, result);
        // return result.getWriter().toString();
    }

    private static String createEmptyString(int depth) {
        char[] charArray = new char[depth * IDENT_AMOUNT];
        Arrays.fill(charArray, ' ');
        return new String(charArray);
    }

}
