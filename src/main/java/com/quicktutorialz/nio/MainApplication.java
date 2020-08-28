package com.quicktutorialz.nio;

import com.mawashi.nio.jetty.ReactiveJ;
import com.quicktutorialz.nio.endpoints.EndPoints;
import com.quicktutorialz.nio.endpoints.EndpointsV2;

public class MainApplication{
	public static void main(String... args) throws Exception {
		new ReactiveJ().port(3001)
						.endpoints(new EndPoints())
						.endpoints(new EndpointsV2())
						.start();
	}
}
