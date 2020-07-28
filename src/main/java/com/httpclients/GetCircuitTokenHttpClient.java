package com.httpclients;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class GetCircuitTokenHttpClient {

	CloseableHttpClient httpClient = null;
	HttpPost httpPost = null;
	CloseableHttpResponse httpResponse = null;
	String token = "";
	
	public String getAccessToken() {

		System.out.println("Inside GettingAccessToken : getToken()");
		String baseUrl = "https://circuitsandbox.net/oauth/token";
		String clientID = "aa5f6ac2a609494fa436f386c9852560";
		String clientSecret = "40a5018dda284bc1b2710805dc999283";

		try {

			httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(baseUrl);

			String authStringEnc = basicAuth(clientID, clientSecret);

			httpPost.setHeader("Authorization", "Basic " + authStringEnc);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

			List<NameValuePair> requestbody = new ArrayList<NameValuePair>();
			requestbody.add(new BasicNameValuePair("grant_type", "client_credentials"));
			requestbody.add(new BasicNameValuePair("scope", "READ_USER,READ_CONVERSATIONS,WRITE_CONVERSATIONS"));

			HttpEntity postParams = new UrlEncodedFormEntity(requestbody);
			httpPost.setEntity(postParams);

			httpResponse = httpClient.execute(httpPost);
			System.out.println("POST Response Status:: " + httpResponse.getStatusLine().getStatusCode());

			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response.toString());

			JSONObject obj;
			try {
				obj = new JSONObject(response);
				token = obj.getString("access_token");
				System.out.println("Circuit Token : " + token);
				return token;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
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
		return clientSecret;
	}

	private static String basicAuth(String username, String password) {
		// Setting Authentication String
		String authString = username + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		return new String(authEncBytes);

	}
}
