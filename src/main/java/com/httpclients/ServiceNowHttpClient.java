package com.httpclients;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicenow.models.Result;
import com.servicenow.models.ResultObjectList;
import com.servicenow.models.ServicenowResultObject;
import com.servicenow.models.SnowHTTPOutput;
import com.servicenow.models.SnowHTTPRequestObject;
import com.servicenow.models.SnowUser;
import com.servicenow.models.UserObjectList;

public class ServiceNowHttpClient {

	CloseableHttpClient httpClient = null;
	CloseableHttpResponse httpResponse = null;

	SnowHTTPOutput httpOutput = new SnowHTTPOutput();
	Result result = new Result();
	ServicenowResultObject resultOBJ = new ServicenowResultObject();

	public SnowHTTPOutput getTicketDetails(SnowHTTPRequestObject snowRequestObject) {
		System.out.println("Inside ServiceNowHttpClient - getTicketDetails() ");

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			httpClient = HttpClients.createDefault();
			HttpGet request = new HttpGet(snowRequestObject.getBaseUrl());

			// Request headers
			request.addHeader("Content-type", "application/json");

			// Basic Auth
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials(snowRequestObject.getUsername(), snowRequestObject.getPass()));
			httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

			httpResponse = httpClient.execute(request);
			System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				httpOutput.setHttpresponseStatus("error");
				httpOutput.setStatusString("Ticket not found. Please Check Service Now " );
				System.out.println("Ticket Not Found");
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}
			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response.toString());

			try {
				ResultObjectList ticketDetailsList = new ResultObjectList();
				
				ticketDetailsList = objectMapper.readValue(response.toString(), ResultObjectList.class);
				result = ticketDetailsList.getResult()[0];
				httpOutput.setResult(result);
				httpOutput.setHttpresponseStatus("success");
			} catch (Exception e) {
				httpOutput.setHttpresponseStatus("Something Went Wrong while parsing, Please Contact Dev Team");
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
				return httpOutput;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return httpOutput;
	}

	public SnowHTTPOutput createTicket(SnowHTTPRequestObject snowRequestDetails) {
		System.out.println("Inside ServiceNowHttpClient : createTicket()");

		try {
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(snowRequestDetails.getBaseUrl());

			httpPost.setHeader("Content-type", "application/json");

			// Basic Auth
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials(snowRequestDetails.getUsername(), snowRequestDetails.getPass()));
			httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

			StringEntity entity = new StringEntity(snowRequestDetails.getRequestBody(),ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);

			httpResponse = httpClient.execute(httpPost);
			System.out.println("POST Response Status:: " + httpResponse.getStatusLine().getStatusCode());
			
			if (httpResponse.getStatusLine().getStatusCode() != 201) {
				httpOutput.setHttpresponseStatus("error");
				httpOutput.setStatusString("Something went wrong while updating... Please Check Servicenow or Contact Dev Team");
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}
			
			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response.toString());

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
				resultOBJ = objectMapper.readValue(response.toString(), ServicenowResultObject.class);
				result = resultOBJ.getResult();
				httpOutput.setResult(result);
				httpOutput.setHttpresponseStatus("success");
				httpOutput.setStatusString("Ticket has been created successfully");

			} catch (Exception e) {
				httpOutput.setHttpresponseStatus("error");
				httpOutput.setStatusString("Jackon Json Parsing Error, Please Contact the Dev Team");
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
		return httpOutput;
	}

	public SnowHTTPOutput updateTicket(SnowHTTPRequestObject snowRequestDetails) {
		System.out.println("Inside ServiceNowHttpClient : updateTicket()");

		try {

			httpClient = HttpClients.createDefault();
			HttpPut httpPut = new HttpPut(snowRequestDetails.getBaseUrl());

			httpPut.setHeader("Content-type", "application/json");

			// Basic Auth
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials(snowRequestDetails.getUsername(), snowRequestDetails.getPass()));
			httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

			StringEntity entity = new StringEntity(snowRequestDetails.getRequestBody(), ContentType.APPLICATION_JSON);
			httpPut.setEntity(entity);

			httpResponse = httpClient.execute(httpPut);
			System.out.println("PUT Response Status:: " + httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				httpOutput.setHttpresponseStatus("error");
				httpOutput.setStatusString("Something went wrong while updating... Please Check Servicenow or Contact Dev Team");
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}
			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response.toString());

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				resultOBJ = objectMapper.readValue(response.toString(), ServicenowResultObject.class);
				result = resultOBJ.getResult();
				httpOutput.setResult(result);
				httpOutput.setHttpresponseStatus("success");
				httpOutput.setStatusString("Ticket has been updated successfully");

			} catch (Exception e) {
				httpOutput.setHttpresponseStatus("error");
				httpOutput.setStatusString("Jackon Json Parsing Error, Please Contact the Dev Team");
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
		return httpOutput;
	}

	public SnowHTTPOutput verifySNOWUserViaEmail(SnowHTTPRequestObject snowRequestObject) {
		System.out.println("Inside ServiceNowHttpClient - getSNOWUserDetails() ");

		try {

			httpClient = HttpClients.createDefault();
			HttpGet request = new HttpGet(snowRequestObject.getBaseUrl());

			// Request headers
			request.addHeader("Content-type", "application/json");

			// Basic Auth
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials(snowRequestObject.getUsername(), snowRequestObject.getPass()));
			httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

			httpResponse = httpClient.execute(request);
			System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				System.out.println("User not found.");
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}

			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response.toString());
			try {
				UserObjectList userList = new UserObjectList();
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
				userList = objectMapper.readValue(response.toString(), UserObjectList.class);

				if (userList.getResult().length != 0) {
					SnowUser user = new SnowUser();
					user = userList.getResult()[0];
					httpOutput.setHttpresponseStatus("success");
					System.out.println("User Details = " + user);
				} else {
					httpOutput.setHttpresponseStatus("error");
					httpOutput.setStatusString("User Not Found Error: Resubmit the Form");
					System.out.println("User not found.");
				}
			} catch (Exception e) {
				httpOutput.setHttpresponseStatus("JacksonParsingError");
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
				return httpOutput;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return httpOutput;
	}

}
