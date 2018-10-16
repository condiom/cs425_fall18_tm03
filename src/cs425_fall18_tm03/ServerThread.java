package cs425_fall18_tm03;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Random;

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
			// for reading from client
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			// for writing to client
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			String requestText;
			// long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();
			int counter = 0;
			String clientId = "";;
			do {
				requestText = reader.readLine();
				if (requestText.equals("Bye"))
					break;
				counter++;
				String requestTextArray[] = requestText.split(" ");
				// create payload
				Random rand = new Random(System.currentTimeMillis());
				int K = rand.nextInt(1700) + 300;

				byte[] payload = new byte[K*1000];
				new Random().nextBytes(payload);
				String generatedString = new String(payload, Charset.forName("UTF-8"));
				//String generatedString = "";

				// Send payload to client
				clientId = requestTextArray[requestTextArray.length - 1];
				String responseText = "WELCOME " + clientId + generatedString;
				writer.println(responseText);
				writer.println("finish");

			} while (true);

			// helping variables
			long finishTime = System.currentTimeMillis();
			double totalTime = finishTime - startTime;
			OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			// long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			// end helping variables
			
			// throughput
			double throughput = counter / totalTime;
			// CPU load
			double cpuLoad = osBean.getSystemLoadAverage();
			// Memmory utilization
			double MemUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / ((Runtime.getRuntime().totalMemory()*1.0));
			
			FileWriter fileWriter = new FileWriter("ServerStatistics",true);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    // make it percentage
		    int MemU = (int) (MemUsed *100);
			printWriter.println((throughput*1000)+" "+cpuLoad + " " + MemU);
		    printWriter.close();
		    System.out.println(clientId+" finished");
			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}