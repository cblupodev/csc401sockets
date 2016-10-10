import java.io.*;
import java.net.*;
import java.util.*;

public class Accept extends Thread {
    
    ServerSocket server = null;
    
    public Accept(ServerSocket server) {
        this.server = server;
    }
    
    public void run() {
        try {
            Socket c = server.accept();
            ClientEx1.newsock = c;
        } catch (Exception e) { }
    }
	
	public static void sop(Object message) {
		System.out.println(message);
	}
    
}