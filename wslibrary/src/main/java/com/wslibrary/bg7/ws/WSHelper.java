package com.wslibrary.bg7.ws;

import android.app.Activity;

import com.wslibrary.bg7.ws.GetRequestUITask.HttpMethod;

import org.apache.http.client.HttpClient;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * @author elison
 */
public class WSHelper {


	public static final String WS_BASE_URL = "https://preapi.s4bdigital.net/";
	public static String SB_DOMAIN = "preapi.s4bdigital.net";
	public static String boundary = "";

	private static JSONObject token = null;

	public static JSONObject getToken() {
		return token;
	}

	public static void setToken(JSONObject token) {
		WSHelper.token = token;
	}

	/**
	 * @author elison
	 * @param serviceUrl
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 * @return JSONObject
	 */
	@SuppressWarnings("finally")
	public static JSONObject restParamsRequest(Activity activity, String serviceUrl, List<Parameter> params, HttpMethod callMethod) throws ClientProtocolException, IOException, JSONException {

		String url = "";

		if (!serviceUrl.contains("http")) {
			url = WS_BASE_URL.concat(serviceUrl);
		} else {
			url = serviceUrl;
		}

		JSONObject jsonObjResponse = null;

		try     {

			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Commando URL: " + url);

			// Create the POST object and add the parameters
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			// The default value is zero, that means the timeout is not used.
			int timeoutConnection = 30000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

			// Set the default socket timeout (SO_TIMEOUT) in milliseconds which is
			// the timeout for waiting for data.
			int timeoutSocket = 30000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);


			// Create the POST object and add the parameters
			cz.msebera.android.httpclient.client.methods.HttpRequestBase http = null;
			StringEntity entity = null;

			switch (callMethod) {
				case PUT:
					http = new HttpPut(url);
					entity = new StringEntity(getDataString(params), HTTP.UTF_8);
					entity.setContentType("application/json");
					((HttpPut) http).setEntity(entity);
					break;
				case GET:
					http = new HttpGet(url);
					break;
				default:
					http = new HttpPost(url);

					if(url.contains("oauth/token")) {
						http.setHeader(HTTP.CONTENT_TYPE,
								"application/x-www-form-urlencoded;charset=UTF-8");
						entity = new StringEntity(getDataString(params), HTTP.UTF_8);
						entity.setContentType("application/x-www-form-urlencoded");
					}  else {
						http.setHeader(HTTP.CONTENT_TYPE,
								"application/json");

						entity = new StringEntity(clearString((String)params.get(0).getValue()), HTTP.UTF_8);
						entity.setContentType("application/json");
						if(token != null) {
							http.setHeader("Authorization", "Bearer "+token.get("access_token"));
						}
					}
					((HttpPost) http).setEntity(entity);

					break;
			}

			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Request Server -  " + getDataString(params));

			DefaultHttpClient client = new DefaultHttpClient(httpParameters);
			HttpContext localContext = new BasicHttpContext();


			HttpResponse response = client.execute(http, localContext);

			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Request Response CODE -  " + response.getStatusLine().getStatusCode());


			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();

			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}

