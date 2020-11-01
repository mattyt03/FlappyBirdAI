import java.lang.Math;

public class Mutate implements Command 
{
  public double execute(double x) 
  {
    if(Math.random() < NeuralNetwork.mutationRate)
      //return 2 * Math.random()-1;
    	return x + Math.random()*0.2-0.1;
    else
      return x;
  }    
}