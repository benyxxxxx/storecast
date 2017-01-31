package com.mandel.fybertest.activity;

import com.mandel.fybertest.model.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 11/6/2016.
 */
public class ImagesParser {
	
	public static List<ImageItem> parseJson(JSONObject obj) {

		ArrayList<ImageItem> list = new ArrayList<ImageItem>();
		
		try {
			JSONArray images = obj.getJSONArray("images");
			
			if (images != null) {
				
				for (int i = 0; i < images.length(); i++) {
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
					list.add(item);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
