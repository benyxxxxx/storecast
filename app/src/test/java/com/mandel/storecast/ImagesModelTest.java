package com.mandel.storecast;

import com.mandel.storecast.model.ImageItem;
import com.mandel.storecast.model.ImagesModel;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import static org.junit.Assert.assertEquals;

public class ImagesModelTest {

	ImagesModel mImagesModel = null; 
	final int ITEMS_PER_PAGE = 30;

	final String EMPTY_RESULTS_QUERY = "xxxxyyyzzzz";
	
	@Before
	public void setup()
	{
		try {
			JSONObject result = new JSONObject(JsonParserTest.readFile("sample.json"));
			JSONObject emptyObj = new JSONObject(JsonParserTest.readFile("empty.json"));
			
			mImagesModel = new ImagesModel(new ImagesModel.PageBuilder() {
					
					public void execute(ImagesModel.PageBuilderListener listener, int pageIdx,
							    int itemsPerPage, String searchWord) {

						
						final java.util.Timer timer = new java.util.Timer();
						timer.schedule(new java.util.TimerTask() {
								public void run() {
									
									if (searchWord.equals(EMPTY_RESULTS_QUERY)) {  
										listener.onDone(emptyObj, pageIdx);
									} else {
										listener.onDone(result, pageIdx);
									}
									timer.cancel();
								}
							}, 1);
					}
				}, ITEMS_PER_PAGE, null);
		} catch (Exception e) {
			e.printStackTrace();	
		}	
	}

	@Test
	public void initialState() throws Exception {
		assertEquals(0, mImagesModel.getSize());
	}
	
	@Test
	public void queryState() throws Exception {

		mImagesModel.setupNewQuery("mobile");
		assertEquals(ITEMS_PER_PAGE, mImagesModel.getSize());
		Thread.sleep(1000);
		assertEquals(51973, mImagesModel.getSize());
		
		mImagesModel.setupNewQuery("mobile2");
		assertEquals(ITEMS_PER_PAGE, mImagesModel.getSize());
		assertEquals(false, mImagesModel.getElementAt(0).isReady());
		
		
		Thread.sleep(1000);
		assertEquals(51973, mImagesModel.getSize());
		assertEquals(true, mImagesModel.getElementAt(1).isReady());
		
	}
	
	@Test
	public void randomAccess() throws Exception {
		mImagesModel.setupNewQuery("mobile");
		
		assertEquals(ITEMS_PER_PAGE, mImagesModel.getSize());
		Thread.sleep(1000);
		assertEquals(51973, mImagesModel.getSize());
		
		assertEquals(false, mImagesModel.getElementAt(100).isReady());
		Thread.sleep(1000);
		/*
		 * Should be loaded already
		 */
		assertEquals(true, mImagesModel.getElementAt(100).isReady());
		
	}
	
	
	@Test
	public void emptyQueryState() throws Exception {
		mImagesModel.setupNewQuery("mobile");
		
		assertEquals(ITEMS_PER_PAGE, mImagesModel.getSize());
		Thread.sleep(10);
		assertEquals(51973, mImagesModel.getSize());

		mImagesModel.setupNewQuery(EMPTY_RESULTS_QUERY);
		assertEquals(ITEMS_PER_PAGE, mImagesModel.getSize());
		Thread.sleep(10);
		assertEquals(0, mImagesModel.getSize());
		
	}
	
}
