package de.myreality.chunx.visualizer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.myreality.chunx.Chunk;
import de.myreality.chunx.ChunkTarget;
import de.myreality.chunx.caching.Cache;
import de.myreality.chunx.caching.CachedChunkConfiguration;
import de.myreality.chunx.caching.CachedChunkSystem;
import de.myreality.chunx.caching.SimpleCachedChunkConfiguration;
import de.myreality.chunx.caching.SimpleCachedChunkSystem;

public class ChunxVisualizer extends BasicGame {
	
	final int UPDATE_INTERVAL = 20;
	
	private CachedChunkSystem chunkSystem;
	
	private ChunkTarget target;
	
	private World world;

	public ChunxVisualizer(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		Cache cache = chunkSystem.getCache();
		
		for (Chunk chunk : chunkSystem.getChunks()) {
			
			if (chunkSystem.getActiveChunk() != null) {
				if (chunk.equals(chunkSystem.getActiveChunk())) {
					g.setColor(Color.green);
				} else if (cache.containsIndex(chunk)) {
					g.setColor(Color.gray);
				} else {
					g.setColor(Color.darkGray);
				}
				g.fillRect(chunk.getX(), chunk.getY(), chunk.getWidth(), chunk.getHeight());
			}
		}
		
		world.render(g);
		
		g.setColor(Color.white);
		g.drawString("Entities: " + world.size(), 10, 30);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		target = new Entity(140, 200); // You have to write your own implementation
		world = new World(); // You have to write your own implementation
		CachedChunkConfiguration  configuration = new SimpleCachedChunkConfiguration();
		configuration.setFocused(target);
		configuration.setContentProvider(world);
		configuration.setCacheSize(2);
		configuration.setChunkSize(50);
		chunkSystem = new SimpleCachedChunkSystem(configuration);
		ChunkRevitalizer vitalizer = new ChunkRevitalizer(chunkSystem);
		chunkSystem.addListener(vitalizer);
		//ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		//executor.scheduleAtFixedRate(chunkSystem, 0, UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
		chunkSystem.start();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		chunkSystem.update(delta);
		
		world.update(delta);
		
		//if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			target.setX(input.getMouseX());
			target.setY(input.getMouseY());
		//}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ChunxVisualizer("Chunk Visualizer"));
		app.setDisplayMode(800, 600, false);
		app.start();
	}

}
