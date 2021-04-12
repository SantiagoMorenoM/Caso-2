import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {


	public static void main(String[]args){
		String numero = null;
		BufferedReader lector = null;
		try {

			Scanner scaner = new Scanner(System.in);
			System.out.println("Ingrese el número del archivo a usar");
			numero = scaner.next();

			lector = new BufferedReader(new FileReader("./data/referencias"+numero+".txt"));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} 

		try {

			int cantidadMarcosDePagina = Integer.parseInt(lector.readLine());
			int cantidadPaginas = Integer.parseInt(lector.readLine());

			double porcentaje = Double.parseDouble(lector.readLine());

			Paginas threadP = new Paginas(cantidadPaginas, cantidadMarcosDePagina,numero);
			threadP.definirTipo(false);

			Paginas threadE = new Paginas();
			threadE.definirTipo(true);

			threadP.start();
			threadE.start();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}



}
