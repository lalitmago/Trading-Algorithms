package data.extraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.threeten.bp.*;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.Frequency;
import com.jimmoores.quandl.MetaDataRequest;
import com.jimmoores.quandl.MetaDataResult;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.SessionOptions;
import com.jimmoores.quandl.TabularResult;
import com.jimmoores.quandl.classic.ClassicQuandlSession;

public class ExtractData {

	public static void read()
	{
		SessionOptions sessionParameters = SessionOptions.Builder
				.withAuthToken("wyx9oz7nE2po9YVU9k-1")
				.build();
		
		ClassicQuandlSession session = ClassicQuandlSession.create(sessionParameters);
		
		System.out.println("Using auth token - " + sessionParameters.getAuthToken());
		
		LocalDate startDate = LocalDate.of(2017, Month.MAY, 1);
		
		DataSetRequest dsRequest = DataSetRequest.Builder
				.of("BSE/BOM500209")
				.withFrequency(Frequency.MONTHLY)
				.withStartDate(startDate)
				.build();
		
		TabularResult tabResult = session.getDataSet(dsRequest);
		//System.out.println(TabularResult.of(tabResult.getHeaderDefinition(), Arrays.asList(tabResult.get(3))).toPrettyPrintedString());
		
		System.out.println(tabResult.toPrettyPrintedString());
		
		MetaDataRequest mdRequest = MetaDataRequest.of("BSE/BOM500209");
		
		MetaDataResult mdResult = session.getMetaData(mdRequest);
		
		System.out.println();
		
		System.out.println(mdResult.toPrettyPrintedString());
		
		
		
	}
	
	
}
