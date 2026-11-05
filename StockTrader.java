import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("tiny.txt"));
        String symbol = in.next();
        int n = in.nextInt();

        int[] year = new int[n];
        int[] month = new int[n];
        double[] price = new double[n];
        
        loadData(in, year, month, price);
        printData(year,month,price);
        int low = indexOfLowest(price);
        int high = indexOfHighest(price);
        System.out.println(price[low]+ " " +monthName(month[low]) + " "+ year[low]);
        System.out.println(price[high]+ " "+ monthName(month[high])+ " " + year[high]);
        double result = simulate(year, month, price);
		System.out.printf("Final value: $%,.2f%n", result);

        
    }
    public static void loadData( Scanner in, int[] year, int[] month, double[] price){
		for(int i = 0; i<price.length; i++){
			year[i] = in.nextInt();
			month[i] = in.nextInt();
			price[i] = in.nextDouble();
		
		}
    }
    public static String monthName(int month){
		String[] names = {"jan", "feb", "mar", "apr", "may","jun", "jul","aug", "sep", "oct", "nov", "dec"};
		return names[month-1];
    }
   public static void printData(int[] year, int[] month, double[] price){
		for (int i = 0; i<price.length; i++){
			System.out.println(year[i] + " " + month[i] + " " + price[i]);
		}
   }
   public static int indexOfLowest(double[] price){
	   int lowest  = 0;
	    for (int i = 0; i< price.length;i++){
			if(price[i]<price[lowest]){
				lowest = i;
			}
			
		}
		return lowest;	
	}
	public static int indexOfHighest(double[] price){
	   int highest  = 0;
	    for (int i = 0; i< price.length;i++){
			if(price[i]>price[highest]){
				highest = i;
			}
			
		}
		return highest;	
	}
	public static int indexOfBiggestDrop(double[] price){
		int index = -1;
		double biggestDrop = 0;
		for(int i=1; i< price.length;i++){
			double previous = price[i-1];
			double percentDrop = (previous - price[i]) / previous *100;
			if(percentDrop >biggestDrop){
				biggestDrop = percentDrop;
				index = i;
			}
		}
		return index;
	}
	
	public static final double START_CASH = 10000.0;
	public static final double FEE = 10.0;

	public static double simulate(int[] years, int[] months, double[] prices) {
		double cash = START_CASH;
		int shares = 0;
		double boughtAt = 0.0;

		for (int i = 0; i < prices.length; i++) {
			double[] history = Arrays.copyOf(prices, i + 1);
			String action = decide(history, shares > 0, boughtAt);

			if (action.equals("BUY") && shares == 0) {
				int canBuy = (int) ((cash - FEE) / prices[i]);
				if (canBuy > 0) {
					shares = canBuy;
					cash = cash - shares * prices[i] - FEE;
					boughtAt = prices[i];
					System.out.printf("%s %d: BUY %d shares at $%.2f%n",monthName(months[i]), years[i], shares, prices[i]);
				}
			} else if (action.equals("SELL") && shares > 0) {
				cash = cash + shares * prices[i] - FEE;
				System.out.printf("%s %d: SELL %d shares at $%.2f%n",monthName(months[i]), years[i], shares, prices[i]);
				shares = 0;
				boughtAt = 0.0;
			}
		}
		return cash + shares * prices[prices.length - 1];
	}
	public static String decide(double[] history, boolean holding, double boughtAt) {
		return dipBuyer(history, holding, boughtAt);   
	}
	public static String buyAndHold(double[] history, boolean holding, double boughtAt){
		return "BUY";
	}
	public static String dipBuyer(double[] history, boolean holding, double boughtAt){
		int last = history.length-1;
		double today = history[last];
		if(holding&&today*100>=boughtAt*110){
			return "SELL";
			
		}
		if(!holding && last>=2){
			double lastMonth = history[last-1];
			double twoMonthsAgo =  history[last-1];
			if(today <lastMonth && lastMonth<twoMonthsAgo){
				return "BUY";	
			} 
		}
		return "HOLD";
	}
	public static String movingAverage(double[] history, boolean holding, double boughtAt){
		 int n = history.length;
		 
		 if(n<12){
			 return"HOLD";
			
		 }
		 double total = 0;
		 for(int i = n-12; i<n; i++){
			 total += history[i];
		 }
		 double average = total/12;
		 double today = history[n-1];
		 if(!holding && today >average){
			 return "BUY";
		 }
		 if(!holding && today <average){
			 return "SELL";
		 }
		 return "HOLD";
	}
	
}
	
    

