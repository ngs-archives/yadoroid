package org.ngsdev.yadoroid;
import java.util.LinkedList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Gallery;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.net.Uri;
import android.os.Handler;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
import net.jalan.jws.search.hotel.AccessInformation;
import net.jalan.jws.search.hotel.HotelSearchOptions;
import net.jalan.jws.search.hotel.HotelXMLPattern;
import net.jalan.jws.search.hotel.PictSize;
import org.ngsdev.yadoroid.Yadoroid;
import org.ngsdev.yadoroid.HotelImageAdapter;

public class YadoroidDetail extends AbstractYadoroid {
	private HotelSearch hotelSearch;
	public Hotel hotel;
	public HotelSearchOptions searchOptions;
	public String hotelID;
	public TextView hotelName;
	public String hotelNameString;
	public TextView minPrice;
	public TextView catchCopy;
	public Gallery hotelImageGallery;
	public ImageView hotelImage;
	public RatingBar ratingBar;
	public TableLayout detailTable;
	public Button reserveButton;
	public Button mapButton;
	private static final String BR = "<BR>";
	private static final String LF = "\n";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_detail);
		Bundle extras = getIntent().getExtras();
		hotelID = extras==null?"":extras.getString(EXTRA_KEY_HOTEL_ID);
		String name = extras==null?"":extras.getString(EXTRA_KEY_HOTEL_NAME);
		hotelNameString = name;
		if(name!="") {
			setTitle(name+" | "+getString(R.string.hotel_detail));
		}
		searchOptions = new HotelSearchOptions();
		searchOptions.hotelID = hotelID;
		searchOptions.pictSize = PictSize.M;
		searchOptions.picts = 5;
		searchOptions.xmlPattern = HotelXMLPattern.HAS_PRICE;
		hotelSearch = new HotelSearch(true);
		hotelName = (TextView) findViewById(R.id.detail_hotel_name);
		hotelImage = (ImageView) findViewById(R.id.detail_hotel_image);
		hotelImageGallery = (Gallery) findViewById(R.id.detail_hotel_image_gallery);
		detailTable = (TableLayout) findViewById(R.id.detail_table);
		ratingBar = (RatingBar) findViewById(R.id.detail_rating);
		reserveButton = (Button) findViewById(R.id.go_reserve_button);
		mapButton = (Button) findViewById(R.id.map_button);
		setText(hotelName,name);
		init(R.string.progress_hoteldetail);
	}
	public void onResume() {
		super.onResume();
		this.trackPageView("/detail/"+hotelNameString+"/");
	}
	public void doRequest() throws Exception {
		try {
			hotelSearch.request(searchOptions);
		} catch(Exception e) {
			throw e;
		}
	}
	public void onRequestComplete() {
		hotel = hotelSearch.item(0);
		if(hotel==null) {
			showRetryDialog();
			return;
		}
		setText((TextView) findViewById(R.id.detail_catch_copy),hotel.catchCopy);
		setText((TextView) findViewById(R.id.detail_caption),hotel.caption);
		setText((TextView) findViewById(R.id.detail_onsen),hotel.onsen);
		setText((TextView) findViewById(R.id.detail_rating_point),Float.toString(hotel.rate));
		ratingBar.setRating(hotel.rate);
		int rate = hotel.sampleRateFrom;
		if(rate>0) addRow(R.string.sample_rate_from,String.format(getString(R.string.min_price),Integer.toString(rate)));
		addRow(R.string.address,getString(R.string.postal_mark)+hotel.postCode+LF+hotel.address);
		addRow(R.string.creditcard,hotel.creditCard.summary.replace(",",", "));
		addRow(R.string.checkin_time,hotel.checkInTime);
		addRow(R.string.checkout_time,hotel.checkOutTime);
		StringBuilder sb = new StringBuilder("");
		LinkedList<AccessInformation> access = hotel.access;
		int len = access.size();
		for(int i=0;i<len;++i) {
			if(i>0) sb.append(LF+LF);
			AccessInformation a = access.get(i);
			sb.append(a.name+": "+a.text);
		}
		addRow(R.string.access,sb.toString());
		
		reserveButton.setText(String.format(getString(R.string.go_reserve),hotel.type==""?getString(R.string.yado):hotel.type));
		reserveButton.setVisibility(View.VISIBLE);
		reserveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				goReserve();
			}
		});
		
		
		mapButton.setVisibility(View.VISIBLE);
		mapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showMap();
			}
		});
		initGallery();
	}
	public void showPicture(int position) {
		log(Integer.toString(position));
	}
	private void initGallery() {
		final Handler h = new Handler();
		final Thread t = new Thread(new Runnable(){
			public void run() {
				h.post(new Runnable(){
					public void run() {
						hotelImageGallery.setAdapter(new HotelImageAdapter(YadoroidDetail.this,hotel.pictures));
						hotelImageGallery.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView parent, View v, int position, long id) {
								showPicture(position);
							}
						});
					}
				});
			}
		});
		t.start();
	}
	private void setText(TextView v,String t) {
		if(t!="")
			v.setText(t.replace(BR,LF));
		else
			v.setVisibility(View.GONE);
	}
	private void addRow(int strid,String value) {
		if(value=="") return;
		final View row = View.inflate(YadoroidDetail.this,R.layout.detail_row,null);
		setText((TextView) row.findViewById(R.id.detail_th),getString(strid));
		setText((TextView) row.findViewById(R.id.detail_td),value);
		detailTable.addView(row);
	}
	public void goReserve() {
		try {
    		this.trackEvent("Detail","goReserve");
			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(hotel.url.toString()));
			startActivity(intent);
		} catch(Exception e) {}
	}
	public void showMap() {
		try {
    		this.trackEvent("Detail","showMap");
    		final Intent intent = new Intent(YadoroidDetail.this,org.ngsdev.yadoroid.YadoroidMap.class);
    		final Bundle extras = new Bundle();
    		extras.putDouble(EXTRA_KEY_HOTEL_LAT,hotel.latlng.lat);
    		extras.putDouble(EXTRA_KEY_HOTEL_LNG,hotel.latlng.lng);
    		extras.putString(EXTRA_KEY_HOTEL_NAME,hotel.name);
    		intent.putExtras(extras);
    		intent.setAction(Intent.ACTION_VIEW);
    		startActivity(intent);
		} catch(Exception e) {}
	}
}
