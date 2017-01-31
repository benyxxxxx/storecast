package com.mandel.fybertest.activity;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.RequestQueue;
//import com.android.volley.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import android.util.LruCache;
import android.graphics.Bitmap;
import android.content.Context;

public class AppRequestQueue {
	private static AppRequestQueue mInstance;
	private RequestQueue mRequestQueue;
	//private ImageLoader mImageLoader;
	private static Context mCtx;
	
	private AppRequestQueue(Context context) {
		mCtx = context;
		mRequestQueue = getRequestQueue();
		
	/*
	  mImageLoader = new ImageLoader(mRequestQueue,
	  new ImageLoader.ImageCache() {
	  private final LruCache<String, Bitmap>
					       cache = new LruCache<String, Bitmap>(20);
					       
					       @Override
					       public Bitmap getBitmap(String url) {
                return cache.get(url);
		}
		
		@Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
	    }); */
    }

	public static synchronized AppRequestQueue getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new AppRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
	    if (mRequestQueue == null) {
		    // getApplicationContext() is key, it keeps you from leaking the
		    // Activity or BroadcastReceiver if someone passes one in.
		    mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
	    }
	    return mRequestQueue;
    }
	
	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}

	/*
    public ImageLoader getImageLoader() {
        return mImageLoader;
	} */
}
