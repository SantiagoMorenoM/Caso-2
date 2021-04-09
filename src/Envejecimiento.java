import java.util.PriorityQueue;

public class Envejecimiento extends Thread {

	private static PriorityQueue<Referencia> PQ = new PriorityQueue<Referencia>();
	
	public Envejecimiento(){
		
		
		//Estructura de datos con referencias recibida por parametro
	}
	
	public void run(){
		//while(existan referencias por procesar)
		//agregarReferencia(data[])
	}
	
	
	
	public void agregarReferencia(Referencia r){
		PQ.add(r);
	}
	
	public void sacarReferencia(){
		PQ.remove();
	}
	
}
