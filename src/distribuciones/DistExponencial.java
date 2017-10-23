package distribuciones;

public class DistExponencial {
	
	  /**
     * Método estático para generar valores de una variabl aleatoria 
     * con distribución exponencial por medio del método de Transformada Inversa.
     * 
     * @param lambda Parámetro de la distribución exponencial.
     * @return valor para la variable aleatoria con dist. exponencial
     */
    public static Double generadorDistribucionExponencial(double lambda){
        
        double random = Math.random();

        double exponencial = ((Math.log(random)/lambda) * (-1));
        
        return exponencial;
    }
}
