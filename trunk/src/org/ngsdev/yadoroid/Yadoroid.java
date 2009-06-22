package org.ngsdev.yadoroid;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import net.jalan.jws.search.AreaSearch;
import net.jalan.jws.search.APIRequest;

public class Yadoroid extends Activity {
	private static final String TAG = "MyActivity";
	public static final String APIKEY = "leo11111317351";
	private AreaSearch areaSearch;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		APIRequest.apiKey = APIKEY;
		areaSearch = new AreaSearch();
		areaSearch.init();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			this, android.R.layout.simple_list_item_1,
			areaSearch.regions.getNames()
		);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(adapter);
	}
}
