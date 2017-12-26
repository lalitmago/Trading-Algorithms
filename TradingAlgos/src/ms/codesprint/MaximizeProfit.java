package ms.codesprint;

import java.io.*;
import java.util.*;
import java.util.function.IntUnaryOperator;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.stream.IntStream;

public class MaximizeProfit {

	static int maximizeProfit(int[] a, int[] b, int m, int k) {
		// Complete this function
		
		int[] countInBitcoinsArray = Arrays.stream(b).map(b_i -> b_i * m)
				.toArray();
		
		int[] amountInDollarsForAllCryptos = IntStream.range(0, a.length).map(i -> a[i] * countInBitcoinsArray[i])
				.toArray();
		
		int maxAmount = Arrays.stream(amountInDollarsForAllCryptos).max().getAsInt();
		
		if(maxAmount > (m*k))
			return maxAmount;
		else
			return m*k;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		int[] a = new int[n];
		for (int a_i = 0; a_i < n; a_i++) {
			a[a_i] = in.nextInt();
		}
		int[] b = new int[n];
		for (int b_i = 0; b_i < n; b_i++) {
			b[b_i] = in.nextInt();
		}
		int result = maximizeProfit(a, b, m, k);
		System.out.println(result);
		in.close();
	}
}
