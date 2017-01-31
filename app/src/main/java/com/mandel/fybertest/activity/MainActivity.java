package com.mandel.fybertest.activity;

import android.app.Activity;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import android.widget.TextView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AlertDialog;

import android.app.SearchManager;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.Context;

import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import android.widget.AdapterView.OnItemClickListener;

import com.mandel.fybertest.model.ImageItem;
import com.mandel.fybertest.model.ImagesAdapter;
import com.mandel.fybertest.R;
import java.net.HttpURLConnection;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity implements ConnectionJob.JobIsDoneListener {

	public final int IMAGES_PER_PAGE = 30;
	
	private ConnectionUtil mConnectionUtil = null;
	
	ImagesAdapter mAdapter; 
	List<ImageItem> mImages; 
	private String mQuery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SearchView searchView = (SearchView) findViewById(R.id.search);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					//alert("AA", query);

					int size =  mImages.size();
					mImages.clear();
					mAdapter.notifyItemRangeRemoved(0, size);					
					mQuery = query;
					loadNextDataFromApi(0);
					
					return true;
				}
				@Override
				public boolean onQueryTextChange(String s) {
					// UserFeedback.show( "SearchOnQueryTextChanged: " + s);
					return false;
				}
			});


		RecyclerView rvItems = (RecyclerView) findViewById(R.id.listview);

		mImages = new ArrayList<>();
		
		mAdapter = new ImagesAdapter(this/*getApplicationContext()*/);
		mAdapter.setItems(mImages);
		rvItems.setAdapter(mAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		rvItems.setLayoutManager(linearLayoutManager);
		// Retain an instance so that you can call `resetState()` for fresh searches
		EndlessRecyclerViewScrollListener scrollListener =
			new EndlessRecyclerViewScrollListener(linearLayoutManager) {
				@Override
				public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
					// Triggered only when new data needs to be appended to the list
					// Add whatever code is needed to append new items to the bottom of the list
					loadNextDataFromApi(page);
				}
			};

		
      // Adds the scroll listener to RecyclerView
		rvItems.addOnScrollListener(scrollListener);
	}

	Handler handler = new Handler();
	
	public void onDone(JSONObject response, int page) {
		android.util.Log.d("onDone", "loadNextDataFromApi, page is" +page);
		List<ImageItem> newImages = ImagesParser.parseJson(response);
		android.util.Log.d("onDone", "loadNextDataFromApi" + newImages.size());
		for (int i = 0, idx = page*IMAGES_PER_PAGE;  i < newImages.size(); i++, idx++) {
			ImageItem item = mImages.get(idx);
			item.makeItReady(newImages.get(i));
		}
		handler.postDelayed(new Runnable() {
				public void run() {
					android.os.SystemClock.sleep(1); 
					mAdapter.notifyItemRangeChanged(page*IMAGES_PER_PAGE, IMAGES_PER_PAGE);
				}
			}, 5);
	}
	
	public void loadNextDataFromApi(int offset) {
		new ConnectionJob(this, offset, IMAGES_PER_PAGE, mQuery, this).execute();
		
		android.util.Log.d("onScrolled", "loadNextDataFromApi");
		for (int i = 0; i < 30; i++) {
			ImageItem item = new ImageItem();
			mImages.add(item);
		}
		
		handler.postDelayed(new Runnable() {
				public void run() {
					android.os.SystemClock.sleep(1); 
					mAdapter.notifyItemRangeInserted(mAdapter.getItemCount() - IMAGES_PER_PAGE,
									 IMAGES_PER_PAGE);
				}
			}, 5);
					// Send an API request to retrieve appropriate paginated data 
		//  --> Send the request including an offset value (i.e `page`) as a query parameter.
		//  --> Deserialize and construct new model objects from the API response
		//  --> Append the new data objects to the existing set of items inside the array of items
		//  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
	}
	
	private void alert(String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
			.setTitle(title)
			.setMessage(message)
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialogBuilder.create()
			.show();
	}
}
