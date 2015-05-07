package AccessBD;
import Class.Billet;
import Class.Categorie;
import Class.Dossier;
import Class.Place;
import Class.Spectacle;
import Class.Representation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.HashSet;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class MethodesBD {	
    /**
     * Afficher toutes les informations sur tous les spectacles.
     * 
     * @param conn connexion à la base de données
     * @return liste_spectacle: liste des spectacles dans la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static HashSet<Spectacle> methode_a_1(Connection conn) throws SQLException {

        HashSet<Spectacle> liste_spectacle;
        liste_spectacle = new HashSet<Spectacle>();

        // Get a statement from the connection
        Statement stmt = conn.createStatement() ;

        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM LesSpectacles ORDER BY numS") ;

        // Loop through the result set
        while( rs.next() ) {                 
           Integer numS=rs.getInt("numS");
           String nomS=rs.getString("nomS");
           Spectacle spec=new Spectacle();
           spec.setNumS(numS);
           spec.setNom(nomS);
           liste_spectacle.add(spec);
        }

        // Close the result set, statement 
        rs.close() ;
        stmt.close() ;
        return liste_spectacle;
    }

    /**
     * Étant donné un numéro de spectacle, affiche le nom de ce spectacle
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_a_2(Connection conn) throws SQLException {

        //preparation de la requete 
        PreparedStatement st = conn.prepareStatement("SELECT nomS FROM LesSpectacles WHERE numS = ?");
        System.out.println("Saisir le numero de spectale: ");
        Scanner sc = new Scanner(System.in);
        int numero = sc.nextInt();
        st.setInt(1,numero);

        // Execute the query
        ResultSet rs = st.executeQuery() ;

        // Loop through the result set
        while( rs.next() ) {
           System.out.println("Le nom du spectacle " + numero +": " + rs.getString("nomS") ) ;
        }

        // Close the result set, statement 
        rs.close() ;
        st.close() ;
        sc.close() ;		
    }

    /**
     * Étant donné un nom de spectacle, affiche le numéro de ce spectacle
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_a_3(Connection conn) throws SQLException {

        //preparation de la requete 
        PreparedStatement st = conn.prepareStatement("SELECT numS FROM LesSpectacles WHERE nomS = ?");
        System.out.println("Saisir le nom de spectale: ");
        Scanner sc = new Scanner(System.in);
        String nom = sc.nextLine();
        st.setString(1,nom);
        // Execute the query
        ResultSet rs = st.executeQuery() ;

        // Loop through the result set
        while( rs.next() ) {
           System.out.println("Le numéro du spectacle " + nom +": " + rs.getString("numS") ) ;
        }

        // Close the result set, statement 
        rs.close();
        st.close();
        sc.close();
    }

    /**
     * Étant donné un numéro de spectacle,  affiche  le  nom  du  spectacle  et  l'ensemble  des  représentations  
     * de  ce spectacle. 
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_a_4(Connection conn) throws SQLException {

        //preparation de la requete 
        PreparedStatement st = conn.prepareStatement("SELECT * FROM LesSpectacles,LesRepresentations"
                  + " WHERE LesSpectacles.numS = ?"
                  + " AND LesRepresentations.numS = LesSpectacles.numS");
        System.out.println("Saisir le numero de spectale: ");
        Scanner sc = new Scanner(System.in);
        int numero = sc.nextInt();
        st.setInt(1,numero);

        // Execute the query
        ResultSet rs = st.executeQuery() ;

        // Loop through the result set
        System.out.print("Le nom du spectacle " + numero + " : " );
        while( rs.next() ) {
           System.out.print(rs.getString("nomS") ) ;
           System.out.println(", Le Representations: " + rs.getString("dateRep") ) ;
        }

        // Close the result set, statement 
        rs.close() ;
        st.close() ;
        sc.close();
    }


    /**
     * Étant donné un nom de spectacle, affiche les numéros de spectacles associés et, pour chacun d'eux, 
     * l'ensemble des representation du spectacle.  
     * 
     * @param conn connexion à la base de données
     * @param nomS nom du Spectacle
     * @return liste des representations associés avec le spectacle
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static HashSet<Representation> methode_a_5(Connection conn,String nomS) throws SQLException {

        HashSet<Representation> liste_representation;
        liste_representation = new HashSet<Representation>();

        //preparation de la requete 
        PreparedStatement st = conn.prepareStatement("SELECT * FROM LesSpectacles,LesRepresentations"
                  + " WHERE LesRepresentations.numS = LesSpectacles.numS"
                  + " AND LesSpectacles.nomS = ? ORDER BY dateRep");
        st.setString(1,nomS);

          // Execute the query
        ResultSet rs = st.executeQuery() ;

        // Loop through the result set
        while( rs.next() ) {                 
           Integer numS=rs.getInt("numS");
           Date date=rs.getDate("dateRep");
           Representation rep=new Representation();
           rep.setNumS(numS);
           rep.setDate(date);
           liste_representation.add(rep);
        }

        // Close the result set, statement
        rs.close() ;
        st.close() ;        
        return liste_representation;
    }


    /**
     * Affiche tous les spectacles et pour chaque spectacle, toutes ses représentations. 
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_a_6(Connection conn) throws SQLException {

        // Get a statement from the connection
        Statement stmt = conn.createStatement() ;

        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM LesSpectacles") ;

        // Loop through the result set
        while( rs.next() ) {
            int numS=rs.getInt("numS");
            System.out.println("");
            System.out.println("Spectacle: ");
            System.out.println("    Numéro: " + numS) ;
            System.out.println("    Nom: " + rs.getString("nomS")) ;

            PreparedStatement st = conn.prepareStatement("SELECT * FROM LesRepresentations"
                  + " WHERE LesRepresentations.numS = ? ");
           st.setInt(1,numS);
           ResultSet rs1 = st.executeQuery() ;
           while( rs1.next() ) { 
                   System.out.println("    Date de Representasion : " + rs1.getDate("dateRep") ) ;
           }
        }
        // Close the result set, statement 
        rs.close() ;
        stmt.close() ;
    }


    /**
     *  Étant donné une date de représentation, affiche le numéro et le nom du spectacle associé 
     * 
     * @param conn connexion é la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de donn�es
     */
    public static void methode_a_7(Connection conn, Date dateRep) throws SQLException {


        //preparation de la requete 	
        PreparedStatement st = conn.prepareStatement("SELECT * FROM LesSpectacles,LesRepresentations"
                      + " WHERE LesRepresentations.numS = LesSpectacles.numS"
                      + " AND LesRepresentations.dateRep = ?");
        st.setDate(1,dateRep);

            // Execute the query
        ResultSet rs = st.executeQuery() ;

        // Loop through the result set
        System.out.println("");
        System.out.println("Le Representation date: " + dateRep.toString());
        while( rs.next() ) {	
            System.out.println("Spectacle: ");
            System.out.println("    Num�ro: " + rs.getString("numS") ) ;
            System.out.println("    Nom: " + rs.getString("nomS") ) ;	    
        }
        // Close the result set, statement 
        st.close();
        rs.close() ;
    }

    /**
     * Afficher toutes les informations sur tous les Categories.
     * 
     * @param conn connexion à la base de donn�es
     * @return liste des categories
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static HashSet<Categorie> methode_a_8(Connection conn) throws SQLException {
    	
        HashSet<Categorie> liste_categorie;
        liste_categorie = new HashSet<Categorie>();

        // Get a statement from the connection
        Statement stmt = conn.createStatement() ;

        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM LesCategories") ;

        // Loop through the result set
        while( rs.next() ) {                 
            String nomC=rs.getString("nomC");
            int prix=rs.getInt("prix");
            Categorie cat=new Categorie();
            cat.setNomC(nomC);
            cat.setPrix(prix);
            liste_categorie.add(cat);
        }

	    // Close the result set, statement 
	    rs.close() ;
	    stmt.close() ;
	    return liste_categorie;
    }

    /**
     * Afficher toutes les informations sur tous les représentations.
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_a_9(Connection conn) throws SQLException {

        // Get a statement from the connection
        Statement stmt = conn.createStatement() ;

        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM LesRepresentations") ;

        // Loop through the result set
        while( rs.next() ) {
           System.out.print("Numéro de repr�sentations : " + rs.getString("numS")) ;
           System.out.println(", Date de repr�sentations : " + rs.getDate("dateRep") ) ;
        }

        // Close the result set, statement 
        rs.close() ;
        stmt.close() ;
    }

    /**
     * Afficher toutes les informations sur tous les Places.
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_a_10(Connection conn) throws SQLException {

        // Get a statement from the connection
        Statement stmt = conn.createStatement() ;

        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM LesPlaces") ;
        System.out.println("Place | Rang | Zone ");
        System.out.println("------------------------------------------");
        // Loop through the result set
        while( rs.next() ) {
           System.out.print(rs.getString("noPlace")+"") ;
           System.out.print("       " + rs.getString("noRang") ) ;
           System.out.println("       " + rs.getString("numZ") ) ;
        }

        // Close the result set, statement 
        rs.close() ;
        stmt.close() ;
    }

    /**
     * Afficher toutes les informations sur tous les Zones.
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_a_11(Connection conn) throws SQLException {

        // Get a statement from the connection
        Statement stmt = conn.createStatement() ;

        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM LesZones") ;
        System.out.println("Zone | Categorie ");
        System.out.println("------------------------------------------");
        // Loop through the result set
        while( rs.next() ) {
           System.out.print(rs.getString("numZ")+"") ;
           System.out.println("    |  " + rs.getString("nomC") ) ;
        }

        // Close the result set, statement 
        rs.close() ;
        stmt.close() ;
    }

    /**
     * Afficher toutes les informations sur tous les Dossiers.
     * 
     * @param conn connexion à la base de données     
     * @return liste_dossier liste des dossiers     
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static HashSet<Dossier> methode_a_12(Connection conn) throws SQLException {

        HashSet<Dossier> liste_dossier;
        liste_dossier = new HashSet<Dossier>();

        // Get a statement from the connection
        Statement stmt = conn.createStatement() ;

        // Execute the query          

        ResultSet rs = stmt.executeQuery("SELECT * FROM LesDossiers") ;

        while( rs.next() ) {                 
             int noDossier=rs.getInt("noDossier");
             int prixTotal=rs.getInt("prixTotal");
             Dossier dos=new Dossier();
             dos.setNoDossier(noDossier);
             dos.setPrixTotal(prixTotal);
             liste_dossier.add(dos);
          }
        // Close the result set, statement 
        rs.close() ;
        stmt.close() ;
        return liste_dossier;
    }

    /**
     * Afficher toutes les informations sur tous les Billets.
     * 
     * @param conn connexion à la base de données
     * @return liste_billet liste des billets 
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static HashSet<Billet> methode_a_13(Connection conn) throws SQLException {

        HashSet<Billet> liste_billet;
        liste_billet = new HashSet<Billet>();

        // Get a statement from the connection
        Statement stmt = conn.createStatement() ;

        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM LesBillets") ;
        System.out.println("Serie | Spectacle |     DateRep    | Place  |  Rang  |    Date Achat    | Dossier ");
        System.out.println("------------------------------------------");
        // Loop through the result set
        while( rs.next() ) {
            int noSerie = rs.getInt("noSerie");
            int numS = rs.getInt("numS");
            Date dateRep = rs.getDate("dateRep");
            int noRang=rs.getInt("noRang");
            int noPlace=rs.getInt("noPlace");
            Date dateEmission = rs.getDate("dateEmission");
            int noDossier = rs.getInt("noDossier");

            Billet bil=new Billet();
            bil.setNoSerie(noSerie);
            bil.setNumS(numS);
            bil.setDateRep(dateRep);
            bil.setNoRang(noRang);
            bil.setNoPlace(noPlace);
            bil.setDateEmission(dateEmission);
            bil.setNoDossier(noDossier);
            liste_billet.add(bil); 

            System.out.print(noSerie);
            System.out.print("     |    " + numS) ;
            System.out.print("    |  " + dateRep ) ;
            System.out.print("    |  " + noPlace ) ;
            System.out.print("    |  " + noRang ) ;
            System.out.print("    |  " + dateEmission ) ;
            System.out.println("    |  " + noDossier ) ;
        }
        // Close the result set, statement 
        rs.close() ;
        stmt.close() ;
        return liste_billet;            
    }
	
	 /**
     * Étant donné un numéro d'une zone, affiche les informations concernant cette Zone
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_b(Connection conn) throws SQLException {

          //preparation de la requete 
          PreparedStatement st = conn.prepareStatement("SELECT * FROM LesZones WHERE numZ = ?");
          System.out.println("Saisir le numero d'une Zone: ");
          Scanner sc = new Scanner(System.in);
          int numZ = sc.nextInt();
          st.setInt(1,numZ);
          
          // Execute the query
          ResultSet rs = st.executeQuery() ;

          // Loop through the result set
          while( rs.next() ) {
             System.out.println("le Categorie de Zone numero " + numZ +": " + rs.getString("nomC") ) ;
          }

          // Close the result set, statement 
          sc.close();
          rs.close() ;
          st.close() ;
    }
    
    /**
     * Étant donné categorie d'un zone , affiche les numeros de zones associees
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_c(Connection conn) throws SQLException {

          //preparation de la requete 
          PreparedStatement st = conn.prepareStatement("SELECT * FROM LesZones WHERE nomC = ?");
          System.out.println("Saisir le categorie: ");
          Scanner sc = new Scanner(System.in);
          String nomC = sc.nextLine();
          st.setString(1,nomC);
          
          // Execute the query
          ResultSet rs = st.executeQuery() ;

          // Loop through the result set
          System.out.println("les Zone de Categorie " + nomC +": " );
          while( rs.next() ) {            
             System.out.println("Zone " + rs.getString("numZ") ) ;
          }

          // Close the result set, statement 
          sc.close();
          rs.close() ;
          st.close() ;
    }
    
    /**
     * Étant donné categorie d'un zone, affiche le prix de la zone et l'ensemble des places de cette Zone 
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static HashSet<Place> methode_d(Connection conn, String nomC) throws SQLException {
        
        HashSet<Place> liste_place;
        liste_place = new HashSet<Place>();
        
        //preparation de la requete 
        PreparedStatement st = conn.prepareStatement (
                       " SELECT * FROM ( ( SELECT * FROM LesCategories natural join LesZones Where nomC = ? )"
                       + " natural join LesPlaces ) order by numZ " );
        st.setString(1,nomC);

        // Execute the query
        ResultSet rs = st.executeQuery() ;
        // Loop through the result set        
        while( rs.next() ) {                 
                int numZ=rs.getInt("numZ");
                int noPlace=rs.getInt("noPlace");
                int noRang=rs.getInt("noRang");
                int prix=rs.getInt("prix");
                Place pla=new Place();
                pla.setNumZ(numZ);
                pla.setNoPlace(noPlace);
                pla.setNoRang(noRang);
                pla.setPrix(prix);
                liste_place.add(pla);
            }
        
        // Close the result set, statement 
        rs.close() ;
        st.close() ;
        
        return liste_place;
    }
    
    /**
     * Étant donné un numéro d'un zone d'une zone, affiche la catégorie et l'ensemble des places de cette Zone 
     * 
     * @param conn connexion à la base de données
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_e(Connection conn) throws SQLException {

        //preparation de la requete 
        PreparedStatement st = conn.prepareStatement("SELECT * FROM LesZones natural join lesPlaces WHERE numZ = ?");
        System.out.println("Saisir le numero de la zone: ");
        Scanner sc = new Scanner(System.in);
        int numZ = sc.nextInt();
        st.setInt(1,numZ);
        // Execute the query
        ResultSet rs = st.executeQuery() ;

        // Loop through the result set
        if (rs.next()){
                System.out.println("Categorie de la Zone " + numZ +": " + rs.getString("nomC"));
                System.out.println("les Place de Zone " + numZ +": " );
                System.out.println("Place | Rang ");
                    System.out.println("------------------------------------------");
                System.out.print(rs.getString("noPlace") ) ;
                System.out.println("       " + rs.getString("noRang") ) ;
        }
        while( rs.next() ) {            
           System.out.print(rs.getString("noPlace") ) ;
           System.out.println("       " + rs.getString("noRang") ) ;
        }
        // Close the result set, statement 
        sc.close();
        rs.close() ;
        st.close() ;
    }
    
    /**
     * Étant donné une vente, affiche les informations concernant des places achetées 
     * 
     * @param conn connexion à la base de données
     * @param noDossier numéro de dossier 
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static HashSet<Billet> methode_g(Connection conn, int noDossier) throws SQLException {
        
        HashSet<Billet> liste_billet;
        liste_billet = new HashSet<Billet>();
        
        //preparation de la requete 
    	PreparedStatement st = conn.prepareStatement("SELECT * FROM LesDossiers natural join LesBillets WHERE noDossier = ?");
        st.setInt(1,noDossier);
        // Execute the query
        ResultSet rs = st.executeQuery() ;

        // Loop through the result set
      	System.out.println("Dossier " + noDossier);
        System.out.println("les Place achetées ");
        System.out.println("Place | Rang ");
        System.out.println("------------------------------------------");	    
        
        while( rs.next() ) {                 
                int noSerie = rs.getInt("noSerie");
                int numS = rs.getInt("numS");
                Date dateRep = rs.getDate("dateRep");
                int noRang=rs.getInt("noRang");
                int noPlace=rs.getInt("noPlace");
                Date dateEmission = rs.getDate("dateEmission");
                
                Billet bil=new Billet();
                bil.setNoSerie(noSerie);
                bil.setNumS(numS);
                bil.setDateRep(dateRep);
                bil.setNoRang(noRang);
                bil.setNoPlace(noPlace);
                bil.setDateEmission(dateEmission);
                bil.setNoDossier(noDossier);
                liste_billet.add(bil);
                
                System.out.print(noPlace) ;
                System.out.println("       " + noRang ) ;
        }   

        // Close the result set, statement 
        rs.close() ;
        st.close() ;
        return liste_billet;
  }
    
    /**
     *  Étant donné une vente, affiche le prix global de la vente
     * 
     * @param conn connexion à la base de données
     * @param noDossier numéro de dossier 
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static int methode_h(Connection conn, int noDossier) throws SQLException {
        int prixTotal = 0;
        //preparation de la requete 
    	PreparedStatement st = conn.prepareStatement("SELECT prixTotal FROM LesDossiers WHERE noDossier = ?");
        st.setInt(1,noDossier);
        // Execute the query
        ResultSet rs = st.executeQuery() ;

        while( rs.next() ) {
                prixTotal = rs.getInt("prixTotal");
	        System.out.println("Dossier " + noDossier);
	        System.out.println("Prix global: "+ prixTotal) ;
        }
        // Close the result set, statement
        rs.close() ;
        st.close() ;
        return prixTotal;
        
  }
    
    /**
     * Étant donné une date de representation d'un spectacle, 
     * affiche l'ensemble des places vendues de cette représentation 
     * @param conn connexion à la base de données 
     * @throws SQLException en cas d'erreur d'accès à la base de données
     */
    public static void methode_i(Connection conn) throws SQLException {

        //preparation de la requete 
    	PreparedStatement st = conn.prepareStatement(
    	    "SELECT * FROM LesRepresentations natural join LesBillets WHERE dateRep = ?");
        System.out.println("Saisir le Representation: ");
        Scanner sc = new Scanner(System.in);
        String date = sc.nextLine();
        st.setString(1,date);
        
        // Execute the query
        ResultSet rs = st.executeQuery() ;

        // Loop through the result set
        if (rs.next()){
      	  System.out.println("Repesentation " + date);
	          System.out.println("les Place vendue ");
	          System.out.println("Place | Rang ");
		      System.out.println("------------------------------------------");
	          System.out.print(rs.getString("noPlace") ) ;
	          System.out.println("       " + rs.getString("noRang") ) ;
        }
        while( rs.next() ) {            
           System.out.print(rs.getString("noPlace") ) ;
           System.out.println("       " + rs.getString("noRang") ) ;
        }
        // Close the result set, statement 
        sc.close();
        rs.close() ;
        st.close() ;
  }
    
    /**
     * Etant donné une date de représentation d'un spectacle, 
     * affiche l'ensemble des places disponibles de cette représentation 
     * @param conn connexion à la base de données
     * @param date date de représentation
     * @throws SQLException en cas d'erreur d'accès à la base de données
     * @throws java.sql.SQLException
     */
    public static HashSet<Place> methode_j(Connection conn, Date date) throws SQLException {
        
        HashSet<Place> liste_place;
        liste_place = new HashSet<Place>();  
        
        //preparation de la requete 
    	PreparedStatement st = conn.prepareStatement("SELECT * FROM LesPlaces natural join (SELECT noRang,noPlace FROM LesPlaces MINUS SELECT noRang,noPlace FROM LesRepresentations natural join LesBillets WHERE dateRep = ? order by noRang)" );
        st.setDate(1,date);
        
        // Execute the query
        ResultSet rs = st.executeQuery() ;
        
        System.out.println("Repesentation " + date);
	          System.out.println("les Places disponibles ");
	          System.out.println("Zone | Rang | Place ");
		  System.out.println("------------------------------------------");
        
        // Loop through the result set
        while( rs.next() ) {                 
                int numZ=rs.getInt("numZ");
                int noPlace=rs.getInt("noPlace");
                int noRang=rs.getInt("noRang");
                Place pla=new Place();
                pla.setNumZ(numZ);
                pla.setNoPlace(noPlace);
                pla.setNoRang(noRang);
                liste_place.add(pla);
                
                System.out.print(rs.getString("numZ") ) ;
                System.out.print("       " + rs.getString("noRang") ) ;
                System.out.println("       " + rs.getString("noPlace") ) ;
            }
        
        // Close the result set, statement 
        st.close() ;
        return liste_place;
    }
    
      /**
	 * Effectuer la vente d'une seule place 
	 * @param conn connexion à la base de données
     * @param noSerie serie du billet
     * @param numS numéro du spectacle
     * @param dateRep date de représentation
     * @param noRang numéro de rang
     * @param noPlace numéro de place
     * @param dateEmission date de vente
     * @param noDosier numéro de dossier
     * @param prixTotal prix total de vente
	 * @throws SQLException en cas d'erreur d'accés à la base de données
	 */
    public static void methode_k(Connection conn, int noSerie, int numS, Date dateRep, int noRang, int noPlace, Date dateEmission, int noDossier, int prixTotal) throws SQLException {
       
        //preparation de la requete 
        PreparedStatement st = conn.prepareStatement("INSERT INTO LesBillets VALUES(?,?,?,?,?,?,?)");
        PreparedStatement st1 = conn.prepareStatement("INSERT INTO LesDossiers VALUES(?,?)");
        
        st.setInt(1,noSerie);
        st.setInt(2,numS);
        System.out.println(dateRep);
        System.out.println(dateEmission);
        st.setDate(3,dateRep);
        st.setInt(4,noPlace);
        st.setInt(5,noRang);
        st.setDate(6,dateEmission);
        st.setInt(7,noDossier);
        
        st1.setInt(1, noDossier);
        st1.setInt(2, prixTotal);
        // Execute the query
        
        st1.executeQuery() ;
        st.executeQuery() ;
        JOptionPane.showMessageDialog(null, "Reserver");
           
        // Close the result set, statement
        st.close() ;
        st1.close();
    }
    
    /**
	 * Effectuer l'annulation d'une vente d'une seule place
	 * 
	 * @param conn connexion à la base de données
     * @param noSerie serie du billet
     * @param noRang numéro de rang
     * @param noPlace numéro de place
     * @param noDosier numéro de dossier
	 * @throws SQLException en cas d'erreur d'accés à la base de données
	 */
    public static void methode_m(Connection conn, int noSerie, int noRang, int noPlace, int noDossier) throws SQLException {
        int prix = 0;
        //preparation de la requete 
        PreparedStatement st = conn.prepareStatement("DELETE FROM LesBillets WHERE noSerie = ?");
        st.setInt(1,noSerie);

        // Execute the query
        if (JOptionPane.showConfirmDialog(null, "Est ce que vous voulez vraiment supprimer ?")==0) {
            st.executeQuery() ;
            
            PreparedStatement st1 = conn.prepareStatement("SELECT prix FROM LesCategories WHERE nomC=(SELECT nomC FROM LesZones WHERE numZ=(SELECT numZ FROM LesPlaces WHERE noRang=? AND noPlace=?))");
            st1.setInt(1, noRang);
            st1.setInt(2, noPlace);
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                prix = rs.getInt("prix");
            }
            
            PreparedStatement st2 = conn.prepareStatement("UPDATE LesDossiers SET prixTotal=prixTotal-? WHERE noDossier = ?");
            st2.setInt(1,prix);
            st2.setInt(2,noDossier);
            st2.executeQuery();     
            JOptionPane.showMessageDialog(null, "Supprimer");
        } ;        

        // Close the result set, statement
        st.close() ;
    }
    
    /**
	 * Effectuer l'annulation d'une vente d'une vente
	 * 
	 * @param conn connexion à la base de données
	 * @throws SQLException en cas d'erreur d'accés à la base de données
	 */
    public static void methode_n(Connection conn, int noDossier) throws SQLException {
      
        //preparation de la requete 
        PreparedStatement st1 = conn.prepareStatement("DELETE FROM LesBillets WHERE noDossier = ?");
        PreparedStatement st2 = conn.prepareStatement("DELETE FROM LesDossiers WHERE noDossier = ?");
        st1.setInt(1,noDossier);
        st2.setInt(1,noDossier);

        // Execute the query
        if (JOptionPane.showConfirmDialog(null, "Est ce que vous voulez vraiment supprimer ?")==0) {
            st1.executeQuery();
            st2.executeQuery();    
            JOptionPane.showMessageDialog(null, "Supprimer");
        } ;
        
        // Close the result set, statement
        st1.close() ;
        st2.close() ;

    }    
    
}
