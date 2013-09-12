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
	
	private GameMode mode;
	
	enum GameMode { SIMPLE, MULTI, EXTREM }

	public ChunxVisualizer(String title) {
		super(title);
		mode = GameMode.SIMPLE;
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
		
		switch (mode) {
		case EXTREM:
			g.drawString("Mode: Ultra", 10, 50);
			break;
		case MULTI:
			g.drawString("Mode: Multi", 10, 50);
			break;
		case SIMPLE:
			g.drawString("Mode: Simple", 10, 50);
			break;
		default:
			g.drawString("Mode: None", 10, 50);
			break;
		
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		target = new Entity(gc.getWidth() /2f,  gc.getHeight() / 2f);
		world = new World(); 
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
		
		if (input.isKeyPressed(Input.KEY_1)){
			mode =  GameMode.SIMPLE;
		} else if (input.isKeyPressed(Input.KEY_2)) {
			mode = GameMode.MULTI;
		} else if (input.isKeyPressed(Input.KEY_3)) {
			mode = GameMode.EXTREM;
		}
		
		chunkSystem.update(delta);
		
		world.update(delta);
		
		target.setX(input.getMouseX());
		target.setY(input.getMouseY());
		
			
		boolean spam = mode != GameMode.SIMPLE && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		
		if (spam || input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			int amount = getAmount();			
			int indexX = interpreter.translateX(input.getMouseX());
			int indexY = interpreter.translateY(input.getMouseY());
			
			if (chunkSystem.getCache().containsIndex(indexX, indexY)) {
				for (int i = 0; i < amount; ++i) {
					MovingEntity entity = new MovingEntity(input.getMouseX(), input.getMouseY(), chunkSystem.getConfiguration());
					world.add(entity);
				}
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			shutdown();	
			gc.exit();
		}
			
			
	}
	
	private int getAmount() {
		switch (mode) {
		case EXTREM:
			return 150;
		case MULTI:
			return 15;
		case SIMPLE:
			return 1;
		default:
			return 0;
		}
	}
	
	public void shutdown() {
		chunkSystem.shutdown();
	}
	
	public static void main(String[] args) throws SlickException {
		ChunxVisualizer v = new ChunxVisualizer("Chunk Visualizer");
		AppGameContainer app = new AppGameContainer(v);
		app.setDisplayMode(1280, 800, false);
		app.start();	
	}

}
