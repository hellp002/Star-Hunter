package gui;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sharedObject.IRenderable;
import tools.Data;

public class Tiles implements IRenderable {
	private static final Image[] TILE = new Image[5];
	private static final Image[] BLOCK = new Image[2];
	
	
	private static int[][] field = new int[(Data.BOARDERDOWN - Data.BOARDERTOP) / Data.PIXEL + 2][(Data.BOARDERRIGHT - Data.BOARDERLEFT) / Data.PIXEL + 2];
	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return -10;
	}
	
	static {
		Random rd = new Random();
		for (int i = 0; i < (Data.BOARDERDOWN - Data.BOARDERTOP) / Data.PIXEL + 2; i++) {
			for (int j = 0; j < (Data.BOARDERRIGHT - Data.BOARDERLEFT) / Data.PIXEL + 2; j++) {
				if (i == 0 || i == (Data.BOARDERDOWN - Data.BOARDERTOP) / Data.PIXEL + 1 || j == 0
						|| j == (Data.BOARDERRIGHT - Data.BOARDERLEFT) / Data.PIXEL + 1) {
					field[i][j] = rd.nextInt(2);
				} else {
					field[i][j] = rd.nextInt(5);
				}
			}
		}
		for (int i = 0; i < TILE.length ; i++) {
			TILE[i] = new Image(ClassLoader.getSystemResource("pic/floor/tile_" +(i+1) + ".png").toString());
		}
		BLOCK[0] = new Image(ClassLoader.getSystemResource("pic/block/stone/stone_2.png").toString());
		BLOCK[1] = new Image(ClassLoader.getSystemResource("pic/block/bush/bush_2.png").toString());
	}
	
	private Image getTile(int num) {
		switch(num) {
		case 0:
			return TILE[0];
		case 1:
			return TILE[1];
		case 2:
			return TILE[2];
		case 3:
			return TILE[3];
		case 4:
			return TILE[4];
		default:
			return null;
		}
	}
	
	private Image getBlock(int num) {
		switch(num) {
		case 0:
			return BLOCK[0];
		case 1:
			return BLOCK[1];
		default:
			return null;
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		for (int i = 0; i < (Data.BOARDERDOWN - Data.BOARDERTOP) / Data.PIXEL + 2; i++) {
			for (int j = 0; j < (Data.BOARDERRIGHT - Data.BOARDERLEFT) / Data.PIXEL + 2; j++) {
				if (i == 0 || i == (Data.BOARDERDOWN - Data.BOARDERTOP) / Data.PIXEL + 1 || j == 0
						|| j == (Data.BOARDERRIGHT - Data.BOARDERLEFT) / Data.PIXEL + 1) {
					gc.drawImage(getBlock(field[i][j]), j*Data.PIXEL, (Data.BOARDERTOP - Data.PIXEL)+i*Data.PIXEL, Data.PIXEL, Data.PIXEL);
				} else {
					gc.drawImage(getTile(field[i][j]), j*Data.PIXEL, (Data.BOARDERTOP - Data.PIXEL)+ i*Data.PIXEL, Data.PIXEL, Data.PIXEL);
				}
			}
		}

	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}
}
