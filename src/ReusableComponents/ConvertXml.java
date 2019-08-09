package ReusableComponents;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class ConvertXml {

	public static String convertDocumentToString(DOMSource doc, Logger log) {

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			// below code to remove XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
			// "yes");
			StringWriter writer = new StringWriter();
			// DOMSource source = new DOMSource((Node) docu);
			transformer.transform(doc, new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			log.error(e);
		}

		return null;
	}

	public static Document convertStringToDocument(String xmlStr, Logger log) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = (Document) builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public static void writeFile(String yourXML, String file) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(yourXML);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
