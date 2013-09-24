package de.myreality.chunx.visualizer;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Graphics;

import de.myreality.chunx.ContentProvider;
import de.myreality.chunx.util.Updateable;

public class World implements ContentProvider {
	
	private CopyOnWriteArrayList<Object> targets;
	
	public World() {
		targets = new CopyOnWriteArrayList<Object>();
	}

	@Override
	public void add(Object target) {
		targets.add(target);
	}

	@Override
	public Collection<Object> getContent() {
		return targets;
	}

	@Override
	public void remove(Object target) {
		targets.remove(target);
	}
	
	public void update(int delta) {
		for (Object target : targets) {
			if (target instanceof Updateable) {
				((Updateable) target).update(delta);
			}
		}
	}
	
	public void render(Graphics g) {
		for (Object target : targets) {
			if (target instanceof Entity) {
				((Entity) target).draw(g);
			}
		}
	}

	
	public int size() {
		return targets.size();
	}
}
