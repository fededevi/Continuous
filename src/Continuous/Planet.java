package Continuous;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Planet {
	static Random rand = new Random();
	
	double radius = 10;
	double mass = radius * radius;
	
	Vector2D position = new Vector2D();
	Vector2D velocity = new Vector2D();
	Vector2D acceleration = new Vector2D();
	
	public Planet()
	{
		radius = 1 + rand.nextInt(5);
		mass = radius * (rand.nextFloat()+1.0);

		position = new Vector2D(rand.nextFloat() * 512 +256,rand.nextFloat() * 512 + 256);
		
		//Vector2D centerDirection = new Vector2D(512,512).sub(position);
		//Vector2D centerOrbit = new Vector2D(centerDirection.y, -centerDirection.x);
		Vector2D centerOrbit = new Vector2D(rand.nextDouble()-0.5,rand.nextDouble()-0.5);
		velocity = centerOrbit.mul(200.30 );
	}
	
	public Planet(Planet p1, Planet p2) {
			radius = Math.sqrt(p1.radius*p1.radius + p2.radius*p2.radius);
			position = p1.position.mul(p1.mass).sum(p2.position.mul(p2.mass)).mul(1.0/(p1.mass+p2.mass));
			mass = p1.mass + p2.mass;
			velocity = p1.velocity.mul(p1.mass).sum(p2.velocity.mul(p2.mass)).mul(1.0/(p1.mass+p2.mass));
	}

	public void paint(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.drawOval((int)(position.x - radius ), (int)(position.y- radius ), (int)radius*2, (int)radius*2);
		g2.setColor(Color.BLUE);
		g2.drawLine((int)position.x, (int)position.y, (int)position.x + (int)velocity.x,(int) position.y+(int)velocity.y);
		g2.setColor(Color.RED);
		g2.drawLine((int)position.x, (int)position.y, (int)position.x + (int)acceleration.mul(10).x,(int) position.y+(int)acceleration.mul(10).y);
		
	}

	public void step(double et) {
		velocity.sumSelf(acceleration.mul(et));
		position.sumSelf(velocity.mul(et));		
	}
	
}
