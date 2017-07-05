package com.wslibrary.bg7.ws;


import android.app.Activity;

import com.wslibrary.bg7.ws.GetRequestUITask.HttpMethod;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 */
@SuppressWarnings("rawtypes")
public class WSCommands {

    /**
     *  Execute request based in what kind of parameters was passed
     * @param activity
     * @param command
     * @param params
     * @param executeBy
     * @return
     * @throws Exception
     */
	@SuppressWarnings("deprecation")
	public static JSONObject requestWs(Activity activity, String command, List<Parameter> params, HttpMethod executeBy) throws Exception {
		
		JSONObject jsonParameters = new JSONObject();
		
		MultipartEntityBuilder entity = null;
		boolean hasImage = false;
		
		if(params != null) {
			for (Parameter parameter : params) {
				
				 if(command.contains("/attachment") && parameter.getKey().toString().equalsIgnoreCase("image")) {

				 	File file = (File) parameter.getValue();

					 entity = org.apache.http.entity.mime.MultipartEntityBuilder.create();

					 String contentType = LibraryUtil.getMimeType(file.getAbsolutePath());
					 entity.addPart(file.getName(), new FileBody(file, ContentType.create(contentType), file.getName()));

					 hasImage = true;
		            } else {
		                // Normal string data
		            	jsonParameters.put(parameter.getKey().toString(), parameter.getValue());
		            }
			}
		}
		
		JSONObject jsonResult = null;
		
		if(hasImage) {
			jsonResult = WSHelper.restImageRequest(activity, command,  entity);
		} else  {
			jsonResult = WSHelper.restParamsRequest(activity, command, params, executeBy);
		}
		
		return jsonResult;
	}

    /**
     * Execute request based in json parameters
     * @param activity
     * @param command
     * @param jsonParameters
     * @param executeBy
     * @return
     * @throws Exception
     */
	public static JSONObject requestWs(Activity activity, String command, JSONObject jsonParameters, HttpMethod executeBy) throws Exception {
		
		JSONObject jsonResult = WSHelper.restJsonRequest(activity, command, jsonParameters, null);
		
		return jsonResult;
	}
}