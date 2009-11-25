package net.jalan.jws.search.hotel;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
public class AccessInformation {
	public String name;
	public String text;
	static public final String NAME = "name";
	public AccessInformation(Node xml) {
		NamedNodeMap atr = xml.getAttributes();
		name = atr.getNamedItem(NAME).getNodeValue();
		text = xml.getFirstChild().getNodeValue();
	}
}
