package com.mandel.fybertest;


import com.mandel.fybertest.model.Params;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;




public class ParamsTest {


	private Params mParams;

	@Before
	public void setup()
	{
		mParams = new Params() {
				public String getParamValue(int id) {
					return "";
				}		
			};

		mParams.put(0, "json", "format");
		mParams.put(0, "2070", "appid");
		mParams.put(0, "spiderman", "uid");
		mParams.put(0, "DE", "locale");
		mParams.put(0, "7.1.1", "os_version");
		mParams.put(0, "1479201548", "timestamp");
		mParams.put(0, "", "google_ad_id");
		mParams.put(0, "false", "google_ad_id_limited_tracking_enabled");
		mParams.put(0, "109.235.143.113", "ip");
		mParams.put(0, "112", "offer_types");
		mParams.putApiKey(0, "1c915e3b5d42d05136185030892fbb846c278927");

	}

	@Test
	public void buildResultTest() throws Exception {

		String param = mParams.buildResult();
		String eParam ="appid=2070&format=json&google_ad_id=&google_ad_id_limited_tracking_enabled=false&ip=109.235.143.113&locale=DE&offer_types=112&os_version=7.1.1&timestamp=1479201548&uid=spiderman&hashkey=d03affc0a245c23ee5b7e1c283eef5f938262c59";

		assertThat(param, is(eParam));
	}

	@Test
	public void getParamsStringTest() throws Exception {

		String param = mParams.getParamsString();
		String eParam ="appid=2070&format=json&google_ad_id=&google_ad_id_limited_tracking_enabled=false&ip=109.235.143.113&locale=DE&offer_types=112&os_version=7.1.1&timestamp=1479201548&uid=spiderman";

		assertThat(param, is(eParam));
	}

	@Test
	public void getHashTest() throws Exception {

		String hash = mParams.getHash(mParams.getParamsString());
		String eHash ="d03affc0a245c23ee5b7e1c283eef5f938262c59";

		assertThat(hash, is(eHash));
	}

}

