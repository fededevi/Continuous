
public class ContinuousSpace2D {
	
	byte data[];
	int x;
	int y;
	
	public ContinuousSpace2D(int x, int y)
	{
		this.x = x;
		this.y = y;
		data = new byte[x * y];
	}
	
	byte getCell( int i, int j) {
		int i1 = i;
		int j1 = j;

		if (i1 < 0)
			i1 = x + i1 ;

		if (i1 >= x)
			i1 = i1 - x;

		if (j1 < 0)
			j1 = y + j1;

		if (j1 >= y)
			j1 = j1 - y;

		return data[i1*x+j1];
	}

	void setCell( int i, int j, byte value) {
		int i1 = i;
		int j1 = j;

		if (i1 < 0)
			i1 = x + i1;

		if (i1 >= x)
			i1 = i1 - x;

		if (j1 < 0)
			j1 = y + j1;

		if (j1 >= y)
			j1 = j1 - y;

		data[i1*x+j1] =value;
	}

}
