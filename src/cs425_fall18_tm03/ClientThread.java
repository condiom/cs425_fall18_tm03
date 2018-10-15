package cs425_fall18_tm03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

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
		try (Socket socket = new Socket(hostname, port)) {

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String text;

			for (int i=0; i<300; i++) {
				text = "HELLO "+ Inet4Address.getLocalHost().getHostAddress()+" "+socket.getLocalPort()+" "+id;
				
				long startTime=System.currentTimeMillis();


				writer.println(text);

				String responseText = reader.readLine();
				long finishTime=System.currentTimeMillis();
				
				//System.out.println(responseText);
				long rtt=finishTime-startTime;
				System.out.println(rtt);

			}
			writer.println("Bye");

			socket.close();

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());
		}
		
	}
}