import java.lang.Math;

public class Sigmoid implements Command 
{
  public double execute(double x) 
  {
    return 1/(1 + Math.exp(-x));
  }    
}