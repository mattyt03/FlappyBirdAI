import java.util.ArrayList;

public class GeneticAlgorithm 
{
	public static void calculateFitness()
	{
		double sum = 0.0;
		
		for (Bird b: Main.savedBirds)
			sum += b.score;
		
		for (Bird b: Main.savedBirds)
			b.fitness = b.score/sum;
	}
	
	public static Bird pickOne()
	{
		int index = 0;
		double r = Math.random();
		while (r > 0)
		{
			Bird b = Main.savedBirds.get(index);
			r -= b.fitness;
			index++;
		}
		index--;
		return Main.savedBirds.get(index);
	}
	
	public static void nextGeneration() 
	{
		calculateFitness();
		
		for(int i = 0; i < Main.NUM_BIRDS; i++)
		{
			Bird b = pickOne();
			//Matrix.print(b.brain.weightsIH);
			System.out.println();
			Bird child = new Bird(20, Main.HEIGHT/2, Main.room.tubes, b.brain);
			child.mutate2();
			//Matrix.print(child.brain.weightsIH);
			Main.birds.add(child);
		}
		
		Main.savedBirds = new ArrayList<>();
	}
}
