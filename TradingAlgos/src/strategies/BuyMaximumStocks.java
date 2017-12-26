package strategies;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuyMaximumStocks {

	static long buyMaximumProducts(int n, long k, int[] a) {

		long maxCountOfStocks = 0L;
		long purchasePrice = 0L;	
		int temp = 0;
		
		ArrayList<Integer> lstStockPrices = (ArrayList<Integer>) Arrays.stream(a)
				.boxed()
				.collect(Collectors.toList());		
		
		HashMap<Integer, Integer> stockPrices = new HashMap<Integer, Integer>();
		
		stockPrices = (HashMap<Integer, Integer>) IntStream.range(0, lstStockPrices.size())
				.boxed()
				.collect(Collectors.toMap(i -> (i + 1), i -> lstStockPrices.get(i)));
		
		LinkedHashMap<Integer, Integer> mapStockPrices = stockPrices
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(i -> i.getKey(), i -> i.getValue(), (oldValue, newValue) -> oldValue,LinkedHashMap::new));
		/*
		System.out.println(stockPrices);
		System.out.println(mapStockPrices);*/
		
		for(Integer day: mapStockPrices.keySet())
		{
			purchasePrice = day * mapStockPrices.get(day);
			
			if (purchasePrice <= k) {
				maxCountOfStocks = maxCountOfStocks + day;
				k = k - purchasePrice;
			}
			else 
			{	
				temp = day;
				
				while (temp > 1) 
				{
					temp--;
					purchasePrice = temp * mapStockPrices.get(day);
					if (purchasePrice <= k) {
						maxCountOfStocks = maxCountOfStocks + temp;
						k = k - purchasePrice;
						break;
					}					
					
				}				
				if(temp == 1)
					return maxCountOfStocks;
			}
		}
		
		return maxCountOfStocks;		
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] arr = new int[n];
		for (int arr_i = 0; arr_i < n; arr_i++) {
			arr[arr_i] = in.nextInt();
		}
		long k = in.nextLong();
		long result = buyMaximumProducts(n, k, arr);
		System.out.println(result);
		in.close();
	}

}