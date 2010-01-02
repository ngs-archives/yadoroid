package org.ngsdev.yadoroid;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import net.jalan.jws.search.Area;
import net.jalan.jws.search.APIRequest;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
abstract public class AbstractYadoroid extends Activity {
	public static GoogleAnalyticsTracker tracker = null;
	public static final String APIKEY = "leo11111317351";
	public static final String GAID = "UA-200187-17";
	private static final String TAG = "Yadoroid";
	public static final String EXTRA_KEY_SAREA_CODE = "sarea.code";
	public static final String EXTRA_KEY_SAREA_NAME = "sarea.name";
	public static final String EXTRA_KEY_HOTEL_ID	 = "hotel.code";
	public static final String EXTRA_KEY_HOTEL_NAME = "hotel.name";
	public static final String EXTRA_KEY_HOTEL_LAT	 = "hotel.lat";
	public static final String EXTRA_KEY_HOTEL_LNG	 = "hotel.lng";
	public static final String EXTRA_KEY_START = "start";
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;
	private int progressMessage;
	public String trackingPath = "";
	public void onCreate(Bundle savedInstanceState) {
		log("onCreate");
		APIRequest.apiKey = APIKEY;
		super.onCreate(savedInstanceState);
		if(tracker==null) {
			tracker = GoogleAnalyticsTracker.getInstance();
			tracker.start(GAID, this);
		}
	}
	public void onResume() {
		log("onResume");
		super.onResume();
	}
	public void showProgress(final Runnable r,int msgid) {
		final Handler h = new Handler();
		final Thread t = new Thread(new Runnable(){
			public void run() {
				h.postDelayed(r,1000);
			}
		});
		t.start();
		progressDialog = ProgressDialog.show(this,getString(R.string.progress_connect),getString(msgid));
		progressDialog.setIndeterminate(false);
	}
	public void hideProgress() {
		if(progressDialog==null) return;
		progressDialog.dismiss();
		progressDialog = null;
	}
	public void alert(DialogInterface.OnClickListener listener,int title,int message,int oklabel) {
		final AlertDialog.Builder ad = new AlertDialog.Builder(this);
		final AbstractYadoroid context = this;
		ad.setTitle(getString(title));
		ad.setMessage(getString(message));
		ad.setPositiveButton(getString(oklabel),listener);
		ad.setNegativeButton(getString(R.string.cancel),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				context.finish();
			}
		});
		alertDialog = ad.create();
		ad.show();
	}

	public static void log(String text) {
		Log.v(TAG,text);
	}

	public void trackEvent(String category,String event) {
		log(event);
		tracker.trackEvent(category,event,trackingPath,1);
		tracker.dispatch();
	}

	public void trackPageView(String path) {
		trackingPath = trackingPath == "" ? path : trackingPath;
		log(trackingPath);
		tracker.trackPageView(trackingPath);
		tracker.dispatch();
	}
	
	public Boolean isNetworkAvailable() {
		ConnectivityManager m = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = m.getActiveNetworkInfo();
		return ni==null?false:ni.isAvailable();
	}
	public void showRetryDialog() {
		showRetryDialog(R.string.alert_message);
	}
	public void showRetryDialog(int msg) {
		alert(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				init();
			}
		},R.string.alert_connection,msg,R.string.retry);
	}
	public void init() {
		init(progressMessage);
	}
	public void init(final int msg) {
		progressMessage = msg>0?msg:progressMessage;
		showProgress(new Runnable(){
			public void run() {
				try {
					try {
						doRequest();
					} catch(Exception e) {
						hideProgress();
						showRetryDialog();
						e.printStackTrace();
						return;
					}
					onRequestComplete();
					hideProgress();
				} catch(Exception e) {
					showRetryDialog();
					e.printStackTrace();
					return;
				}
			}
		},progressMessage);
	}
	public void openResults(Area area) {
		openResults(area.name,area.code,1);
	}
	public void openResults(String name,String code) {
		openResults(name,code,1);
	}
	public void openResults(String name,String code,int start) {
		log(Integer.toString(start));
		final Bundle extras = new Bundle();
		final Intent intent;
		intent = new Intent(AbstractYadoroid.this,org.ngsdev.yadoroid.YadoroidResults.class);
		extras.putString(EXTRA_KEY_SAREA_CODE,code);
		extras.putString(EXTRA_KEY_SAREA_NAME,name);
		extras.putInt(EXTRA_KEY_START,start);
		intent.putExtras(extras);
		startActivity(intent);
	}
	abstract public void doRequest() throws Exception;
	abstract public void onRequestComplete();
}
