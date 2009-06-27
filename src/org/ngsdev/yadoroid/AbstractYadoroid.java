package org.ngsdev.yadoroid;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
abstract public class AbstractYadoroid extends Activity {
	private static final String TAG = "Yadoroid";
	private ProgressDialog progressDialog;
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
	public static void log(String text) {
		Log.v(TAG,text);
	}

}
