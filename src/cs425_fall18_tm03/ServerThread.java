package cs425_fall18_tm03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			String requestText;
			String payload = "";
			for (int i = 0; i < 10000; i++) {
				payload += "abcdefghijklmnopqrstuvwyz";
			}
			long startTime = System.currentTimeMillis();
			int counter = 0;
			do {
				requestText = reader.readLine();
				// System.out.println(requestText);
				if (requestText.equals("Bye"))
					break;
				counter++;
				String requestTextArray[] = requestText.split(" ");
				String responseText = "WELCOME " + requestTextArray[requestTextArray.length - 1] + payload;

				writer.println(responseText);

			} while (true);
			long finishTime = System.currentTimeMillis();

			double totalTime = finishTime - startTime;
			double throughput = counter / totalTime;
			System.out.println(throughput * 1000);
			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}