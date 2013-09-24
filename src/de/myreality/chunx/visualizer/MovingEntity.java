package de.myreality.chunx.visualizer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import de.myreality.chunx.ChunkConfiguration;
import de.myreality.chunx.moving.MoveableChunkTarget;
import de.myreality.chunx.moving.MovementDetector;
import de.myreality.chunx.moving.SimpleMovementDetector;

public class MovingEntity extends Entity implements MoveableChunkTarget {
	
	private static final long serialVersionUID = 3296990541751043791L;
	private MovementDetector detector;
	
	private float xVelo, yVelo;
	
	private float maxSpeed = 0.1f;

	public MovingEntity(float x, float y, ChunkConfiguration config) {
		super(x, y);
		setMovementDetector(new SimpleMovementDetector(this, config));
	}

	@Override
	public void update() {
		update(0);
	}

	@Override
	public void update(float delta) {
		
		float factorX = 0, factorY = 0;
		
		if (Math.abs(xVelo) < maxSpeed) {
			factorX += (float) (Math.random() * 0.005f);
		}
		
		if (Math.abs(yVelo) < maxSpeed) {
			factorY += (float) (Math.random() * 0.005f);
		}
		
		factorX *= (Math.random() > 0.5f) ? 1 : -1;
		factorY *= (Math.random() > 0.5f) ? 1 : -1;
		
		xVelo += factorX;
		yVelo += factorY;
		
		setX((float) (getX() + xVelo * delta));
		setY((float) (getY() + yVelo * delta));
		
		if (detector != null) {
			detector.update(delta);
		}
	}

	@Override
	public MovementDetector getMovementDetector() {		
		return detector;
	}

	@Override
	public void setMovementDetector(MovementDetector detector) {
		this.detector = detector;
	}
	
	@Override
	public void draw(Graphics g) {
		final int size = 8;
		
		g.setColor(Color.green);
		
		if (this.detector != null) {
			int listeners = this.getMovementDetector().getListeners().size();
			
			if (listeners < 1) {
				g.setColor(Color.red);
			}		
		}
		
		g.fillRect(getX() - size / 2f, getY() - size / 2f, size, size);
		g.setLineWidth(2);
		g.setColor(Color.black);
		g.drawRect(getX() - size / 2f, getY() - size / 2f, size, size);
	}
}
