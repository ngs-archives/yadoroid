package org.ngsdev.yadoroid;
import java.util.HashMap;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
import net.jalan.jws.search.hotel.HotelSearchOptions;
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
		options.start = start;
		final HashMap params = options.getHashMap();
		showProgress(new Runnable(){
			public void run() {
				try {
					hotelSearch.request(params);
				} catch(Exception e) {
					alert(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int whichButton) {
							init();
						}
					},R.string.alert_connection,R.string.alert_message,R.string.retry);
					e.printStackTrace();
					return;
				}
				setHotels();
			}
		},R.string.progress_hotelapi);
	}
	private void setHotels() {
		final HotelListAdapter adp = new HotelListAdapter(this,hotelSearch);
		list.setAdapter(adp);
		hideProgress();
		log("hotelLoaded");
	}
	private void openDetail() {
		
	}
}
