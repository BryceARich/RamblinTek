package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Random;

/**
 * Creates a randomly generated price based on the previous price
 */
public class FakeStockQuote implements StockQuote {
    int temp_chest = 0;
    int temp_back = 0;
    int temp_left = 1000;
    int temp_right = 1000;
    int temp_average = 500;
    public static XbeeStream instance;
    XbeeStream xbee = null;
    public Double firstPrice(Double symbol) {
        return 98.6;
    }
    public void getManager() {
        instance = XbeeStream.getInstance();
    }




    public Double newPrice(String symbol){
        getManager();

        try {
            while (instance.manager == null) {
                //System.out.println(symbol);
                //manager.getAllDocuments();
            }
            //System.out.println(symbol + " " + instance.manager);
            if(symbol.equals("Chest")) {
                //JSONObject json = man.addDoc((temp_chest++), 1);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                //return Double.parseDouble(json.get("temp").toString());
                return instance.manager.chestObj();
            }else if(symbol.equals("Left")) {
                //JSONObject json = man.addDoc((temp_left++), 2);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                //return Double.parseDouble(json.get("temp").toString());
                return instance.manager.leftObj();
            }else if(symbol.equals("Back")) {
                //JSONObject json = man.addDoc((temp_back--), 3);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                //return Double.parseDouble(json.get("temp").toString());
                return instance.manager.backObj();
            }else if(symbol.equals("Right")) {
                //JSONObject json = man.addDoc((temp_right--), 4);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                //return Double.parseDouble(json.get("temp").toString());
                return instance.manager.rightObj();
            } else if(symbol.equals("Average")){
                return instance.manager.averageObj();
            } else {
                //JSONObject json = man.addDoc(0, 1);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                return 50.0;// Double.parseDouble(json.get("temp").toString());
            }
            //return (double) (temp++);
        } catch (Exception e){
            e.printStackTrace();
            //System.out.println(e.getMessage());
            return 0.0;
        }
    }


    /**
     * public Double newPrice(String symbol) {
     *     1. get Json object from Manager
     *     2. get temp
     *     3. return temp
     *
     * }
     *
     */

}