			jsonObjResponse = treatReturnValue(builder.toString());

		}catch(ConnectTimeoutException e)    {
			LibraryUtil.Log.file("Erro na requisicao ao servidor - ConnectTimeoutException: "+(e instanceof ConnectTimeoutException ? e.toString() : e.getMessage()));
		}catch(SocketTimeoutException e)   {
			LibraryUtil.Log.file("Erro na requisicao ao servidor - SocketTimeoutException: "+(e instanceof SocketTimeoutException ? e.toString() : e.getMessage()));
		}catch(Exception e){
			LibraryUtil.Log.file("Erro na requisicao ao servidor - Exception: "+(e instanceof NullPointerException ? e.toString() : e.getMessage()));
		} finally {
			return jsonObjResponse;
		}
	}



	/**
	 * @author elison
	 * @param serviceUrl
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 * @return JSONObject
	 */
	@SuppressWarnings("finally")
	public static JSONObject restImageRequest(Activity activity, String serviceUrl,  MultipartEntityBuilder multipart) throws ClientProtocolException, IOException, JSONException {

		String url = "";

		if (!serviceUrl.contains("http")) {
			url = WS_BASE_URL.concat(serviceUrl);
		} else {
			url = serviceUrl;
		}

		JSONObject jsonObjResponse = null;

		try     {

			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Commando URL: " + url);

			org.apache.http.client.methods.HttpPost filePost = new org.apache.http.client.methods.HttpPost(url);

			if(multipart != null) {
				HttpClient manager = new org.apache.http.impl.client.DefaultHttpClient();
				if(token != null) {
					filePost.setHeader("Authorization", "Bearer "+token.get("access_token"));
				}

				filePost.setEntity(multipart.build());

				org.apache.http.HttpResponse response = manager.execute(filePost);

				LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Request Response CODE -  " + response.getStatusLine().getStatusCode());


				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();

				for (String line = null; (line = reader.readLine()) != null;) {
					builder.append(line).append("\n");
				}

				jsonObjResponse = treatReturnValue(builder.toString());
			}


		}catch(ConnectTimeoutException e)    {
			LibraryUtil.Log.file("Erro na requisicao ao servidor - ConnectTimeoutException: "+(e instanceof ConnectTimeoutException ? e.toString() : e.getMessage()));
		}catch(SocketTimeoutException e)   {
			LibraryUtil.Log.file("Erro na requisicao ao servidor - SocketTimeoutException: "+(e instanceof SocketTimeoutException ? e.toString() : e.getMessage()));
		}catch(Exception e){
			LibraryUtil.Log.file("Erro na requisicao ao servidor - Exception: "+(e instanceof NullPointerException ? e.toString() : e.getMessage()));
		} finally {
			return jsonObjResponse;
		}
	}





	/**
	 * @author elison
	 * @param serviceUrl
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 * @return JSONObject
	 */
	@SuppressWarnings("finally")
	public static JSONObject restJsonRequest(Activity activity, String serviceUrl, JSONObject jsonObject,  HttpMethod callMethod) throws ClientProtocolException, IOException, JSONException {

		String url = "";

		if (!serviceUrl.contains("http")) {
			url = WS_BASE_URL.concat(serviceUrl);
		} else {
			url = serviceUrl;
		}

		JSONObject jsonObjResponse = null;

		try     {

			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Commando URL: " + url);

			// Create the POST object and add the parameters
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			// The default value is zero, that means the timeout is not used.
			int timeoutConnection = 30000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

			// Set the default socket timeout (SO_TIMEOUT) in milliseconds which is
			// the timeout for waiting for data.
			int timeoutSocket = 30000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);


			// Create the POST object and add the parameters
			cz.msebera.android.httpclient.client.methods.HttpRequestBase http = null;
			StringEntity entity = null;

			switch (callMethod) {
				case PUT:
					http = new HttpPut(url);
					entity = new StringEntity(clearString(jsonObject.toString()), HTTP.UTF_8);
					entity.setContentType("application/json");
					((HttpPut) http).setEntity(entity);
					break;
				case GET:
					http = new HttpGet(url);
					break;
				default:
					http = new HttpPost(url);

					http.setHeader(HTTP.CONTENT_TYPE,
							"application/x-www-form-urlencoded;charset=UTF-8");
					entity = new StringEntity(clearString(jsonObject.toString()), HTTP.UTF_8);
					if(url.contains("oauth/token")) {
						entity.setContentType("application/x-www-form-urlencoded");
					} else {
						entity.setContentType("application/json");
					}
					((HttpPost) http).setEntity(entity);


					break;
			}

			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Request Server -  " + clearString(jsonObject.toString()));

			DefaultHttpClient client = new DefaultHttpClient(httpParameters);
			HttpContext localContext = new BasicHttpContext();


			HttpResponse response = client.execute(http, localContext);

			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, "Request Response CODE -  " + response.getStatusLine().getStatusCode());


			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();

			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}

			jsonObjResponse = treatReturnValue(builder.toString());

		}catch(ConnectTimeoutException e)    {
			LibraryUtil.Log.file("Erro na requisicao ao servidor - ConnectTimeoutException: "+(e instanceof ConnectTimeoutException ? e.toString() : e.getMessage()));
		}catch(SocketTimeoutException e)   {
			LibraryUtil.Log.file("Erro na requisicao ao servidor - SocketTimeoutException: "+(e instanceof SocketTimeoutException ? e.toString() : e.getMessage()));
		}catch(Exception e){
			LibraryUtil.Log.file("Erro na requisicao ao servidor - Exception: "+(e instanceof NullPointerException ? e.toString() : e.getMessage()));
		} finally {
			return jsonObjResponse;
		}
	}

	private static String getDataString(List<Parameter> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for(Parameter param: params){
			if (first)
				first = false;
			else
				result.append("&");
			result.append(URLEncoder.encode((String) param.getKey(), "UTF-8"));
			result.append("=");
			result.append(clearString((String)param.getValue()));
			//result.append(URLEncoder.encode((String) param.getValue(), "UTF-8"));
		}
		return result.toString();
	}


	private static String clearString(String string) {

		String clear = string.replace("\\", "");
		clear = clear.replace("\"{", "{");
		clear = clear.replace("}\"", "}");

		return clear;
	}

	public static JSONObject treatReturnValue(String response) {

		try {
			LibraryUtil.PrintLog(LibraryUtil.TypeLog.I, LibraryUtil.TAG, "Service Response: " + response.toString());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		JSONObject object = new JSONObject();

		if (response != null && !response.equals("") && !response.equals("null") && !response.trim().equals("{}")) {
			try {

				if(response.equals("true") || response.equals("false") || response.equals("0\n") || response.equals("1\n") || response.equals("2\n")) {
					response = "{response = "+response+" }";
				} else if(response.startsWith("http")) {
					response = "{response = \""+response+"\" }";
				}

				return new JSONObject(response);
			} catch (JSONException e) {
				try {
					return new JSONArray(response).getJSONObject(0);
				} catch (JSONException e1) {
					return object;
				}
			}
		}

		return object;
	}
}