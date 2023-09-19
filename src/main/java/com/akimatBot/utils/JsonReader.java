package com.akimatBot.utils;

import com.jayway.jsonpath.JsonPath;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

@Getter
@Setter
public class JsonReader {
    private String longitude;
    private String latitude;
    private String url = "https://geocode-maps.yandex.ru/1.x?format=json&lang=ru_RU&kind=house&geocode=";
    private final String API = "fbd8181e-6b02-4764-bfef-fc1a12f7c045";

    //    private String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
//    private final String API = "AIzaSyARrdm2oRC22Bwd0-mmGwzlxVVmosgv7Uo";
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //    https://geocode-maps.yandex.ru/1.x?format=json&lang=ru_RU&kind=house&geocode=37.617585,55.751903&apikey=ТОКЕН
    //https://maps.googleapis.com/maps/api/geocode/json?latlng=44.4647452,7.3553838&key=YOUR_API_KEY
    public JSONObject readJsonFromUrl() throws IOException, JSONException {
        url += longitude + "," + latitude + "&apikey=" + API + "&language=ru-RU";
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public String getFormattedAddress() throws IOException {
        JSONObject jsonObject = readJsonFromUrl();
//        getAttribute(jsonObject, "$.respo")
        return getAttribute(jsonObject, "$.response.GeoObjectCollection.featureMember[0].GeoObject.metaDataProperty.GeocoderMetaData.AddressDetails.Country.AddressLine");

//        return getAttribute(jsonObject, "$.results[0].formatted_address");
    }

    public String getAttribute(JSONObject json, String path) {
        return JsonPath.read(json.toString(), path);
    }
//    public static void main(String[] args) throws IOException, JSONException {
//        JSONObject json = readJsonFromUrl("https://graph.facebook.com/19292868552");
//        System.out.println(json.toString());
//        System.out.println(json.get("id"));
//    }
}
