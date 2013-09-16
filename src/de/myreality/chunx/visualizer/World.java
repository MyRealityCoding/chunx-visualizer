package de.myreality.chunx.visualizer;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Graphics;

import de.myreality.chunx.ChunkTarget;
import de.myreality.chunx.ContentProvider;
import de.myreality.chunx.util.Updateable;

public class World implements ContentProvider {
	
	private CopyOnWriteArrayList<ChunkTarget> targets;
	
	public World() {
		targets = new CopyOnWriteArrayList<ChunkTarget>();
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
	
	public void update(int delta) {
		for (ChunkTarget target : targets) {
			if (target instanceof Updateable) {
				((Updateable) target).update(delta);
			}
		}
	}
	
	public void render(Graphics g) {
		for (ChunkTarget target : targets) {
			if (target instanceof Entity) {
				((Entity) target).draw(g);
			}
		}
	}

	
	public int size() {
		return targets.size();
	}
}
