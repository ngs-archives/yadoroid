package org.ngsdev.yadoroid.map;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import android.os.Bundle;
import android.graphics.drawable.Drawable;
import com.google.android.maps.*;
import org.ngsdev.yadoroid.Yadoroid;

public class PinItemizedOverlay extends ItemizedOverlay<PinOverlayItem> {

    private List<GeoPoint> points = new ArrayList<GeoPoint>();

    public PinItemizedOverlay(Drawable defaultMarker) {
        super( boundCenterBottom(defaultMarker) );
    }

    @Override
    protected PinOverlayItem createItem(int i) {
        GeoPoint point = points.get(i);
        return new PinOverlayItem(point);
    }

    @Override
    public int size() {
        return points.size();
    }

    public void addPoint(GeoPoint point) {
        this.points.add(point);
        populate();
    }
	
    public void clearPoint() {
        this.points.clear();
        populate();
    }
}