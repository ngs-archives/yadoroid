package org.ngsdev.yadoroid;
import java.util.LinkedList;
import java.net.URL;
import org.ngsdev.yadoroid.Yadoroid;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.net.Uri;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
import net.jalan.jws.search.hotel.AccessInformation;
import net.jalan.jws.search.hotel.HotelPicture;
public class HotelListAdapter extends BaseAdapter {
	public HotelSearch hotelSearch;
	public Context context;
	public TextView hotelName;
	public TextView minPrice;
	public TextView catchCopy;
	public ImageView hotelImage;
	public HotelListAdapter(Context context,HotelSearch hotelSearch) {
		super();
		this.hotelSearch = hotelSearch;
		this.context = context;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		final View v = View.inflate(context,R.layout.hotel_item,null);
		final Hotel h = getItem(position);
		final YadoroidResults results = (YadoroidResults) context;
		hotelName = (TextView) v.findViewById(R.id.hotel_name);
		hotelImage = (ImageView) v.findViewById(R.id.hotel_image);
		catchCopy = (TextView) v.findViewById(R.id.catch_copy);
		minPrice = (TextView) v.findViewById(R.id.min_price);
		//
		hotelName.setText(h.name);
		int rate = h.sampleRateFrom;
		StringBuilder sb = new StringBuilder("");
		if(h.type!="") {
			sb.append(h.type);
		}
		if(rate>0) {
			if(sb.length()>0) sb.append("/");
			sb.append(
				context.getString(R.string.sample_rate_from)+": "+
				String.format(context.getString(R.string.min_price),Integer.toString(rate))
			);
		}
		if(sb.length()>0) 
			minPrice.setText(sb.toString());
		else
			minPrice.setVisibility(View.GONE);
		String ccp = h.catchCopy;
		if(ccp!="")
			catchCopy.setText(ccp);
		else
			catchCopy.setVisibility(View.GONE);
		if(h.pictures.size()>0) h.pictures.get(0).load(hotelImage);
		v.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				results.openDetail(h.id);
			}
		});
		return v;
	}
	public Hotel getItem(int position) {
		return hotelSearch.item(position);
	}
	public long getItemId(int position) {
		return new Long(getItem(position).id);
	}
	public int getCount() {
		return hotelSearch.size();
	}

}

