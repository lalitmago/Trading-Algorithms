package data.extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

import model.Stock;
import scanners.GapUpOpening;

public class ExecutionClass {
	
	private static String fileName = "data/NSE-MIS-List.txt";
	
	private static LocalDate startDate = LocalDate.of(2017, Month.JANUARY, 1);
	private static LocalDate endDate = LocalDate.of(2017, Month.JUNE, 30);
	
	public static void main(String[] args) {
		
		long startTime = System.nanoTime();
		
		String line;
		
		ArrayList<String> listOfStockCodes = new ArrayList<>();
		BufferedReader br = null;
		
		try {
			FileReader fr = new FileReader(new File(fileName));
			br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null) {
				listOfStockCodes.add(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		finally {
			if(br!=null)
				try {
					br.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
		}	
		
		//ExtractData.readAndPrint(listOfStockCodes, startDate, endDate);
		
		List<Stock> lstStocksAndData = ExtractData.read(listOfStockCodes, startDate, endDate);
		System.out.println("Data for " + lstStocksAndData.size() + " stocks collected.");
		System.out.println("Latest date : " + lstStocksAndData.get(0).getHistoricalData().rowKeyList().get(0));
		int size = lstStocksAndData.get(0).getHistoricalData().rowKeyList().size();
		System.out.println("Earliest date : " + lstStocksAndData.get(0).getHistoricalData().rowKeyList().get(size - 1));
		//System.out.println(GapUpOpening.scout(lstStocksAndData));
		
		System.out.println("Success rate for the duration of Jan-Mar : " + GapUpOpening.backtest(lstStocksAndData));
		
		long endTime = System.nanoTime();
		System.out.println("Total time taken : " + (endTime - startTime)/1000000 + " milliseconds...");
		
		System.out.println();
		System.out.println("Positive Outcomes : " + GapUpOpening.noOfPositiveOutcomes);
		System.out.println("Observations : " + GapUpOpening.noOfObservations);
		System.out.println("Population : " + GapUpOpening.totalPopulation);

	}

}
