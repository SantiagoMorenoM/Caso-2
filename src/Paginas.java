
public class Paginas {

	private static int[] tabla;
	private static Referencia[] referencias;
	private static boolean[] marcos;
	private static int numFallos=0;
	public Paginas(int numPaginas, int numMarcos) {
		tabla= new int[numPaginas];
		referencias= new Referencia[numPaginas];
		marcos= new boolean[numMarcos];
		for(int i=0; i<marcos.length;i++) {
			marcos[i]=false;
		}
		for(int i=0; i<tabla.length;i++) {
			tabla[i]=-1;
			referencias[i]= new Referencia();
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
				semTabla.v();}
		}
		if(noHayDesocupada) {
			Semaforo semFallos= new Semaforo(1);
			semFallos.p();
			numFallos++;
			semFallos.v();
		}
	}
}
