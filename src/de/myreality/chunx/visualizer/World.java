package de.myreality.chunx.visualizer;

import java.util.ArrayList;
import java.util.Collection;

import de.myreality.chunx.ChunkTarget;
import de.myreality.chunx.ContentProvider;

public class World implements ContentProvider {
	
	private ArrayList<ChunkTarget> targets;
	
	public World() {
		targets = new ArrayList<ChunkTarget>();
	}

	@Override
	public void add(ChunkTarget target) {
		targets.add(target);
	}

	@Override
	public Collection<ChunkTarget> getContent() {
		return targets;
	}

	@Override
	public void remove(ChunkTarget target) {
		targets.remove(target);
	}

}
