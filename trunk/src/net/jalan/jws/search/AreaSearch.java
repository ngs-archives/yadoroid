package net.jalan.jws.search;
import org.w3c.dom.Document;
import net.jalan.jws.search.APIRequest;

public class AreaSearch {
	public APIRequest request;
	public AreaSearch() {
		request = new APIRequest(APIRequest.AREA);
	}
	public void init() {
		request.connect();
	}
}