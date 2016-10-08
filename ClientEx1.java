package csc481hw1.section3;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientEx1 {
	
	private Socket firstSocket = null;
	private ServerSocket secondSocket = null;
	private String serverAddress = "152.1.13.219";
	private int serverPort = 20001;
	private String clientAddress;  // public ip address
	private String clientPort = "6789";
	
	public ClientEx1(String clientAddress) {
		this.clientAddress = clientAddress;
		run();
	}

	public void run() {
		try {
		    
			firstSocket = new Socket(serverAddress, serverPort);
			PrintWriter out = new PrintWriter(firstSocket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(firstSocket.getInputStream()));
		    Random rand = new Random();
		    int usernum = rand.nextInt((60000 - 20100) + 1) + 20100;
		    out.printf("%s %s-%s %s-%s %d %s\n",
		               "ex1", serverAddress, serverPort, clientAddress, clientPort, usernum, "C.B.Lupo");
		    String readLine = "";
		    while (!in.ready()); // busy wait until ready to read
		    readLine = in.readLine();
		    if (readLine.contains("OK")) {
		        out.printf("%s %d\n",
		                   "ex1", usernum + 1, Integer.parseInt(readLine.split(" ")[1]) + 1);
		    }
		    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ClientEx0 c = new ClientEx0(args[0]);
		c.run();
	}
	
}
