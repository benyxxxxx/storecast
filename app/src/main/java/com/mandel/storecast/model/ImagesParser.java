package com.mandel.storecast.model;

import com.mandel.storecast.model.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImagesParser {
	
	public static  int parseJson(String obj, List<ImageItem> list, int listIdxStart) {
		try {
			return parseJson(new JSONObject(obj), list, listIdxStart);
		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}	
	}

	/**
	 * Parses an JsonObject and fill up the given list with data
	 * @param obj a json object to parse
	 * @list an obj t populate with data
	 * @listIdxStart an start  index inside of list to be populated 
	 */
	public static  int parseJson(JSONObject obj, List<ImageItem> list, int listIdxStart) {
		
		int querySize = 0;
		try {
			JSONArray images = obj.getJSONArray("images");
			querySize = obj.getInt("result_count");
			if (images != null) {
				
				for (int i = 0, listIdx = listIdxStart; i < images.length(); i++, listIdx++) {
					JSONObject image = (JSONObject)images.get(i);
					
					ImageItem item = new ImageItem();
					item.setId(image.getString("id"));
					item.setTitle(image.getString("title"));
					item.setCaption(image.getString("caption"));
					
					JSONArray display_sizes = image.getJSONArray("display_sizes");
					
					if (display_sizes != null) { 
						for (int j = 0; j < display_sizes.length(); j++) {
							JSONObject display = (JSONObject)display_sizes.get(j);
							String name = display.getString("name");	
							if (name.equals("thumb")) {
								item.setUri(display.getString("uri"));
								break;
							}
						}
					}
					
					if (list.size() <= listIdx)
						list.add(item);
					else
						list.get(listIdx).makeItReady(item);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return querySize;
	}
}
