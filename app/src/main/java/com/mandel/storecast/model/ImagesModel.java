
package com.mandel.storecast.model;

import java.util.ArrayList;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.util.Log;

public class ImagesModel  {
	
	public interface ImagesModelListener {
		public void dataChanged(int from, int to);
	}
	
	public interface PageBuilderListener {
		void onDone(JSONObject response, int page);
		void onError();
	}
	
	public interface PageBuilder {
		void execute(PageBuilderListener listener, int pageIdx, int itemsPerPage, String searchWord);
	}
	

	private ArrayList<ImageItem> mData = new ArrayList<ImageItem>();
	private String mSearchWord;
	private int mItemsPerPage;

	private boolean mConnected = false;
	private boolean mHasQuery = false;

	private int mQuerySize = 0;
	private ImagesModelListener mListener;

	private PageBuilder mPageBuilder;
	
	public ImagesModel(PageBuilder pageBuilder, int itemsPerPage,
			   ImagesModelListener listener) {
		
		mPageBuilder = pageBuilder;
		mItemsPerPage = itemsPerPage;
		mListener = listener;
	}
	
	public int getSize() {
		checkSizeCapacity();
		return mQuerySize;
	}
	
	public ImageItem getElementAt(int i) {
		checkAccess(i);
		return mData.get(i);
	}
	
	public void setupNewQuery(String searchWord) {
		mSearchWord = searchWord;
		mConnected = false;
		mQuerySize = 0;
		mData.clear();
		mHasQuery = true;
	}
	
	
	
	private void checkSizeCapacity() {
		/**
		 * First time, create a dummy page
		 */
		if (mHasQuery && !mConnected && mData.size() == 0) {
			createDummyPage(0);
			mQuerySize = mItemsPerPage;
		}
	}
	
	private void checkAccess(int i) {
		if (i >= mData.size())
			createDummyPage(i/mItemsPerPage);	
	}
	
	private void createDummyPage(int pageIdx) {

		int start = mData.size()/mItemsPerPage;
		if (mData.size() % mItemsPerPage == 0)
			start--;	
		
		while(start++ < pageIdx)
			addPage();	
	}

	private void addPage() {
		int start = mData.size();
		for (int i = 0; i < mItemsPerPage; i++) {
			mData.add(new ImageItem());
		}
		loadPage(start/mItemsPerPage);
		
		if (mListener != null && start != mData.size()) 
			mListener.dataChanged(start, mData.size());
	}
	
	private void loadPage(int pageIdx) {
		mPageBuilder.execute(mPageBuilderListener, pageIdx, mItemsPerPage, mSearchWord);
	}


	PageBuilderListener mPageBuilderListener = new PageBuilderListener() {
			
			@Override
			public void onDone(JSONObject response, int page) { 
				mQuerySize = ImagesParser.parseJson(response, mData, page*mItemsPerPage);
				mConnected = true;
				if (mListener != null) 
					mListener.dataChanged(page*mItemsPerPage, (page+1)*mItemsPerPage);
			}
			
			@Override
			public void onError() {
			}
		};
}
