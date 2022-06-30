import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This class is used to create the tree using the DFS. This is where the
 * program is run from.
 * 
 * @author joshdebruxelles
 *
 */
public class Tree {

	/**
	 * Main method; this takes input from the user as comma seperated
	 * representations of the states and outputs: R(S1) - the set of all reachable
	 * states from a given board S1. |R(S1)| - the cardinality of R(S1). R(S2) - the
	 * set of all reachable states from a given board S2. |R(S2)| - the cardinality
	 * of R(S2). R(S1) ∩ R(S2) - the intersection between R(S1) and R(S2). |R(S1) ∩
	 * R(S2)| - cardinality of the intersection between R(S1) and R(S2).
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// User Input of Board
		Scanner inputScanner = new Scanner(System.in);
		System.out.println("Enter s1:");
		String s1In = inputScanner.nextLine();
		System.out.println("Enter s2:");
		String s2In = inputScanner.nextLine();
		inputScanner.close();

		// create state and board object for s1
		String[] stateArr = s1In.split(",");
		String[][] s1State = { { stateArr[0], stateArr[1], stateArr[2] }, { stateArr[3], stateArr[4], stateArr[5] },
				{ stateArr[6], stateArr[7], stateArr[8] } };
		Board s1Start = new Board(s1State, null, null, null);
		HashSet<String> s1Nodes = new HashSet<String>();
		// create state and board object for s2
		stateArr = s2In.split(",");
		String[][] s2State = { { stateArr[0], stateArr[1], stateArr[2] }, { stateArr[3], stateArr[4], stateArr[5] },
				{ stateArr[6], stateArr[7], stateArr[8] } };
		Board s2Start = new Board(s2State, null, null, null);
		HashSet<String> s2Nodes = new HashSet<String>();

		// perform dfs to determine the reachable states from a given node
		s1Nodes = DFS.dfsTreeConstruct(s1Start);
		s2Nodes = DFS.dfsTreeConstruct(s2Start);

		// main outputs
		System.out.println("R(S1) States:");
		// output all reachable states for s1
		for (String state : s1Nodes) {
			System.out.println(state);
		}

		System.out.println("R(S2) States:");
		// output all reachable states for s2
		for (String state : s2Nodes) {
			System.out.println(state);
		}

		// determine intersection of R(S1) and R(S2)
		HashSet<String> intersect = determineIntersection(s1Nodes, s2Nodes);
		System.out.println("R(S2) AND R(S1) States:");
		// output intersection of all reachable states for S1 and S2
		for (String state : intersect) {
			System.out.println(state);
		}
		// cardinality outputs
		System.out.println("|R(S1)| = " + s1Nodes.size());
		System.out.println("|R(S2)| = " + s2Nodes.size());
		System.out.println("|R(S1) AND R(S2)| = " + intersect.size());
	}

	/**
	 * This method returns the intersection between two given sets.
	 * 
	 * @param s1Nodes
	 * @param s2Nodes
	 * @return set containing the intersection of two given sets.
	 */
	public static HashSet<String> determineIntersection(HashSet<String> s1Nodes, HashSet<String> s2Nodes) {
		HashSet<String> intersection = new HashSet<String>(s1Nodes); // blank set to store intersection
		intersection.retainAll(s2Nodes); // keep all nodes which appear in both R(S1) and R(S2)
		return intersection;
	}

	/**
	 * This method returns the possible children for a given board based on the
	 * possible moves from the parent state. The previous move for the parent is
	 * used to remove any moves that return the state to its previous position in
	 * order to limit duplicate states.
	 * 
	 * @param board
	 * @return list of child boards.
	 */
	public static ArrayList<Board> createChildren(Board board) {
		ArrayList<Board> children = new ArrayList<Board>();
		// get required data from board object
		int[] blankLoc = board.getBlankLoc();
		String[][] state = board.stateCopy(board.getState());
		ArrayList<Move> moves = board.getPossibleMoves();
		Move prev = board.getPrevMove();
		if (prev != null) { // if a previous move has been made to get to the object's current state (i.e.
							// it is not a root node)
			switch (prev) { // restrict the opposite of the previous move to minimise duplicates
			case UP:
				moves.remove(moves.indexOf(Move.DOWN));
				break;
			case DOWN:
				moves.remove(moves.indexOf(Move.UP));
				break;
			case LEFT:
				moves.remove(moves.indexOf(Move.RIGHT));
				break;
			case RIGHT:
				moves.remove(moves.indexOf(Move.LEFT));
				break;
			default:
				break;
			}
		}
		for (Move m : moves) {
			switch (m) { // create children based on given moves and add to list of children
			case UP:
				// create child board after moving blank up
				String[][] upState = board.makeMove(state, m, blankLoc);
				Board upChild = new Board(upState, null, board, m);
				children.add(upChild);
				break;
			case DOWN:
				// create child board after moving blank down
				String[][] downState = board.makeMove(state, m, blankLoc);
				Board downChild = new Board(downState, null, board, m);
				children.add(downChild);
				break;
			case LEFT:
				// create child board after moving blank left
				String[][] leftState = board.makeMove(state, m, blankLoc);
				Board leftChild = new Board(leftState, null, board, m);
				children.add(leftChild);
				break;
			case RIGHT:
				// create child board after moving blank right
				String[][] rightState = board.makeMove(state, m, blankLoc);
				Board rightChild = new Board(rightState, null, board, m);
				children.add(rightChild);
				break;
			default:
				System.out.println("No move given.");
				break;
			}
		}
		return children;
	}

}
