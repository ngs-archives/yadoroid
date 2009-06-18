package org.ngsdev.yadoroid;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import net.jalan.jws.search.AreaSearch;

public class Yadoroid extends Activity
{
	private static final String TAG = "MyActivity";
	public static final String APIKEY = "leo11111317351";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        	this, android.R.layout.simple_list_item_1,
        	new String[] { "Hoge", "Foo", "Bar" }
        );
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(adapter);
        
        AreaSearch yadoSearch = new AreaSearch(APIKEY);
        
        
        Log.v(TAG, yadoSearch.apiKey);

    }
}
