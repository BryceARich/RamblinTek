package utils;

import com.google.inject.Singleton;
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
@Singleton
public class Manager {
    public String host;
    public String port;
    public String collectionName;
    public DefaultHttpClient httpClient;
    public String ADD_JSON;
    public String QUERY_ALL;
    public long START_TIME;
    public int sensor1StartTime = 0;
    public int sensor2StartTime = 1;
    public int sensor3StartTime = 2;
    public int sensor4StartTime = 3;
    public int sensor5StartTime = 4;
    public double lastLeft;
    public double lastRight;
    public double lastChest;
    public double lastAverage;
    public double lastBack;
    public static final double DEFAULT_TEMP = 98.6;

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

    public double getMostRecentChest() {
        return lastChest != 0 ? lastChest : DEFAULT_TEMP;
    }
    public double getMostRecentBack() {
        return lastBack != 0 ? lastBack : DEFAULT_TEMP;
    }
    public double getMostRecentLeft() {
        return lastLeft != 0 ? lastLeft : DEFAULT_TEMP;
    }
    public double getMostRecentRight() {
        return lastRight != 0 ? lastRight : DEFAULT_TEMP;
    }
    public double getMostRecentAverage() {
        return lastAverage != 0 ? lastAverage : DEFAULT_TEMP;
    }


    public JSONObject addDoc(double temp, int sensor) throws Exception{ // need to make sure Solr recognizes this as a double
        System.out.println("Adding doc temp = " + temp + " sensor = " + sensor);
        int id = 0;

        /**
         * 1 = Chest
         * 2 = Left
         * 3 = Back
         * 4 = Right
         * 5 = Average
        */
        if (sensor == 1) {
            sensor1StartTime += 5000;
            id = sensor1StartTime;
            lastChest = temp;

        } else if (sensor == 2) {
            sensor2StartTime += 5000;
            id = sensor2StartTime;
            lastLeft = temp;
        } else if (sensor == 3) {
            sensor3StartTime += 5000;
            id = sensor3StartTime;
            lastBack = temp;
        } else if (sensor == 4) {
            sensor4StartTime += 5000;
            id = sensor4StartTime;
            lastRight = temp;
        } else if (sensor == 5) {
            sensor5StartTime += 5000;
            id = sensor5StartTime;
            lastAverage = temp;
        }


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(ADD_JSON);

        StringEntity entity  = new StringEntity(
                "{\"add\": { \"doc\": {\"id\": \" "+  id + " \",\"temp\":"+ temp +", \"sensor\":"+sensor+"}}}", "UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        System.out.println(response);


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


       

        JSONArray arr = manager.getAllDocuments();
        for (int i = 0; i < arr.length();i++) {
            JSONObject o = (JSONObject) arr.get(i);
            System.out.println(o.toString());
        }

        System.out.println(arr.toString());
    }
}