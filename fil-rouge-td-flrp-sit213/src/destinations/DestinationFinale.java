package destinations;

import information.Information;
import information.InformationNonConforme;
/**
 * Cette classe hérite  de la classe abstraite Destination. 
 * Elle reçoit une info en fin de chaîne et la conserve dans une variable.
 
 * @author FLRP&TD
 *
 * @param <T>
 */
public class DestinationFinale <T> extends Destination<T> {

	/**
	 *Cette méthode reçoit en paramètre une information à conserver.
	 *Elle la place dans une variable.
 	*/
	@Override
	public void recevoir(Information<T> information) throws InformationNonConforme {
	
		informationRecue = information;
		
	}

}
