package utils;

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


    public Double firstPrice(Double symbol) {
        return 98.6;
    }
    public Double newPrice(String symbol){
        Manager manager = null;
        while (manager == null) {
            manager = XbeeStream.getManager();
        }
        try {
//            System.out.println(symbol);
            //symbol = "x";
            if(symbol.equals("Chest")) {
                //JSONObject json = man.addDoc((temp_chest++), 1);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                //return Double.parseDouble(json.get("temp").toString());
                return manager.getMostRecentChest();
            }else if(symbol.equals("Left")) {
                //JSONObject json = man.addDoc((temp_left++), 2);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                //return Double.parseDouble(json.get("temp").toString());
                return manager.getMostRecentLeft();
            }else if(symbol.equals("Back")) {
                //JSONObject json = man.addDoc((temp_back--), 3);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                //return Double.parseDouble(json.get("temp").toString());
                return manager.getMostRecentBack();
            }else if(symbol.equals("Right")) {
                //JSONObject json = man.addDoc((temp_right--), 4);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                //return Double.parseDouble(json.get("temp").toString());
                return manager.getMostRecentRight();
            } else {
                //JSONObject json = man.addDoc(0, 1);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                return 50.0;// Double.parseDouble(json.get("temp").toString());
            }
            //return (double) (temp++);
        } catch (Exception e){
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
