package data.extraction;

import org.threeten.bp.*;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.Frequency;
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
		
		TabularResult result = session.getDataSet(dsRequest);
		System.out.println(result.toPrettyPrintedString());
	}
	
	
}
