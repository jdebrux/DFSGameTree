import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * This class is used to handle the depth first search and its required methods.
 * 
 * @author joshdebruxelles
 *
 */

public class DFS {
	/**
	 * This method is used to return the reachable states from a given start board
	 * node.
	 * 
	 * @param start
	 * @return set of reachable states stored as strings.
	 */
	public static HashSet<String> dfsTreeConstruct(Board start) {
		Stack<Board> stack = new Stack<>(); // initialise stack for DFS to allow backtracking
		stack.push(start); // push the root node to the stack
		ArrayList<Board> visitedOut = new ArrayList<Board>();
		HashSet<String> visitedStates = new HashSet<String>();
		// visit root node - store both as string and object
		visitedStates.add(start.toString());
		visitedOut.add(start);
		int i = 0; // used to track the number of iterations made
		while (!(stack.isEmpty())) { // continues the DFS while there are child nodes on the stack
			i++;
			System.out.println(i);
			Board current = stack.pop(); // item at the top of the stack becomes the current node to be expanded
			ArrayList<Board> successors = Tree.createChildren(current); // generate children of current node
			for (Board child : successors) {
				if (!duplicateChecker(visitedStates, child.toString())) { // check if children are duplicates (i.e.
																			// already expanded/visited)
					stack.push(child); // add each child to the stack
					// visit child node - store both as string and object
					visitedStates.add(child.toString());
					visitedOut.add(child);

				} else { // duplicate found - proceed to next child or backtrack if none
					continue;
				}
			}
		}
		return visitedStates;
	}

	/**
	 * This method is used to determine if a given child state is a duplicate. This
	 * uses the property of sets that they cannot contain duplicate values -
	 * therefore a give state is a duplicate if it cannot be added to the set.
	 * 
	 * @param set
	 * @param newState
	 * @return boolean value depending if the state is a duplicate, or not.
	 */
	public static boolean duplicateChecker(HashSet<String> set, String newState) {
		if (set.add(newState) == false) { // use set property that all elements must be unique
			return true;
		}
		return false;
	}
}
