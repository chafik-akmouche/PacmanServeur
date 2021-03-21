package Serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur{
	public static PrintWriter out;
	public static String chemin = "res/layouts/contestClassic.lay"; 
	public static int port = 5000;
	
	public static void main(String[] args) throws IOException{
		try {
			@SuppressWarnings("resource")
			// Creation d'un socket serveur en ecoute du port (port)
			ServerSocket serveurSocket = new ServerSocket(port);
			System.out.println("Serveur "+InetAddress.getLocalHost()+" lancé");
			System.out.println("Port écouté :"+port);
			// Tant qu'il y a des clients qui demandent de se connecter
			while (true) {
				// Accepter le socket client
				Socket clientSocket = serveurSocket.accept();
				System.out.println("********** Un client vient de se connecter **********");
				System.out.println("IP et port client : "+clientSocket.getRemoteSocketAddress());
				
				// Lancer le thread envoi
				new ServeurEnvoi(clientSocket).start();
				
				// Dès qu'un client est connecté, le serveur lui demande de s'authentifier
				ServeurEnvoi.setMessage("authentification:");
				
				// Lancer le thread reception
				new ServeurReception(clientSocket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}