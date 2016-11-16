package utils;

import java.util.Random;

/**
 * Creates a randomly generated price based on the previous price
 */
public class FakeStockQuote implements StockQuote {

    public Double newPrice(Double lastPrice) {
        // todo: this trends towards zero
        return lastPrice * (0.99  + (.02 * new Random().nextDouble())); // lastPrice * (0.99 to 1.02)
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
