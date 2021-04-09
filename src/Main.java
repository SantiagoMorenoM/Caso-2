import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[]args){
		
//		Lector lector1 = new Lector();
//		Lector lector2 = new Lector();
//		lector1.start();
//		lector2.start();
		
		Referencia r = new Referencia();
		
		System.out.println(r.getBitMask());
		
		r.AgregarUno();
		
		System.out.println(r.getBitMask());
		
		r.AgregarUno();
		
		System.out.println(r.getBitMask());
		
r.AgregarUno();
		
		System.out.println(r.getBitMask());
		
r.AgregarUno();
		
		System.out.println(r.getBitMask());
		
		r.AgregarCero();
		System.out.println(r.getBitMask());
		
		r.AgregarCero();
		System.out.println(r.getBitMask());
		
		r.AgregarCero();
		System.out.println(r.getBitMask());
		
		r.AgregarCero();
		System.out.println(r.getBitMask());
		
		
	}
	
	
	
}
