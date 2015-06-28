package com.martinandersson.simplerecyclerviewapp.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by martin on 6/28/15.
 */
public class JSONModel implements Serializable {

    private static ObjectMapper sObjectMapper;

    public JSONModel() {

    }

    protected static ObjectMapper getObjectMapper() {
        if (sObjectMapper == null) {
            sObjectMapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        }
        return sObjectMapper;
    }

    /**
     * Wrapper call for converting a JSONObject into the target class.
     *
     * @param json
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T fromObject(JSONObject json, Class<T> targetClass) {
        return fromObject(json.toString(), targetClass);
    }

    /**
     * Wrapper call for converting a String representation of a JSONObject into the target class.
     *
     * @param jsonString
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T fromObject(String jsonString, Class<T> targetClass) {
        try {
            return getObjectMapper().readValue(jsonString, targetClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Wrapper call for converting a JSONArray into an ArrayList of the target class.
     *
     * @param jsonArray
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> fromArray(JSONArray jsonArray, Class<T> targetClass) {
        return fromArray(jsonArray.toString(), targetClass);
    }

    /**
     * Wrapper call for converting a String representation of a JSONArray into an ArrayList of the target class.
     *
     * @param jsonStringArray
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> fromArray(String jsonStringArray, Class<T> targetClass) {
        CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, targetClass);

        ArrayList<T> collection = null;
        try {
            collection = getObjectMapper().readValue(jsonStringArray, collectionType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return collection;
    }

    /**
     * Return this object as a JSONObject.
     *
     * @return JSONObject representation or null if errors are found.
     */
    public JSONObject asJSON() {
        JSONObject clientJSON = null;
        try {
            String clientJSONString = getObjectMapper().writeValueAsString(this);
            clientJSON = new JSONObject(clientJSONString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clientJSON;
    }

    /**
     * Write this object as JSON to the specified file.
     *
     * @param file
     */
    public void writeToFile(File file) {
        try {
            getObjectMapper().writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
