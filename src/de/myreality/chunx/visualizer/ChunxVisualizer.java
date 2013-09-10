package de.myreality.chunx.visualizer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.myreality.chunx.Chunk;
import de.myreality.chunx.ChunkTarget;
import de.myreality.chunx.caching.CachedChunkConfiguration;
import de.myreality.chunx.caching.SimpleCachedChunkConfiguration;
import de.myreality.chunx.caching.SimpleCachedChunkSystem;
import de.myreality.chunx.concurrent.ConcurrentChunkSystem;

public class ChunxVisualizer extends BasicGame {
	
	final int UPDATE_INTERVAL = 20;
	
	private ConcurrentChunkSystem chunkSystem;
	
	private ChunkTarget target;

	public ChunxVisualizer(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Chunk chunk : chunkSystem.getChunks()) {
			Color color = randomColor(chunk.getIndexX(), chunk.getIndexY());
			g.setColor(color);
			g.fillRect(chunk.getX(), chunk.getY(), chunk.getWidth(), chunk.getHeight());
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		target = new Entity(140, 200); // You have to write your own implementation
		World world = new World(); // You have to write your own implementation
		CachedChunkConfiguration  configuration = new SimpleCachedChunkConfiguration();
		configuration.setFocused(target);
		configuration.setContentProvider(world);
		configuration.setCacheSize(4);
		configuration.setChunkSize(20);
		chunkSystem = new ConcurrentChunkSystem(new SimpleCachedChunkSystem(configuration));
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleAtFixedRate(chunkSystem, 0, UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
		chunkSystem.start();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			target.setX(input.getMouseX());
			target.setY(input.getMouseY());
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ChunxVisualizer("Chunk Visualizer"));
		app.setDisplayMode(800, 600, false);
		app.start();
	}
	
	private Color randomColor(int x, int y) {
		return new Color((float)Math.pow(x, 2) / 100f, (float)Math.pow(y, 2) / 100f, (float)Math.pow(x + y, 2)  / 100f);
	}

}
