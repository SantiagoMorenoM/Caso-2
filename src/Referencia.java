
public class Referencia implements Comparable{
	
	private int pagina;
	private int bitMask;
	
	
	
	public Referencia(int pagina){
		
		this.pagina = pagina;
		bitMask = 0;
		
	}
	
	public void AgregarUno(){
		this.bitMask >>= 1;
		this.bitMask = this.bitMask ^ 536870912;
	}
	
	public void AgregarCero(){
		this.bitMask >>= 1;
	}
	
	
	public int compareTo(Referencia r) {
		
		if(this.bitMask > r.bitMask){
			return -1;
		}
		else if(this.bitMask < r.bitMask){
			return 1;
		}
		else return 0;
	
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getBitMask(){
		return this.bitMask;
	}
	
	
	public int darPagina(){
		return this.pagina;
	}
	
}
