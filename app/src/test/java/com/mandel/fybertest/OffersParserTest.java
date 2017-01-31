package com.mandel.fybertest;

import com.mandel.fybertest.model.Offer;
import com.mandel.fybertest.activity.OffersParser;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OffersParserTest {

	
	@Before
	public void setup()
	{
	}

	@Test
	public void parseJson() throws Exception {
		
		String data = readFile("sample.json");
		List<Offer> list = OffersParser.parseJson(data);
		assertEquals(1, list.size());
		assertEquals("Download and START", list.get(0).getTeaser());
	}
	
	public String readFile (String filename) throws IOException {
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
