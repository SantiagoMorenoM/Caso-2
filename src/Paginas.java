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
	private static Semaforo[] semaforos= new Semaforo[4];
	private static boolean procesando = true;

	
	
	public Paginas(int numPaginas, int numMarcos) {
		for(int i=0;i<semaforos.length;i++)
			semaforos[i]=new Semaforo(1);
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
				
				semaforos[0].p();
				referenciados.add(Integer.parseInt(linea));
				semaforos[0].v();
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
				semaforos[0].p();
				if(referenciados.size()>0) {
				boolean repetido = false;
				semaforos[0].v();
				
				semaforos[0].p();
				semaforos[3].p();
				repetido=marcos[referenciados.get(0)];
				semaforos[3].v();
				semaforos[0].v();
				

				boolean noHayDesocupada=true;
				
				semaforos[3].p();
				for(int i=0;i<marcos.length&&noHayDesocupada;i++) {
					
				
					noHayDesocupada=marcos[i];
			
					if(!noHayDesocupada) {
						if(!repetido) {
						semaforos[0].p();
						semaforos[2].p();
						tabla[referenciados.get(0)]=i;
						semaforos[2].v();
						Referencia ref = new Referencia(referenciados.get(0));
						referenciados.remove(0);
						semaforos[0].v();
						correrAlaDerecha(ref);
						}
						else {
							semaforos[0].p();
							Referencia ref = new Referencia(referenciados.get(0));
							semaforos[0].v();
							correrAlaDerecha(ref);
						}
					}
				}
				semaforos[3].v();
				if(noHayDesocupada) {
					Semaforo semFallos= new Semaforo(1);
					semFallos.p();
					numFallos++;
					semFallos.v();
					
				    int min=0;
					semaforos[1].p();
			        for(int i=0;i<referencias.length;i++) {
					if(referencias[min].compareTo(referencias[i])<0) {
							min=i;
						}
						}
			    	semaforos[1].v();
					semaforos[2].p();
					int marcoDePagina = tabla[min];
					tabla[min] = -1;
					semaforos[2].v();
					semaforos[1].p();
					referencias[min]= new Referencia(min);
					semaforos[1].v();
					semaforos[0].p();
					semaforos[2].p();
					tabla[referenciados.get(0)]=marcoDePagina;
					semaforos[2].v();
					Referencia ref = new Referencia(referenciados.get(0));
					semaforos[0].v();
					correrAlaDerecha(ref);
				}
				
				
				}
			}
			
			
		}
		
		
	}
	
	

	
	
	public void  correrAlaDerecha(Referencia r){
		
		
	
		semaforos[1].p();
		for (int i = 0; i < referencias.length; i++) {
				
			Referencia actual = referencias[i];
			
			if(actual.equals(r)) actual.AgregarUno();
				
			else actual.AgregarCero();
			
		}
		semaforos[1].v();
 
	}
}
