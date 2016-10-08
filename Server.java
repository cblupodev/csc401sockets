package csc481hw1.section3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	
	public void run() {
        ServerSocket serverSocket = null;
        Socket client = null;
        
        try {
			serverSocket = new ServerSocket(6789);
			System.out.println("server started");
				// wait for all the clients to connect before continuing 
				client = serverSocket.accept(); // accept the first client connection
			    BufferedReader in1 = new BufferedReader(new InputStreamReader(client.getInputStream()));
				client = serverSocket.accept();
			    BufferedReader in2 = new BufferedReader(new InputStreamReader(client.getInputStream()));
				client = serverSocket.accept();
			    BufferedReader in3 = new BufferedReader(new InputStreamReader(client.getInputStream()));
			    System.out.println("all the client buffers are in");
			    
			    // try and read from each client
				String inline;
				System.out.println("read from client 1:");
				while ((inline = in1.readLine()) != null) { // continue to read if haven't read all the lines
					System.out.println(inline);
				}
				
				System.out.println("read from client 2:");
				while ((inline = in2.readLine()) != null) {
					System.out.println(inline);
				}

				System.out.println("read from client 3:");
				while ((inline = in3.readLine()) != null) {
					System.out.println(inline);
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.run();
	}
}
