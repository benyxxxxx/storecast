package com.mandel.storecast;

import com.mandel.storecast.model.ImageItem;
import com.mandel.storecast.model.ImagesParser;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class JsonParserTest {

	
	@Before
	public void setup()
	{
	}

	@Test
	public void parseJson() throws Exception {
		
		String data = readFile("sample.json");
		List<ImageItem> list = new ArrayList<>();
		int itemsCount = ImagesParser.parseJson(data, list, 0);
		assertEquals(51973, itemsCount);
		assertEquals("452217160", list.get(0).getId());
	}
	
	public static String readFile (String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("../app/src/test/assets/" + filename)));
		StringBuilder buffer = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			buffer.append(line);
			line = reader.readLine();
		}
		return buffer.toString();
	}
}
