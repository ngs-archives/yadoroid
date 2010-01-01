package org.ngsdev.yadoroid;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.view.ViewGroup;
import net.jalan.jws.search.Area;
import net.jalan.jws.search.AreaSearch;
import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;

public class Yadoroid extends AbstractYadoroid {
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
		trackPageView("/");
		if(currentArea!=null) {
			log("onCreate"+currentArea.name);
			activeArea = currentArea;
			showList();
		} else {
			if(areaSearch.regions!=null) {
				showList();
			} else {
				init(R.string.progress_areaapi);
			}
		}
	}
	public void onResume() {
		super.onResume();
		trackPageView("/");
		if(activeArea!=null) {
			currentArea = activeArea;
			setTitle((!currentArea.type.equals(Area.AREA)?currentArea.name+" | ":"")+getString(R.string.select_area));
		}
	}
	public void doRequest() throws Exception {
		try {
			areaSearch = new AreaSearch();
			areaSearch.init();
		} catch(Exception e) {
			throw e;
		}
	}
	public void onRequestComplete() {
		currentArea = areaSearch.regions;
		activeArea = currentArea;
		showList();
	}
	public void setList() {
		setList(areaSearch.regions);
	}
	public void setList(Area area) {
		final Intent intent;
		if(area.type.equals(Area.SAREA)) {
			openResults(area);
		} else {
			intent = new Intent(Yadoroid.this,org.ngsdev.yadoroid.Yadoroid.class);
			currentArea = area;
			mode = area.type.equals(Area.REGION) ? MODE_REGION :
				area.type.equals(Area.PREFECTURE) ? MODE_PREFECTURE :
					area.type.equals(Area.LAREA) ? MODE_LAREA : MODE_TOP;
			intent.setAction(Intent.ACTION_VIEW);
			startActivity(intent);
		}
	}
	private void showList() {
		try {
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
		} catch(Exception e) {
			showRetryDialog();
		}
	}
	private void hideList() { list.setVisibility(View.GONE); }
}
