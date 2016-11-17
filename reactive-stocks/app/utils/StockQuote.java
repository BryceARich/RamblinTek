package utils;

public interface StockQuote {
    public Double newPrice(Double lastPrice);
    public Double newPrice(String symbol);
}
