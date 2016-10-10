import java.io.*;
import java.net.*;
import java.util.*;

// pay attention the instructions from section 4.2

public class ClientEx1 {
	
	private Socket psock = null;
	private String serverAddress = "152.1.13.219";
	private int serverPort = 20001;
	private String clientAddress;  // client ip address
	public static Socket newsock = null;
	
	// 3
	private int clientPort = 6788;
	
	public ClientEx1(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
	public void run() {
		try {
		    
		    // 1
		    // Create a socket (of type SOCK_STREAM and family AF_INET) using socket()
		    // 2
		    // Bind the socket psock to a random port number in the range from 20100 to 60000, using bind()
		    Random rand = new Random();
		    int ranport = rand.nextInt((60000 - 20100) + 1) + 20100;
			psock = new Socket(serverAddress, serverPort, null, clientPort);
			PrintWriter out = new PrintWriter(psock.getOutputStream(), true); // the stream writer
		    BufferedReader in = new BufferedReader(new InputStreamReader(psock.getInputStream())); // the stream reader
		    
		    
    		ServerSocket server = null;
		    try {
		    	server = new ServerSocket(ranport);
		    } catch (Exception se) { 
		    	se.printStackTrace();
		    } // ignore all ready bound exception
		    
		    
		    // 3
		    // Find out what port psock was bound to using the getsockname() function.   To determine
			// the IP address of the socket, you can use the gethostname() and getaddrinfo()
			// functions. The former returns the name of the host, while the latter returns the list of IP
			// addresses associated with a name of this type. Print out the address and port, so the user can
			// see what's going on.
		    sop("<The IP address and port number of psock([IP] [PORT])>:");
		    sop(psock.getLocalAddress() + " " + ranport);
		    sop("");
		    
		    // 5,6
		    // Construct the request string to be sent to the server. The process is similar to Exercise 0, but
			// the client endpoint specifier has a different meaning.
			// Open the first connection to the server and send the request.
		    String req = "ex1 " + serverAddress+"-"+serverPort + " " + clientAddress+"-"+psock.getLocalPort() + " " + ranport + " " + " C.B.Lupo\n";
		    sop("<Sending to the server on the FRIST connection>:");
		    sop(req);
		    sop(""); // write empty line
		    out.println(req); // send the request 

		    // 7
		    // Read data from the socket (using recv()) until the second newline character is encountered
		    String[] readLine = new String[2];
		    // I'm hardcoding the array indexes because for whatever reason the while(in.ready()) isn't working right here
	    	readLine[0] = in.readLine();
	    	readLine[1] = in.readLine();
    		
    		// 7
    		// Receive the confirmation string from the server on the first connection opened in step 6, if the
			// first word of the status line is "OK", save the random number for constructing the string that will
			// be sent on the second connection
		    if (readLine[1].contains("OK")) {
		    	int servernum = Integer.parseInt(readLine[1].split(" ")[3]);
	    		sop("<Received from server first confirmation string on the FIRST connection>:");
	    		sop(readLine[0]);
	    		sop(readLine[1]);
	    		sop("");
	    		
	    		newsock = server.accept();
	    		
	    		// 8
	    		// Call accept() on psock, if successful, this will return another socket (call it newsock) for
				// the second connection, which has been initiated by the server
	    		BufferedReader in2 = new BufferedReader(new InputStreamReader(newsock.getInputStream()));
	    		PrintWriter out2 = new PrintWriter(newsock.getOutputStream(), true);
	    		
	    		String readLine2 = "";
	    		
	    		// 9
	    		// Call recv() on newsock to get the new random number from server
	    		readLine2 = in2.readLine();
	    		sop("<Received from server on the SECOND connection>:");
	    		sop(readLine2);
	    		sop("");
	    		int newservernum = Integer.parseInt(readLine2.split(" ")[4]);
	    		sop("<Sending to the server on the SECOND connection>:");
	    		sop((servernum + 1) + " " + (newservernum + 1));
	    		sop("");
	    		
	    		// 10, send the string and then close the second connection
	    		out2.println((servernum + 1) + " " + (newservernum + 1));
	    		newsock.close();
	    		
	    		// 11
	    		// Receive data on the original connection, printing out the result. Close the original connection and exit
	    		readLine2 = ""; // clear the buffer
	    		readLine2 = in.readLine();
	    		sop("<Received from server the second confirmation on the FIRST connection>:");
	    		sop(readLine2);
		        
		    } else {
		    	// 5
		    	System.out.println("Error   " + readLine[1]); // error occured
		    }
		    psock.close();
		} catch (Exception e) {
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
