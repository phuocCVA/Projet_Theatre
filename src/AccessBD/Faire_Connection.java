package AccessBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Faire_Connection {

 private static final String configurationFile = "etc/BD.properties";
 
 public static Connection getConnexion() {
    Connection conn = null;  
    try {
	  String jdbcDriver, dbUrl, username, password;
	  
	  DatabaseAccessProperties dap = new DatabaseAccessProperties(configurationFile);
	  jdbcDriver	= dap.getJdbcDriver();
	  dbUrl			= dap.getDatabaseUrl();
	  username		= dap.getUsername();
	  password		= dap.getPassword();
	  
    // Load the database driver
    Class.forName(jdbcDriver) ;

    // Get a connection to the database
    conn = DriverManager.getConnection(dbUrl,username,password);
    if (conn!=null) System.out.println("Connecté");
    
    //Faire une méthode
    //	MethodesBD.methode_a_2(conn);

    // Print information about connection warnings
    SQLWarningsExceptions.printWarnings(conn);	 
    }
    catch( SQLException se ) {	  
	  // Print information about SQL exceptions
	  SQLWarningsExceptions.printExceptions(se);
    }
    catch( Exception e ) {
      System.err.println( "Exception: " + e.getMessage()) ;
      e.printStackTrace();
    }
    return conn; 
}

}
