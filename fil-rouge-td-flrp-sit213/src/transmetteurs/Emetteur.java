package transmetteurs;


import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;




public class Emetteur extends Transmetteur<Boolean, Float> {
	
	private int nbEchantillon;
	private float amplMax;
	private float amplMin;
	private Forme forme;
	
	


public Emetteur(int nbEchantillon, float amplMax, float amplMin, Forme forme) {

	this.nbEchantillon = nbEchantillon;
	this.amplMax = amplMax;
	this.amplMin = amplMin;
	this.forme = forme;
}

@Override
public void recevoir(Information<Boolean> information) throws InformationNonConforme {
	
	informationRecue = information;
	emettre();
}
	
@Override
public void emettre() throws InformationNonConforme {

	//String value = null;
	//Forme forme = Forme.valueOf(value);
	
	Float[] informationAnalogique = new Float[nbEchantillon * informationRecue.nbElements()];
	
	switch (forme) {
		
		case NRZ:
			
			for (int i = 0; i < informationRecue.nbElements(); i++) {
				
				if (informationRecue.iemeElement(i) == true) {
					
					for (int j = 0; j < nbEchantillon; j++) 
						
						informationAnalogique[i * nbEchantillon + j] = amplMax;
				}
				
				 else 
					 
					for (int j = 0; j < nbEchantillon; j++) 
						
						informationAnalogique[i * nbEchantillon + j] = amplMin; 
				}
				
			
			break;
		
		case NRZT:
			
			float pente = (amplMax - amplMin) / (nbEchantillon/3);
			
			for (int i = 0; i < informationRecue.nbElements(); i++) {

				for (int j = 0; j < nbEchantillon; j++) {
					informationAnalogique[i * nbEchantillon + j] = amplMin; //on met tous les échantillons à Amin dans un premier temps
				}	
				
				if(informationRecue.iemeElement(i) == true){
					
					
					for (int j = 0; j < (nbEchantillon/3); j++) 
						
						informationAnalogique[i * nbEchantillon + j] = amplMin + pente*j; //ou pente*(j+1) à tester

					for (int j = (nbEchantillon/3); j < 2*(nbEchantillon/3); j++) 
	
						informationAnalogique[i * nbEchantillon + j] = amplMax;
					
					
					for (int j = 2*(nbEchantillon/3) ; j < 3*(nbEchantillon/3); j++) {
						
						informationAnalogique[i * nbEchantillon + j] = amplMax + -pente*(j-2*(nbEchantillon/3)); //ou pente*(j+1) à tester
						
					}
					
				}//fin du if
				
				
				}//fin du premier for
				
				
			
			
			
			break;
			
		case RZ:
			
			for (int i = 0; i < informationRecue.nbElements(); i++) {

				for (int j = 0; j < nbEchantillon; j++) {
					informationAnalogique[i * nbEchantillon + j] = amplMin; //on met tous les échantillons à Amin dans un premier temps
				}	
				
				
				if(informationRecue.iemeElement(i) == true){
					
					for (int j = (nbEchantillon/3); j < 2*(nbEchantillon/3); j++) 
						
						informationAnalogique[i * nbEchantillon + j] = amplMax;
				}
					
			}
			
			
			
			
			
			break;
		
		default:
			System.out.println("forme d'onde invalide");
			break;
		
	}
	
	Information<Float> infoEnvoye = new Information<Float>(informationAnalogique);
	
	this.informationEmise = infoEnvoye;
	
	for (int i = 0; i < destinationsConnectees.size(); i++) {
		
		destinationsConnectees.get(i).recevoir(infoEnvoye);
		
	}
	
	
}

	

}
