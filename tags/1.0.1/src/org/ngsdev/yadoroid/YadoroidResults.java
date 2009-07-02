package org.ngsdev.yadoroid;
import java.util.HashMap;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
	public Button nextButton;
	public Button prevButton;
	public LinearLayout pageNavigation;
	public int start = 1;
	private HotelListAdapter adapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotels);
		list = (ListView) findViewById(R.id.list_view);
		Bundle extras = getIntent().getExtras();
		sarea = extras==null?"":extras.getString(EXTRA_KEY_SAREA_CODE);
		areaName = extras==null?"":extras.getString(EXTRA_KEY_SAREA_NAME);
		start = extras==null?1:extras.getInt(EXTRA_KEY_START);
		start = start>1?start:1;
		log(sarea+"::"+Integer.toString(start));
		if(areaName!="") {
			setTitle(areaName+" | "+getString(R.string.list));
		}
		init(R.string.progress_hotelapi);
	}
	public void doRequest() throws Exception {
		try {
			hotelSearch = new HotelSearch(true);
			final HotelSearchOptions options = new HotelSearchOptions();
			options.smallArea = sarea;
			options.pictSize = PictSize.SS;
			options.start = start;
			options.xmlPattern = HotelXMLPattern.HAS_PRICE;
			hotelSearch.request(options);
		} catch(Exception e) {
			throw e;
		}
	}
	public void onRequestComplete() {
		adapter = new HotelListAdapter(YadoroidResults.this,hotelSearch);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				openDetail(hotelSearch.item(position).id);
			}
		});
		
		nextButton = (Button) findViewById(R.id.next_button);
		prevButton = (Button) findViewById(R.id.prev_button);
		pageNavigation = (LinearLayout) findViewById(R.id.page_navigation);
		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				goNext();
			}
		});
		prevButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				goPrev();
			}
		});
		Boolean p = hotelSearch.prevStart>0;
		Boolean n = hotelSearch.nextStart>0;
		if(p) {
			prevButton.setText(String.format(getString(R.string.prev_button),Integer.toString(hotelSearch.prevCount)));
			prevButton.setVisibility(View.VISIBLE);
		}
		if(n) {
			nextButton.setText(String.format(getString(R.string.next_button),Integer.toString(hotelSearch.nextCount)));
			nextButton.setVisibility(View.VISIBLE);
		}
		if(p||n) pageNavigation.setVisibility(View.VISIBLE);
		log(
			"total::"+Integer.toString(hotelSearch.total)+
			"\ncount::"+Integer.toString(hotelSearch.count)+
			"\nnextCount::"+Integer.toString(hotelSearch.nextCount)+
			"\nprevCount::"+Integer.toString(hotelSearch.prevCount)+
			"\nnextStart::"+Integer.toString(hotelSearch.nextStart)+
			"\nprevStart::"+Integer.toString(hotelSearch.prevStart)
		);
	}
	public void goPrev() {
		openResults(areaName,sarea,hotelSearch.prevStart);
	}
	public void goNext() {
		openResults(areaName,sarea,hotelSearch.nextStart);
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
