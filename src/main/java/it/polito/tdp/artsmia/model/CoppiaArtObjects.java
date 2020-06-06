package it.polito.tdp.artsmia.model;

public class CoppiaArtObjects {

	private ArtObject ob1; 
	private ArtObject ob2; 
	
	private int peso; //numero di exhibition in cui sono stati esposti contemporaneamente

	/**
	 * @param ob1
	 * @param ob2
	 * @param peso
	 */
	public CoppiaArtObjects(ArtObject ob1, ArtObject ob2, int peso) {
		super();
		this.ob1 = ob1;
		this.ob2 = ob2;
		this.peso = peso;
	}

	public ArtObject getOb1() {
		return ob1;
	}

	public void setOb1(ArtObject ob1) {
		this.ob1 = ob1;
	}

	public ArtObject getOb2() {
		return ob2;
	}

	public void setOb2(ArtObject ob2) {
		this.ob2 = ob2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "CoppiaArtObjects [ob1=" + ob1 + ", ob2=" + ob2 + ", peso=" + peso + "]";
	}
	
	
}
