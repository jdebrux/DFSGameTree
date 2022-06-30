import java.util.ArrayList;

/**
 * This class is used to construct the board object and its associated methods.
 * 
 * @author joshdebruxelles
 *
 */
public class Board {
	private String[][] state = new String[3][3];
	private ArrayList<Board> children;
	private Board parent;
	private int[] blankLoc = new int[2];
	private Move prevMove;

	public Board(String[][] state, ArrayList<Board> children, Board parent, Move prevMove) {
		this.setState(state);
		this.setChildren(children);
		this.setParent(parent);
		int[] blankLoc = findBlankLoc(state);
		this.setBlankLoc(blankLoc);
		this.setPrevMove(prevMove);
	}

	/**
	 * This method is used to determine the position of the blank on the board.
	 * 
	 * @param state
	 * @return the coordinates of the blank location in an array [x,y].
	 */
	public int[] findBlankLoc(String[][] state) {
		int[] blankLoc = new int[2];
		for (int y = 0; y <= 2; y++) { // loop through the rows
			for (int x = 0; x <= 2; x++) { // loop through the columns
				if (state[x][y].equals("o")) { // find coordinate matching blank
					blankLoc[0] = y;
					blankLoc[1] = x;
				}
			}
		}
		return blankLoc;
	}

	/**
	 * This method determines the possible moves that can be made from a given state
	 * based on the coordinates of the blank.
	 * 
	 * @return the possible moves that can be made from a given state.
	 */
	public ArrayList<Move> getPossibleMoves() {
		ArrayList<Move> moves = new ArrayList<>();
		int y = blankLoc[1];
		int x = blankLoc[0];
		if (y == 1 || y == 2) {
			moves.add(Move.UP);
		}
		if (y == 0 || y == 1) {
			moves.add(Move.DOWN);
		}
		if (x == 1 || x == 2) {
			moves.add(Move.LEFT);
		}
		if (x == 0 || x == 1) {
			moves.add(Move.RIGHT);
		}
		return moves;
	}

	/**
	 * This method is used to copy a 2d array representing a state.
	 * 
	 * @param prevState
	 * @return 2d array copy of the state.
	 */
	public String[][] stateCopy(String[][] prevState) {
		String[][] newState = new String[3][3];
		for (int i = 0; i <= 2; i++) { // loop through rows
			for (int j = 0; j <= 2; j++) { // loop through columns
				newState[i][j] = prevState[i][j]; // copy to new 2d array
			}
		}
		return newState;
	}

	/**
	 * This method performs the swaps necessary for a given move to be taken by a
	 * board.
	 * 
	 * @param parentState
	 * @param move
	 * @param blankLoc
	 * @return the changed board after a move has been made.
	 */
	public String[][] makeMove(String[][] parentState, Move move, int[] blankLoc) {
		String[][] temp = stateCopy(parentState);
		switch (move) { // swap the coordinates based on the given move
		case UP:
			// swap up
			temp[blankLoc[1]][blankLoc[0]] = parentState[blankLoc[1] - 1][blankLoc[0]];
			temp[blankLoc[1] - 1][blankLoc[0]] = "o";
			break;
		case DOWN:
			// swap down
			temp[blankLoc[1]][blankLoc[0]] = parentState[blankLoc[1] + 1][blankLoc[0]];
			temp[blankLoc[1] + 1][blankLoc[0]] = "o";
			break;
		case LEFT:
			// swap left
			temp[blankLoc[1]][blankLoc[0]] = parentState[blankLoc[1]][blankLoc[0] - 1];
			temp[blankLoc[1]][blankLoc[0] - 1] = "o";
			break;
		case RIGHT:
			// swap right
			temp[blankLoc[1]][blankLoc[0]] = parentState[blankLoc[1]][blankLoc[0] + 1];
			temp[blankLoc[1]][blankLoc[0] + 1] = "o";
			break;
		default:
			System.out.println("No move given.");
			break;
		}
		return temp;

	}

	public String[][] getState() {
		return state;
	}

	public void setState(String[][] state) {
		this.state = state;
	}

	public Board getParent() {
		return parent;
	}

	public void setParent(Board parent) {
		this.parent = parent;
	}

	public int[] getBlankLoc() {
		return blankLoc;
	}

	public void setBlankLoc(int[] blankLoc) {
		this.blankLoc = blankLoc;
	}

	public ArrayList<Board> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Board> children) {
		this.children = children;
	}

	public Move getPrevMove() {
		return prevMove;
	}

	public void setPrevMove(Move prevMove) {
		this.prevMove = prevMove;
	}

//	@Override
//	public String toString() {
//		String outputBoard = "[";
//		String[][] state = getState();
//		for(int y=0;y<=2;y++) {
//			for(int x=0;x<=2;x++) {
//				if(x==2 && y==2) {
//					outputBoard += state[y][x];
//				} else {
//					outputBoard += state[y][x]+",";
//				}
//				if(x==2 && y!=2) {
//					outputBoard+="\n ";
//				}
//			}
//		}
//		outputBoard+="] \n";
//		return outputBoard;
//	}

	@Override
	public String toString() {
		String outputBoard = "";
		String[][] state = getState();
		for (int y = 0; y <= 2; y++) {
			for (int x = 0; x <= 2; x++) {
				outputBoard += state[y][x];
			}
		}
		return outputBoard;
	}
}
