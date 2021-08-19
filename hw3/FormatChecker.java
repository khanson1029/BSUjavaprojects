
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

public class FormatChecker {

   public static void main(String[] args) throws IOException {
	   // A conditional statements to see if the user gives any arguments
       if(args.length==0) {
           System.out.println("No arguments given...");
       } else {
           for (int i = 0; i < args.length; i++) {
        	   System.out.println(args[i]);
               BufferedReader br = null;
              // Setting a boolean to true at the beginning of my loop
               boolean isValid = true;
               // Loop to run through the scanned file, and ascertain if it is a valid or invalid file
               try {
            	   // Creating a buffered reader to read a new file specified by an argument in the i position
                   br = new BufferedReader(new FileReader(args[i]));
                   // String to store lines of the scanned file into
                   String scannedLine = "";

                   //Values to store which line I'm scanning, the rows and columns specified by the graph, and the rows that I actually count.
                   double line = 0, rows = 0, columns = 0, rowsCounted = 0;
                   
                   //If the scanner is reading a line, do the following
                   while ((scannedLine = br.readLine()) != null) {
                	   
                       // Extract the number of rows and columns unless there is an illegal character or more than or less than 2 dimension inputs 
                       if (line == 0) {
                           String zeroLine[] = scannedLine.trim().split("\\s+");
                           for (int a = 0; a < zeroLine.length; a++) {
                        	   if(zeroLine[a].matches("^-?[0-9]*\\.?[0-9]+$"))            	               
                           				{
                           					isValid = true;
                           				}
                        	   else {
                        		   isValid = false;
                        		   throw new NumberFormatException("For: input string: " + zeroLine[a] );
                        	   }
                           }
                           rows = Double.parseDouble(zeroLine[0]);
                           columns = Double.parseDouble(zeroLine[1]);
                                                      
                           if (zeroLine.length != 2) {
                        	   isValid = false;
                        	   throw new InputMismatchException("For: Expected: 2 dimensions: Returned: " + zeroLine.length + " dimensions." );
                               }
                           }
                       //For lines other than the first line, and not including blank lines, check for matches between expected columns and rows to the actual values of columns and rows I count.
                       //Also check for any illegal characters within any of these rows
                       else if (!"".equals(scannedLine.trim())){
                    	   rowsCounted++;
                           String rowCheck[] = scannedLine.trim().split("\\s+");
                           if(rowCheck.length != columns)
                           {
                        	   isValid = false;
                               throw new InputMismatchException("For: Expected: " + columns + " columns: " + "Returned: " + rowCheck.length + " columns");
                           }
                           
                           
                           for (int y = 0; y < rowCheck.length; y++) {
                        	   if(rowCheck[y].matches("^-?[0-9]*\\.?[0-9]+$"))            	               
                           				{
                           					isValid = true;
                           				}
                        	   else {
                        		   isValid = false;
                        		   throw new NumberFormatException("For: input string: " + rowCheck[y] );
                        	   }
                         
                           }
                       }
                       line++;

                   }
                   // check for correct row number
                   if (rowsCounted != rows) {
                       isValid = false;
                       throw new InputMismatchException("For: Expected: " + rows + " rows: " + "Returned: " + rowsCounted + " rows");
                   }
                   
               } 
               // catch any unspecified errors, and print out whether or not the file is valid or invalid
               catch (FileNotFoundException e) {
                   isValid = false;
                   System.out.println("FileNotFoundException For: the specified file cannot be found");
                   }
               catch (InputMismatchException e1) {
            	   isValid = false;
            	   System.out.println(e1.getMessage());
               }
               catch (NumberFormatException e2) {
            	   isValid = false;
            	   System.out.println(e2.getMessage());
               }
               finally {
                   
                   if (isValid == true) {
                       System.out.println("VALID");
                   } else {
                       System.out.println("INVALID");
                   }
                   try {
                       if (br != null)
                           br.close();
                   } catch (IOException ex) {
                       isValid = false;
                 
                   }
               }
           }

       }
   }
}




