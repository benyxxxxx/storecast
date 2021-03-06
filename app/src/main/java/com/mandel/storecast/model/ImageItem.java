package com.mandel.storecast.model;

/**
 * Holds data for an Image item.
 * Can be in a loaded state(ready) and unloaded state (not ready)  
 *
 */
public class ImageItem {
	
	private String mTitle = "";
	private String mID = "";
	private String mUri = "";
	private String mCaption = "";
	private boolean mIsReady = false;
	
	public String getTitle() {
		return mTitle;
	}
	
	public void setTitle(String title) {
		this.mTitle = title;
	}
	
	public String getCaption() {
		return mCaption;
	}

	public void setCaption(String caption) {
		this.mCaption = caption;
	}

	public String getUri() {
		return mUri;
	}

	public void setUri(String uri) {
		this.mUri = uri;
	}

	public String getId() {
		return mID;
	}
	
	public void setId(String id) {
		this.mID = id;
	}

	public boolean isReady() {
		return mIsReady;
	}

	/**
	 * This call will load the object with data and put it in ready state.
	 */
	public void makeItReady(ImageItem placeHolder) {
		this.mUri = placeHolder.getUri();
		this.mTitle = placeHolder.getTitle();
		this.mCaption = placeHolder.getCaption();
		this.mID = placeHolder.getId();
		mIsReady = true;
	}
}
