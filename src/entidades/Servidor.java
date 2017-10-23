package entidades;

import java.util.ArrayList;

import distribuciones.DistExponencial;

public class Servidor {
	public  boolean ocupado;
	
	public Servidor(){
		ocupado = false;
	}

	public  boolean isOcupado() {
		return ocupado;
	}

	public  void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}
}

