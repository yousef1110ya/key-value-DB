import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

public class Main{
	public static void main(String[] args){
		ServerSocket serverSocket = null ; 
		Socket clientSocket = null ;
		int port = 6379;
		try{
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);

			clientSocket = serverSocket.accept();
			while(true){
				// Read the input from client
				clientSocket.getOutputStream().write("java-redis: > ".getBytes());
				byte[] input = new byte[1024];
				clientSocket.getInputStream().read(input);
				String inputString = new String(input).trim();
				System.out.println("recived:" + inputString);
				String res = inputString.replace("PING" , "").trim();
				if (res.isEmpty()) {
					res = "PONG";
				}
				res = res + "\r\n";
				clientSocket.getOutputStream().write("+PONG\r\n".getBytes());

			}
		}catch(IOException e ){
			System.out.println("catched an IOException error ");
			System.out.println(e.getMessage());
		
		}finally{
			try{
				if(clientSocket != null){
					clientSocket.close();
				}
			} catch(IOException e){
				System.out.println("catched an IOException while closing the clientSocket");
				System.out.println(e.getMessage());
			}
		}
	}


}
