package strategies;

import java.util.Scanner;

public class CurrencyArbitrage {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int x = in.nextInt();
        int s = in.nextInt();
        int f = in.nextInt();
        int m = in.nextInt();
        double[][] A = new double[n][n];
        for(int A_i = 0; A_i < n; A_i++){
            for(int A_j = 0; A_j < n; A_j++){
                A[A_i][A_j] = in.nextDouble();
            }
        }
        in.close();

	}

}
