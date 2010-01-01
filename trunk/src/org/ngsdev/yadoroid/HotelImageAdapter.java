package org.ngsdev.yadoroid;
import java.util.LinkedList;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import net.jalan.jws.search.hotel.HotelPicture;
public class HotelImageAdapter extends BaseAdapter {
	public LinkedList<HotelPicture> list;
	public Context context;
	public HotelImageAdapter(Context context,LinkedList<HotelPicture> list) {
		this.list = list;
		this.context = context;
	}
	public int getCount() {
		return list.size();
	}
	
	public Object getItem(int position) {
		return position;
	}
		
	public long getItemId(int position) {
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(context);
		i.setMaxWidth(200);
		i.setMaxHeight(150);
		i.setLayoutParams(new Gallery.LayoutParams(200, 150));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		list.get(position).loadAsync(i);
		return i;
	}
}