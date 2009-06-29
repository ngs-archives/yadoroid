package org.ngsdev.yadoroid;
import android.content.Context;
import android.widget.SimpleAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.net.Uri;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
public class HotelListAdapter extends SimpleAdapter {
	public HotelSearch hotelSearch;
	public Context context;
	public TextView hotelName;
	public ImageView hotelImage;
	public HotelListAdapter(Context context,HotelSearch hotelSearch) {
		//super(context,hotelSearch.hotelList,R.layout.hotel_item,new String[] { "name", ""})
		this.hotelSearch = hotelSearch;
		this.context = context;
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

