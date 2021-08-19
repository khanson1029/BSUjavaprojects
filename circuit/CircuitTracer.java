import java.awt.Dimension;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 * @author Kyle Hanson
 */
public class CircuitTracer {
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	private ArrayList<TraceState> bestPaths;
	private static final String O = null;

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); //create this with args
		}
		catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
	
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		//TODO: print out clear usage instructions when there are problems with
		// any command line args
		// See https://en.wikipedia.org/wiki/Usage_message for format and content guidance
		System.out.println("Invalid or missing args. \n" + 
							"Usage: java CircuitTracer [-s | -q] [-c | -g] <filename> \n" + 
							"first argument: -s (stack) or -q (queue) \n" +
							"second argument: -c (console) or -g (GUI) \n" +
							"third argument: <filename> (the filename) \n" +
							"Note: -g (GUI) is non functional, please use -c (console)");
		System.exit(1);
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public CircuitTracer(String[] args) throws NumberFormatException, IOException {
		if(args[0].equals("-s"))
		{
			stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
		}
		else if(args[0].equals("-q"))
		{
			stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
		}
		else {
			printUsage();
		}
		
try {
		bestPaths = new ArrayList<TraceState>();
		int bestPathLength = Integer.MAX_VALUE;

		board = new CircuitBoard(args[2]);
		
		if(board.isOpen(board.getStartingPoint().x, board.getStartingPoint().y-1)) {
			TraceState trace1 = new TraceState(board, board.getStartingPoint().x, board.getStartingPoint().y-1);
			stateStore.store(trace1);
		}
		if(board.isOpen(board.getStartingPoint().x, board.getStartingPoint().y+1)) {
			TraceState trace2 = new TraceState(board, board.getStartingPoint().x, board.getStartingPoint().y+1);
			stateStore.store(trace2);
		}
		if(board.isOpen(board.getStartingPoint().x-1, board.getStartingPoint().y)) {
			TraceState trace3 = new TraceState(board, board.getStartingPoint().x-1, board.getStartingPoint().y);
			stateStore.store(trace3);
		}
		if(board.isOpen(board.getStartingPoint().x+1, board.getStartingPoint().y)) {
			TraceState trace4 = new TraceState(board, board.getStartingPoint().x+1, board.getStartingPoint().y);
			stateStore.store(trace4);
		}
			

			while (!stateStore.isEmpty())
			{
				TraceState currentState = stateStore.retrieve();

				board = currentState.getBoard();
				
				if (currentState.isComplete())
				{
					if (currentState.pathLength() == bestPathLength)
						bestPaths.add(currentState);
					else if (currentState.pathLength() < bestPathLength) {
						bestPaths.clear();
						bestPaths.add(currentState);
						bestPathLength = currentState.pathLength();
					}
				} else
				{
					if (board.isOpen(currentState.getRow() + 1, currentState.getCol()))
					{
						stateStore.store(new TraceState(currentState, currentState.getRow() + 1, currentState.getCol()));
					}
					if (board.isOpen(currentState.getRow() - 1, currentState.getCol()))
					{
						stateStore.store(new TraceState(currentState, currentState.getRow() - 1, currentState.getCol()));
					}
					if (currentState.isOpen(currentState.getRow(), currentState.getCol() + 1))
					{
						stateStore.store(new TraceState(currentState, currentState.getRow(), currentState.getCol() + 1));
					}
					if (currentState.isOpen(currentState.getRow(), currentState.getCol()- 1))
					{
						stateStore.store(new TraceState(currentState, currentState.getRow(), currentState.getCol() - 1));
					}
				}
			}

			if (args[1].equals("-c"))
			{
				for (TraceState traceState : bestPaths)
					System.out.println(traceState);
			} else if (args[1].equals("-g"))
			{
				System.out.println("No GUI functionality");		
				}
			else {
				printUsage();
			}
			
}
			catch(FileNotFoundException fnfe) {
				System.out.println("FileNotFoundException");
			}
			catch(InvalidFileFormatException iffe) {
				System.out.println("InvalidFileFormatException");
			}
		}
	
		//TODO: output results to console or GUI, according to specified choice
}
	
 // class CircuitTracer

