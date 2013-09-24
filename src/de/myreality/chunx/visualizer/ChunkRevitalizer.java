package de.myreality.chunx.visualizer;

import de.myreality.chunx.Chunk;
import de.myreality.chunx.ChunkSystemListener;

public class ChunkRevitalizer implements ChunkSystemListener {
	
	final int COUNT = 1;

	@Override
	public void afterCreateChunk(Chunk chunk) {
		
		// Spawn entities
		for (int i = 0; i < COUNT; ++i) {
			float x = (float) (chunk.getX() + Math.random() * chunk.getWidth());
			float y = (float) (chunk.getY() + Math.random() * chunk.getHeight());
			
			if (Math.random() * 100 < 8) {
				Entity entity = new Entity(x, y);
				chunk.add(entity);
			}
		}
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
