package org.ngsdev.yadoroid.map;
import java.util.LinkedList;
import android.os.Bundle;
import com.google.android.maps.*;
import org.ngsdev.yadoroid.Yadoroid;

public class PinOverlayItem extends OverlayItem {

    public PinOverlayItem(GeoPoint point){
        super(point, "", "");
    }
}