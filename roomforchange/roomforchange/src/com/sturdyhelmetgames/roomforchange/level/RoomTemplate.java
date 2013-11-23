package com.sturdyhelmetgames.roomforchange.level;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sturdyhelmetgames.roomforchange.entity.Mummy;
import com.sturdyhelmetgames.roomforchange.entity.Snake;
import com.sturdyhelmetgames.roomforchange.entity.Spider;
import com.sturdyhelmetgames.roomforchange.level.Level.LevelTileType;

public class RoomTemplate {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 6;
	private final LevelTileType[][] tileTypes;
	private final Pixmap pixmap;

	private final Array<Class<?>> entityTypes = new Array<Class<?>>();

	public Pixmap getPixmap() {
		return pixmap;
	}

	public LevelTileType[][] getTileTypes() {
		return tileTypes;
	}

	public Array<Class<?>> getEntityTypes() {
		return entityTypes;
	}

	public RoomTemplate(Pixmap pixmap) {
		this.pixmap = pixmap;
		tileTypes = new LevelTileType[WIDTH][HEIGHT];
		if (pixmap.getWidth() > WIDTH || pixmap.getHeight() > HEIGHT) {
			throw new IllegalArgumentException(
					"Template is too big! Should be 10x6px.");
		}

		int yFlip = 0;
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = pixmap.getHeight() - 1; y >= 0; y--) {
				int pixel = pixmap.getPixel(x, yFlip);
				if (pixel == Color.BLACK) {
					tileTypes[x][y] = LevelTileType.WALL_FRONT;
				} else if (pixel == Color.RED) {
					tileTypes[x][y] = LevelTileType.HOLE;
				} else if (pixel == Color.BLUE) {
					tileTypes[x][y] = LevelTileType.ROCK;
				} else if (pixel == Color.GREEN) {
					tileTypes[x][y] = LevelTileType.LEVER;
				} else {
					tileTypes[x][y] = LevelTileType.GROUND;
				}
				yFlip++;
			}
			yFlip = 0;
		}

		// randomize room difficulty level
		final int difficultyLevel = MathUtils.random(5);
		for (int i = 0; i < difficultyLevel; i++) {
			final int enemyType = MathUtils.random(100);
			if (enemyType < 30) {
				entityTypes.add(Mummy.class);
			} else if (enemyType < 60) {
				entityTypes.add(Snake.class);
			} else if (enemyType < 100) {
				entityTypes.add(Spider.class);
			}
		}
	}
}
