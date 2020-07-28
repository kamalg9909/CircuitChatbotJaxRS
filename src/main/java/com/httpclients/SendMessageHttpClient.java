package com.httpclients;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.chatbot.models.CircuitSendMessageRequestObject;

public class SendMessageHttpClient {
	
	String contentType = "application/x-www-form-urlencoded";

	public void postmessage(CircuitSendMessageRequestObject details ) {

		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse httpResponse = null;

		try {

			httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(details.getBaseURL());

			httpPost.setHeader("AUTHORIZATION", "Bearer " + details.getToken());
			httpPost.setHeader("Content-type", contentType);

			List<NameValuePair> requestbody = new ArrayList<NameValuePair>();
			requestbody.add(new BasicNameValuePair("content", details.getContentMessage()));
			requestbody.add(new BasicNameValuePair("subject", details.getSubject()));
			if(!details.getFormMetaData().equalsIgnoreCase("")){
			requestbody.add(new BasicNameValuePair("formMetaData", details.getFormMetaData()));
			}

			System.out.println("33333333333333333333333");
			HttpEntity postParams = new UrlEncodedFormEntity(requestbody);
			httpPost.setEntity(postParams);

			httpResponse = httpClient.execute(httpPost);
			System.out.println("POST Response Status:: " + httpResponse.getStatusLine().getStatusCode());

			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response.toString());

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
	}

}
