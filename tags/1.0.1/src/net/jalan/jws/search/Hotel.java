package net.jalan.jws.search;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.graphics.Point;
import java.net.URLEncoder;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import net.jalan.jws.search.Area;
import net.jalan.jws.search.LatLong;
import net.jalan.jws.search.hotel.AccessInformation;
import net.jalan.jws.search.hotel.CreditCard;
import net.jalan.jws.search.hotel.HotelPicture;
import net.jalan.jws.search.hotel.Plan;
import org.ngsdev.yadoroid.Yadoroid;
public class Hotel {
	static public final String BASE_URI = "http://ck.jp.ap.valuecommerce.com/servlet/referral?sid=2462325&pid=878274998&vc_url=";
	static public final String BEACON_URI = "http://ad.jp.ap.valuecommerce.com/servlet/gifbanner?sid=2462325&pid=878248727";
	static public final String TAG_NAME = "Hotel";
	public String id;
	public String name;
	public String kana;
	public String onsen;
	public String postCode;
	public String address;
	public HashMap<String,String> area;
	public String type;
	public URL url;
	public String catchCopy;
	public String caption;
	public LinkedList<HotelPicture> pictures;
	public LinkedList<AccessInformation> access;
	public LinkedList<Plan> plans;
	public String checkInTime;
	public String checkOutTime;
	public LatLong latlng;
	public int sampleRateFrom;
	public int ratingCount;
	public float rate;
	public Date lastUpdate;
	public CreditCard creditCard;
	private Node node;
	public Hotel(Node node) {
		this.node = node;
		parse(node);
	}
	private void parse(Node node) {
		final NodeList nodes = node.getChildNodes();
		final int len = nodes.getLength();
		pictures = new LinkedList<HotelPicture>();
		access = new LinkedList<AccessInformation>();
		plans = new LinkedList<Plan>();
		for(int i=0;i<len;++i) {
			Node n = nodes.item(i);
			String nm = n.getNodeName();
			if(nm.equals("HotelID"))
				id = getStrValue(n);
			else if(nm.equals("HotelName"))
				name = getStrValue(n);
			else if(nm.equals("HotelNameKana"))
				kana = getStrValue(n);
			else if(nm.equals("OnsenName"))
				onsen = getStrValue(n);
			else if(nm.equals("PostCode"))
				postCode = getStrValue(n);
			else if(nm.equals("HotelAddress"))
				address = getStrValue(n);
			else if(nm.equals("Area")) {
				area = new HashMap<String,String>();
				parse(n);
			} else if(
				nm.equals("Region") ||
				nm.equals("Prefecture") ||
				nm.equals("LargeArea") ||
				nm.equals("SmallArea")
			) area.put(nm,getStrValue(n));
			else if(nm.equals("HotelType"))
				type = getStrValue(n);
			else if(nm.equals("HotelDetailURL"))
				try {
					url = new URL(BASE_URI+URLEncoder.encode(getStrValue(n)));
				} catch(Exception e) {}
			else if(nm.equals("HotelCatchCopy"))
				catchCopy = getStrValue(n);
			else if(nm.equals("HotelCaption"))
				caption = getStrValue(n);
			else if(nm.equals("PictureURL"))
				pictures.add(new HotelPicture(n));
			else if(nm.equals("AccessInformation"))
				access.add(new AccessInformation(n));
			else if(nm.equals("CheckInTime"))
				checkInTime = getStrValue(n);
			else if(nm.equals("CheckOutTime"))
				checkOutTime = getStrValue(n);
			else if(nm.equals("X")&&getStrValue(n)!="")
				latlng = new LatLong(new Integer(getStrValue(n)),new Integer(getStrValue(n.getNextSibling())));
			else if(nm.equals("SampleRateFrom"))
				sampleRateFrom = getIntValue(n);
			else if(nm.equals("NumberOfRatings"))
				ratingCount = getIntValue(n);
			else if(nm.equals("Rating"))
				rate = getFloatValue(n);
			else if(nm.equals("CreditCard"))
				creditCard = new CreditCard(n);
			else if(nm.equals("Plan"))
				plans.add(new Plan(n));
		}
	}
	public static String getStrValue(Node n) {
		final NodeList nl = n.getChildNodes();
		final int len = nl.getLength();
		final StringBuffer sb = new StringBuffer("");
		for(int i=0;i<len;i++) {
			sb.append(nl.item(i).getNodeValue());
		}
		return sb.toString();
	}
	public static int getIntValue(Node n) {
		try {
			return new Integer(getStrValue(n));
		} catch(Exception e) {
			return 0;
		}
		
	}
	public static float getFloatValue(Node n) {
		try {
			return new Float(getStrValue(n));
		} catch(Exception e) {
			return 0;
		}
	}
}

