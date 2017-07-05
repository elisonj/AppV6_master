package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by elison on 03/07/17.
 */

public class AttachmentDataHandler extends SugarRecord<AttachmentDataHandler> {

    private DataSource dataSource;
    private String contentType;
    private String outputStream = "OutputStream";
    private JSONArray allCommands;
    private JSONArray preferredCommands;
    private JSONArray transferDataFlavors;
    private String name;
    private String inputStream = "InputStream";
    private String content = "Object";



    /**
     * Return a JSON representation from Attachment
     * @return
     */
    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {


            if(dataSource != null) jsonObject.put("dataSource", dataSource.toJSON());
            if(contentType != null) jsonObject.put("contentType", contentType);
            if(outputStream != null) jsonObject.put("outputStream", outputStream);
            if(allCommands != null) jsonObject.put("allCommands", allCommands);
            if(preferredCommands != null) jsonObject.put("preferredCommands", preferredCommands);
            if(transferDataFlavors != null) jsonObject.put("transferDataFlavors", transferDataFlavors);
            if(name != null) jsonObject.put("name", name);
            if(inputStream != null) jsonObject.put("inputStream", inputStream);
            if(content != null) jsonObject.put("content", content);


            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }

    }





    public AttachmentDataHandler() {
        try {
            allCommands = new JSONArray().put(new JSONObject().put("OutputStream","").put("commandClass",""));
            preferredCommands = new JSONArray().put(new JSONObject().put("OutputStream","").put("commandClass",""));
            preferredCommands = new JSONArray().put(new JSONObject().
                    put("humanPresentableName","").
                    put("mimeType","").
                    put("subType","").
                    put("defaultRepresentationClassAsString","").
                    put("primaryType","").
                    put("flavorJavaFileListType","").
                    put("flavorRemoteObjectType","").
                    put("flavorSerializedObjectType","").
                    put("flavorTextType","").
                    put("mimeTypeSerializedObject","").
                    put("representationClassByteBuffer","").
                    put("representationClassCharBuffer","").
                    put("representationClassInputStream","").
                    put("representationClassReader","").
                    put("representationClassRemote","").
                    put("representationClassSerializable",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class DataSource {
        String contentType;
        String outputStream = "OutputStream";
        String name;
        String inputStream = "InputStream";




        /**
         * Return a JSON representation from Attachment
         * @return
         */
        public JSONObject toJSON(){

            JSONObject jsonObject= new JSONObject();
            try {


                jsonObject.put("contentType", contentType);
                jsonObject.put("outputStream", outputStream);
                jsonObject.put("name", name);
                jsonObject.put("inputStream", inputStream);



                return jsonObject;
            } catch (JSONException e) {
                e.printStackTrace();
                return jsonObject;
            }

        }



        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getOutputStream() {
            return outputStream;
        }

        public void setOutputStream(String outputStream) {
            this.outputStream = outputStream;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInputStream() {
            return inputStream;
        }

        public void setInputStream(String inputStream) {
            this.inputStream = inputStream;
        }
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(String outputStream) {
        this.outputStream = outputStream;
    }

    public JSONArray getAllCommands() {
        return allCommands;
    }

    public void setAllCommands(JSONArray allCommands) {
        this.allCommands = allCommands;
    }

    public JSONArray getPreferredCommands() {
        return preferredCommands;
    }

    public void setPreferredCommands(JSONArray preferredCommands) {
        this.preferredCommands = preferredCommands;
    }

    public JSONArray getTransferDataFlavors() {
        return transferDataFlavors;
    }

    public void setTransferDataFlavors(JSONArray transferDataFlavors) {
        this.transferDataFlavors = transferDataFlavors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputStream() {
        return inputStream;
    }

    public void setInputStream(String inputStream) {
        this.inputStream = inputStream;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
