package de.myreality.chunx.visualizer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import de.myreality.chunx.ChunkTarget;

public class Entity implements ChunkTarget {

	private static final long serialVersionUID = 1L;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	private float x, y;

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}
	
	public void draw(Graphics g) {
		int size = 18;
		g.setColor(Color.blue);
		g.fillOval(getX() - size / 2f, getY() - size / 2f, size, size);
		g.setLineWidth(2);
		g.setColor(Color.black);
		g.drawOval(getX() - size / 2f, getY() - size / 2f, size, size);
	}
}
