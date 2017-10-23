package entidades;

import distribuciones.DistExponencial;
import distribuciones.DistUniforme;

public class Cliente {

	double tiempoArribo;
	double lambda;
	boolean regular;
	public static double distReg = 0.5;
	public static double distRap = 1.11;
	
	public Cliente(double ta) {
		this.tiempoArribo = ta;
		double random =  Math.random();
		if (random<=0.37) {
			this.lambda = distRap;
			this.regular = false;
		}
		else {
			this.lambda = distReg;
			this.regular = true;
		}
	}
	
	public double calcularDemoraEnCola(double tiempoActual){
		
		return tiempoActual-tiempoArribo;
	}
	
	public double calcularTiempoServicio() {
		double random = Math.random();
		double servicio = 0;
		if(random<=0.35) {
			servicio=DistUniforme.generadorDistribucionUniforme(7, 12) +DistExponencial.generadorDistribucionExponencial(this.lambda);
		}
		else {
			servicio = DistExponencial.generadorDistribucionExponencial(this.lambda);
		}
		return servicio;
	}

	public double getTiempoArribo() {
		return tiempoArribo;
	}

	public void setTiempoArribo(double tiempoArribo) {
		this.tiempoArribo = tiempoArribo;
	}

	public double getLambda() {
		return lambda;
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	public boolean isRegular() {
		return regular;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}
}
