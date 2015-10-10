package transmetteurs;

//import java.util.LinkedList;
import java.util.List;


import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class Recepteur extends Transmetteur<Float, Boolean>  {
	
	private int nbEchantillon;
	private Forme forme;
	private float seuil;
	

	
	public Recepteur(int nbEchantillon, float amplMax, float amplMin, Forme forme) {

		this.nbEchantillon = nbEchantillon;
		seuil = (amplMax + amplMin)/2;

		this.forme = forme;
		
	}
	
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConforme {
		
		informationRecue = information;
		emettre();
	}
	
	
	@Override
	public void emettre() throws InformationNonConforme {
		
    Boolean[] informationNumerique = new Boolean[informationRecue.nbElements()/nbEchantillon];	
		
	
		
	switch (forme) {
			
	
			case NRZ: 
				
				for (int i = 0; i < informationRecue.nbElements()/nbEchantillon; i++) {
					
					if(informationRecue.iemeElement((int)(nbEchantillon/2) + (nbEchantillon*i)) > seuil)
						
						informationNumerique[i] = true;
						
					else 
						
						informationNumerique[i] = false;
					
					}//fin du for
				
				
				
				
				break;
			
			
			case NRZT: 

for (int i = 0; i < informationRecue.nbElements()/nbEchantillon; i++) {
					
					if(informationRecue.iemeElement((int)(nbEchantillon/2) + (nbEchantillon*i)) > seuil)
						
						informationNumerique[i] = true;
						
					else 
						
						informationNumerique[i] = false;
					
					}//fin du for
				
				break;
			
			
			case RZ: 

for (int i = 0; i < informationRecue.nbElements()/nbEchantillon; i++) {
					
					if(informationRecue.iemeElement((int)(nbEchantillon/2) + (nbEchantillon*i)) > seuil)
						
						informationNumerique[i] = true;
						
					else 
						
						informationNumerique[i] = false;
					
					}//fin du for
				
				break;
		
		
			default:
				System.out.println("forme d'onde invalide");
				break;
		
		
		
		
	}
	
	

	
	Information<Boolean> infoEnvoye = new Information<Boolean>(informationNumerique);
	
	this.informationEmise = infoEnvoye;
	
	for(int i=0;i<destinationsConnectees.size();i++){
		
		destinationsConnectees.get(i).recevoir(infoEnvoye);
		
	}
	

		
		
		
	
		
		
	}
	
}
