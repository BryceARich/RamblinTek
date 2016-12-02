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
import scala.util.parsing.json.JSON;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public static double lastLeft;
    public static double lastRight;
    public static double lastChest;
    public static double lastBack;
    public static double lastAverage;
    public static final double DEFAULT_TEMP = 98.6;
    public static ArrayList<JSONObject> chestArr;
    public static ArrayList<JSONObject> leftArr = null;
    public static ArrayList<JSONObject> rightArr = null;
    public static ArrayList<JSONObject> backArr = null;
    public static ArrayList<JSONObject> averageArr;
    public static int chestIterator = 0;
    public static int leftIterator = 0;
    public static int rightIterator = 0;
    public static int backIterator = 0;
    public static int averageIterator = 0;
    boolean processed = false;
    public Manager(String h, String p, String cN) {
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
        //System.out.println("Adding doc temp = " + temp + " sensor = " + sensor);
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
        //System.out.println(response);


        JSONObject obj = new JSONObject("{\"id\": \" "+  id + " \",\"temp\":"+ temp +", \"sensor\":"+sensor+"}");
        return obj;
    }


    public synchronized boolean getAllDocuments() throws Exception {
        if (processed) { return false; }
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

        //System.out.println("Response Code1 : " +
        //        response.getStatusLine().getStatusCode());

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
        for (int i = 0; i < documents.length(); i++) {
            JSONObject obj = (JSONObject) documents.get(i);
            int s = (int) obj.get("sensor");
            if (s == 1) {
                if(chestArr ==null){
                    chestArr = new ArrayList<JSONObject>();
                }
                chestArr.add(obj);
            } else if (s == 2) {
                if(leftArr ==null){
                    leftArr = new ArrayList<JSONObject>();
                }
                leftArr.add(obj);
            } else if (s == 3) {
                if(backArr ==null){
                    backArr = new ArrayList<JSONObject>();
                }
                backArr.add(obj);
            } else if (s == 4) {
                if(rightArr ==null){
                    rightArr = new ArrayList<JSONObject>();
                }
                rightArr.add(obj);
            } else if (s == 2) {
                if(averageArr ==null){
                    averageArr = new ArrayList<JSONObject>();
                }
                averageArr.add(obj);
            }
        }

        Collections.sort(chestArr, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String s1 = (String) o1.get("id");
                String s2 = (String) o2.get("id");
                return Integer.parseInt(s1) - Integer.parseInt(s2);             }
        });

        Collections.sort(leftArr, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String s1 = (String) o1.get("id");
                String s2 = (String) o2.get("id");
                return Integer.parseInt(s1) - Integer.parseInt(s2);             }
        });

        Collections.sort(rightArr, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String s1 = (String) o1.get("id");
                String s2 = (String) o2.get("id");
                return Integer.parseInt(s1) - Integer.parseInt(s2);             }
        });
        Collections.sort(backArr, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String s1 = (String) o1.get("id");
                String s2 = (String) o2.get("id");
                return Integer.parseInt(s1) - Integer.parseInt(s2);             }
        });
        Collections.sort(averageArr, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String s1 = (String) o1.get("id");
                String s2 = (String) o2.get("id");
                return Integer.parseInt(s1) - Integer.parseInt(s2);             }
        });

        processed = true;
        return true;
    }

    public static double chestObj() {
        if (chestArr == null) { return 0.0; }
        if (chestIterator >= chestArr.size()) {
            return lastChest;
        }

        JSONObject obj = chestArr.get(chestIterator++);
        //System.out.println(obj.toString());
        lastChest = obj.getDouble("temp");
        return lastChest;
    }

    public static double leftObj() {
        if (leftArr == null) { return 0.0; }
        if (leftIterator >= leftArr.size()) {
            return lastLeft;
        }
        JSONObject obj =  leftArr.get(leftIterator++);
        lastLeft = obj.getDouble("temp");
        return lastLeft;
    }

    public static double rightObj() {
        if (rightArr == null) { return 0.0; }
        if (rightIterator >= rightArr.size()) {
            return lastRight;
        }

        JSONObject obj = rightArr.get(rightIterator++);
        lastRight= obj.getDouble("temp");
        return lastRight;
    }

    public static double backObj() {
        if (backArr == null) { return 0.0; }
        if (backIterator >= backArr.size()) {
            return lastBack;
        }

        JSONObject obj = backArr.get(backIterator++);
        lastBack = obj.getDouble("temp");
        return lastBack;
    }

    public static double averageObj() {
        if (averageArr == null) { return 0.0; }
        if (averageIterator >= averageArr.size()) {
            return lastAverage;
        }

        JSONObject obj = averageArr.get(averageIterator++);
        //System.out.println(obj.toString());
        lastAverage = obj.getDouble("temp");
        return lastAverage;
    }
    public static void main(String[] args) throws Exception{

        Manager manager = new Manager("localhost", "8983", "test");

        //Call XBEE connection

        /**
         * while (connection is active && time is less than 25 minutes)
         *     parseData(Xbee connection) -> return a pair of values [temp and sensor]
         *     manager.addDoc(temp, sensor);
         *
         *     https://github.com/andrewrapp/xbee-api
         */



    }
}