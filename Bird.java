import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Bird extends Rectangle
{
	
	private static final long serialVersionUID = 1L;
	private float spd = 4;
	public boolean isPressed = false;
	public static int highScore;
	private boolean isFalling;
	public boolean isAlive;
	public int score;
	public double fitness;
	public NeuralNetwork brain;
	private ArrayList<Rectangle> tubes;
	
	private float gravity = 0.3f;
	
	public Bird(int x, int y, ArrayList<Rectangle> tubes) 
	{
		setBounds(x,y,32,32);
		this.tubes = tubes;
		this.brain = new NeuralNetwork(4,4,1);
		isAlive = true;
		score = 0;
		fitness = 0.0;
	}
	
	public Bird(int x, int y, ArrayList<Rectangle> tubes, NeuralNetwork brain) 
	{
		setBounds(x,y,32,32);
		this.tubes = tubes;
		this.brain = NeuralNetwork.copy(brain);
		isAlive = true;
		score = 0;
		fitness = 0.0;
	}
	
	public void mutate2()
	{
		//System.out.println("yes");
		brain.mutate1();
	}
	
	public void think(ArrayList<Rectangle> tubes)
	{	
		
		Rectangle closestBottom = tubes.get(0);
		double closestBottomDistance = closestBottom.x - x;
		for(int i = 2; i < tubes.size(); i+=2)
		{
			Rectangle rect = tubes.get(i);
			double d = rect.x - x;
			if (d < closestBottomDistance && d > 0)
			{
				closestBottom = rect;
				closestBottomDistance = d;
			}
		}
		
		Rectangle closestTop = tubes.get(1);
		double closestTopDistance = closestTop.x - x;
		for(int i = 3; i < tubes.size(); i+=2) 
		{
			Rectangle rect = tubes.get(i);
			double d = rect.x - x;
			if (d < closestTopDistance && d > 0)
			{
				closestTop = rect;
				closestTopDistance = d;
			}
		}
		
		double[] inputs = new double[4];
		double h = Main.HEIGHT;
		double w = Main.WIDTH;
		inputs[0] = y/h;
		//System.out.println(inputs[0]);
		inputs[1] = closestBottom.height/h;
		//System.out.println(inputs[1]);
		inputs[2] = closestTop.y/h;
		//System.out.println(inputs[2]);
		inputs[3] = closestBottomDistance/w;
		//System.out.println(inputs[3]);
		
		double[] outputs = brain.feedForward(inputs);
		//System.out.print(outputs[0]);
		if (outputs[0] > 0.5)
			isPressed = true;
		else
			isPressed = false;
	}

	public void update() 
	{
		think(tubes);
		isFalling = false;
		if(isPressed) 
		{
			if (y > 0) 
			{
				spd = 4;
				y-=spd; //How are you going up?
			}
		}
		else 
		{
			if (y < Main.HEIGHT - height) 
			{
				isFalling = true;
				y+=spd; //How are you going down?
			}
		}
		
		if(isFalling) 
		{
			spd+=gravity;
			if(spd >= 8) spd = 8;
		}
		
		for (int i = 0; i < tubes.size(); i++) 
		{
			if(this.intersects(tubes.get(i))) 
			{ 
				isAlive = false;
				/*//restart the game
				Main.room = new Room(80);
				tubes = Main.room.tubes; //I don't get this
				y = Main.HEIGHT/2;
				if (Main.score > highScore) {
					highScore = (int) Main.score;
				}
				Main.score = 0;
				spd = 4;
				break;*/
			}
		}
		
		/*if(y>= Main.HEIGHT || y<=0) {
			//restart the game
			Main.room = new Room(80);
			tubes = Main.room.tubes;
			y = Main.HEIGHT/2;
			if (Main.score > highScore) {
				highScore = (int) Main.score;
			}
			Main.score = 0;
			spd = 4;
		}*/
	}
	
	public void render(Graphics g) 
	{
		g.setColor(Color.orange);
		g.fillOval(x, y, width, height);
	}
}