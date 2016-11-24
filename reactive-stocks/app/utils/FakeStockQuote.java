package utils;

import org.json.JSONObject;
import java.util.Random;

/**
 * Creates a randomly generated price based on the previous price
 */
public class FakeStockQuote implements StockQuote {
    Manager man = new Manager("testhost", "testport", "testcollection");
    int temp_chest = 0;
    int temp_back = 0;
    int temp_left = 1000;
    int temp_right = 1000;


    /*public Double newPrice(Double lastPrice) {
        // todo: this trends towards zero

        return lastPrice * (0.99  + (.02 * new Random().nextDouble())); // lastPrice * (0.99 to 1.02)

    }*/
    public Double firstPrice(Double symbol) {
        return 0.0;
    }
    public Double newPrice(String symbol){
        try {
//            System.out.println(symbol);
            if(symbol.equals("Chest")) {
                JSONObject json = man.addDoc((temp_chest++), 0);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                return Double.parseDouble(json.get("temp").toString());
            } else if(symbol.equals("Back")) {
                JSONObject json = man.addDoc((temp_back--), 1);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                return Double.parseDouble(json.get("temp").toString());
            }else if(symbol.equals("Left")) {
                JSONObject json = man.addDoc((temp_left++), 2);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                return Double.parseDouble(json.get("temp").toString());
            }else if(symbol.equals("Right")) {
                JSONObject json = man.addDoc((temp_right--), 3);
//                System.out.println(Double.parseDouble(json.get("temp").toString()));
                return Double.parseDouble(json.get("temp").toString());
            } else {
                JSONObject json = man.addDoc(0, 1);
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
