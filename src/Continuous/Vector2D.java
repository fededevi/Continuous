package Continuous;

public class Vector2D {
	double x;
	double y;
	
	public Vector2D()
	{
		x = 0;
		y = 0;
	}
	
	public Vector2D( double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void sumSelf(Vector2D v)
	{
		this.x += v.x;
		this.y += v.y;
	}

	public Vector2D mul(double m) {
		return new Vector2D(this.x * m  , this.y * m);
	}

	public double squareRadius() {
		return x * x + y *y;
	}

	public Vector2D sub(Vector2D v) {
		return new Vector2D(this.x - v.x , this.y - v.y);
	}
	
	double magnitude()
	{
		return Math.sqrt(x*x + y*y);
	}

	public Vector2D normalized() {
		double m = this.magnitude();
		return new Vector2D(this.x / m, this.y / m);
	}

	public Vector2D sum(Vector2D v) {
		return new Vector2D(this.x +v.x, this.y +v.y);
	}
}
