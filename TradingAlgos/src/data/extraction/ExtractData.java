package data.extraction;

import java.util.ArrayList;
import java.util.List;

import org.threeten.bp.*;
import org.threeten.bp.format.DateTimeFormatter;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Lists;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.Frequency;
import com.jimmoores.quandl.MetaDataRequest;
import com.jimmoores.quandl.MetaDataResult;
import com.jimmoores.quandl.TabularResult;
import com.jimmoores.quandl.classic.ClassicQuandlSession;

import model.Stock;
import utils.*;

public class ExtractData {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	static List<Stock> read(ArrayList<String> listOfStockCodes, LocalDate startDate, LocalDate endDate) {

		ClassicQuandlSession session = SessionUtility.getSessionId();
		
		LocalDate eachDate;
		
		ArrayTable<LocalDate, String, Double> data;
		Stock stock;
		List<Stock> lstStocksAndData = new ArrayList<>();
		
		List<LocalDate> rows;
		List<String> columns = Lists.newArrayList("Open", "High", "Low", "Close", "Volumes");

		DataSetRequest dsRequest;
		TabularResult tabResult;

		for (String eachStockCode : listOfStockCodes) {
			
			dsRequest = DataSetRequest.Builder
					.of(eachStockCode)
					.withFrequency(Frequency.DAILY)
					.withStartDate(startDate)
					.withEndDate(endDate)
					.build();

			tabResult = session.getDataSet(dsRequest);

			rows = Lists.newArrayListWithCapacity(tabResult.size());

			if (rows.isEmpty())
				for (int i = 0; i < tabResult.size(); i++)
					rows.add(LocalDate.parse(tabResult.get(i).getString("Date"), formatter));

			if(rows.size() == 0)
				continue;
			
			data = ArrayTable.create(rows, columns);

			for (int i = 0; i < tabResult.size(); i++) {
				eachDate = LocalDate.parse(tabResult.get(i).getString("Date"), formatter);
				data.put(eachDate, "Open", Double.parseDouble(tabResult.get(i).getString("Open")));
				data.put(eachDate, "High", Double.parseDouble(tabResult.get(i).getString("High")));
				data.put(eachDate, "Low", Double.parseDouble(tabResult.get(i).getString("Low")));
				data.put(eachDate, "Close", Double.parseDouble(tabResult.get(i).getString("Close")));
				data.put(eachDate, "Volumes", Double.parseDouble(tabResult.get(i).getString("Total Trade Quantity")));
			}

			stock = new Stock(eachStockCode);
			stock.setHistoricalData(data);
			lstStocksAndData.add(stock);			
			
			//System.out.println(data);
			
			/*for(LocalDate itr : data.rowKeyList()) {
				System.out.println(data.get(itr, "Open"));
			}*/

			//System.out.println(stock.getHistoricalData());
			//System.out.println();
		}
		
		return lstStocksAndData;
	}
	
	public static void readAndPrint(ArrayList<String> lstStockCodes, LocalDate startDate, LocalDate endDate) {
		
		ClassicQuandlSession session = SessionUtility.getSessionId();		

		DataSetRequest dsRequest;
		TabularResult tabResult;

		for (String eachStockCode : lstStockCodes) {
			
			dsRequest = DataSetRequest.Builder
					.of(eachStockCode)
					.withFrequency(Frequency.DAILY)
					.withStartDate(startDate)
					.withEndDate(endDate)
					.build();

			tabResult = session.getDataSet(dsRequest);
			System.out.println("********" + eachStockCode + "********");
			System.out.println(tabResult.toPrettyPrintedString());
		}
		
	}

	public static void read() {

		ClassicQuandlSession session = SessionUtility.getSessionId();

		LocalDate startDate = LocalDate.of(2018, Month.FEBRUARY, 22);

		DataSetRequest dsRequest;
		TabularResult tabResult;

		dsRequest = DataSetRequest.Builder.of("BSE/BOM500209").withFrequency(Frequency.DAILY).withStartDate(startDate)
				.build();

		tabResult = session.getDataSet(dsRequest);

		System.out.println(tabResult.toPrettyPrintedString());

		MetaDataRequest mdRequest = MetaDataRequest.of("BSE/BOM500209");

		MetaDataResult mdResult = session.getMetaData(mdRequest);

		System.out.println();

		System.out.println(mdResult.toPrettyPrintedString());

	}

}
