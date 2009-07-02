package net.jalan.jws.search;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import net.jalan.jws.search.APIRequest;
import net.jalan.jws.search.Area;
public class AreaSearch {
	private APIRequest request;
	public static Area regions;
	public AreaSearch() {
	}
	public void init() throws Exception {
		request = new APIRequest(APIRequest.AREA);
		final Document doc;
		try {
			doc = request.connect();
		} catch(Exception e) {
			throw e;
		}
		if(doc!=null) regions = new Area(doc);
	}
}
