import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Paginas extends Thread{

	private static PriorityQueue<Referencia> PQ = new PriorityQueue<Referencia>();
	private static int[] tabla;
	private static boolean[] marcos;
	private static int numFallos=0;
	
	private boolean esEnvejecimiento;
	
	private static boolean procesando = true;
	
	private static int fallosActivos = 0;
	
	
	public Paginas(int numPaginas, int numMarcos) {
		tabla= new int[numPaginas];
		marcos= new boolean[numMarcos];
		
		for(int i=0; i<marcos.length;i++) {
			marcos[i]=false;
		}
		for(int i=0; i<tabla.length;i++) {
			tabla[i]=-1;
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
				
				referenciar(Integer.parseInt(linea));
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
				
				if(fallosActivos > 0){
					Referencia ultimaEliminada = sacarReferencia();
					int marcoDePagina = tabla[ultimaEliminada.darPagina()];
					tabla[ultimaEliminada.darPagina()] = -1;
					marcos[marcoDePagina] = false;
					 
				}
				
			}
			
			
		}
		
		
	}
	
	
	
	public void referenciar(int pagina) {
		Semaforo semMarcos= new Semaforo(1);
		boolean noHayDesocupada=true;
		for(int i=0;i<marcos.length&&noHayDesocupada;i++) {
			
			semMarcos.p();
			noHayDesocupada=marcos[i];
			semMarcos.v();
			if(!noHayDesocupada) {
				Semaforo semTabla= new Semaforo(1);
				semTabla.p();
				tabla[pagina]=i;
				Referencia ref = new Referencia(pagina);
				agregarReferencia(ref);
				correrAlaDerecha(ref);
				semTabla.v();
			}
		}
		if(noHayDesocupada) {
			Semaforo semFallos= new Semaforo(1);
			semFallos.p();
			numFallos++;
			fallosActivos++;
			semFallos.v();
		}
	}
	
	
	public synchronized void agregarReferencia(Referencia r){
		PQ.add(r);
	}
	
	public synchronized Referencia sacarReferencia(){
		return PQ.remove();
	}
	
	
	public synchronized void correrAlaDerecha(Referencia r){
		
		Iterator it = PQ.iterator();
		
		while (it.hasNext()) {
				
			Referencia re = (Referencia)(it.next());
			
	         if(re == r){
	        	
	        	re.AgregarUno();
	         }
	         
	         else re.AgregarCero();
	         
	    }
	}
}
