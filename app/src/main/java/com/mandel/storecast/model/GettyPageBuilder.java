package com.mandel.storecast.model;


import android.content.Context;

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
import android.util.Log;


public class GettyPageBuilder implements ImagesModel.PageBuilder {

	private final String API_KEY = "4x3mqfykgft2uj2zynnw4b9w";
	private final String GETTY_URI = "https://api.gettyimages.com/v3/search/images";
	

	private Context mContext = null;
	
	public GettyPageBuilder(Context context) {
		this.mContext = context;	
	}
	
	public void execute(ImagesModel.PageBuilderListener listener, int pageIdx, int itemsPerPage, String searchWord) {
		new ConnectionJob(mContext, listener, pageIdx, itemsPerPage, searchWord).execute();
	}
	
	public class ConnectionJob  {
		
		private Context mContext = null;
		private int mPage;
		private int mItemsPerPage;
		private String mSearchWord;
		private ImagesModel.PageBuilderListener mListener;
		
		public ConnectionJob(Context context, ImagesModel.PageBuilderListener listener,
				     int page, int itemsPerPage, String searchWord) {
			mContext = context;	
			mPage = page;
			mItemsPerPage = itemsPerPage;
			mListener = listener;
			mSearchWord = searchWord;
		}
		
		public void execute() {
		
		// TODO Auto-generated method stub  
		String searchString = "What you want to search";  
		final HashMap<String, String> header = new HashMap<String, String>();  
		header.put("Api-Key", API_KEY);  
		String getImageUrl = null;  
		getImageUrl = GETTY_URI +
			"?page=" + (mPage+1)  +
			"&page_size=" + mItemsPerPage +
			"&phrase=" + mSearchWord;  

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
							mListener.onError();
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
			mListener.onDone(response, mPage);
		} 
	}
}
