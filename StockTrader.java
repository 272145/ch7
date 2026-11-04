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
        System.out.println(price[low]+ " " + monthName[low]+ " "+ year[low]);
        System.out.println(price[high]+ " "+ monthName[high]+ " " + year[high]);
        
        
    }
    public static void loadData( Scanner in, int[] year, int[] month, double[] price){
		for(int i = 0; i<price.length; i++){
			years[i] = in.nextInt();
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
			if(price[i]>price[lowest]){
				highest = i;
			}
			
		}
		return highest;	
	}
	
    
}
