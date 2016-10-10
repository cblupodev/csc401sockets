import java.io.*;
import java.net.*;
import java.util.*;

// pay attention the instructions from section 4.2

public class Ex0 {
	
	private Socket socket = null;
	private String serverAddress = "152.1.13.219";
	private int serverPort = 20001;
	private String clientAddress;  // client ip address
	
	// 3
	private String clientPort = "6789";
	
	public Ex0(String clientAddress) {
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
		    String req = "ex0 " + serverAddress+"-"+serverPort + " " + clientAddress+"-"+clientPort + " " + usernum + " " + " C.B.Lupo\n";
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
    		
    		// 5
    		// Verify that the first word on the second line is "OK", the value of usernum+1, and output the
    		// received random number (servernum).
		    if (readLine[1].contains("OK")) {
		    	int usernum2 = Integer.parseInt(readLine[1].split(" ")[1]);
		    	int servernum = Integer.parseInt(readLine[1].split(" ")[3]);
	    		sop("<STEP2: Received the confirmation string from the server>:");
	    		sop(readLine[0]);
	    		sop(readLine[1]);
	    		sop("");
		    	if (usernum2 == usernum+1) {
					
					// 5, 6		    		
			    	// Construct an ack string and write it to the socket (using send())
		    		req = "ex0 " + usernum2 + " " + (servernum + 1);
		    		sop("<STEP3: Sending an ACK message to the server>:");
		    		sop(req);
		    		sop("");
		    		out.println(req);
			        
				    // 7
				    // Read data from the socket (using recv()) until the newline character is encountered
	    			readLine[0] = in.readLine();
	    			readLine[1] = in.readLine();
			    	
			    	// 8
			    	// Verify that the string "OK" is received and output the received value of servernum+1. If not verified, print
					// an error indication and the received staticring.
				    if (readLine[1].contains("OK")) {
					    int servernum2 = Integer.parseInt(readLine[1].split(" ")[1]);
			    		sop("<STEP4: Received the second ACK from the server>:");
			    		sop(readLine[0]);
			    		sop(readLine[1]);
				    	if (servernum2 == servernum + 1) {
				    	} else {
				    		sop("\nError   servernum is not one value greater than the previous one");
				    	}
				        socket.close();
				    } else {
				    	sop("\nError   " + readLine[1]);
				    }
				// If the first word is not "OK", print an error indication and the received string.
		    	} else {
		    		sop("\nError   usernum is not one value greater than the previous one");
		    	}
		        
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
		Ex0 c = new Ex0(args[0]);
		c.run();
	}
	
	public static void sop(Object message) {
		System.out.println(message);
	}
	
}
