package br.com.bg7.appvistoria.database;

import org.json.JSONObject;

import java.util.List;

import br.com.bg7.appvistoria.core.Util;
import br.com.bg7.appvistoria.vo.Product;
import br.com.bg7.appvistoria.vo.Properties;
import br.com.bg7.appvistoria.vo.Property;
import br.com.bg7.appvistoria.vo.PropertyList;

/**
 * Created by elison on 05/07/17.
 */

public class ProductDB {


    /**
     * Save product Item based on JSON String
     * @param json
     */
    public Product save(String json) {
        try {
            Product product = Product.fromJson(new JSONObject(json));
            if (product != null) {
                Util.Log.i("Sucesso! " + product.getProductYourRef());

                Properties properties = product.getProperties();

                product.save();             // Save product in database
                long productId = product.getId();
                properties.setProductId(productId);

                List<PropertyList> propertyGroupList = properties.getPropertyGroupList();


                properties.save();          // Save properties in database
                long propertiesId = properties.getId();

                for (PropertyList pList : propertyGroupList) {
                    pList.setPropertiesId(propertiesId);
                    pList.save();           // save propertyList in database

                    long propertyListId = pList.getId();

                    List<Property> propertyList = pList.getPropertyList();

                    for (Property property : propertyList) {
                        property.setPropertyListId(propertyListId);
                        property.save();        // save property in database
                    }
                }

            }
            return product;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * Delete product Item based on Product object
     * @param json
     */
    public boolean delete(Product obj) {

        try {
            Properties properties = obj.getProperties();

            List<PropertyList>  listProperties =  properties.getPropertyGroupList();
            for(PropertyList list: listProperties ) {

                List<Property> propertys = list.getPropertyList();

                for(Property property: propertys) {
                    property.delete();
                }
                list.delete();

            }
            properties.delete();
            obj.delete();
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
    }

    /**
     * Get all Products from DB
     * @return
     */
    public List<Product> getAll() {
        List<Product> items = Product.listAll(Product.class);
        if(items != null && items.size() > 0) {

            for(Product product: items) {

                List<Properties> properties = Properties.find(Properties.class, "product_id = ?", String.valueOf(product.getId()));

                if (properties != null && properties.size() > 0) {
                    product.setProperties(properties.get(0));

                    List<PropertyList> propertyGroupList = PropertyList.find(PropertyList.class, "properties_id = ?", String.valueOf(product.getProperties().getId()));

                    if (propertyGroupList != null && propertyGroupList.size() > 0) {
                        product.getProperties().setPropertyGroupList(propertyGroupList);

                        for (PropertyList propertyList : product.getProperties().getPropertyGroupList()) {

                            List<Property> propertys = Property.find(Property.class, "property_list_id = ?", String.valueOf(propertyList.getId()));

                            if (propertys != null && propertys.size() > 0) {
                                propertyList.setPropertyList(propertys);
                            }
                        }
                    }
                }
            }
        }

        return items;

    }

}
