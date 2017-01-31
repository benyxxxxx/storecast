package com.mandel.fybertest.activity;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.NetworkResponse;
import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import android.util.Log;

public class ConnectionJob {

	private final String API_KEY = "4x3mqfykgft2uj2zynnw4b9w";

	private Context mContext = null;
	private int mPage;
	private int mItemsPerPage;
	private String mSearchWord;
	private JobIsDoneListener mListener;
	
	public ConnectionJob(Context context, int page, int itemsPerPage, String searchWord,
			     JobIsDoneListener listener) {
		this.mContext = context;	
		mPage = page;
		mItemsPerPage = itemsPerPage;
		mListener = listener;
		mSearchWord = searchWord;
	}

	public static interface JobIsDoneListener {
		void onDone(JSONObject response, int page);
	}
	
	public void execute() {
		
		// TODO Auto-generated method stub  
		String searchString = "What you want to search";  
		final HashMap<String, String> header = new HashMap<String, String>();  
		header.put("Api-Key", API_KEY);  
		String getImageUrl = null;  
		getImageUrl =
			"https://api.gettyimages.com/v3/search/images?page=" + (mPage+1)  +
			"&page_size=" + mItemsPerPage +
			"&phrase=" + mSearchWord;  
		Log.d("--->getImageUrl", getImageUrl);
		
		JsonObjectRequest getImageData =
			new JsonObjectRequest(getImageUrl, null, new Response.Listener<JSONObject>() {  
					//@SuppressLint("NewApi") @Override  
					public void onResponse(JSONObject response) {  
						try {  
							onResponseGetImages(response);  
						} catch (JSONException e) {  
								// TODO Auto-generated catch block  
							e.printStackTrace();  
						}  
					}  
				}, new Response.ErrorListener() {  
						@Override  
						public void onErrorResponse(VolleyError error) {  
							// TODO Auto-generated method stub  
						}  
					}){  
				@Override  
				public Map<String, String> getHeaders() throws AuthFailureError {  
					return header;  
				}  
			};  
		AppRequestQueue.getInstance(mContext).addToRequestQueue(getImageData);  
	}  
	
	private void onResponseGetImages(JSONObject response)  
		throws JSONException {  
		Log.d("onResponseGetImages", response.toString());
		mListener.onDone(response, mPage);
	}  
		
		
	//@Override
	//protected void onPostExecute(Message msg) {
	//mHandler.sendMessage(msg);
	//}
}


	
    
