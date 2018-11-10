import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FixedSizeCanvas extends Component {

	ContinuousSpace2D buffer1;
	ContinuousSpace2D buffer2;

	BufferedImage img;
	Random r = new Random();

	int x;
	int y;

	public FixedSizeCanvas(int xi, int yi) {
		this.x = xi;
		this.y = yi;

		buffer1 = new ContinuousSpace2D(x, y);
		buffer2 = new ContinuousSpace2D(x, y);

		img = new BufferedImage(x, y, BufferedImage.TYPE_BYTE_GRAY);

		for (int x = 0; x < this.x; x++) {
			for (int y = 0; y < this.y; y++) {
				if (r.nextInt(100) > 50)
				{
					buffer1.setCell(x, y, (byte) 1);
					buffer2.setCell(x, y, (byte) 1);
				}
			}
		}
	}

	public void paint(Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics;

		step();

		for (int x = 0; x < this.x; x++) {
			for (int y = 0; y < this.y; y++) {
				if (buffer1.getCell(x, y) > 0)
					img.setRGB(x, y, (0x00 << 24) | (0x00 << 16) | (0x00 << 8) | 0x00);
				else
					img.setRGB(x, y, (0xFF << 24) | (0xFF << 16) | (0xFF << 8) | 0xFF);

				// int a = (int)(Math.random()*256); //alpha
				// int r = (int)(Math.random()*256); //red
				// int g = (int)(Math.random()*256); //green
				// int b = (int)(Math.random()*256); //blue
				// int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel

			}
		}

		g2.drawImage(img, 0, 0, null);

	}

	private void step() {
		// RULES FOR CONWAY
		// Any live cell with fewer than two live neighbors dies, as if by
		// underpopulation.
		// Any live cell with two or three live neighbors lives on to the next
		// generation.
		// Any live cell with more than three live neighbors dies, as if by
		// overpopulation.
		// Any dead cell with exactly three live neighbors becomes a live cell, as if by
		// reproduction.
/*
		ContinuousSpace2D count = new ContinuousSpace2D(this.x, this.y);
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (buffer1.getCell(i, j) > 0) {
					count.setCell(i, j,  (byte) (count.getCell(i, j) +1));
				}
				
			}
		}*/

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				buffer2.setCell(i, j, buffer1.getCell(i, j));
				int count = 0;
				count += buffer1.getCell(i + 1, j);
				count += buffer1.getCell(i, j + 1);
				count += buffer1.getCell(i - 1, j);
				count += buffer1.getCell(i, j - 1);
				count += buffer1.getCell(i - 1, j - 1);
				count += buffer1.getCell(i - 1, j + 1);
				count += buffer1.getCell(i + 1, j - 1);
				count += buffer1.getCell(i + 1, j + 1);

				if (buffer1.getCell(i, j) > 0) {
					// LIVE CELL
					if (count < 2) // DIE BY UNDERPOPULATION
					{
						buffer2.setCell(i, j, (byte) 0);
					} else if (count > 3) // DIE BY OVERPOPULATION
					{
						buffer2.setCell(i, j, (byte) 0);
					}
				} else {
					// EMPTY SPACE
					if (count == 3) // BIRTH
					{
						buffer2.setCell(i, j, (byte) 1);
					}
				}
			}
		}

		// Swap Image buffers
		ContinuousSpace2D swap = buffer1;
		buffer1 = buffer2;
		buffer2 = swap;

		// ---------------
		repaint();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7684228328800944136L;

}
