package cdi.custom.scope.cdr.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
@author mkamin
*/
public enum OracleJDBC {

	OracleJDBC;

	public final static String MARGARET = "margaret";
	public final static String ARIEL = "ariel";
	public final static String CALYPSO = "calypso";

	private enum Destination {
		MARGARET("margaret", "jdbc:oracle:thin:@margaret:1521:margaret", "cdrs", "cdrsq12"),
		ARIEL("ariel", "jdbc:oracle:thin:@ariel:1521:oberon2", "cdrs", "cdrsq12"),
		CALYPSO("calypso", "jdbc:oracle:thin:@calypso:1521:calypso", "cdrs", "cdrsq12");

		private String name;
		private String connStr;
		private String login;
		private String pass;

		private Destination(String name, String connStr, String login, String pass) {
			this.setName(name);
			this.setConnStr(connStr);
			this.setLogin(login);
			this.setPass(pass);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getConnStr() {
			return connStr;
		}

		public void setConnStr(String connStr) {
			this.connStr = connStr;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}

		public static Destination getByName(String name) {
			for (int i = 0; i < Destination.values().length; i++) {
				if (name.equals(Destination.values()[i].getName())) { return Destination.values()[i]; }
			}
			return MARGARET;
		}
	}

	public Connection getConnection(String name) throws FileNotFoundException {

		//		System.out.println("-------- Oracle JDBC Connection Testing ------");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
		}
		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		try {
			Destination db = Destination.getByName(name);
			connection = DriverManager.getConnection(db.getConnStr(), db.getLogin(), db.getPass());
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}

		if (connection != null) {
			System.out.println("You made it, take control of your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return connection;
	}
}
