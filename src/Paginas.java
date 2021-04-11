import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Paginas extends Thread{

	private static Referencia[]  referencias;
	private static int[] tabla;
	private static boolean[] marcos;
	private static int numFallos=0;
	private static ArrayList<Integer> referenciados;
	private boolean esEnvejecimiento;
	
	private static boolean procesando = true;

	
	
	public Paginas(int numPaginas, int numMarcos) {
		tabla= new int[numPaginas];
		marcos= new boolean[numMarcos];
		referencias= new Referencia[numPaginas];
		for(int i=0; i<marcos.length;i++) {
			marcos[i]=false;
		}
		for(int i=0; i<tabla.length;i++) {
			tabla[i]=-1;
			referencias[i]= new Referencia(i);
		}
	}
	
	public Paginas(){
		
	}
	
	
	public void definirTipo(boolean a){
		esEnvejecimiento = a;
	}
	
	public void run(){
		if(!esEnvejecimiento){
			BufferedReader lector = null;
			
			try {
				lector = new BufferedReader(new FileReader("./data/referencias1.txt"));
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} 
			
			
			//Leer los tres primeros datos y guardarlos
			
			String linea = null;
			
			try {
				
				linea = lector.readLine();
				linea = lector.readLine();
				linea = lector.readLine();
				linea = lector.readLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(linea != null){
				
				System.out.println(linea);
				
				
				referenciados.add(Integer.parseInt(linea));
				
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
				
				
				try {
					linea = lector.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
			procesando = false;
			
			
		
			
		}
		else {
			
			while(procesando){
				
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				if(referenciados.size()>0) {
				boolean repetido = false;
				
				
				repetido=marcos[referenciados.get(0)];
				
				
				Semaforo semMarcos= new Semaforo(1);
				boolean noHayDesocupada=true;
				
				
				for(int i=0;i<marcos.length&&noHayDesocupada;i++) {
					
					semMarcos.p();
					noHayDesocupada=marcos[i];
					semMarcos.v();
					if(!noHayDesocupada) {
						if(!repetido) {
						Semaforo semTabla= new Semaforo(1);
						semTabla.p();
						tabla[referenciados.get(0)]=i;
						Referencia ref = new Referencia(referenciados.get(0));
						referenciados.remove(0);
						correrAlaDerecha(ref);
						semTabla.v();}
						else {
							Referencia ref = new Referencia(referenciados.get(0));
							correrAlaDerecha(ref);
						}
					}
				}
				if(noHayDesocupada) {
					Semaforo semFallos= new Semaforo(1);
					semFallos.p();
					numFallos++;
					semFallos.v();
					
				    int min=0;
			        for(int i=0;i<referencias.length;i++) {
					if(referencias[min].compareTo(referencias[i])<0) {
							min=i;
						}
						}
					int marcoDePagina = tabla[min];
					tabla[min] = -1;
					referencias[min]= new Referencia(min);
					tabla[referenciados.get(0)]=marcoDePagina;
					Referencia ref = new Referencia(referenciados.get(0));
					correrAlaDerecha(ref);
				}
				
				
				}
			}
			
			
		}
		
		
	}
	
	

	
	
	public void  correrAlaDerecha(Referencia r){
		
		
	
		
		for (int i = 0; i < referencias.length; i++) {
				
			Referencia actual = referencias[i];
			
			if(actual.equals(r)) actual.AgregarUno();
				
			else actual.AgregarCero();
			
		}
 
	}
}
