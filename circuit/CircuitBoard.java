import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 * @author Kyle Hanson
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private int ROWS; //initialized in constructor
	private int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";
	private boolean valid = true;
	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException, InvalidFileFormatException, IOException{
		
		
		Scanner fileScan = new Scanner(new File(filename));
		
		
		Scanner scan1 = new Scanner(fileScan.nextLine());
		
		if(!scan1.hasNextInt()) {
			valid = false;
			throw new InvalidFileFormatException("");
		}
		
		ROWS = scan1.nextInt();
		
		if(!scan1.hasNextInt()) {
			valid = false;
			throw new InvalidFileFormatException("For: Expected: Int: Given: String");
		}
		
		COLS  = scan1.nextInt();
		
		if(scan1.hasNext()) {
			valid = false;
			throw new InvalidFileFormatException("For: Invalid Dimensions");
		}
		
		board = new char[ROWS][COLS];
	/*/
	 * Reading in the board	
	 */
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if(!fileScan.hasNext()) {
					valid = false;
					throw new InvalidFileFormatException("For: Invalid Dimensions");
				}
				String next = fileScan.next();
				char c = next.charAt(0);
				board[i][j] = c;
			}
			
		}
		
		fileScan.close();
		
		File grids = new File(filename);
		Scanner line = new Scanner(grids);
		
		line.nextLine();
		
		int c1 = 0;
		int c2 = 0;
		
		int rowcount = 0;
		int colcount = 0;
/*/
 * Scanning Board for Exceptions
 */
		while(line.hasNextLine()) {
			colcount = 0;
			Scanner co = new Scanner(line.nextLine());
			while(co.hasNext()) {
				String c = co.next();
				if(c.length() > 1) {
					valid = false;
					throw new InvalidFileFormatException("");
				}
				if(c.equals("1")) {
					startingPoint = new Point(rowcount, colcount);
					c1++;
				}
				if(c.equals("2")) {
					endingPoint = new Point(rowcount, colcount);
					c2++;
				}
				if(!c.matches("O|X|T|1|2")) {
					valid = false;
					throw new InvalidFileFormatException("For: Invalid Input Characters");
				}
				colcount++;
			}
			rowcount++;
		}
/*/
 * More exceptions
 */
		if(c1 != 1) {
			valid = false;
			throw new InvalidFileFormatException("For: Duplicate Starting Points");
		}
		if(c2 != 1) {
			valid = false;
			throw new InvalidFileFormatException("For: Duplicate Ending Points");
		}
		if(rowcount != ROWS) {
			valid = false;
			throw new InvalidFileFormatException("For: Rows: " + rowcount + " Does not equal expected rows : " + ROWS);
		}
		if(colcount != COLS) {
			valid = false;
			throw new InvalidFileFormatException("For: " + colcount + " Does not equal: " + COLS);
		}}

	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
