package scanners;

import java.util.*;
import java.util.List;

import model.Stock;

public class GapUpOpening {

	private static int TODAY = 0;
	private static int YESTERDAY = 1;

	private static int OPEN = 0;
	private static int HIGH = 1;
	private static int LOW = 2;
	private static int CLOSE = 3;
	private static int VOLUMES = 4;
	
	public static volatile int noOfObservations = 0;
	public static volatile int noOfPositiveOutcomes = 0;
	public static volatile int totalPopulation = 0;

	public static List<String> scout(List<Stock> lstStocksAndData) {

		List<String> opportunities = new ArrayList<>();

		lstStocksAndData.parallelStream().forEach(eachStock -> {
			if (check(eachStock, YESTERDAY, TODAY)) {
					opportunities.add(eachStock.getStockCode());
					// System.out.println(eachStock.getHistoricalData());
				}
		});

		return opportunities;
	}

	public static double backtest(List<Stock> lstStocksAndData) {
		List<Stock> observations = new ArrayList<>();
		List<Stock> postiveOutcomes = new ArrayList<>();
		
		lstStocksAndData.parallelStream().forEach(eachStock -> {
			totalPopulation = totalPopulation + eachStock.getHistoricalData().rowKeyList().size();
			
			for (int i = eachStock.getHistoricalData().rowKeyList().size() - 1; i > 1; i--) {
				if(check(eachStock, i, i - 1)) {
					noOfObservations = noOfObservations + 1;
					observations.add(eachStock);
					System.out.println("Observation : " + eachStock.getStockCode() + " tDay : " + eachStock.getHistoricalData().rowKeyList().get(i - 1) + ", tMinusOneDay : " + eachStock.getHistoricalData().rowKeyList().get(i));
					/*System.out.println(eachStock.getStockCode() + " tDay : " + eachStock.getHistoricalData().rowKeyList().get(i - 1) + ", tMinusOneDay : " + eachStock.getHistoricalData().rowKeyList().get(i));
					System.out.println("Volumes(t) : " + eachStock.getHistoricalData().at(i - 1, VOLUMES) + ", Volumes(t-1) : " + eachStock.getHistoricalData().at(i, VOLUMES));
					System.out.println("High(t-1) : " + eachStock.getHistoricalData().at(i, HIGH) + ", Low(t) : " + eachStock.getHistoricalData().at(i - 1, LOW));
					System.out.println("Open(t) : " + eachStock.getHistoricalData().at(i - 1, OPEN) + ", Close(t) : " + eachStock.getHistoricalData().at(i - 1, CLOSE));
					System.out.println("Open(t + 1) : " + eachStock.getHistoricalData().at(i - 2, OPEN) + ", Close(t + 1) : " + eachStock.getHistoricalData().at(i - 2, CLOSE));*/
					if(eachStock.getHistoricalData().at(i - 2, OPEN) > (eachStock.getHistoricalData().at(i - 2, CLOSE))) {
						noOfPositiveOutcomes = noOfPositiveOutcomes + 1;
						postiveOutcomes.add(eachStock);
						System.out.println("Positive Outcome : " + eachStock.getStockCode() + " tDay : " + eachStock.getHistoricalData().rowKeyList().get(i - 1) + ", tMinusOneDay : " + eachStock.getHistoricalData().rowKeyList().get(i));
					}
					else {
						System.out.println("Loss Trade : " + eachStock.getStockCode() + " tDay : " + eachStock.getHistoricalData().rowKeyList().get(i - 1) + ", tMinusOneDay : " + eachStock.getHistoricalData().rowKeyList().get(i));
					}
				}
			}
		});		
		
		System.out.println("No of Positive Outcomes : " + noOfPositiveOutcomes + ", from the list : " + postiveOutcomes.size());
		System.out.println(postiveOutcomes);
		System.out.println("No of Pattern Observations : " + noOfObservations + ", from the list : " + observations.size());
		System.out.println(observations);
		System.out.println("Total Population : " + totalPopulation);
		
		return (double) noOfPositiveOutcomes/(double) noOfObservations;
	}

	private static boolean check(Stock stock, int tMinusOneDay, int tDay) {
		if ((stock.getHistoricalData().at(tDay, VOLUMES) > 50000) && (stock.getHistoricalData().at(tDay,
				VOLUMES) > stock.getHistoricalData().at(tMinusOneDay, VOLUMES)))
			return (stock.getHistoricalData().at(tMinusOneDay, HIGH) < stock.getHistoricalData().at(tDay, LOW))
					&& (stock.getHistoricalData().at(tDay, OPEN) > stock.getHistoricalData().at(tDay,
					CLOSE));
		return false;
	}
}

/*System.out.println(stock.getStockCode() + " tDay : " + stock.getHistoricalData().rowKeyList().get(tDay) + ", tMinusOneDay : " + stock.getHistoricalData().rowKeyList().get(tMinusOneDay));
System.out.println("Volumes(t) : " + stock.getHistoricalData().at(tDay, VOLUMES) + ", Volumes(t-1) : " + stock.getHistoricalData().at(tMinusOneDay, VOLUMES));
System.out.println("High(t-1) : " + stock.getHistoricalData().at(tMinusOneDay, HIGH) + ", Low(t) : " + stock.getHistoricalData().at(tDay, LOW));
System.out.println("Open(t) : " + stock.getHistoricalData().at(tDay, OPEN) + ", Close(t) : " + stock.getHistoricalData().at(tDay, CLOSE));*/

/*System.out.println(eachStock.getStockCode() + " tDay : " + eachStock.getHistoricalData().rowKeyList().get(i - 1) + ", tMinusOneDay : " + eachStock.getHistoricalData().rowKeyList().get(i));
System.out.println("Volumes(t) : " + eachStock.getHistoricalData().at(i - 1, VOLUMES) + ", Volumes(t-1) : " + eachStock.getHistoricalData().at(i, VOLUMES));
System.out.println("High(t-1) : " + eachStock.getHistoricalData().at(i, HIGH) + ", Low(t) : " + eachStock.getHistoricalData().at(i - 1, LOW));
System.out.println("Open(t) : " + eachStock.getHistoricalData().at(i - 1, OPEN) + ", Close(t) : " + eachStock.getHistoricalData().at(i - 1, CLOSE));
System.out.println("Open(t + 1) : " + eachStock.getHistoricalData().at(i - 2, OPEN) + ", Close(t + 1) : " + eachStock.getHistoricalData().at(i - 2, CLOSE));*/
