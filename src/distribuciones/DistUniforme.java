package distribuciones;

public class DistUniforme {

	

    public static Double generadorDistribucionUniforme(int a, int b){
        
        double random =Math.random();
        
        double uniforme = (b-a)*random+a;
        
        return uniforme;
    }
}
