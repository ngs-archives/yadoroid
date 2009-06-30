package org.ngsdev.yadoroid;
import android.content.Context;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.net.Uri;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
public class HotelListAdapter extends BaseAdapter {
	public HotelSearch hotelSearch;
	public Context context;
	public TextView hotelName;
	public ImageView hotelImage;
	public HotelListAdapter(Context context,HotelSearch hotelSearch) {
		this.hotelSearch = hotelSearch;
		this.context = context;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv = new TextView(context);
		tv.setText(getItem(position).name);
		return tv;
	}
	public Hotel getItem(int position) {
		return hotelSearch.item(position);
	}
	public long getItemId(int position) {
		return new Long(getItem(position).id);
	}
	public int getCount() {
		return hotelSearch.getLength();
	}

}

