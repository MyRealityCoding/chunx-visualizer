package de.myreality.chunx.visualizer;

import de.myreality.chunx.Chunk;
import de.myreality.chunx.ChunkListener;
import de.myreality.chunx.ChunkSystem;

public class ChunkRevitalizer implements ChunkListener {
	
	private ChunkSystem system;
	
	final int COUNT = 0;
	
	public ChunkRevitalizer(ChunkSystem system) {
		this.system = system;
	}

	@Override
	public void afterCreateChunk(Chunk chunk) {
		
		// Spawn entities
		//for (int i = 0; i < COUNT; ++i) {
			//float x = (float) (chunk.getX() + Math.random() * chunk.getWidth());
			//float y = (float) (chunk.getY() + Math.random() * chunk.getHeight());
			
			//MovingEntity entity = new MovingEntity(x, y, system.getConfiguration());
			//chunk.add(entity);
		//ist}
	}

	@Override
	public void afterLoadChunk(Chunk arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterRemoveChunk(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterSaveChunk(Chunk arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCreateChunk(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeLoadChunk(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeRemoveChunk(Chunk arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeSaveChunk(Chunk arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnterChunk(Chunk arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeaveChunk(Chunk arg0) {
		// TODO Auto-generated method stub

	}

}
