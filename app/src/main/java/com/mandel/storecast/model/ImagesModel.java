
package com.mandel.storecast.model;

import java.util.ArrayList;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.util.Log;

/**
 * Image's model. This model support pagination on demand. 
 * 
 *  This is the flow:
 *
 *  1. Will be initialased with an empty page if asked for size. 
 *  2. Will get the real items count when the first data arrives.
 *  3. Asks for data on demand, loads missing pages whenever unloaded item is accessed.
 *
 */
public class ImagesModel  {
	
	public interface ImagesModelListener {
		public void dataChanged(int from, int to);
	}

	
	/**
	 * An Interface for asynchronous data requests.  
	 */
	public interface PageBuilder {
		void execute(PageBuilderListener listener, int pageIdx, int itemsPerPage, String searchWord);
	}

	public interface PageBuilderListener {
		void onDone(JSONObject response, int page);
		void onError();
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

	/**
	 * Retrieves a current items size of the model, can change when data arrives.
	 * Can take a temporal value equal of the page size before data arrived.
	 */
	
	public int getSize() {
		checkSizeCapacity();
		return mQuerySize;
	}

	/**
	 * Retrieves an element at index
	 * @param i index of an element
	 * @return ImageItem, maybe in loaded and unloaded state. 
	 */
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
