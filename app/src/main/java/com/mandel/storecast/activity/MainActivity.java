package com.mandel.storecast.activity;


import android.widget.SearchView;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Context;

import android.os.Bundle;
import android.os.Message;

import com.mandel.storecast.view.ImagesAdapter;
import com.mandel.storecast.R;

import android.view.inputmethod.InputMethodManager;

public class MainActivity extends ActionBarActivity {
	
	public final int IMAGES_PER_PAGE = 30;
	ImagesAdapter mAdapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAdapter = new ImagesAdapter(this, IMAGES_PER_PAGE);
		
		SearchView searchView = (SearchView) findViewById(R.id.search);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					mAdapter.setupNewQuery(query);					
					
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
					
					return true;
				}
				@Override
				public boolean onQueryTextChange(String s) {
					return false;
				}
			});
		
		
		RecyclerView rvItems = (RecyclerView) findViewById(R.id.listview);
		rvItems.setAdapter(mAdapter);
		rvItems.setLayoutManager(new LinearLayoutManager(this));
	}
}
