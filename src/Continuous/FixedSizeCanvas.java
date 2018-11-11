package Continuous;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FixedSizeCanvas extends Component {

	ContinuousSpace2D buffer1;
	ContinuousSpace2D buffer2;
	ContinuousSpace2D countBuffer;

	BufferedImage img;
	Random r = new Random();

	int x;
	int y;

	public FixedSizeCanvas(int xi, int yi) {
		this.x = xi;
		this.y = yi;

		buffer1 = new ContinuousSpace2D(x, y);
		buffer2 = new ContinuousSpace2D(x, y);
		countBuffer = new ContinuousSpace2D(x, y);

		img = new BufferedImage(x*2, y*2, BufferedImage.TYPE_BYTE_GRAY);

		for (int x = 0; x < this.x; x++) {
			for (int y = 0; y < this.y; y++) {
				if (r.nextInt(100) > 50) {
					buffer1.setCell(x, y, (byte) 100);
					// buffer2.setCell(x, y, (byte) 1);
				}
			}
		}
	}

	public void paint(Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics;

		step();

		for (int x = 0; x < this.x; x++) {
			for (int y = 0; y < this.y; y++) {
				int val = buffer1.getCell(x, y) * 2;
				
				img.setRGB(x*2, y*2, (val << 24) | (val << 16) | (val << 8) | val);
				img.setRGB(x*2+1, y*2+1, (val << 24) | (val << 16) | (val << 8) | val);
				img.setRGB(x*2, y*2+1, (val << 24) | (val << 16) | (val << 8) | val);
				img.setRGB(x*2+1, y*2, (val << 24) | (val << 16) | (val << 8) | val);

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

		countBuffer.clear();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				int val = buffer1.getCell(i, j);
				if (buffer1.getCell(i, j) > 0) {
					countBuffer.sumCell(i + 1, j,val);
					countBuffer.sumCell(i, j + 1,val);
					countBuffer.sumCell(i - 1, j,val);
					countBuffer.sumCell(i, j - 1,val);
					countBuffer.sumCell(i + 1, j + 1,val);
					countBuffer.sumCell(i - 1, j + 1,val);
					countBuffer.sumCell(i + 1, j - 1,val);
					countBuffer.sumCell(i - 1, j - 1,val);
				}
			}
		}

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				buffer2.setCell(i, j, buffer1.getCell(i, j));
				int count = countBuffer.getCell(i, j);

				if (buffer1.getCell(i, j) > 128) {
					// LIVE CELL
					if (count < 64) // DIE BY UNDERPOPULATION
					{
						buffer2.sumCell(i, j, -10);
					} else if (count >= 160 ) // DIE BY OVERPOPULATION
					{
						buffer2.sumCell(i, j,-10);
					}
				} else {
					// EMPTY SPACE
					if (count < 160 && count >= 64) // BIRTH
					{
						buffer2.sumCell(i, j, +1);
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
