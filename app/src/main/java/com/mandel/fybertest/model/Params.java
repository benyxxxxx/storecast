package com.mandel.fybertest.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.security.MessageDigest;

import android.content.Context;

public abstract class Params extends  ArrayList<Param> {
	
	Map<String, Param> mMap = new HashMap();
	private Param mApiKey = null;

	public Params() {
	}
	
	public String getValue(String uriName) {
		Param p = mMap.get(uriName);
		if (p != null) {
			return p.getValue();
		}
		return "";
	}
	
	public void put(int id, String defaultValue, String uriName) {
		Param p = new Param(this, id, defaultValue, uriName);
		super.add(p);	
		mMap.put(uriName, p);
	}

	public void putApiKey(int id, String defaultValue) {
		mApiKey = new Param(this, id, defaultValue, "");
		super.add(mApiKey);
	}

	public String getApiKey() {
		return mApiKey.getValue();
	}
	
	public String getParamValue(int id) {
		return "";
	}
	
	private void sort() {
		Collections.sort(this, new Comparator<Param>() {
				@Override
				public int compare(Param f, Param f1)
				{
					
					return  f.getUriParamName().compareTo(f1.getUriParamName());
				}
			});
	}

	public String getParamsString() {
		sort();
		StringBuffer result = new StringBuffer();
		boolean first = true;
		for (Param entry : this) {
			
			String value = entry.getValue();
			String name = entry.getUriParamName();
			
			if (name.equals("")) {
				continue;
			}	
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			result.append(name+"="+value);
		}
		
		return result.toString();
	}
	
	public String buildResult() {
		String paramsData = getParamsString();
		String hash = getHash(paramsData);
		android.util.Log.d("-------------->buildResult hash ", hash);
		return paramsData + "&hashkey="+hash;
	}

	public String getHash() {
		return getHash(getParamsString());
	}
	
	public String getHash(String params) {
		return calculateHash(params + "&" + getApiKey());
	}

	public String getSignature(String data) {
		return calculateHash(data  + getApiKey());	
	}

	

	
	
	public static String calculateHash(String text) {
		try
			{
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
				messageDigest.update(text.getBytes("UTF-8"));
				byte[] bytes = messageDigest.digest();
				StringBuilder buffer = new StringBuilder();
				for (byte b : bytes)
					{
						buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
					}
				return buffer.toString();
			}
		catch (Exception ignored)
			{
				ignored.printStackTrace();
				return null;
			}
	}	
}
