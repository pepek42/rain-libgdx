package com.github.pepek42.rain.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.pepek42.rain.Rain;
import com.github.pepek42.rain.resource.Resource;

import java.util.Iterator;

public class RainManager {
    private final Array<Rectangle> raindrops;
    private final Rain game;
    private final Texture dropImage;
    private final Sound dropSound;
    private long lastDropTime;
    private final float dropSpeed;

    public RainManager(final Rain game, float dropSpeed) {
        this.game = game;
        raindrops = new Array<>();
        dropImage = game.getTexture(Resource.DROP_TEXTURE);
        dropSound = game.getResource(Resource.DROP_SOUND, Sound.class);
        this.dropSpeed = dropSpeed;
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void renderRain() {
        for (Rectangle raindrop : raindrops) {
            game.getBatch().draw(dropImage, raindrop.x, raindrop.y);
        }
    }

    public void update(float delta, Rectangle bucketRectangle) {
        if (TimeUtils.nanoTime() - lastDropTime > 1_000_000_000)
            spawnRaindrop();

        Iterator<Rectangle> iterator = raindrops.iterator();
        while (iterator.hasNext()) {
            Rectangle raindrop = iterator.next();
            raindrop.y -= dropSpeed * delta;
            if (raindrop.y + 64 < 0)
                iterator.remove();
            if (raindrop.overlaps(bucketRectangle)) {
                game.registerDropGathered();
                dropSound.play();
                iterator.remove();
            }
        }
    }
}
