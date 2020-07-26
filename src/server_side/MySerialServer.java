package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {

	private int port;
	private ClientHandler ch;
	private volatile boolean stop;

	public MySerialServer(int port,ClientHandler ch) {
		this.port=port;
		this.ch=ch;
		stop=false;
	}

	@Override
	public void start() {
		new Thread(()->{
			try {
				runServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start(); 

	}

	@Override
	public void stop() {
		stop=true;
	}

	private void runServer()throws Exception {

		ServerSocket server=new ServerSocket(port);
		server.setSoTimeout(1000);

		while(!stop){
			try{
				Socket aClient=server.accept(); // blocking call
				try {
				//	while(aClient.isConnected()) {
						ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
					//}
					stop = true;
					aClient.close();
				} catch (IOException e) {e.getStackTrace();}
			}catch(SocketTimeoutException e) {e.getStackTrace();}
		}
		server.close();
	}

}
