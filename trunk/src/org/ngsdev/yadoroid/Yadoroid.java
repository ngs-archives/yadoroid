package org.ngsdev.yadoroid;
import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import net.jalan.jws.search.Area;
import net.jalan.jws.search.AreaSearch;
import net.jalan.jws.search.APIRequest;

public class Yadoroid extends Activity {
	private static final String TAG = "MyActivity";
	public static final String APIKEY = "leo11111317351";
	public static final int MODE_REGION = 1;
	public static final int MODE_PREFECTURE = 2;
	public static final int MODE_LAREA = 3;
	public static final int MODE_SAREA = 4;
	private AreaSearch areaSearch;
	public ListView list;
	public LinearLayout navigation;
	public int mode;
	private Area currentArea;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		APIRequest.apiKey = APIKEY;
		areaSearch = new AreaSearch();
		areaSearch.init();
		list = (ListView) findViewById(R.id.list_view);
		navigation = (LinearLayout) findViewById(R.id.navigation);
		setList();
	}
	public void setList() {
		mode = MODE_REGION;
		currentArea = areaSearch.regions;
		setArray();
		hideNavigation();
		showList();
	}
	public void setList(int index) {
		mode = ++mode;
		String cd = currentArea.getCode(index);
		Log.v(TAG,cd);
		currentArea = currentArea.item(cd);
		showNavigation();
		showList();
		if(mode==MODE_SAREA) showHotels();
		else setArray();
	}
	private void showHotels() {
		Log.v(TAG,currentArea.name);
		hideList();
	}
	private ArrayAdapter<String> setArray() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			this, android.R.layout.simple_list_item_1, currentArea.getNames()
		);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				setList(position);
			}
		});
		return adapter;
	}

	private void showNavigation() { navigation.setVisibility(View.VISIBLE); }
	private void hideNavigation() { navigation.setVisibility(View.GONE); }
	private void showList() { list.setVisibility(View.VISIBLE); }
	private void hideList() { list.setVisibility(View.GONE); }
}
