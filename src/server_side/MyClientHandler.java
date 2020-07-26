package server_side;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import algorithm.BestFirstSearch;
import algorithm.Searchable;
import algorithm.State;
import matrix.Matrix;
import matrix.MatrixSearchable;

public class MyClientHandler implements ClientHandler{

	Solver<Searchable, String> solver;
	CacheManager<String, String> cm;

	public MyClientHandler() {
		solver = new SolverSearcher(new BestFirstSearch<String>());
		cm = new FileCacheManager<>();
	}
	
	@Override
	public void handleClient(InputStream inFormClient, OutputStream outFromServer) {
		
		BufferedReader clientInput = new BufferedReader(new InputStreamReader(inFormClient));
		PrintWriter outToClient = new PrintWriter(outFromServer);

		try {
			String line;
			String[] stringMatrix;
			int rows = 0;
			int cols = 0;
			ArrayList<String[]> arr = new ArrayList<>();
			line = clientInput.readLine();
			stringMatrix = line.split(",");
			while (!line.equals("end")) {
				arr.add(stringMatrix);
				line = clientInput.readLine();
				stringMatrix = line.split(",");
			}
			rows = arr.size();
			cols = arr.get(0).length;
			int[][] matrix = new int[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					matrix[i][j] = Integer.parseInt(arr.get(i)[j]);
				}
			}
			String[] entrance = clientInput.readLine().split(",");
			Point entrancePoint = new Point(Integer.parseInt(entrance[0]), Integer.parseInt(entrance[1]));
			String[] exit = clientInput.readLine().split(",");
			Point exitPoint = new Point(Integer.parseInt(exit[0]), Integer.parseInt(exit[1]));

			Searchable problem = new MatrixSearchable(
					new Matrix(matrix, new State<>(entrancePoint), new State<>(exitPoint)));
			String problemData = problem.getString();
			String solution;
			if (cm.doesSolutionExists(problemData)) {
				solution = cm.getSolution(problemData);
			} else {
				solution = solver.solve(problem);
				cm.saveSolution(problemData, solution);
			}
			outToClient.println(solution);
			outToClient.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
