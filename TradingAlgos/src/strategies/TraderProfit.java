package strategies;

import java.util.*;
import java.util.stream.Collectors;



public class TraderProfit {
	
	static int traderProfit(int k, int n, int[] A) {
        
		ArrayList<Integer> lstStockPrices = (ArrayList<Integer>) Arrays.stream(A)
				.boxed()
				.collect(Collectors.toList());
		
		LinkedHashMap<Integer, Integer> mapLowPrices;	
		
		int subListStartIndex = 0;
		int subListEndIndex = 0;
		
		int highPrice = 0;
		int lowPrice = 0;
		
		int profit = 0;
		
		for(int i = 1; (i <= k) && (lstStockPrices.size() > 0); i++)
		{			
			subListEndIndex = lstStockPrices.indexOf(Collections.max(lstStockPrices));
			
			highPrice = Collections.max(lstStockPrices);			
			lowPrice = highPrice;			
			
			for(int j = subListEndIndex; j >= 0; j--)
			{
				if(lstStockPrices.get(j) <= lowPrice)
				{
					lowPrice = lstStockPrices.get(j);
					subListStartIndex = j;					
				}
				else
					break;
			}
			
			/*subListStartIndex = lstStockPrices
					.indexOf(Collections
							.min(lstStockPrices.subList(subListStartIndex, subListEndIndex)));*/
			
			
			//lowPrice = Collections.min(lstStockPrices.subList(subListStartIndex, subListEndIndex));
			
			profit = profit + (highPrice - lowPrice);
			//System.out.println(subListStartIndex + " - " + subListEndIndex);			
			
			lstStockPrices.subList(subListStartIndex, subListEndIndex + 1).clear();
			
			//System.out.println(lstStockPrices.size());
			subListStartIndex = 0;
			subListEndIndex = 0;
			
		}
		
		return profit;
    }

	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int k = in.nextInt();
            int n = in.nextInt();
            int[] arr = new int[n];
            for(int arr_i = 0; arr_i < n; arr_i++){
                arr[arr_i] = in.nextInt();
            }
            int result = traderProfit(k, n, arr);
            System.out.println(result);
        }
        in.close();
    }

}
