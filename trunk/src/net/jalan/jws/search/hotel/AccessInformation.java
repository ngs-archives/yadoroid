package net.jalan.jws.search.hotel;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
public class AccessInformation {
	public String name;
	public String text;
	static public final String NAME = "name";
	public AccessInformation(Node xml) {
		Node n;
		NamedNodeMap atr = xml.getAttributes();
		if(atr != null) {
			n = atr.getNamedItem(NAME);
			name = n != null ? n.getNodeValue() : "";
		}
		n = xml.getFirstChild();
		text = n != null ? n.getNodeValue() : "";
	}
}
