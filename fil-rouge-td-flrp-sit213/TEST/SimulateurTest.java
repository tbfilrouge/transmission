import static org.junit.Assert.*;

import org.junit.Test;

import transmetteurs.Emetteur;


public class SimulateurTest {
	Simulateur A;
	String tab0[]={"-mess","4"};
	String tab1[]={"-mess", "-4"};
	String tab2[]={"-mess", "abcd"};
	String tab3[]={"-mess", ""};
	String tab4[]={"-mess","4","-form","BUG"};
	String tab5[]={"-mess","4","-form",""};
	String tab6[]={"-mess","4","-nbEch", "-30"};
	String tab7[]={"-mess","4","-nbEch", "abcd"};
	String tab8[]={"-mess","4","-ampl", "abcd" , "abcdef"};
	String tab9[]={"-mess","4","-ampl", "12.0" , "12"};
	String tab10[]={"-mess","4","-ampl", "12.0" , "12.0"};
	String tab11[]={"-mess","4","-ampl", "10.0" , "12.0"};
	String tab12[]={"-mess","4","-seed","abdc"};
	String tab13[]={"-mess","4","-seed","1"};
	
	
	
	String ok0[]={"-mess","4"};
	String ok1[]={"-mess","101010101010101000111"};
	String ok2[]={"-mess","4","-nbEch", "30"};
	String ok3[]={"-mess","4","-form","RZ"};
	String ok4[]={"-mess","4","-form","NRZ"};
	String ok5[]={"-mess","4","-form","NRZT"};
	
	
	
	
	@Test
	public void testSimulateur0() throws ArgumentsException {
		Simulateur A = new Simulateur(tab0);
	}

	@Test(expected = ArgumentsException.class)
	public void testSimulateur1() throws ArgumentsException {
		Simulateur A = new Simulateur(tab1);
	}

	@Test(expected = ArgumentsException.class)
	public void testSimulateur2() throws ArgumentsException {
		Simulateur A = new Simulateur(tab2);
	}
	
	@Test(expected = ArgumentsException.class)
	public void testSimulateur4() throws ArgumentsException {
		Simulateur A = new Simulateur(tab4);
	}
	
	@Test(expected = ArgumentsException.class)
	public void testSimulateur5() throws ArgumentsException {
		Simulateur A = new Simulateur(tab5);
	}
	
	@Test(expected = ArgumentsException.class) // Bug corrigé
	public void testSimulateur6() throws ArgumentsException {
		Simulateur A = new Simulateur(tab6);
	}
	
	@Test(expected = ArgumentsException.class)
	public void testSimulateur7() throws ArgumentsException {
		Simulateur A = new Simulateur(tab7);
	}
	
	@Test(expected = ArgumentsException.class) // TODO
	public void testSimulateur8() throws ArgumentsException {
		Simulateur A = new Simulateur(tab8);
	}
	
	@Test(expected = ArgumentsException.class)
	public void testSimulateur9() throws ArgumentsException {
		Simulateur A = new Simulateur(tab9);
	}
	
	@Test(expected = ArgumentsException.class)
	public void testSimulateur10() throws ArgumentsException {
		Simulateur A = new Simulateur(tab10);
	}
	
	@Test(expected = ArgumentsException.class)
	public void testSimulateur11() throws ArgumentsException {
		Simulateur A = new Simulateur(tab11);
	}
	
	@Test(expected = ArgumentsException.class)
	public void testSimulateur12() throws ArgumentsException {
		Simulateur A = new Simulateur(tab12);
	}
	
	@Test(expected = ArgumentsException.class)
	public void testSimulateur13() throws ArgumentsException {
		Simulateur A = new Simulateur(tab13);
	}
		
	
	@Test
	public void testCalculTauxErreurBinaire() {
		fail("Not yet implemented");
	}
// TEST DES CAS NORMAUX 
	
	@Test
	public void testok0() throws ArgumentsException {
		Simulateur A = new Simulateur(ok0);
	}
	
	@Test
	public void testok1() throws ArgumentsException {
		Simulateur A = new Simulateur(ok1);
	}
	
	@Test
	public void testok2() throws ArgumentsException {
		Simulateur A = new Simulateur(ok2);
	}
	
	@Test
	public void testok3() throws ArgumentsException {
		Simulateur A = new Simulateur(ok3);
	}
	
	@Test
	public void testok4() throws ArgumentsException {
		Simulateur A = new Simulateur(ok4);
	}
	
	@Test
	public void testok5() throws ArgumentsException {
		Simulateur A = new Simulateur(ok5);
	}
	
	
}

