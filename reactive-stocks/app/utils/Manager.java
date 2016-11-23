package utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;

/**
 *
 *
 * Solr cloud will have one table of sensors
 *
 */

public class Manager {
    public String host;
    public String port;
    public String collectionName;
    public DefaultHttpClient httpClient;
    public String ADD_JSON;
    public String QUERY_ALL;
    public long START_TIME;
    public int sensor1StartTime = 0;
    public int sensor2StartTime = 0;
    public int sensor3StartTime = 0;
    public int sensor4StartTime = 0;


    public Manager(String h, String p, String cN) {
        XbeeStream stream = new XbeeStream();
        host = h;
        port = p;
        collectionName = cN;
        httpClient = new DefaultHttpClient();
        createAddJson();
        createQueryAll();
        START_TIME = System.nanoTime();
    }

    public void createAddJson() {
        ADD_JSON = "http://"+host+":"+port+"/solr/"+collectionName+"/update/json?wt=json&commit=true";
    }

    public void createQueryAll() {
        QUERY_ALL = "http://"+host+":"+port+"/solr/"+collectionName+"/select?indent=on&q=*:*&rows=10000&start=0&wt=json";
    }


    public JSONObject addDoc(int temp, int sensor) throws Exception{

        int id = 0;

        if (sensor == 1) {
            id = sensor1StartTime + 5000;
        } else if (sensor == 2) {
            id = sensor2StartTime + 5000;
        } else if (sensor == 3) {
            id = sensor3StartTime+ 5000;
        } else if (sensor == 4) {
            id = sensor4StartTime + 5000;
        }

/*
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(ADD_JSON);

        StringEntity entity  = new StringEntity(
                "{\"add\": { \"doc\": {\"id\": \" "+  id + " \",\"temp\":"+ temp +", \"sensor\":"+sensor+"}}}", "UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        System.out.println(response);
*/

        JSONObject obj = new JSONObject("{\"id\": \" "+  id + " \",\"temp\":"+ temp +", \"sensor\":"+sensor+"}");
        return obj;
    }


    public JSONArray getAllDocuments() throws Exception {

        String url = QUERY_ALL;

        httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header

        ArrayList<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "100000"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = httpClient.execute(post);

        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        JSONObject jo = new JSONObject(result.toString());
        JSONObject docs = (JSONObject) jo.get("response");
        JSONArray documents = (JSONArray) docs.get("docs");
        return documents;
    }


    public static void main(String[] args) throws Exception{

        Manager manager = new Manager("localhost", "8983", "trial");

        //Call XBEE connection

        /**
         * while (connection is active && time is less than 25 minutes)
         *     parseData(Xbee connection) -> return a pair of values [temp and sensor]
         *     manager.addDoc(temp, sensor);
         *
         *     https://github.com/andrewrapp/xbee-api
         */


        manager.addDoc(35, 1);
        manager.addDoc(36, 1);
        manager.addDoc(37, 1);
        manager.addDoc(38, 1);
        manager.addDoc(37, 1);
        manager.addDoc(36, 1);

        JSONArray arr = manager.getAllDocuments();
        for (int i = 0; i < arr.length();i++) {
            JSONObject o = (JSONObject) arr.get(i);
            System.out.println(o.toString());
        }

        System.out.println(arr.toString());
    }
}