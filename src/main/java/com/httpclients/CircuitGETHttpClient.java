package com.httpclients;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.chatbot.models.CircuitUserDetails;
import com.chatbot.models.Item;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CircuitGETHttpClient {

	CloseableHttpClient httpClient = null;
	HttpGet request = null;
	CloseableHttpResponse httpResponse = null;

	public String getUserEmailID(String httpURL, String token) {
		CircuitUserDetails userDetails = new CircuitUserDetails();
		try {

			httpClient = HttpClients.createDefault();
			request = new HttpGet(httpURL);

			request.setHeader("AUTHORIZATION", "Bearer " + token);
			request.setHeader("Content-type", "application/json");

			httpResponse = httpClient.execute(request);
			System.out.println("Response Status:: " + httpResponse.getStatusLine().getStatusCode());

			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response.toString());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				userDetails = objectMapper.readValue(response.toString(), CircuitUserDetails.class);
				System.out.println("User Details : " + userDetails);
			} catch (Exception e) {
				System.out.println("Jackon Json Parsing Error");
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
				httpClient.close();
				System.out.println("HttpClient is Closed");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return userDetails.getEmailAddress();

	}
	
	public Item getCircuitParentItem(String httpURL, String token) {
		Item circuitItem = new Item();
		try {

			httpClient = HttpClients.createDefault();
			request = new HttpGet(httpURL);

			request.setHeader("AUTHORIZATION", "Bearer " + token);
			request.setHeader("Content-type", "application/json");

			httpResponse = httpClient.execute(request);
			System.out.println("Response Status:: " + httpResponse.getStatusLine().getStatusCode());

			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response.toString());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}
			
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				circuitItem = objectMapper.readValue(response.toString(), Item.class);
				System.out.println(circuitItem);
			} catch (Exception e) {
				
				System.out.println("Jackon Json Parsing Error");
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
				httpClient.close();
				System.out.println("HttpClient is Closed");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return circuitItem;

	}
	
}