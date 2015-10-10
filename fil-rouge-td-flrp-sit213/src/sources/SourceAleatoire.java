package sources;

import java.util.Random;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Hérite de Source<Boolean>
 * Cette classe va générer une séquence aléatoire dont la longueur sera passée au constructeur
 * Elle transmet ensuite cette sequence aux destination qui sont connectées lors de l'emmission.
 * 
 * @author FLRP&TD
 *
 */
public class SourceAleatoire extends Source<Boolean> {
	
	/**
	 * La longueur de la sequence aléatoire est passée au constructeur 
	 * 
	 * Il instancie la variable informationGeneree
	 * avec une Information contenant un tableau de boolean repmi aleatoirement avec la taille de la séquence à générer
	 * 
	 * @param nbBits
	 */
	public SourceAleatoire(int nbBits) {
		
		super();
		
		Boolean[] tabBool = new Boolean[nbBits];
		 Random random = new Random();
		
		for(int i=0; i<nbBits; i++) {
			
			tabBool[i] = random.nextBoolean();
			
		}

		informationGeneree = new Information<Boolean>(tabBool);
		
	}

	/**
	 * Cette méthode transmet l'informationEmise à tous les objets DestinationInterface dans la liste de destination.
	 * 
	 * Pour transmettre elle utilise la méthode recevoir() de l'interface DestinationInterface
	 */
	@Override
	public void emettre() throws InformationNonConforme {
		
		this.informationEmise = informationGeneree;  
		
        
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
           destinationConnectee.recevoir(informationEmise);
        }
		
	}

}
