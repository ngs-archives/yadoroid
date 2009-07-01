package org.ngsdev.yadoroid;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.net.Uri;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
import net.jalan.jws.search.hotel.HotelSearchOptions;
import net.jalan.jws.search.hotel.HotelXMLPattern;
import net.jalan.jws.search.hotel.PictSize;
import org.ngsdev.yadoroid.Yadoroid;

public class YadoroidDetail extends AbstractYadoroid {
	private HotelSearch hotelSearch;
	public Hotel hotel;
	public HotelSearchOptions searchOptions;
	public String hotelID;
	public TextView hotelName;
	public TextView minPrice;
	public TextView catchCopy;
	public ImageView hotelImage;
	public RatingBar ratingBar;
	public TableLayout detailTable;
	public Button reserveButton;
	private static final String BR = "<BR>";
	private static final String LF = "\n";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_detail);
		Bundle extras = getIntent().getExtras();
		hotelID = extras==null?"":extras.getString(EXTRA_KEY_HOTEL_ID);
		String name = extras==null?"":extras.getString(EXTRA_KEY_HOTEL_NAME);
		if(name!="") {
			setTitle(name+" | "+getString(R.string.hotel_detail));
		}
		searchOptions = new HotelSearchOptions();
		searchOptions.hotelID = hotelID;
		searchOptions.pictSize = PictSize.M;
		searchOptions.xmlPattern = HotelXMLPattern.HAS_PRICE;
		hotelSearch = new HotelSearch(true);
		hotelName = (TextView) findViewById(R.id.detail_hotel_name);
		hotelImage = (ImageView) findViewById(R.id.detail_hotel_image);
		detailTable = (TableLayout) findViewById(R.id.detail_table);
		ratingBar = (RatingBar) findViewById(R.id.detail_rating);
		reserveButton = (Button) findViewById(R.id.go_reserve_button);
		setText(hotelName,name);
		init(R.string.progress_hoteldetail);
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
		//
		if(hotel.pictures.size()>0)
			hotel.pictures.get(0).load(hotelImage);
		else
			hotelImage.setVisibility(View.GONE);
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
		reserveButton.setText(String.format(getString(R.string.go_reserve),hotel.type==""?getString(R.string.yado):hotel.type));
		reserveButton.setVisibility(View.VISIBLE);
		reserveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				goReserve();
			}
		});
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
			Intent intent = new Intent("android.intent.action.VIEW",Uri.parse(hotel.url.toString()));
			startActivity(intent);		
		} catch(Exception e) {}
	}
}
