package net.jalan.jws.search;
import org.w3c.dom.Node;
import android.graphics.Point;
import java.net.URLEncoder;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import net.jalan.jws.search.Area;
import net.jalan.jws.search.HotelPicture;
import map.WGSUtil;
public class Hotel {
	static public final String BASE_URI = "http://ck.jp.ap.valuecommerce.com/servlet/referral?sid=2462325&pid=878248727&vc_url=";
	static public final String BEACON_URI = "http://ad.jp.ap.valuecommerce.com/servlet/gifbanner?sid=2462325&pid=878248727";
	static public final String TAG_NAME = "Hotel";
	public String id;
	public String name;
	public String kana;
	public String onsen;
	public String postCode;
	public String address;
	public HashMap<String,Area> area;
	public String type;
	public URL url;
	public String catchCopy;
	public String caption;
	public LinkedList<HotelPicture> pictures;
	public LinkedList<String> access;
	public String checkInTime;
	public String checkOutTime;
	public Point latlng;
	public int sampleRateFrom;
	public Date lastUpdate;
	
	//
	private Node node;
	public Hotel(Node node) {
		this.node = node;
		
	}
	private String getString(String n) {
		return "";
	}
	private int getInteger(String n) {
		return 1;
	}
}

