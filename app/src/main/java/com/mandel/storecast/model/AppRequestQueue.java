package com.mandel.storecast.model;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import android.graphics.Bitmap;
import android.content.Context;

public class AppRequestQueue {
	private static AppRequestQueue mInstance;
	private RequestQueue mRequestQueue;
	private static Context mCtx;
	
	private AppRequestQueue(Context context) {
		mCtx = context;
		mRequestQueue = getRequestQueue();
	}
	
	public static synchronized AppRequestQueue getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new AppRequestQueue(context);
		}
		return mInstance;
	}
	
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	}
	
	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}
}
