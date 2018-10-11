package cs425_fall18_tm03;


import java.net.*;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
	Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		System.out.println("Server thread started");
	}
}