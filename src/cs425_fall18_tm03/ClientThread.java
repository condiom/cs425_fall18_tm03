package cs425_fall18_tm03;



/**
 * This thread is responsible to handle client connection.
 */
public class ClientThread extends Thread {
	int id, port;
	String hostname;

	public ClientThread(int id, String hostname, int port) {
		this.id = id;
		this.hostname = hostname;
		this.port = port;
	}

	public void run() {
		System.out.println("Client thread started");
		// TODO
		
	}
}