package org.ngsdev.yadoroid;
import java.util.HashMap;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import net.jalan.jws.search.Area;
import net.jalan.jws.search.AreaSearch;
import net.jalan.jws.search.APIRequest;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;

public class Yadoroid extends Activity {
	private static final String TAG = "Yadoroid";
	public static final String APIKEY = "leo11111317351";
	public static final int MODE_TOP = 0;
	public static final int MODE_REGION = 1;
	public static final int MODE_PREFECTURE = 2;
	public static final int MODE_LAREA = 3;
	public static final int MODE_SAREA = 4;
	private static AreaSearch areaSearch;
	private HotelSearch hotelSearch;
	public ListView list;
	public static int mode;
	private Area activeArea;
	private ProgressDialog progressDialog;
	private static Area currentArea;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log("onCreate");
		setContentView(R.layout.main);
		list = (ListView) findViewById(R.id.list_view);
		if(currentArea!=null) {
			log("onCreate"+currentArea.name);
			activeArea = currentArea;
			if(mode==MODE_SAREA) showHotels();
			else showList();
		} else {
			init();
		}
	}
	public void onResume() {
		super.onResume();
		if(activeArea!=null) {
			currentArea = activeArea;
			setTitle(currentArea.type.equals(Area.AREA)?getString(R.string.app_name):currentArea.name);
		}
		log("onResume");
	}
	private void init() {
		APIRequest.apiKey = APIKEY;
		areaSearch = new AreaSearch();
		if(areaSearch.regions!=null) {
			showList();
			return;
		}
		showProgress(new Runnable(){
			public void run() {
				areaSearch = new AreaSearch();
				areaSearch.init();
				currentArea = areaSearch.regions;
				activeArea = currentArea;
				showList();
				hideProgress();
			}
		}, R.string.progress_areaapi);
	}
	public void setList() {
		setList(areaSearch.regions);
	}
	public void setList(Area area) {
		currentArea = area;
		if(area.type.equals(Area.AREA)) {
			mode = MODE_TOP;
		} else if(area.type.equals(Area.REGION)) {
			mode = MODE_REGION;
		} else if(area.type.equals(Area.PREFECTURE)) {
			mode = MODE_PREFECTURE;
		} else if(area.type.equals(Area.LAREA)) {
			mode = MODE_LAREA;
		} else if(area.type.equals(Area.SAREA)) {
			mode = MODE_SAREA;
		}
		log(Integer.toString(mode));
		Intent intent = new Intent(Yadoroid.this,org.ngsdev.yadoroid.Yadoroid.class);
		intent.setAction(Intent.ACTION_VIEW);
		startActivity(intent);
	}
	private void setHotels() {
		hideList();
		hideProgress();
		log("hotelLoaded");
	}
	private void showHotels() {
		HashMap param = new HashMap();
		param.put("s_area",currentArea.code);
		hotelSearch = new HotelSearch(true);
		showProgress(new Runnable(){
			public void run() {
				hotelSearch.request();
				setHotels();
			}
		},R.string.progress_hotelapi);
		log(currentArea.name);
	}
	private void showList() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			this, android.R.layout.simple_list_item_1, currentArea.getNames()
		);
		list.setAdapter(adapter);
		final Area a = currentArea;
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				setList(a.item(a.getCode(position)));
			}
		});
		list.setVisibility(View.VISIBLE);
	}
	private void hideList() { list.setVisibility(View.GONE); }
	public void showProgress(final Runnable r,int msgid) {
		final Handler h = new Handler();
		final Thread t = new Thread(new Runnable(){
			public void run() {
				h.postDelayed(r,1000);
			}
		});
		t.start();
		progressDialog = ProgressDialog.show(Yadoroid.this,getString(R.string.progress_connect),getString(msgid));
		progressDialog.setIndeterminate(false);
	}
	public void hideProgress() {
		if(progressDialog==null) return;
		progressDialog.dismiss();
		progressDialog = null;
	}
	static public void log(String text) {
		Log.v(TAG,text);
	}
}
