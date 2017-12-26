package strategies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class IndexRebalancing {

	private static HashMap<String, Integer> portfolio;
	private static HashMap<String, String> stocksClosingPrices;
	private static HashMap<String, Long> stocksFreeFloat;	
	private static HashMap<Integer, Double> indexClosingPricesByDay;
	
	private static HashMap<String, Double> stocksCurrentClosingPrices;
	private static HashMap<String, Double> stocksMarketCapOnGivenDay;
	private static HashMap<String, Double> stocksWeights;	

	public static String readFile(File thisFile) {
		StringBuilder content = new StringBuilder("");
		String line = "";
		BufferedReader br;

		try {
			FileReader fr = new FileReader(thisFile);
			br = new BufferedReader(fr);

			line = br.readLine();
			while ((line = br.readLine()) != null) {
				content.append(line);
				content.append(";");
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	public static void loadMaps(String contents, String mapName) 
	{
		String[] rows = contents.split(";");

		switch (mapName) {
		case "portfolio":portfolio = (HashMap<String, Integer>) Arrays.asList(rows).stream()
				.map(eachRow -> eachRow.split(","))
				.collect(Collectors.toMap(eachRow -> eachRow[0], eachRow -> Integer.parseInt(eachRow[1])));			
			break;
		case "info":stocksFreeFloat = (HashMap<String, Long>) Arrays.asList(rows).stream().map(eachRow -> eachRow.split(","))
				.collect(Collectors.toMap(eachRow -> eachRow[0], eachRow -> (long) Double.parseDouble(eachRow[3])));			
			break;
		case "index":indexClosingPricesByDay = (HashMap<Integer, Double>) Arrays.asList(rows).stream().map(eachRow -> eachRow.split(","))
				.collect(Collectors.toMap(eachRow -> Integer.parseInt(eachRow[0]), eachRow -> Double.parseDouble(eachRow[1])));			
			break;
		case "stocks_closing":stocksClosingPrices = (HashMap<String, String>) Arrays.asList(rows).stream().map(eachRow -> eachRow.split(","))
				.collect(Collectors.toMap(eachRow -> eachRow[0], eachRow -> eachRow[1]));			
			break;			
		default: System.err.println("Incorrect Map Name!");
			break;
		}
		
	}
	
	public static void calculateMarketCap(int i)
	{
		stocksCurrentClosingPrices = (HashMap<String, Double>) stocksClosingPrices.entrySet()
				.stream()
				.collect(Collectors.toMap(eachEntry -> eachEntry.getKey(), eachEntry -> Double.parseDouble(eachEntry.getValue().split(",")[i])));		
		
		stocksMarketCapOnGivenDay = (HashMap<String, Double>) stocksCurrentClosingPrices.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, eachEntry -> eachEntry.getValue() * stocksFreeFloat.get(eachEntry.getKey())));
		
		Double totalMarketCap = stocksMarketCapOnGivenDay.values()
				.stream()
				.mapToDouble(Double::doubleValue).sum();
		
		stocksWeights = (HashMap<String, Double>) stocksMarketCapOnGivenDay.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, eachEntry -> eachEntry.getValue()/totalMarketCap));
		
		System.out.println(totalMarketCap);
		
		System.out.println(stocksWeights.values().stream().mapToDouble(Double::doubleValue).sum());
	}

	public static void main(String[] args) {

		File directory = new File(
				"C://Users/User/Desktop/Hackerrank NSE-ISB contest/Problem 2 - Portfolio maximisation/index_rebalance_dataset");
		File[] allFiles = directory.listFiles();

		String fileContents = "";

		for (File eachFile : allFiles) {
			fileContents = readFile(eachFile);

			if (eachFile.getName().contains("portfolio"))
				loadMaps(fileContents, "portfolio");

			if (eachFile.getName().contains("info"))
				loadMaps(fileContents, "info");

			if (eachFile.getName().contains("index"))
				loadMaps(fileContents, "index");
			
			if (eachFile.getName().contains("stocks_closing"))
				loadMaps(fileContents, "stocks_closing");
		}	
		
		calculateMarketCap(0);
		System.out.println();
		System.out.println(portfolio);
		
		Double investedAmount = portfolio.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, eachEntry -> eachEntry.getValue() * stocksCurrentClosingPrices.get(eachEntry.getKey())))
				.values()
				.stream()
				.mapToDouble(Double::doubleValue).sum();
		
		System.out.println();
		System.out.println(investedAmount);

	}

}
