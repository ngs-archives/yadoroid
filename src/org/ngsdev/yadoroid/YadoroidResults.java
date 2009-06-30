package org.ngsdev.yadoroid;
import java.util.HashMap;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
import net.jalan.jws.search.hotel.HotelSearchOptions;
import net.jalan.jws.search.hotel.HotelXMLPattern;
import net.jalan.jws.search.hotel.PictSize;
import org.ngsdev.yadoroid.Yadoroid;

public class YadoroidResults extends AbstractYadoroid {
	private HotelSearch hotelSearch;
	public ListView list;
	public String sarea;
	public String areaName;
	public int start = 1;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotels);
		list = (ListView) findViewById(R.id.list_view);
		Bundle extras = getIntent().getExtras();
		sarea = extras==null?"":extras.getString(EXTRA_KEY_SAREA_CODE);
		areaName = extras==null?"":extras.getString(EXTRA_KEY_SAREA_NAME);
		start = extras==null?1:extras.getInt(EXTRA_KEY_START);
		start = start>1?start:1;
		log(sarea);
		if(areaName!="") {
			setTitle(areaName+" | "+getString(R.string.list));
		}
		init();
	}
	public void init() {
		hotelSearch = new HotelSearch(true);
		final HotelSearchOptions options = new HotelSearchOptions();
		options.smallArea = sarea;
		options.pictSize = PictSize.SS;
		options.start = start;
		options.xmlPattern = HotelXMLPattern.HAS_PRICE;
		final YadoroidResults context = this;
		showProgress(new Runnable(){
			public void run() {
				try {
					hotelSearch.request(options);
					final HotelListAdapter adp = new HotelListAdapter(context,hotelSearch);
					list.setAdapter(adp);
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
		},R.string.progress_hotelapi);
	}
	public void openDetail(String id) {
		final Hotel hotel = hotelSearch.getHotelById(id);
		final Intent intent = new Intent(YadoroidResults.this,org.ngsdev.yadoroid.YadoroidDetail.class);
		final Bundle extras = new Bundle();
		extras.putString(EXTRA_KEY_HOTEL_ID  ,hotel.id);
		extras.putString(EXTRA_KEY_HOTEL_NAME,hotel.name);
		intent.putExtras(extras);
		intent.setAction(Intent.ACTION_VIEW);
		startActivity(intent);
	}
}
