package algorithm;

import java.util.ArrayList;
import java.util.List;

public interface Searchable<T> {

	public State<T> getInitialState();

	public State<T> getGoalState();

	public ArrayList<State<T>> getAllPossibleStates(State<T> s);

	public <Solution> Solution backtrace(State goal);
	
	public String getString();
}
