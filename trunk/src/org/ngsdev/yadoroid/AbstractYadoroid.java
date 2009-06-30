package org.ngsdev.yadoroid;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
abstract public class AbstractYadoroid extends Activity {
	private static final String TAG = "Yadoroid";
	public static final String EXTRA_KEY_SAREA_CODE = "sarea.code";
	public static final String EXTRA_KEY_SAREA_NAME = "sarea.name";
	public static final String EXTRA_KEY_START = "start";
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;
	public void onCreate(Bundle savedInstanceState) {
		log("onCreate");
		super.onCreate(savedInstanceState);
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
		ad.setTitle(getString(title));
		ad.setMessage(getString(message));
		ad.setPositiveButton(getString(oklabel),listener);
		alertDialog = ad.create();
		ad.show();
	}

	public static void log(String text) {
		Log.v(TAG,text);
	}

}
