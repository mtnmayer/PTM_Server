package algorithm;

import java.awt.Point;
import java.util.PriorityQueue;

public abstract class CommonSearcher<Solution> implements Searcher{

	protected PriorityQueue<State<Point>> openList;
	private int evaluatedNodes;

	public CommonSearcher() {
		this.openList = new PriorityQueue<>(((o1, o2) -> (int) (o1.getCost() - o2.getCost())));
		evaluatedNodes=0;
	}

	final protected State popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}

	@Override
	public abstract Solution search(Searchable s); 

}
