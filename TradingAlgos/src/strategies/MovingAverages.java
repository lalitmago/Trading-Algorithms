package strategies;

import java.text.DecimalFormat;
import java.util.*;

public class MovingAverages {
	
	private static int shortTerm = 60;
	private static int longTerm = 300;
	
	private static List<Double> prepareMovingAverageList(Double[] entryList, int term)
	{
		List<Double> listOfIntegers = new ArrayList<Double>(Arrays.asList(entryList));		
		
		Queue<Double> movingAverageQueue = new LinkedList<Double>();
		
		List<Double> movingAveragesList = new ArrayList<Double>();
		double sum = 0.0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		for(Double i : listOfIntegers)
		{
			sum = sum + i;
			movingAverageQueue.add(i);
			if(movingAverageQueue.size() != term)				
				continue;							
			else
			{				
				movingAveragesList.add(Double.parseDouble(df.format(sum/term)));
				sum = sum - movingAverageQueue.poll();				
			}
		}
		
		return movingAveragesList;		
	}
	
	private static List<Integer> findCrossovers(List<Double> stmaList, List<Double> ltmaList)
	{
		List<Integer> crossoverList = new ArrayList<Integer>();
		
		int startingPoint = stmaList.size() - ltmaList.size();
		
		double previousDaysSTMAValue = 0.0;
		double previousDaysLTMAValue = 0.0;
		
		double currentSTMAValue = 0.0;
		double currentLTMAValue = 0.0;
		
		for(int i = 1, j = startingPoint + 1; (i < ltmaList.size() && j < stmaList.size()); i++, j++)
		{			
			previousDaysLTMAValue = ltmaList.get(i - 1);
			previousDaysSTMAValue = stmaList.get(j - 1);
			
			currentLTMAValue = ltmaList.get(i);
			currentSTMAValue = stmaList.get(j);
			
			if((previousDaysSTMAValue > previousDaysLTMAValue) && (currentSTMAValue <= currentLTMAValue))			
				crossoverList.add(j + shortTerm);			
			
			if((previousDaysSTMAValue < previousDaysLTMAValue) && (currentSTMAValue >= currentLTMAValue))
				crossoverList.add(j + shortTerm);			
			
			if((previousDaysSTMAValue == previousDaysLTMAValue) && (currentSTMAValue != currentLTMAValue))		
				crossoverList.add(j + shortTerm);
		}	
		
		return crossoverList;
	}
	
	private static LinkedHashMap<Integer, Double> prepareMapOfDayAndMovingAvg(int startingPointForListIteration, List<Double> movingAvgList, int startingkeyIndexForMap)
	{
		LinkedHashMap<Integer, Double> mapMovingAvgWithDays = new LinkedHashMap<Integer, Double>();			
		
		for(int i = startingPointForListIteration; i < movingAvgList.size(); i++)
		{
			mapMovingAvgWithDays.put(startingkeyIndexForMap++, movingAvgList.get(i));
		}
		
		return mapMovingAvgWithDays;
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
		if(n > 1000 || n <= 300)
		{
			System.err.println("Invalid number of days, this should be between 300 and 1000.");
			System.exit(0);
		}
		
		
		Double[] p = new Double[n];
		
		double stockPrice = 0;
		
		for(int p_i = 0; p_i < n; p_i++)
		{
			stockPrice = sc.nextDouble();
			
			if(stockPrice <= 1 || stockPrice >= 100000 )
			{
				System.err.println("Invalid stock price, this should be between 1 and 100000.");
				System.exit(0);
			}
			p[p_i] = stockPrice;
		}	
		
		sc.close();		
		
		List<Double> stmaList = prepareMovingAverageList(p, shortTerm);
		List<Double> ltmaList = prepareMovingAverageList(p, longTerm);	
		
		int startingPoint = stmaList.size() - ltmaList.size();
		
		LinkedHashMap<Integer, Double> stmaMap = prepareMapOfDayAndMovingAvg(startingPoint, stmaList, longTerm);		
		LinkedHashMap<Integer, Double> ltmaMap = prepareMapOfDayAndMovingAvg(0, ltmaList, longTerm);		
				
		List<Integer> indexListOfCrossovers = findCrossovers(stmaList, ltmaList);		
		
		if(indexListOfCrossovers.size() == 0)
		{
			System.err.println("No crossovers in the given dataset");
			System.exit(0);
		}
		
		DecimalFormat df = new DecimalFormat("#.00");		
				
		for(int crossoverDay : indexListOfCrossovers)
		{
			System.out.println(crossoverDay + "  " + df.format(stmaMap.get(crossoverDay)) + "  " + df.format(ltmaMap.get(crossoverDay)));
		}
	}

}
