import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Main {

	private static PriorityQueue<Referencia> PQ = new PriorityQueue<Referencia>();
	
	public static void main(String[]args){
		
		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new FileReader("./data/referencias1.txt"));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
		
		try {
			
			int cantidadPaginas = Integer.parseInt(lector.readLine());
			int cantidadMarcosDePagina = Integer.parseInt(lector.readLine());
			double porcentaje = Double.parseDouble(lector.readLine());
			
			Paginas threadP = new Paginas(cantidadPaginas, cantidadMarcosDePagina);
			threadP.definirTipo(false);
			
			Paginas threadE = new Paginas();
			threadE.definirTipo(true);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
