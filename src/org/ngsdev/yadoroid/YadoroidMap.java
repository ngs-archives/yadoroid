package org.ngsdev.yadoroid;
import java.util.LinkedList;
import android.os.Bundle;
import com.google.android.maps.*;
import org.ngsdev.yadoroid.AbstractYadoroid;
import org.ngsdev.yadoroid.Yadoroid;
import org.ngsdev.yadoroid.map.PinItemizedOverlay;
import android.graphics.drawable.Drawable;

public class YadoroidMap extends MapActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		Bundle extras = getIntent().getExtras();
		String name = extras==null?"":extras.getString(AbstractYadoroid.EXTRA_KEY_HOTEL_NAME);
		if(name!="") setTitle(name+" | "+getString(R.string.hotel_map));
		double lat = extras==null?(double) 0:extras.getDouble(AbstractYadoroid.EXTRA_KEY_HOTEL_LAT);
    	double lng = extras==null?(double) 00:extras.getDouble(AbstractYadoroid.EXTRA_KEY_HOTEL_LNG);
    	GeoPoint pt = new GeoPoint(Double.valueOf(lat*1E6).intValue(),Double.valueOf(lng*1E6).intValue());
		// debug 0royquBZ8caAEmKwYbuUXg7DHysNM2Vm9TIB_mw
		// release 0royquBZ8caByyaGpJfNdK--DV8vZlYk3PM0sRw
		MapView map = new MapView(this, "0royquBZ8caByyaGpJfNdK--DV8vZlYk3PM0sRw");
		map.setEnabled(true);
		map.setBuiltInZoomControls(true);
		map.setClickable(true);
		Drawable pin = getResources().getDrawable( R.drawable.pin);
        PinItemizedOverlay pinOverlay = new PinItemizedOverlay(pin);
        map.getOverlays().add(pinOverlay);;
        pinOverlay.addPoint(pt);
        MapController ctrl = map.getController();
        ctrl.setCenter(pt);
        ctrl.setZoom(19);
		setContentView(map);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
