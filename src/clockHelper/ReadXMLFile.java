package clockHelper;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXMLFile {
	static String resault = null;
	
	public static String readConfig(String key) {

		try {
			File fXmlFile = new File("ClockConfig.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Info");


			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					 resault = eElement.getElementsByTagName(key).item(0).getTextContent();

				}
			}
		} catch (Exception e) {
			if(e.getCause() == null) {
				System.out.println("Line '" + key + "' doesnt exist");
			} else {
				e.printStackTrace();
			}
			resault = null;
		}
		
		return resault;
	}

}