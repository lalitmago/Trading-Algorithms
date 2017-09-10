package strategies;

import java.util.Scanner;

public class BankAccounts {
	
	public static String feeOrUpfront(int n, int k, int x, int d, int[] p)
	{
		double paymentFee = 0.0; 
		
		for(int i =  0; i < p.length; i++)
		{
			paymentFee = paymentFee + Double.max(k, ((double)x * p[i])/100);
		}
		
		if(paymentFee <= d)
			return "fee";
		else		
			return "upfront";
	}
	
	private static void validate(int valuetoValidate, int lowerLimit, int upperLimit)
	{
		if((valuetoValidate < lowerLimit) || (valuetoValidate > upperLimit))
        {
        	System.err.println("Invalid value of q!");
        	System.exit(0);
        }
	}

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        validate(q, 1, 5);        
        
        for(int a0 = 0; a0 < q; a0++)
        {
            int n = in.nextInt();
            validate(n, 1, 100);
            
            int k = in.nextInt();
            validate(k, 1, 1000);
            
            int x = in.nextInt();
            validate(x, 1, 100);
            
            int d = in.nextInt();
            validate(d, 1, 10000);
            
            int[] p = new int[n];
            for(int p_i = 0; p_i < n; p_i++){
                p[p_i] = in.nextInt();
                validate(p[p_i], 1, 1000);
            }
            
            String result = feeOrUpfront(n, k, x, d, p);
            System.out.println(result);
        }
        in.close();
	}

}
