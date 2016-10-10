import java.io.*;
import java.net.*;
import java.util.*;

// pay attention the instructions from section 4.2

public class ClientEx1 {
	
	private Socket socket = null;
	private String serverAddress = "152.1.13.219";
	private int serverPort = 20001;
	private String clientAddress;  // client ip address
	
	// 3
	private String clientPort = "6789";
	
	public ClientEx1(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public void run() {
		try {
		    
		    // 1,2
		    // Create a socket (of type SOCK_STREAM and family AF_INET) using socket()
			socket = new Socket(serverAddress, serverPort);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // the stream writer
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // the stream reader
		    
		    // 3
		    // Generate an integer from 20100 to 60000 randomly (usernum)
		    Random rand = new Random();
		    int usernum = rand.nextInt((60000 - 20100) + 1) + 20100;
		    
		    // 4
		    // Write the client request string to the socket (using send()).
		    String req = "ex1 " + serverAddress+"-"+serverPort + " " + clientAddress+"-"+clientPort + " " + usernum + " " + " C.B.Lupo\n";
		    sop("<STEP1: Sending the initial request to the server>:");
		    sop(req);
		    sop(""); // write empty line
		    out.println(req); // send the request 

		    // 5
		    // Read data from the socket (using recv()) until the second newline character is encountered
		    String[] readLine = new String[2];
		    // I'm hardcoding the array indexes because for whatever reason the while(in.ready()) isn't working right here
	    	readLine[0] = in.readLine();
	    	readLine[1] = in.readLine();
    		
    		// 7
    		// Receive the confirmation string from the server on the first connection opened in step 6; if the
			// first word of the status line is “OK”, save the random number for constructing the string that will
			// be sent on the second connection
		    if (readLine[1].contains("OK")) {
		    	int servernum = Integer.parseInt(readLine[1].split(" ")[1]);
	    		sop("<STEP2: Received the confirmation string from the server>:");
	    		sop(readLine[0]);
	    		sop(readLine[1]);
	    		sop("");
	    		
	    		
	    		// 8
	    		// Call accept() on psock; if successful, this will return another socket (call it newsock) for
				// the second connection, which has been initiated by the server
	    		ServerSocket server = new Socket();
	    		Socket newsock = server.accept();
	    		BufferedReader in2 = new BufferedReader(new InputStreamReader(socket.getInputStream));
	    		PrintWriter out2 = new PrintWriter(socket.getOutputStream(), true);
	    		
	    		readLine = "";
	    		
	    		// 9
	    		// Call recv() on newsock to get the new random number from server
	    		readLine = in2.readLine();
	    		int newservernum = Integer.parseInt(readLine.split(" ")[4]);
	    		sop("CSC 401 server sent " + newservernum);
	    		
	    		// 10, send the string and then close the second connection
	    		out2.println((servernum + 1) + " " + (newservernum + 1));
	    		newsock.close();
	    		
	    		// 11
	    		// Receive data on the original connection, printing out the result. Close the original connection and exit
	    		readLine = ""; // clear the buffer
	    		readLine = socket.readLine();
	    		sop(readLine);
		        
		    } else {
		    	// 5
		    	System.out.println("Error   " + readLine[1]); // error occured
		    }
		    socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// 3, address from the user
		ClientEx1 c = new ClientEx1(args[0]);
		c.run();
	}
	
	public static void sop(Object message) {
		System.out.println(message);
	}
	
}
