package net.jalan.jws.search;
import net.jalan.jws.search.APIRequest;
import org.w3c.dom.Document;
import java.util.LinkedHashMap;
public class HotelSearch {
	public String sarea;
	public Boolean advance = false;
	public LinkedHashMap<String,Hotel> hotels;
	private APIRequest request;
	public int total = 0;
	public int count = 0;
	public int start;
	public HotelSearch(String sarea) {
		this.sarea = sarea;
	}
	public HotelSearch(String sarea,Boolean advance) {
		this.advance = advance;
		this.sarea = sarea;
	}
	public void request() { request(1); }
	public void request(int start) {
		this.start = start>1?start:1;
		request = new APIRequest(APIRequest.HOTEL_ADVANCE);
		Document doc = request.connect();
	}
}
