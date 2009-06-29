package org.ngsdev.yadoroid;
import java.util.HashMap;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.view.View;
import android.view.ViewGroup;
import net.jalan.jws.search.Area;
import net.jalan.jws.search.AreaSearch;
import net.jalan.jws.search.APIRequest;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;

public class Yadoroid extends AbstractYadoroid {
	public static final String APIKEY = "leo11111317351";
	public static final int MODE_TOP = 0;
	public static final int MODE_REGION = 1;
	public static final int MODE_PREFECTURE = 2;
	public static final int MODE_LAREA = 3;
	public static final int MODE_SAREA = 4;
	private static AreaSearch areaSearch;
	public ListView list;
	public static int mode;
	private Area activeArea;
	private static Area currentArea;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.areas);
		list = (ListView) findViewById(R.id.list_view);
		if(currentArea!=null) {
			log("onCreate"+currentArea.name);
			activeArea = currentArea;
			showList();
		} else {
			init();
		}
	}
	public void onResume() {
		super.onResume();
		if(activeArea!=null) {
			currentArea = activeArea;
			setTitle((!currentArea.type.equals(Area.AREA)?currentArea.name+" | ":"")+getString(R.string.select_area));
		}
	}
	public void init() {
		APIRequest.apiKey = APIKEY;
		areaSearch = new AreaSearch();
		if(areaSearch.regions!=null) {
			showList();
			return;
		}
		showProgress(new Runnable(){
			public void run() {
				try {
					areaSearch = new AreaSearch();
					areaSearch.init();
				} catch(Exception e) {
					alert(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int whichButton) {
							init();
						}
					},R.string.alert_connection,R.string.alert_message,R.string.retry);
					e.printStackTrace();
					return;
				}
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
		final Intent intent;
		final Bundle extras = new Bundle();
		if(area.type.equals(Area.SAREA)) {
			intent = new Intent(Yadoroid.this,org.ngsdev.yadoroid.YadoroidResults.class);
			extras.putString(EXTRA_KEY_SAREA_CODE,area.code);
			extras.putString(EXTRA_KEY_SAREA_NAME,area.name);
			intent.putExtras(extras);
		} else {
			intent = new Intent(Yadoroid.this,org.ngsdev.yadoroid.Yadoroid.class);
			currentArea = area;
			mode = area.type.equals(Area.REGION) ? MODE_REGION :
				area.type.equals(Area.PREFECTURE) ? MODE_PREFECTURE :
					area.type.equals(Area.LAREA) ? MODE_LAREA : MODE_TOP;
		}
		log(area.type);
		intent.setAction(Intent.ACTION_VIEW);
		startActivity(intent);
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
}
