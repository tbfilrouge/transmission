package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
/**
 * Hérite de Transmetteur, 
 * Implémente SourceInterface et DestinationInterface 
 * 
 * Cette classe peut recevoir et emettre des objets de type Information<R> et Information<E>
 *
 * 
 * 
 * @author FLRP&TD
 *
 * @param <R>
 * @param <E>
 */
public class TransmetteurParfait <R,E> extends Transmetteur <R,E> {
	
	/**
	 * Le constructeur fait appel à celui de sa classe mère
	 */
	public TransmetteurParfait() {
		
		super();
		
	}

	/**
	 * 
	 * Cette méthode permet de garder l'info dans une variable
	 *
	 */
	@Override
	public void recevoir(Information information) throws InformationNonConforme {
		
		informationRecue = information;
		emettre();
	}

	/**
	 * Cette méthode transmet l'informationEmise à tous les objets DestinationInterface dans la liste de destination.
	 * 
	 * L'information émise est la même que l'information reçue car il modèlise un transmetteur parfait
	 * 
	 */
	@Override
	public void emettre() throws InformationNonConforme {

		this.informationEmise = (Information<E>) informationRecue;   
		
   
        for (DestinationInterface<E> destinationConnectee : destinationsConnectees) {
           destinationConnectee.recevoir(informationEmise);			 			      

        }
		
	}

}
