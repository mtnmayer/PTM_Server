package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.rmi.UnknownHostException;

public class MyTestClientHandler implements ClientHandler {

	Solver<String,String> solver;
	CacheManager<String,String> cm;

	public MyTestClientHandler() {

		cm=new FileCacheManager();
		solver=(s)->new StringBuilder(s).reverse().toString();
	}
	
	@Override
	public void handleClient(InputStream inFormClient, OutputStream outFromServer) {

		BufferedReader clientInput = new BufferedReader(new InputStreamReader(inFormClient));
		PrintWriter outToClient = new PrintWriter(outFromServer);

		String line;
		try {
			while(!(line = clientInput.readLine()).equals("end")) {

				if(cm.doesSolutionExists(line) == true) {
					outToClient.println(cm.getSolution(line));
					outToClient.flush();
				}
				else {
					String solution = solver.solve(line);
					cm.saveSolution(line, solution);
					outToClient.println(solution);
					outToClient.flush();
				}
			}

			clientInput.close();
			outToClient.close();

		}	catch (UnknownHostException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}

	}

}
