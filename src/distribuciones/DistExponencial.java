package distribuciones;

public class DistExponencial {
	
	  /**
     * M�todo est�tico para generar valores de una variabl aleatoria 
     * con distribuci�n exponencial por medio del m�todo de Transformada Inversa.
     * 
     * @param lambda Par�metro de la distribuci�n exponencial.
     * @return valor para la variable aleatoria con dist. exponencial
     */
    public static Double generadorDistribucionExponencial(double lambda){
        
        double random = Math.random();

        double exponencial = ((Math.log(random)/lambda) * (-1));
        
        return exponencial;
    }
}
