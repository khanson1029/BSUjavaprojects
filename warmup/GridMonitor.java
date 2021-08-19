import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GridMonitor implements GridMonitorInterface{
	//Fields
	private String filename;
	private int row;
	private int col;
	private double[][] baseGrid;
	private double[][] surroundingSumGrid;
	private double[][] surroundingAvgGrid;
	private double[][] deltaGrid;
	private double[][] dangerGrid;
	
	//Constructor
	public GridMonitor(String filename) throws FileNotFoundException
	{
		//Creates a file to scan, and a scanner which passes the dimensions of the array to the row and col variables.
		this.filename = filename;
		File file = new File(filename);
		Scanner scan = new Scanner(file);
		
		String firstLine = scan.nextLine();
		Scanner firstLineScanner = new Scanner(firstLine);

		row = firstLineScanner.nextInt();
		col = firstLineScanner.nextInt();
		
		//Instantiates the baseGrid double array, and populates the array with the numbers from the text file scanned in above
		this.baseGrid = new double[row][col];
		
		for(int i = 0; i < baseGrid.length; i++)
			for(int j = 0; j < baseGrid[0].length; j++)
				baseGrid[i][j] = scan.nextDouble();
		
	}
	//Methods
	
	public double[][] getBaseGrid(){ //Returns the numbers given in the text file.  Used primarily to test smaller pieces of code before moving to the GridMonitorTest

		//creates a copy of the baseGrid above to enforce encapsulation
		double[][] copyGrid = new double[row][col];
		for (int i = 0; i < baseGrid.length; i++) {
			for (int j = 0; j < baseGrid[0].length; j++) {
				copyGrid[i][j] = baseGrid[i][j];
			}
		}
		return copyGrid;
	}
	
	public double[][] getSurroundingSumGrid() { // Returns the sum of the surrounding numbers (top, bottom, left, and right) of a number in the base grid.  Any edge number uses "itself" as it's missing value
		// Creates a copy of baseGrid and uses that copy to create a sum array that adds the surrounding numbers
		double[][] copyGrid = new double[row][col];
		double[][] sum = new double[row][col];
		
		for (int i = 0; i < baseGrid.length; i++) {
			for (int j = 0; j < baseGrid[0].length; j++) {
				copyGrid[i][j] = baseGrid[i][j];
			}}
		for(int i = 0; i < copyGrid.length; i ++) 
		{
			for(int j = 0; j < copyGrid[i].length; j ++)
			{
	                    double result = 0;
	                    result += copyGrid[Math.max(0, i - 1)][j];
	                    if (i == copyGrid.length - 1) 
	                    {
	                        result += copyGrid[i][j];
	                    } 
	                    else 
	                    {
	                        result += copyGrid[i + 1][j];
	                    }
	                    result += copyGrid[i][Math.max(0, j - 1)];
	                    if (j == copyGrid[0].length - 1) {
	                        result += copyGrid[i][j];
	                    } else {
	                        result += copyGrid[i][j + 1];
	                    }
	                    sum[i][j] = result;
	                }        
	            }
	   //Returns the added numbers
	            return sum;
	        }
	


	public double[][] getSurroundingAvgGrid() { // A copy of the logic above, with the result divided by four to return the average
		double[][] copyGrid = new double[row][col];
		double[][] sum = new double[row][col];
		
		for (int i = 0; i < baseGrid.length; i++) {
			for (int j = 0; j < baseGrid[0].length; j++) {
				copyGrid[i][j] = baseGrid[i][j];
			}}
		for(int i = 0; i < copyGrid.length; i ++) 
		{
			for(int j = 0; j < copyGrid[i].length; j ++)
			{
	                    double result = 0;
	                    result += copyGrid[Math.max(0, i - 1)][j];
	                    if (i == copyGrid.length - 1) 
	                    {
	                        result += copyGrid[i][j];
	                    } 
	                    else 
	                    {
	                        result += copyGrid[i + 1][j];
	                    }
	                    result += copyGrid[i][Math.max(0, j - 1)];
	                    if (j == copyGrid[0].length - 1) {
	                        result += copyGrid[i][j];
	                    } else {
	                        result += copyGrid[i][j + 1];
	                    }
	                    sum[i][j] = result/4;
	                }        
	            }
	            for (int i = 0; i < row; i++) {
	                for (int j = 0; j < col; j++) {
	                    System.out.println(sum[i][j]);
	                }
	            }
	            return sum;
	        }

	
	public double[][] getDeltaGrid() { //Another copy of the logic above but using the instructions to "divde the average by two" to get the delta grid.
		double[][] copyGrid = new double[row][col];
		double[][] sum = new double[row][col];
		
		for (int i = 0; i < baseGrid.length; i++) {
			for (int j = 0; j < baseGrid[0].length; j++) {
				copyGrid[i][j] = baseGrid[i][j];
			}}
		for(int i = 0; i < copyGrid.length; i ++) 
		{
			for(int j = 0; j < copyGrid[i].length; j ++)
			{
	                    double result = 0;
	                    result += copyGrid[Math.max(0, i - 1)][j];
	                    if (i == copyGrid.length - 1) 
	                    {
	                        result += copyGrid[i][j];
	                    } 
	                    else 
	                    {
	                        result += copyGrid[i + 1][j];
	                    }
	                    result += copyGrid[i][Math.max(0, j - 1)];
	                    if (j == copyGrid[0].length - 1) {
	                        result += copyGrid[i][j];
	                    } else {
	                        result += copyGrid[i][j + 1];
	                    }
	                    sum[i][j] = Math.abs(((result/4)/2)); // was failing the negatives delta test, so took the absolute value
	                }        
	            }
	            for (int i = 0; i < row; i++) {
	                for (int j = 0; j < col; j++) {
	                    System.out.println(sum[i][j]);
	                }
	            }
	            return sum;
	   	}

	
	public boolean[][] getDangerGrid() { // Uses the logic from above and then creates another boolean array to check if the resulting sums are within the range of the delta numbers +- the original number.
		double[][] copyGrid = new double[row][col];
		double[][] avgSum = new double[row][col];
		double[][] deltas = new double[row][col];
		boolean[][] danger = new boolean[row][col];
		
		for (int i = 0; i < baseGrid.length; i++) {
			for (int j = 0; j < baseGrid[0].length; j++) {
				copyGrid[i][j] = baseGrid[i][j];
			}}
		for(int i = 0; i < copyGrid.length; i ++) 
		{
			for(int j = 0; j < copyGrid[i].length; j ++)
			{
	                    double result = 0;
	                    result += copyGrid[Math.max(0, i - 1)][j];
	                    if (i == copyGrid.length - 1) 
	                    {
	                        result += copyGrid[i][j];
	                    } 
	                    else 
	                    {
	                        result += copyGrid[i + 1][j];
	                    }
	                    result += copyGrid[i][Math.max(0, j - 1)];
	                    if (j == copyGrid[0].length - 1) {
	                        result += copyGrid[i][j];
	                    } else 
	                    {
	                        result += copyGrid[i][j + 1];
	                    }
	                    avgSum[i][j] = (result/4);
	                }   
			}		
		for(int i = 0; i < copyGrid.length; i ++) 
		{
			for(int j = 0; j < copyGrid[i].length; j ++)
			{
                double result = 0;
                result += copyGrid[Math.max(0, i - 1)][j];
                if (i == copyGrid.length - 1) 
                {
                    result += copyGrid[i][j];
                } 
                else 
                {
                    result += copyGrid[i + 1][j];
                }
                result += copyGrid[i][Math.max(0, j - 1)];
                if (j == copyGrid[0].length - 1) {
                    result += copyGrid[i][j];
                } else 
                {
                    result += copyGrid[i][j + 1];
                }
                deltas[i][j] = ((result/4)/2);
			}}
		for(int i = 0; i < copyGrid.length; i ++) 
		{
			for(int j = 0; j < copyGrid[i].length; j ++)
			{
				
                boolean result = false;
                if (((Math.abs(copyGrid[i][j])) < (Math.abs((avgSum[i][j]-deltas[i][j])))) || (Math.abs(copyGrid[i][j])  > (Math.abs((avgSum[i][j] + deltas[i][j])))))
                {
                	result = true;
                }
                else
                	result = false;
                danger[i][j] = result;
			}}
		return danger;
		
		
		
		}
		
	@Override
	//written to pass the lameToString method in the test class
	public String toString() {
	String stringGrid = new String();

	return getBaseGrid().toString();
	}
	
	
	
	public static void main (String[] args) throws FileNotFoundException
	{
		GridMonitor one = new GridMonitor("sample.txt");
		one.getBaseGrid();

		}
}


