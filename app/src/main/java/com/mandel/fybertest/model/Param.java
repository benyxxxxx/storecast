
package com.mandel.fybertest.model;

public  final class Param {
	private int mId; 
	private String mDefaultValue; 
	private String mUriName; 
	private Params mParent;
		
	public Param(Params parent, int id, String defaultValue, String uriName) {
		this.mParent = parent;
		this.mId = id;
		this.mDefaultValue = defaultValue;
		this.mUriName = uriName;
	}
	public int getId() {
		return mId;
	}
	public String getDefaultValue() {
		return mDefaultValue;
	}
	public String getUriParamName() {
		return mUriName;
	}
	
	public String getValue() {
		if (mId != 0) 
			return mParent.getParamValue(mId);
		else
			return mDefaultValue;
	}
}
