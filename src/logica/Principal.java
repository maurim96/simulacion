package logica;

import java.util.ArrayList;

import distribuciones.DistExponencial;
import entidades.Cliente;
import entidades.Servidor;

public class Principal {

	public static double tiempoSim;
	public static double reloj;
	public static double[] listaEv; /*¨0: arribos  - 1 y 2: partida regular y rapida*/
	public static Servidor[] cajerosRegular;
	public static Servidor[] cajerosRapido;
	public static double tiempoTotal; /*tiempo total de clientes regulares en cola*/
	public static int nccd; /*numero de clientes regulares que completaron su demora*/
	public static double distArr = 0.476;
	public static double tiemProm; /*Tiempo promedio de espera de los clientes regulares en cola*/
	public static ArrayList<Cliente> colaReg;
	public static ArrayList<Cliente> colaRap;
	public static int cantEv = 3;
	public static double acumtiem;

	public static void main(String[] args) {
		/*meter cantidad de servidores*/
		int eventoProx;
		Principal.inicializacion(1,1,cantEv);
		/*Tiempo de simulacion harcodeado*/
		for(int i = 0; i < 100; i++) {
		while(reloj < tiempoSim) {
			eventoProx = tiempos(cantEv);
			switch (eventoProx) {
			case 0:
				arriboCliente();
				break;
			case 1:
				partidaRegular(0);
				break;
			case 2:
				partidaRapida(0);
				break;
			default:
				break;
			}
		}
		/*reporte();*/
		acumtiem = acumtiem + (tiempoTotal/nccd);
		}
		System.out.println("TOTAL PROMEDIO: " +acumtiem/100);
	}
	
	private static void reporte() {
		System.out.println("Tiempo promedio de espera de los clientes regulares en cola: " +tiempoTotal/nccd);
		System.out.println("Tiempo total: " +tiempoTotal);
		System.out.println("NCCD: " +nccd);
		System.out.println("Reloj: " +reloj);
	}

	private static void partidaRapida(int nroCaj) {
		if (colaRap.size() != 0) { /*Si hay clientes en cola de cajero rapido*/
			GenerarPartidaRapida(colaRap.get(0));
			colaRap.remove(0);
		}
		else {
			if (colaReg.size() != 0) { /*Si hay clientes en cola de cajero regular*/
				GenerarPartidaRapida(colaReg.get(0));
				nccd++;
				tiempoTotal = tiempoTotal + colaReg.get(0).calcularDemoraEnCola(reloj);
				colaReg.remove(0);
			}
			else {
				cajerosRapido[0].setOcupado(false);
				listaEv[2] = tiempoSim;
			}
		}
	}

	private static void partidaRegular(int nroCaj) {
		if (colaReg.size() != 0) { /*Si hay clientes en cola de cajero regular*/
			generarPartidaRegular(colaReg.get(0));
			tiempoTotal = tiempoTotal + colaReg.get(0).calcularDemoraEnCola(reloj);
			nccd++;
			colaReg.remove(0);
		}
		else {
			if (colaRap.size() != 0) { /*Si hay clientes de cajero rapido*/
				generarPartidaRegular(colaRap.get(0));
				colaRap.remove(0);
			}
			else {
				cajerosRegular[0].setOcupado(false);
				listaEv[1] = tiempoSim;
			}
		}
	}

	private static void arriboCliente() {
		generarArriboCliente();
		Cliente cli = new Cliente(reloj);
		if (cli.isRegular()) {
			Servidor cajero = buscarCajeroRegularDesocupado();
			if (cajero != null) { /*Si el cajero regular esta desocupado*/
				generarPartidaRegular(cli);
				nccd++;
				cajero.setOcupado(true);
			}
			else {
				Servidor cajeroRap = buscarCajeroRapidoDesocupado();
				if (cajeroRap != null && colaReg.size() == 0) { /*Si el cajero rapido esta desocupado y no hay clientes en cola en cajero regular*/
					GenerarPartidaRapida(cli);
					nccd++;
					cajeroRap.setOcupado(true);
				}
				else {
					colaReg.add(cli);
				}
			}
		}
		else {
			Servidor cajero = buscarCajeroRapidoDesocupado();
			if (cajero != null) { /*Si el cajero rapido esta desocupado*/
				GenerarPartidaRapida(cli);
				cajero.setOcupado(true);
			}
			else {
				Servidor cajeroReg = buscarCajeroRegularDesocupado();
				if (cajeroReg != null && colaRap.size() == 0) { /*Si elcajero regular esta desocupado y no hay clientes en cola de cajero rapido*/
					generarPartidaRegular(cli);
					cajeroReg.setOcupado(true);
				}
				else {
					colaRap.add(cli);
				}
			}
		}
	}

	public static Servidor buscarCajeroRegularDesocupado() {
		Servidor cajero = null;
		for(int i = 0; i < cajerosRegular.length; i++) {
			if (!cajerosRegular[i].isOcupado()) {
				cajero = cajerosRegular[i];
				break;
			}
		}
		return cajero;
	}
	
	public static Servidor buscarCajeroRapidoDesocupado() {
		Servidor cajero = null;
		for(int i = 0; i < cajerosRapido.length; i++) {
			if (!cajerosRapido[i].isOcupado()) {
				cajero = cajerosRapido[i];
				break;
			}
		}
		return cajero;
	}

	public static int tiempos(int cantEv) {
		int eventoProx = 0;
		for (int i = 1; i < cantEv; i++) {
			if(listaEv[i] < listaEv[eventoProx]) {
				eventoProx = i;
			}
		}
		reloj = listaEv[eventoProx];
		return eventoProx;
		
	}
	
	public static void inicializacion(int cantReg, int cantRap, int cantEv) {
		tiempoSim = 480;
		reloj = 0;
		colaReg = new ArrayList<Cliente>();
		colaRap = new ArrayList<Cliente>();
		cajerosRapido = new Servidor[cantRap];
		cajerosRegular = new Servidor[cantReg];
		listaEv = new double[cantEv];
		cajerosRegular = new Servidor[cantReg];
		for (int i = 0; i < cajerosRapido.length; i++) {
			cajerosRegular[i] = new Servidor();
		}
		cajerosRapido = new Servidor[cantRap];
		for (int i = 0; i < cajerosRapido.length; i++) {
			cajerosRapido[i] = new Servidor();
		};
		tiempoTotal = 0;
		nccd = 0;
		tiemProm = 0;
		generarPrimeros();
		
		
	}
	
	public static void generarPrimeros() {
		listaEv[0] = DistExponencial.generadorDistribucionExponencial(distArr);
		listaEv[1] = tiempoSim;
		listaEv[2] = tiempoSim;
	}
	
	public static void generarArriboCliente() {
		listaEv[0] = DistExponencial.generadorDistribucionExponencial(distArr) + reloj;
	}
	
	public static void generarPartidaRegular(Cliente cli) {
		listaEv[1] = cli.calcularTiempoServicio() + reloj;		
	}
	
	public static void GenerarPartidaRapida(Cliente cli) {
		listaEv[2] = cli.calcularTiempoServicio() + reloj;	
	}

}
