package sources;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
/**
 * Hérite de Source<Boolean>
 * 
 * Cette classe va générer une séquence définie et passée à son constructeur
 * Cette classe g�n�re un message d�termin� lors de son instanciation.
 * Elle transmet ensuite cette sequence aux destination qui sont connectées lors de l'emmission.
 *
 * @author FLRP&TD
 *
 */
public class SourceFixe extends Source<Boolean> {
	
	/**
	 * La séquence binaire est passée au constructeur sous forme de chaîne de caractères
	 * 
	 * Cette chaîne de est ensuite mise sous forme de tableau de booléens
	 * On instancie ensuite informationGeneree avec ce tableau
	 * 
	 * 
	 * @param mess
	 */
	public SourceFixe(String mess) {

		super();
		
		char[] charArray = mess.toCharArray();
		Boolean[] tabBool = new Boolean[charArray.length];

		for(int i=0;i<charArray.length;i++) {
			
			if(charArray[i] == '0') 
				tabBool[i] = false;
			
			else 
				tabBool[i] = true;
			
			
		}
		
		informationGeneree = new Information<Boolean>(tabBool);
		
	}

	/**
	 * 
	 * Cette méthode transmet l'informationEmise à tous les objets DestinationInterface dans la liste de destination.
	 * 
	 * Pour transmettre elle utilise la méthode emettre() de l'interface DestinationInterface
	 * 
	 */
	@Override
	public void emettre() throws InformationNonConforme {
		
		this.informationEmise = informationGeneree;   

      
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
           destinationConnectee.recevoir(informationEmise);
        }
		
	}

}
