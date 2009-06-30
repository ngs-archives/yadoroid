package org.ngsdev.yadoroid;
import java.util.HashMap;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
import net.jalan.jws.search.hotel.HotelSearchOptions;
import net.jalan.jws.search.hotel.HotelXMLPattern;
import net.jalan.jws.search.hotel.PictSize;
import org.ngsdev.yadoroid.Yadoroid;

public class YadoroidDetail extends AbstractYadoroid {
	private HotelSearch hotelSearch;
	public String hotelID;
	public String hotelName;
	public Hotel hotel;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_detail);
		Bundle extras = getIntent().getExtras();
		hotelID = extras==null?"":extras.getString(EXTRA_KEY_HOTEL_ID);
		hotelName = extras==null?"":extras.getString(EXTRA_KEY_HOTEL_NAME);
		init();
	}
	public void init() {
		hotelSearch = new HotelSearch(true);
		final HotelSearchOptions options = new HotelSearchOptions();
		options.hotelID = hotelID;
		options.pictSize = PictSize.M;
		options.xmlPattern = HotelXMLPattern.HAS_PRICE;
		final YadoroidDetail context = this;
		showProgress(new Runnable(){
			public void run() {
				try {
					hotelSearch.request(options);
					showDetail();
					hideProgress();
				} catch(Exception e) {
					alert(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int whichButton) {
							init();
						}
					},R.string.alert_connection,R.string.alert_message,R.string.retry);
					e.printStackTrace();
					return;
				}
			}
		},R.string.progress_hoteldetail);
	}
	public void showDetail() {
		hotel = hotelSearch.item(0);
	}
}
