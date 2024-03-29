
   import sources.*;
import destinations.*;
import transmetteurs.*;
import information.*;
import visualisations.*;

   import java.util.regex.*;
import java.util.*;
import java.lang.Math;

	
   import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/** La classe Simulateur permet de construire et simuler une cha�ne de transmission compos�e d'une Source, d'un nombre variable de Transmetteur(s) et d'une Destination.  
 * @author cousin
 * @author prou
 *
 */
   public class Simulateur {
      	
   /** indique si le Simulateur utilise des sondes d'affichage */
      private          boolean affichage = false;
   /** indique si le Simulateur utilise un message g�n�r� de mani�re al�atoire */
      private          boolean messageAleatoire = true;
   /** indique si le Simulateur utilise un germe pour initialiser les g�n�rateurs al�atoires */
      private          boolean aleatoireAvecGerme = false;
   /** la valeur de la semence utilis�e pour les g�n�rateurs al�atoires */
      private          Integer seed = null;
   /** la longueur du message al�atoire � transmettre si un message n'est pas impose */
      private          int nbBitsMess = 100; 
   /** la cha�ne de caract�res correspondant � m dans l'argument -mess m */
      private          String messageString = "100";
      
      
      private          Forme type = Forme.RZ; 
      /** la chaï¿œne de caractï¿œres qui le nb d'échantillon */
      private          int nbEch=30 ;
      /** la chaï¿œne de caractï¿œres qui le amplitude min */
      private          float amplMin = 0.0f;
      /** la chaï¿œne de caractï¿œres qui le amplitude max */
      private          float amplMax = 1.0f;
      
      private          boolean messageAnalogique = false;
   	
   /** le  composant Source de la chaine de transmission */
      private			  Source <Boolean>  source = null;
   /** le  composant Transmetteur parfait logique de la chaine de transmission */
      private			  Transmetteur <Boolean, Boolean>  transmetteurLogique = null;
   /** le  composant Destination de la chaine de transmission */
      private			  Destination <Boolean>  destination = null;
      
      private			Emetteur emetteur = null;
      
      private			Recepteur recepteur = null;
     
      private			  Transmetteur <Float, Float>  transmetteurAnalogique = null;
   
   /** Le constructeur de Simulateur construit une cha�ne de transmission compos�e d'une Source <Boolean>, d'une Destination <Boolean> et de Transmetteur(s) [voir la m�thode analyseArguments]...  
   * <br> Les diff�rents composants de la cha�ne de transmission (Source, Transmetteur(s), Destination, Sonde(s) de visualisation) sont cr��s et connect�s.
   * @param args le tableau des diff�rents arguments.
   *
   * @throws ArgumentsException si un des arguments est incorrect
   *
   */   
      public  Simulateur(String [] args) throws ArgumentsException {
      

      	
         analyseArguments(args);
      
     
        if(messageAleatoire) {
        	
        	source = new SourceAleatoire(nbBitsMess);
        	
        }
        else {
        	
        	source = new SourceFixe(messageString);
        	
        }
        
        
        
        SondeLogique sl1 = new SondeLogique("Sonde log.  1", 500);
        SondeLogique sl2 = new SondeLogique("Sonde log.  2", 500);
        SondeLogique sl3 = new SondeLogique("Sonde log.  3", 500);
        
        
        SondeAnalogique sa1 = new SondeAnalogique("Sonde anal.  1");
        SondeAnalogique sa2 = new SondeAnalogique("Sonde anal.  2");
        
        

        transmetteurLogique = new TransmetteurParfait<Boolean, Boolean>();
        transmetteurAnalogique = new TransmetteurParfait<Float, Float>();
 
        emetteur = new Emetteur (nbEch, amplMax, amplMin, type);
        recepteur = new Recepteur (nbEch, amplMax, amplMin, type);
        destination = new DestinationFinale<Boolean>();

        
        source.connecter(sl1);
        source.connecter(emetteur);
        source.connecter(transmetteurLogique);
        
        emetteur.connecter(transmetteurAnalogique);
        emetteur.connecter(sa1);
        
        transmetteurLogique.connecter(sl2);
        transmetteurLogique.connecter(destination);
        
        transmetteurAnalogique.connecter(sa2);
        transmetteurAnalogique.connecter(recepteur);
        
        recepteur.connecter(sl3);
        recepteur.connecter(destination);
        
      		
      }
   
   
   
   /** La m�thode analyseArguments extrait d'un tableau de cha�nes de caract�res les diff�rentes options de la simulation. 
   * Elle met � jour les attributs du Simulateur.
   *
   * @param args le tableau des diff�rents arguments.
   * <br>
   * <br>Les arguments autoris�s sont : 
   * <br> 
   * <dl>
   * <dt> -mess m  </dt><dd> m (String) constitu� de 7 ou plus digits � 0 | 1, le message � transmettre</dd>
   * <dt> -mess m  </dt><dd> m (int) constitu� de 1 � 6 digits, le nombre de bits du message "al�atoire" � transmettre</dd> 
   * <dt> -s </dt><dd> utilisation des sondes d'affichage</dd>
   * <dt> -seed v </dt><dd> v (int) d'initialisation pour les g�n�rateurs al�atoires</dd> 
   * <br>
   * <dt> -form f </dt><dd>  codage (String) RZ, NRZR, NRZT, la forme d'onde du signal � transmettre (RZ par d�faut)</dd>
   * <dt> -nbEch ne </dt><dd> ne (int) le nombre d'�chantillons par bit (ne >= 6 pour du RZ, ne >= 9 pour du NRZT, ne >= 18 pour du RZ,  30 par d�faut))</dd>
   * <dt> -ampl min max </dt><dd>  min (float) et max (float), les amplitudes min et max du signal analogique � transmettre ( min < max, 0.0 et 1.0 par d�faut))</dd> 
   * <br>
   * <dt> -snr s </dt><dd> s (float) le rapport signal/bruit en dB</dd>
   * <br>
   * <dt> -ti i dt ar </dt><dd> i (int) numero du trajet indirect (de 1 � 5), dt (int) valeur du decalage temporel du i�me trajet indirect 
   * en nombre d'�chantillons par bit, ar (float) amplitude relative au signal initial du signal ayant effectu� le i�me trajet indirect</dd>
   * <br>
   * <dt> -transducteur </dt><dd> utilisation de transducteur</dd>
   * <br>
   * <dt> -aveugle </dt><dd> les r�cepteurs ne connaissent ni l'amplitude min et max du signal, ni les diff�rents trajets indirects (s'il y en a).</dd>
   * <br>
   * </dl>
   * <br> <b>Contraintes</b> :
   * Il y a des interd�pendances sur les param�tres effectifs. 
   *
   * @throws ArgumentsException si un des arguments est incorrect.
   *
   */   
      public  void analyseArguments(String[] args)  throws  ArgumentsException { //ajouter forme, nb ech et a max et min
      		
         for (int i=0;i<args.length;i++){ 
         
              
            if (args[i].matches("-s")){
               affichage = true;
            }
            else if (args[i].matches("-seed")) {
               aleatoireAvecGerme = true;
               i++; 
            	// traiter la valeur associee
               try { 
                  seed =new Integer(args[i]);
               }
                  catch (Exception e) {
                     throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
                  }           		
            }
            
            else if (args[i].matches("-mess")){
               i++; 
            	// traiter la valeur associee
               messageString = args[i];
               if (args[i].matches("[0-9]{7,}")) {
                  messageAleatoire = false;
                  nbBitsMess = args[i].length();
               } 
               else if (args[i].matches("[0-9]{1,6}")) {
                  messageAleatoire = true;
                  nbBitsMess = new Integer(args[i]);
                  if (nbBitsMess < 1) 
                     throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
               }
               else 
                  throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
            }
            
            else if (args[i].matches("-form")){
                i++; 
             	// traiter la valeur associee
                //messageStringTemporaire = args[i];
                messageAnalogique = true;
                
				switch(args[i]){
				case "NRZ":
					type=Forme.NRZ;
					break;
				case "NRZT":
					type=Forme.NRZT;
					break;
				case "RZ":
					type=Forme.RZ;
					break;
				
				default : 
					throw new ArgumentsException ("Valeur du parametre -form invalide");
				
				}
            }
                
            else if (args[i].matches("-nbEch")){
                i++; 
             	// traiter la valeur associee
           //     messageStringTemporaire = args[i];
                if (args[i].matches("[1-9][0-9]*")) {
                   nbEch = new Integer(args[i]);
                } 
                else 
                      throw new ArgumentsException ("Valeur du parametre -nbEch invalide : ");
               
             }
            
            else if (args[i].matches("-ampl")){
                i++; 
             	// traiter la valeur associee
              //  messageStringTemporaire = args[i];
                float amintemp =  new Float(args[i]);
                i++; 
                float amaxtemp =  new Float(args[i]);
                
                if ( amaxtemp > amintemp ) {
                   amplMin = amintemp;
                   amplMax = amaxtemp;
                } 
                else 
                      throw new ArgumentsException ("Valeur du parametre -ampl invalide : ");
               
             }
            
            
           
                                 
            else throw new ArgumentsException("Option invalide :"+ args[i]);
         }
      
      }
     
    
   	
   /** La m�thode execute effectue un envoi de message par la source de la cha�ne de transmission du Simulateur. 
   * @return les options explicites de simulation.
   *
   * @throws Exception si un probl�me survient lors de l'ex�cution
   *
   */ 
      public void execute() throws Exception {      
         
         source.emettre();

         
      	     	      
      }
   
   	   	
   	
   /** La m�thode qui calcule le taux d'erreur binaire en comparant les bits du message �mis avec ceux du message re�u.
   *
   * @return  La valeur du Taux dErreur Binaire.
   */   	   
      public float  calculTauxErreurBinaire() {
      
      	// A compl�ter
    	int nbErreurs = 0;
    	for(int i=0;i<this.nbBitsMess;i++) {
    		if(destination.getInformationRecue().iemeElement(i)!=source.getInformationEmise().iemeElement(i))
    			nbErreurs++;
    	}

        return  (float)((float)nbErreurs/(float)this.nbBitsMess);
      }
   
   
   
   
   /** La fonction main instancie un Simulateur � l'aide des arguments param�tres et affiche le r�sultat de l'ex�cution d'une transmission.
   *  @param args les diff�rents arguments qui serviront � l'instanciation du Simulateur.
   */
      public static void main(String [] args) { 
      
         Simulateur simulateur = null;
      	
         try {
            simulateur = new Simulateur(args);
         }
            catch (Exception e) {
               System.out.println(e); 
               System.exit(-1);
            } 
      		
         try {
            simulateur.execute();
            float tauxErreurBinaire = simulateur.calculTauxErreurBinaire();
            String s = "java  Simulateur  ";
            for (int i = 0; i < args.length; i++) {
         		s += args[i] + "  ";
         	}
            System.out.println(s + "  =>   TEB : " + tauxErreurBinaire);
         }
            catch (Exception e) {
               System.out.println(e);
               e.printStackTrace();
               System.exit(-2);
            }              	
      }
   	
   }

