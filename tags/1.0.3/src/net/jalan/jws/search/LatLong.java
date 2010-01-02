package net.jalan.jws.search;
import java.net.URLEncoder;
import org.ngsdev.android.map.WGSUtil;
import android.graphics.Point;
public class LatLong {
	public double lat;
	public double lng;
	public Point geoPoint;
	private int rawLat;
	private int rawLng;
	public LatLong(int x,int y) {
		double xx = Integer.valueOf(x).doubleValue()/3600000;
		double yy = Integer.valueOf(y).doubleValue()/3600000;
		geoPoint = WGSUtil.tokyoToWGS(xx,yy);
		this.lng = (double) geoPoint.x/1E6;
		this.lat = (double) geoPoint.y/1E6;
		rawLng = y;
		rawLat = x;
	}
	public String getRaw() {
		final StringBuffer sb = new StringBuffer("");
		sb.append(Integer.toString(rawLat));
		sb.append(",");
		sb.append(Integer.toString(rawLng));
		return sb.toString();
	}
	public String toUriString(String name) {
		final StringBuffer sb = new StringBuffer("geo:");
		sb.append(Double.toString(lat));
		sb.append(",");
		sb.append(Double.toString(lng));
		//sb.append("?q=");
		//sb.append(URLEncoder.encode(name));
		return sb.toString();
	}
}