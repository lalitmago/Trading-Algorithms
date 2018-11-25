package model;


import org.threeten.bp.LocalDate;
import com.google.common.collect.ArrayTable;

public class Stock {

    private String stockCode;
    private ArrayTable<LocalDate, String, Double> historicalData;

    public Stock(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public ArrayTable<LocalDate, String, Double> getHistoricalData() {
        return historicalData;
    }

    public void setHistoricalData(ArrayTable<LocalDate, String, Double> historicalData) {
        this.historicalData = historicalData;
    }

    @Override
    public String toString() {
        //return "Stock Code : " + this.stockCode + " with " + this.historicalData.size() + " days of data.";
        return this.stockCode;
    }


}
