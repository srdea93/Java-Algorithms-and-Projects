package dea_s_hw9;
import java.io.*;
import java.net.*;

public class main {

	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(20002);
		}
		catch (IOException e) {
			System.err.println("Could not listen on port: 20002.");
			System.exit(1);
		}
		
		Socket clientSocket = null;
		while(true) {
			clientSocket = serverSocket.accept();
			BhcThread thread = new BhcThread(clientSocket);
			thread.start();
		}

	}

}
