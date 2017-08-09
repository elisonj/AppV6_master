package br.com.bg7.appvistoria.data.source.remote.dto;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;


public class ProductResponse extends SugarRecord<ProductResponse> {

        private long productId;
        private int modelId;
        private String detailedDescCompl;
        private boolean photoIllustrative;
        private String detailedDesc;
        private boolean inConditions;
        private String miniPhoto;
        private int subCategoryId;
        private int cityId;
        private int sellerId;
        private String descURL;
        private String productYourRef;
        private String attach1URL;
        private String shortDesc;
        private int projectId;
        private String attach2URL;
        private Properties properties;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public ProductResponse() {
    }

    public static ProductResponse fromJson(JSONObject jsonObject) {
        ProductResponse b = new ProductResponse();
        try {

            if(jsonObject.has("productId")) b.productId = jsonObject.getLong("productId");
            if(jsonObject.has("modelId"))  b.modelId = jsonObject.getInt("modelId");
            if(jsonObject.has("detailedDescCompl")) b.detailedDescCompl = jsonObject.getString("detailedDescCompl");
            if(jsonObject.has("photoIllustrative")) b.photoIllustrative = jsonObject.getBoolean("photoIllustrative");
            if(jsonObject.has("detailedDesc")) b.detailedDesc = jsonObject.getString("detailedDesc");
            if(jsonObject.has("inConditions")) b.inConditions = jsonObject.getBoolean("inConditions");
            if(jsonObject.has("miniPhoto")) b.miniPhoto = jsonObject.getString("miniPhoto");
            if(jsonObject.has("subCategoryId")) b.subCategoryId = jsonObject.getInt("subCategoryId");
            if(jsonObject.has("cityId")) b.cityId = jsonObject.getInt("cityId");
            if(jsonObject.has("sellerId")) b.sellerId = jsonObject.getInt("sellerId");
            if(jsonObject.has("descURL")) b.descURL = jsonObject.getString("descURL");
            if(jsonObject.has("productYourRef")) b.productYourRef = jsonObject.getString("productYourRef");
            if(jsonObject.has("attach1URL")) b.attach1URL = jsonObject.getString("attach1URL");
            if(jsonObject.has("shortDesc")) b.shortDesc = jsonObject.getString("shortDesc");
            if(jsonObject.has("projectId")) b.projectId = jsonObject.getInt("projectId");
            if(jsonObject.has("attach2URL")) b.attach2URL = jsonObject.getString("attach2URL");

            if(jsonObject.has("properties")) {
                Properties p  = Properties.fromJson(jsonObject.getJSONObject("properties"));
                b.properties = p;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }


    /**
     * Return a JSON representation from Object
     */
    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            if(productId > 0)  jsonObject.put("productId", productId);
            jsonObject.put("modelId", modelId);
            jsonObject.put("detailedDescCompl", detailedDescCompl);
            jsonObject.put("photoIllustrative", photoIllustrative);
            jsonObject.put("detailedDesc", detailedDesc);
            jsonObject.put("inConditions", inConditions);
            jsonObject.put("miniPhoto", miniPhoto);
            jsonObject.put("subCategoryId", subCategoryId);
            jsonObject.put("cityId", cityId);
            jsonObject.put("sellerId", sellerId);
            jsonObject.put("descURL", descURL);
            jsonObject.put("productYourRef", productYourRef);
            jsonObject.put("attach1URL", attach1URL);
            jsonObject.put("shortDesc", shortDesc);
            jsonObject.put("projectId", projectId);
            jsonObject.put("attach2URL", attach2URL);
            if(properties != null) jsonObject.put("properties", properties.toJSON());

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }
    }
}
