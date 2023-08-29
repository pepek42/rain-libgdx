package com.github.pepek42.rain.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.pepek42.rain.Rain;
import com.github.pepek42.rain.entities.Bucket;
import com.github.pepek42.rain.resource.Resource;

import java.util.Iterator;

public class GameScreen extends ScreenAdapter {
    public static final int DROP_SPEED = 500;
    private final Rain game;

    Texture dropImage;

    Sound dropSound;
    Music rainMusic;

    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    private final Bucket bucket;
    private final Viewport viewport;


    public GameScreen(final Rain game) {
        this.game = game;

        dropImage = game.getTexture(Resource.DROP_TEXTURE);
        dropSound = game.getResource(Resource.DROP_SOUND, Sound.class);
        rainMusic = game.getResource(Resource.RAIN_MUSIC, Music.class);
        rainMusic.setLooping(true);

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Rain.GAME_AREA_WIDTH, Rain.GAME_AREA_HEIGHT);
        viewport = new FitViewport(Rain.GAME_AREA_WIDTH, Rain.GAME_AREA_HEIGHT, camera);

        bucket = new Bucket(this.game, Gdx.input);

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<>();
        spawnRaindrop();

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

    @Override
    public void render(float delta) {
        viewport.apply();
        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        renderSprites();

        bucket.update(delta, viewport.getCamera());

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        Iterator<Rectangle> iterator = raindrops.iterator();
        while (iterator.hasNext()) {
            Rectangle raindrop = iterator.next();
            raindrop.y -= DROP_SPEED * delta;
            if (raindrop.y + 64 < 0)
                iterator.remove();
            if (raindrop.overlaps(bucket.getRectangle())) {
                dropsGathered++;
                dropSound.play();
                iterator.remove();
            }
        }
    }

    private void renderSprites() {
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Drops Collected: " + dropsGathered, 0, 480);
        game.getBatch().draw(bucket.getTexture(), bucket.getX(), bucket.getY());
        for (Rectangle raindrop : raindrops) {
            game.getBatch().draw(dropImage, raindrop.x, raindrop.y);
        }
        game.getBatch().end();
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
