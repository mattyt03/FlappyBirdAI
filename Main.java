import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable
{ //, KeyListener
	

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640, HEIGHT = 480;
	private boolean running = false;
	private Thread thread; //I don't get this
	
	public static double score = 0;
	
	
	public static Room room;
	public static ArrayList<Bird> birds;
	public static ArrayList<Bird> savedBirds;
	public static final int NUM_BIRDS = 1000;
	public static int idealFPS = 120;
	public static int numGens = 0;

	//public Bird bird;
	
	public Main() 
	{
		Dimension d = new Dimension (Main.WIDTH, Main.HEIGHT);
		setPreferredSize(d);
		//addKeyListener(this);
		//setFocusable(true);
		//setFocusTraversalKeysEnabled(false);
		room = new Room(80);
		savedBirds = new ArrayList<>();
		birds = new ArrayList<>();
		for (int i = 0; i < NUM_BIRDS; i++) 
		{
			birds.add(new Bird(20, Main.HEIGHT/2, room.tubes));
		}
		//bird = new Bird(20, Main.HEIGHT/2, room.tubes);
	}
	
	public synchronized void start() 
	{
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public synchronized void stop() 
	{
		if(!running) return;
		running = false;
		try 
		{
			thread.join();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main (String[] args) 
	{
		JFrame frame = new JFrame ("Flappy Bird");
		Main flappy = new Main();
		frame.add(flappy);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		flappy.start();
	}
		

	@Override
	public void run() 
	{
		int currentFPS = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double ns = 1000000000 / idealFPS; //Number of nanoseconds in 1/fpsth of a second
		double delta = 0;
		while(running) 
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) 
			{
			update();
			render();
			currentFPS++;
			delta --;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) 
			{
				System.out.println("FPS:" + currentFPS);
				currentFPS = 0;
				timer += 1000;
			}
		}
		
		stop();
	}
	
	/*private void calculateFitness()
	{
		double sum = 0.0;
		
		for (Bird b: savedBirds)
			sum += b.score;
		
		for (Bird b: savedBirds)
			b.fitness = b.score/sum;
	}
	
	private Bird pickOne()
	{
		int index = 0; //Try to understand
		double r = Math.random();
		while (r > 0)
		{
			Bird b = savedBirds.get(index);
			r -= b.fitness;
			index++;
		}
		index--;
		return savedBirds.get(index);
		
		//Bird child = new Bird(20, Main.HEIGHT/2, room.tubes, parent.brain);
		//child.mutate2();
		//return child;
	}
	
	private void nextGeneration() 
	{
		calculateFitness();
		
		for(int i = 0; i < NUM_BIRDS; i++)
		{
			Bird b = pickOne();
			//Matrix.print(b.brain.weightsIH);
			System.out.println();
			Bird child = new Bird(20, Main.HEIGHT/2, room.tubes, b.brain);
			child.mutate2();
			//Matrix.print(child.brain.weightsIH);
			birds.add(child);
		}
		
		savedBirds = new ArrayList<>();
	}*/

	private void render() 
	{
		BufferStrategy bs = getBufferStrategy(); //I don't get this
		if(bs == null) 
		{
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		room.render(g);
		for (Bird b : birds)
			b.render(g);
		//bird.render(g);
		g.setColor(Color.orange);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 19));
		g.drawString("Score: "+ ((int)score), 10, 20);
		//g.setColor(Color.red);
		//g.setFont(new Font(Font.DIALOG, Font.BOLD, 19));
		//g.drawString("Best: "+ Bird.highScore, 100, 20);
		g.dispose();
		bs.show();
	}

	private void update() 
	{
		if (birds.size() == 0)
		{
			room = new Room(80);
			room.tubes = new ArrayList<>();
			/*if (Main.score > highScore) //Finish
			{
				highScore = (int) Main.score;
			}*/
			score = 0;
			GeneticAlgorithm.nextGeneration();
		}
		room.update();
		for (Bird b : birds)
			b.update();
		for (int i = 0; i < birds.size(); i++)
		{
			Bird b = birds.get(i);
			if (b.isAlive == false)
			{
				savedBirds.add(b);
				birds.remove(i--);
			}
			else
				b.score ++;
		}
		//bird.update();
	}
	

	/*Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		if(code == KeyEvent.VK_SPACE) {
			bird.isPressed = true;
		}
		
	}*/

	/*@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			bird.isPressed = false;
		}
		
		
	}*/

	/*@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}*/
}