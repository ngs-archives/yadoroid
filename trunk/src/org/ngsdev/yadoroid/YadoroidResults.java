package org.ngsdev.yadoroid;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import net.jalan.jws.search.Hotel;
import net.jalan.jws.search.HotelSearch;
import org.ngsdev.yadoroid.Yadoroid;

public class YadoroidResults extends AbstractYadoroid {
	private HotelSearch hotelSearch;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hotelSearch = new HotelSearch(true);
		final HashMap params = new HashMap();
		showProgress(new Runnable(){
			public void run() {
				hotelSearch.request(params);
				setHotels();
			}
		},R.string.progress_hotelapi);
	}
	private void setHotels() {
		log("hotelLoaded");
	}
	private void openDetail() {
		
	}
}
