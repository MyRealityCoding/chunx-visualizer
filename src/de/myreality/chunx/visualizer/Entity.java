package de.myreality.chunx.visualizer;

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
}
