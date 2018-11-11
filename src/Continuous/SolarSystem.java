package Continuous;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;


public class SolarSystem extends Component {

	Random r = new Random();
	
	ArrayList<Planet> planets;
	
	Planet sun;
	public SolarSystem()
	{
		planets =  new ArrayList<>(300);
		
		sun = new Planet();
		sun.position = new Vector2D(512,512);
		sun.velocity = new Vector2D(0,0);
		sun.mass = 10;
		sun.radius = 10;
		
		planets.add( sun);
		
		for (int i = 0; i < 300; i++)
		{
			planets.add( new Planet());
		}
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics;


		for (Planet planet : planets) {
			planet.paint(g2);
		}

		step();

	}
	
	long lastElapsedTime = System.nanoTime();
	
	private void step() {
		// TODO Auto-generated method stub
		long elapsedTime = (System.nanoTime() - lastElapsedTime);
		lastElapsedTime = System.nanoTime();
		
		double  seconds = elapsedTime / 1000000000.0;
		System.out.println(seconds);

		LinkedList<Planet> merge1 = new LinkedList<>();
		LinkedList<Planet> merge2 = new LinkedList<>();
		
		//check Mergers
		for (Planet planet1 : planets) {
			if (planet1 == sun)
				continue;
			
			for (Planet planet2 : planets) {
				if (planet1 == planet2)
					continue;
				
				if (planet1.position.sub(planet2.position).magnitude() < planet1.radius + planet2.radius) {
					if (!merge1.contains(planet1) && !merge2.contains(planet1) && !merge1.contains(planet2) && !merge2.contains(planet2))
					{
						merge1.add(planet1);
						merge2.add(planet2);
					}
				}
			}
		}
		
		for (int i = 0; i < merge1.size(); i++)
		{
			Planet p1 = merge1.get(i);
			Planet p2 = merge2.get(i);
			
			planets.remove(p1);
			planets.remove(p2);
			
			planets.add(new Planet(p1,p2));
		}
		
		for (Planet planet1 : planets) {
			Vector2D acceleration = new Vector2D(0,0);
			if (planet1 == sun)
				continue;
			
			for (Planet planet2 : planets) {
				if (planet1 == planet2)
					continue;
				Vector2D direction = planet2.position.sub(planet1.position);
				Vector2D normalized = direction.normalized();
				double squareRadius = direction.squareRadius();
				double gravityMagnitude = (planet1.mass + planet2.mass) / squareRadius;
				
				if (gravityMagnitude > 10)
					gravityMagnitude = 10;
				
				Vector2D gravity = normalized.mul(gravityMagnitude * 100);
				acceleration.sumSelf(gravity.mul(1.0/planet1.mass));
			}
			
			planet1.acceleration = acceleration;			
			planet1.step(seconds*1.0);
		}
		
		repaint();		
	}

}
