package net.jalan.jws.search;
import org.w3c.dom.Node;
import java.net.URLEncoder;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import net.jalan.jws.search.Area;
import net.jalan.jws.search.HotelPicture;
public class Hotel {
	static public final String BASE_URI = "http://ck.jp.ap.valuecommerce.com/servlet/referral?sid=2462325&pid=878248727&vc_url=";
	static public final String BEACON_URI = "http://ad.jp.ap.valuecommerce.com/servlet/gifbanner?sid=2462325&pid=878248727";
	public String name;
	public String id;
	public String postCode;
	public String address;
	public HashMap<String,Area> area;
	public String type;
	public URL url;
	public String catchCopy;
	public String caption;
	public LinkedList<HotelPicture> pictures;
	public LinkedList<String> access;
	public Hotel(Node xml) {
		
	}

}

