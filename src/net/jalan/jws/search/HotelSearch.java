package net.jalan.jws.search;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.HashMap;
import net.jalan.jws.search.APIRequest;
import net.jalan.jws.search.hotel.HotelSearchOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class HotelSearch {
	public String sarea;
	public Boolean advance = false;
	public LinkedHashMap<String,Hotel> hotels;
	public LinkedList<Hotel> hotelList;
	private APIRequest _request;
	public int total = 0;
	public int count = 0;
	public int start;
	public HotelSearchOptions params;
	public HotelSearch() {
	}
	public HotelSearch(Boolean advance) {
		this.advance = advance;
	}
	public void request() { request(1); }
	public void request(int start) { request(new HotelSearchOptions(),start); }
	public void request(HotelSearchOptions params) { request(params,1); }
	public void request(HotelSearchOptions params,int start) {
		this.start = start>1?start:1;
		params.start = start;
		this.params = params;
		_request = new APIRequest(APIRequest.HOTEL_ADVANCE,params.getHashMap());
		Document doc = _request.connect();
		NodeList list = doc.getElementsByTagName("Hotel");
		int len = list.getLength();
		hotels = new LinkedHashMap<String,Hotel>();
		hotelList = new LinkedList<Hotel>();
		for(int i=0;i<len;++i) {
			Node n = (Node) list.item(i);
			if(n.getNodeName().equals(Hotel.TAG_NAME)){
				Hotel h = new Hotel(n);
				hotels.put(h.id,h);
				hotelList.add(h);
			}
		}
	}
	public Hotel getHotelById(String id) {
		return hotels.get(id);
	}
	public Hotel item(int position) {
		return hotelList.get(position);
	}
	public int getLength() {
		return hotelList.size();
	}
}
