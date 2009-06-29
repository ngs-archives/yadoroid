package net.jalan.jws.search.hotel;
import org.w3c.dom.Node;
import java.net.URL;
public class HotelPicture {
	public URL url;
	public String caption;
	public HotelPicture(Node xml) {
		try {
			url = new URL(xml.getFirstChild().getNodeValue());
		} catch(Exception e) {}
		caption = xml.getNextSibling().getFirstChild().getNodeValue();
	}
}

