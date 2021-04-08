import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lector extends Thread{
		
		private int cantidadPaginas;
		
		private int cantidadMarcosDePagina;
		
		private double porcentaje;
	
	
		public Lector(){
			
		}
		
		public void run(){
			
			BufferedReader lector = null;
			
			try {
				lector = new BufferedReader(new FileReader("./data/referencias1.txt"));
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} 
			
			
			//Leer los tres primeros datos y guardarlos
			
			try {
				
				cantidadPaginas = Integer.parseInt(lector.readLine());
				cantidadMarcosDePagina = Integer.parseInt(lector.readLine());
				porcentaje = Double.parseDouble(lector.readLine());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String linea = null;
			
			try {
				linea = lector.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(linea != null){
				
				System.out.println(linea);
				
				//delay entre cada lectura de una nueva referencia
				
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
			
			
		}
		
	}