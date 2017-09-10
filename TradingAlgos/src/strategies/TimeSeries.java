package strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TimeSeries {
	
	static List<Integer> stockPriceList;
	static List<Integer> timeSeriesList;
	
	static int returnQueryResult(int type, int value) {
		int checkValue;		

		switch (type) {
		case 1:
			checkValue = stockPriceList.stream().filter(val -> val > value).findFirst().orElse(-1);
			if (checkValue > -1) {
				int index = stockPriceList.indexOf(checkValue);
				return timeSeriesList.get(index);
			} else
				return checkValue;
		case 2:
			checkValue = timeSeriesList.stream().filter(val -> val >= value).findFirst().orElse(-1);
			if (checkValue > -1) {
				int index = timeSeriesList.indexOf(checkValue);				
				return Collections.max(stockPriceList.subList(index, stockPriceList.size()));
			} else
				return checkValue;
		}
		return 0;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();		
		
		int q = in.nextInt();
		
		int[] t = new int[n];
		for (int t_i = 0; t_i < n; t_i++) {
			t[t_i] = in.nextInt();
		}
		
		timeSeriesList = Arrays.stream(t).boxed().collect(Collectors.toList());
		
		int[] p = new int[n];
		for (int p_i = 0; p_i < n; p_i++) {
			p[p_i] = in.nextInt();
		}
		
		stockPriceList = Arrays.stream(p).boxed().collect(Collectors.toList());
		
		ArrayList<String> queryList = new ArrayList<String>();
		
		for (int a0 = 0; a0 < q; a0++) {
			int _type = in.nextInt();
			int v = in.nextInt();
			queryList.add(_type + "," + v);			
		}

		in.close();
		
		queryList.stream()
		.forEach(eachEntry -> System.out.println(returnQueryResult(Integer.parseInt(eachEntry.split(",")[0]), Integer.parseInt(eachEntry.split(",")[1]))));
		
		//System.out.println(returnQueryResult(_type, v, t, p));
	}

}
