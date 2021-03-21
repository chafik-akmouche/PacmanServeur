package BaseDeDonnee;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BaseDeDonnee {
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultatRequette = null; 
	
	// methode connexion a la bdd
	public static void connexion () throws IOException, ClassNotFoundException, SQLException {
		Properties properties = new Properties();

		FileInputStream fiStream = new FileInputStream("conf.properties");
		properties.load(fiStream);
		
		Class.forName(properties.getProperty("jdbc.driver.class")); //com.mysql.jdbc.Driver
		String url = properties.getProperty("jdbc.url");
		String login = properties.getProperty("jdbc.login");
		String password = properties.getProperty("jdbc.password");
		
		System.out.println(url + " " + login + " " + password);
		
		connection = DriverManager.getConnection(url, login, password);		
	}
	
	
	//Vérifie si le pseudo est dans la BDD et renvoi l'ID correspondant au pseudo
		public static int verificationID(String username, String password) {
			try {
				//Connexion à la BDD
				BaseDeDonnee.connexion();
				
				String requette = "SELECT * FROM utilisateur WHERE pseudo = '" + username + "';" ;
				
				statement = connection.createStatement();
				resultatRequette = statement.executeQuery(requette);
				
				//On va vérifier si dans le resultatRequette il existe un pseudo correspondant à username
				if(!resultatRequette.next()) {
					//System.out.println("Ce compte n'existe pas");
					return -1;
				} else {
					//Mot de passe récupérer de la BBD correspondant à pseudo
					String passwordRequette = resultatRequette.getString("password");
					
					if(passwordRequette.equals(password)) {
						//id récupérer de la BBD correspondant à pseudo
						int idRequette = resultatRequette.getInt("id");
						return idRequette;
					} else
						return -1;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(resultatRequette != null)
					try {
						resultatRequette.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
				if(statement != null)
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			
			return -1;
		}
		
		
		//Enregistre le score d'un utilisateur dans la BDD
		public static void enregistrerScore(int id,int score) {
			try {
				//Connexion à la BDD
				BaseDeDonnee.connexion();
				
				String requette = "INSERT INTO partie (id,score,date) VALUES ('" + id + "','" + score + "',NOW());";
				
				statement = connection.createStatement();
				statement.executeUpdate(requette);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(resultatRequette != null)
					try {
						resultatRequette.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
				if(statement != null)
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	

}
