package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by elison on 27/06/17.

 {
 "modelId": 273,
//   "detailedDescCompl": "Predovic, Predovic and PredovicProduct",
//    "photoIllustrative": true,
              "detailedDesc": "Expanded 5th generation workforce",
                      "inConditions": true,
                      "miniPhoto": "http://www.superbid.com.br/photos/img36-20000426-115811.jpg",
                      "subCategoryId": 10004,
                      "cityId": 9183,
                      "photoUrl": ["PHOTO 1"],
                      "sellerId": 8684,
                      "descURL": "http://www.superbid.com.br/photos/img36-20000426-115811.jpg",
                      "productYourRef": "xxxxxxxxx",
                      "attach1URL": "http://www.superbid.com.br/photos/img36-20000426-115811.jpg",
                      "shortDesc": "car full-range",
                      "projectId": 19835,
                      "attach2URL": "http://www.superbid.com.br/photos/img36-20000426-115811.jpg",
*/


public class Product extends SugarRecord<Product> {

       int modelId;
       String detailedDescCompl;
       boolean photoIllustrative;
       String detailedDesc;
       boolean inConditions;
       String miniPhoto;
       int subCategoryId;
       int cityId;
       List<String> photoUrl;
       int sellerId;
       String descURL;
       String productYourRef;
       String attach1URL;
       String shortDesc;
       int projectId;
       String attach2URL;
       Properties properties;


    public Product() {
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getDetailedDescCompl() {
        return detailedDescCompl;
    }

    public void setDetailedDescCompl(String detailedDescCompl) {
        this.detailedDescCompl = detailedDescCompl;
    }

    public boolean isPhotoIllustrative() {
        return photoIllustrative;
    }

    public void setPhotoIllustrative(boolean photoIllustrative) {
        this.photoIllustrative = photoIllustrative;
    }

    public String getDetailedDesc() {
        return detailedDesc;
    }

    public void setDetailedDesc(String detailedDesc) {
        this.detailedDesc = detailedDesc;
    }

    public boolean isInConditions() {
        return inConditions;
    }

    public void setInConditions(boolean inConditions) {
        this.inConditions = inConditions;
    }

    public String getMiniPhoto() {
        return miniPhoto;
    }

    public void setMiniPhoto(String miniPhoto) {
        this.miniPhoto = miniPhoto;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public List<String> getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(List<String> photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getDescURL() {
        return descURL;
    }

    public void setDescURL(String descURL) {
        this.descURL = descURL;
    }

    public String getProductYourRef() {
        return productYourRef;
    }

    public void setProductYourRef(String productYourRef) {
        this.productYourRef = productYourRef;
    }

    public String getAttach1URL() {
        return attach1URL;
    }

    public void setAttach1URL(String attach1URL) {
        this.attach1URL = attach1URL;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getAttach2URL() {
        return attach2URL;
    }

    public void setAttach2URL(String attach2URL) {
        this.attach2URL = attach2URL;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    // Decodes model json into Model model object
    public static Product fromJson(JSONObject jsonObject) {
        Product b = new Product();
        // Deserialize json into object fields
        try {
            b.modelId = jsonObject.getInt("modelId");
            b.detailedDescCompl = jsonObject.getString("detailedDescCompl");
            b.photoIllustrative = jsonObject.getBoolean("photoIllustrative");
            b.detailedDesc = jsonObject.getString("detailedDesc");
            b.inConditions = jsonObject.getBoolean("inConditions");
            b.miniPhoto = jsonObject.getString("miniPhoto");
            b.subCategoryId = jsonObject.getInt("subCategoryId");
            b.cityId = jsonObject.getInt("cityId");
       //     b.photoUrl = jsonObject.getString("photoUrl");
            b.sellerId = jsonObject.getInt("sellerId");
            b.descURL = jsonObject.getString("descURL");
            b.productYourRef = jsonObject.getString("productYourRef");
            b.attach1URL = jsonObject.getString("attach1URL");
            b.shortDesc = jsonObject.getString("shortDesc");
            b.projectId = jsonObject.getInt("projectId");
            b.attach2URL = jsonObject.getString("attach2URL");

            if(jsonObject.has("properties")) {
                Properties p  = Properties.fromJson(jsonObject.getJSONObject("properties"));
                b.properties = p;
            }




        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }





}
