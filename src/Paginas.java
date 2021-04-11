import java.awt.event.ActionEvent;
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

	private String numArchivo = "1";
	
	
	
	public Paginas(int numPaginas, int numMarcos, String archivo) {
		
		numArchivo = archivo;
		referenciados = new ArrayList<Integer>();
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
				lector = new BufferedReader(new FileReader("./data/referencias"+numArchivo+".txt"));
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
			
			int x =0;
			while(procesando){
				x++;
			//	System.out.println("Entrando al while "+x);
				
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
				semaforos[0].p();
				//System.out.println("paso sem if");
				if(referenciados.size()>0) {
			//		System.out.println("entro al if");	
				boolean repetido = false;
				semaforos[0].v();
				
				semaforos[0].p();
			//	System.out.println("prueba marcos");
				
				semaforos[2].p();
				if(tabla[referenciados.get(0)] >= 0){
					semaforos[3].p();
					repetido=marcos[tabla[referenciados.get(0)]];
					semaforos[3].v();
				}
				semaforos[2].v();
			//	System.out.println("prueba  salida marcos");
				semaforos[0].v();
				

				if(!repetido){
					//System.out.println("Entrando al for");
		
					semaforos[3].p();
					boolean noHayDesocupada=true;
					for(int i=0;i<marcos.length&&noHayDesocupada;i++) {
						
					
						noHayDesocupada=marcos[i];
					//	System.out.println(marcos[i] +" "+i );
						
						if(!noHayDesocupada) {
							//System.out.println("Entro a nohaydesocupada");
							semaforos[0].p();
							semaforos[2].p();
							tabla[referenciados.get(0)]=i;
							semaforos[2].v();
							//System.out.println("Se hizo cambio entabla");
							
							marcos[i] = true;
							
							//System.out.println("Se hizo cambio en marcos");
							Referencia ref = new Referencia(referenciados.get(0));
							referenciados.remove(0);
							semaforos[0].v();
							correrAlaDerecha(ref);
							
						}
						//System.out.println("salio de nohaydesocupada");
					}
					semaforos[3].v();
					if(noHayDesocupada) {
					//	System.err.println("Entro a un fallo " +numFallos);
						Semaforo semFallos= new Semaforo(1);
						semFallos.p();
						numFallos++;
						semFallos.v();
						
					    int min=0;
						semaforos[1].p();
						
				        for(int i=0;i<referencias.length;i++) {
				        	
				        	if(referencias[min].compareTo(referencias[i])==1) {
				        	
								min=i;
							}
						}
				    	semaforos[1].v();
						semaforos[2].p();
						int marcoDePagina = tabla[min];
						System.out.println("Sacó a:"+ min);
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
				
				else{
					semaforos[0].p();
					Referencia ref = new Referencia(referenciados.get(0));
					referenciados.remove(0);
					semaforos[0].v();
					correrAlaDerecha(ref);
				}
				
			//	System.out.println("Saliendo del for");
				
				}
				
				else{
					semaforos[0].v();
				}
				
			}
			System.out.println(numFallos);
			
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
