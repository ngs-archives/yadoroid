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
	public int start = 1;
	public int nextStart = 0;
	public int prevStart = 0;
	public int nextCount = 0;
	public int prevCount = 0;
	public float apiVersion = 0;
	public HotelSearchOptions params;
	public HotelSearch() {
	}
	public HotelSearch(Boolean advance) {
		this.advance = advance;
	}
	public void request() { request(new HotelSearchOptions()); }
	public void request(HotelSearchOptions params) {
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
		total = Hotel.getIntValue(doc.getElementsByTagName("NumberOfResults").item(0));
		count = params.count;
		start = params.start;
		nextStart = start+count;
		if(nextStart>total) nextStart = 0;
		nextCount = total-nextStart+1;
		if(nextCount>count) nextCount = count;
		else if(nextCount<0) nextCount = 0;
		prevStart = start>count?start-count:0;
		prevCount = start>count?count:0;
		apiVersion = Hotel.getFloatValue(doc.getElementsByTagName("APIVersion").item(0));
	}
	public Hotel getHotelById(String id) {
		return hotels.get(id);
	}
	public Hotel item(int position) {
		return hotelList.get(position);
	}
	public int size() {
		return hotelList.size();
	}
}
