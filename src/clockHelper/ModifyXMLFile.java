package clockHelper;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModifyXMLFile {

	static String old = null;
	
	public static void modifyConfig(String key, String value) {
		
		try {
			old = ReadXMLFile.readConfig(key);
			String filepath = "ClockConfig.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Node info = doc.getElementsByTagName("Info").item(0);

			NodeList list = info.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {

				Node node = list.item(i);

				if (key.equals(node.getNodeName())) {
					node.setTextContent(value);
				}

			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			
		} catch (Throwable e) {
			System.out.println(e);
		} finally {
			if(old == null) {
				System.out.println("Could not modify '" + key +"' it doesnt exist");
			} else {
				System.out.println("Changed " +key + " from '" + old + "' to '" + value + "'");
			}
		}
		
		
	}
}