package boot;

import server_side.MySerialServer;
import server_side.MyTestClientHandler;
import server_side.Server;

public class Main {

	public static void main(String[] args) {
		
		Server s = new MySerialServer(3001, new MyTestClientHandler());
		s.start();
		System.out.println("Server is live and running");
		
	}

}
