package clockHelper;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXMLFile {

	static boolean resault = false;
	
	public static boolean createConfig() {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("ClockConfig");
			doc.appendChild(rootElement);

			Element Info = doc.createElement("Info");
			rootElement.appendChild(Info);
			Info.setAttribute("id", "1");

			Element bgcolor = doc.createElement("backgroundcolor");
			bgcolor.appendChild(doc.createTextNode("#404040"));
			Info.appendChild(bgcolor);

			Element fgcolor = doc.createElement("timeanddatecolor");
			fgcolor.appendChild(doc.createTextNode("#ffffff"));
			Info.appendChild(fgcolor);
			
			Element lastx = doc.createElement("lastx");
			lastx.appendChild(doc.createTextNode("0"));
			Info.appendChild(lastx);
			
			Element lasty = doc.createElement("lasty");
			lasty.appendChild(doc.createTextNode("0"));
			Info.appendChild(lasty);
			
			Element timeformat = doc.createElement("timeformat");
			timeformat.appendChild(doc.createTextNode("h:mm a"));
			Info.appendChild(timeformat);
			
			Element dateformat = doc.createElement("dateformat");
			dateformat.appendChild(doc.createTextNode("E, MMM. d, y"));
			Info.appendChild(dateformat);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("ClockConfig.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");
			resault = true;
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			resault = false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			resault = false;
		}
		
		return resault;
	}
}
