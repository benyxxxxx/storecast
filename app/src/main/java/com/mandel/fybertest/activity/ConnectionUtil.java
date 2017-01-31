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

public class ConnectionUtil {

	private Context mContext = null;
	
	public ConnectionUtil(Context context) {
		this.mContext = context;	
	}
	
	public void execute(Handler handler) {
		
		// TODO Auto-generated method stub  
		String searchString = "What you want to search";  
		final HashMap<String, String> header = new HashMap<String, String>();  
		header.put("Api-Key", "4x3mqfykgft2uj2zynnw4b9w");  
		String getImageUrl = null;  
		getImageUrl =
			"https://api.gettyimages.com/v3/search/images?page=1&page_size=20&phrase=mobiles";  
		
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
		
	}  
		
		
	//@Override
	//protected void onPostExecute(Message msg) {
	//mHandler.sendMessage(msg);
	//}
}


	
    
