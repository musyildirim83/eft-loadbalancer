package tr.com.eft.odev.loadbalancer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	private HttpClientUtil() {
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String fetchGet(String url) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		HttpResponse response = httpClient.execute(request);

		LOGGER.info("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
}
