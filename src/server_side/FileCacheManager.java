package server_side;

import java.util.HashMap;

public class FileCacheManager <Problem, Solution> implements CacheManager<Problem, Solution> {

	HashMap<Problem, Solution> hm = new HashMap<>(); 
	
	@Override
	public boolean doesSolutionExists(Problem p) {
		if(hm.containsKey(p))
			return true;
		
		return false;
	}

	@Override
	public Solution getSolution(Problem p) {
		return hm.get(p);
	}

	@Override
	public void saveSolution(Problem p, Solution s) {
		hm.put(p, s);
	}

	

}
