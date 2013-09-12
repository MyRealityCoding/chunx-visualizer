package de.myreality.chunx.visualizer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.myreality.chunx.Chunk;
import de.myreality.chunx.ChunkConfiguration;
import de.myreality.chunx.ChunkTarget;
import de.myreality.chunx.caching.Cache;
import de.myreality.chunx.caching.CachedChunkConfiguration;
import de.myreality.chunx.caching.CachedChunkSystem;
import de.myreality.chunx.caching.SimpleCachedChunkConfiguration;
import de.myreality.chunx.caching.SimpleCachedChunkSystem;
import de.myreality.chunx.util.PositionInterpreter;
import de.myreality.chunx.util.SimplePositionInterpreter;

public class ChunxVisualizer extends BasicGame {
	
	final int UPDATE_INTERVAL = 20;
	
	private CachedChunkSystem chunkSystem;
	
	private ChunkTarget target;
	
	private World world;
	
	private PositionInterpreter interpreter;

	public ChunxVisualizer(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		Cache cache = chunkSystem.getCache();
		ChunkConfiguration config = chunkSystem.getConfiguration();
		
		
		for (int x = 0; x < gc.getWidth(); x += config.getChunkWidth()) {
			for (int y = 0; y < gc.getHeight(); y += config.getChunkHeight()) {
				g.setColor(Color.gray);
				g.setLineWidth(2);
				g.drawRect(x, y, config.getChunkWidth(), config.getChunkHeight());
			}
		}
		
		for (Chunk chunk : chunkSystem.getChunks()) {

			if (chunkSystem.getActiveChunk() != null) {
				if (chunk.equals(chunkSystem.getActiveChunk())) {
					g.setColor(Color.lightGray);
				} else if (cache.containsIndex(chunk)) {
					g.setColor(Color.gray);
				} else {
					g.setColor(Color.darkGray);
				}
				g.fillRect(chunk.getX(), chunk.getY(), chunk.getWidth(), chunk.getHeight());
				g.setColor(Color.black);
				g.drawRect(chunk.getX(), chunk.getY(), chunk.getWidth(), chunk.getHeight());
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
		configuration.setCacheSizeX(3);
		configuration.setCacheSizeY(3);
		configuration.setChunkSize(50);
		chunkSystem = new SimpleCachedChunkSystem(configuration);
		ChunkRevitalizer vitalizer = new ChunkRevitalizer(chunkSystem);
		chunkSystem.addListener(vitalizer);
		//ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		//executor.scheduleAtFixedRate(chunkSystem, 0, UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
		chunkSystem.start();
		interpreter = new SimplePositionInterpreter(configuration);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		chunkSystem.update(delta);
		
		world.update(delta);
		
		target.setX(input.getMouseX());
		target.setY(input.getMouseY());
		
			
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			int indexX = interpreter.translateX(input.getMouseX());
			int indexY = interpreter.translateY(input.getMouseY());
			
			if (chunkSystem.getCache().containsIndex(indexX, indexY)) {
				for (int i = 0; i < 50; ++i) {
					MovingEntity entity = new MovingEntity(input.getMouseX(), input.getMouseY(), chunkSystem.getConfiguration());
					world.add(entity);
				}
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gc.exit();
		}
			
			
	}
	
	public void shutdown() {
		chunkSystem.shutdown();
	}
	
	public static void main(String[] args) throws SlickException {
		ChunxVisualizer v = new ChunxVisualizer("Chunk Visualizer");
		AppGameContainer app = new AppGameContainer(v);
		app.setDisplayMode(800, 600, true);
		app.start();
		v.shutdown();		
	}

}
