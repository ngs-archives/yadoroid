package net.jalan.jws.search.hotel;
import java.util.LinkedList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import net.jalan.jws.search.hotel.HotelPicture;
import net.jalan.jws.search.Hotel;
public class Plan {
	public String name;
	public LinkedList<String> roomType;
	public String roomName;
	public String checkInTime;
	public String checkOutTime;
	public LinkedList<HotelPicture> pictures;
	public String meal;
	public int sampleRateFrom;
	public Plan(Node xml) {
		final NodeList nodes = xml.getChildNodes();
		final int len = nodes.getLength();
		roomType = new LinkedList<String>();
		pictures = new LinkedList<HotelPicture>();
		for(int i=0;i<len;++i) {
			Node n = nodes.item(i);
			String nm = n.getNodeName();
			if(nm.equals("PlanName"))
				name = Hotel.getStrValue(n);
			else if(nm.equals("RoomType"))
				roomType.add(Hotel.getStrValue(n));
			else if(nm.equals("RoomName"))
				roomName = Hotel.getStrValue(n);
			else if(nm.equals("PlanCheckIn"))
				checkInTime = Hotel.getStrValue(n);
			else if(nm.equals("PlanCheckOut"))
				checkOutTime = Hotel.getStrValue(n);
			else if(nm.equals("PlanPictureURL"))
				pictures.add(new HotelPicture(n));
			else if(nm.equals("PlanSampleRateFrom"))
				sampleRateFrom = Hotel.getIntValue(n);
		}
		
	}

}


