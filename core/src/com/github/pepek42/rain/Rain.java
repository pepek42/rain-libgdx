package com.github.pepek42.rain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.pepek42.rain.resource.Resource;
import com.github.pepek42.rain.screen.MainMenuScreen;

public class Rain extends Game {
    public static final int GAME_AREA_WIDTH = 800;
    public static final int GAME_AREA_HEIGHT = 480;
    public static final float TIMER_IN_SECONDS = 1 * 60f;

    private SpriteBatch batch;
    private BitmapFont font;
    private AssetManager assetManager;
    private int dropsGathered;
    private float timer;

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Texture getTexture(Resource resource) {
        return getResource(resource, Texture.class);
    }

    public <T> T getResource(Resource resource, Class<T> clazz) {
        return assetManager.get(resource.getPath(), clazz);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        assetManager = new AssetManager();
        assetManager.load(Resource.BUCKET_TEXTURE.getPath(), Texture.class);
        assetManager.load(Resource.DROP_TEXTURE.getPath(), Texture.class);
        assetManager.load(Resource.DROP_SOUND.getPath(), Sound.class);
        assetManager.load(Resource.RAIN_MUSIC.getPath(), Music.class);
        assetManager.finishLoading();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        assetManager.dispose();
    }

    public void reset() {
        dropsGathered = 0;
        timer = TIMER_IN_SECONDS;
    }

    public void updateTimer(float delta) {
        timer -= delta;
    }

    public boolean isGameOver() {
        return timer <= 0;
    }

    public void registerDropGathered() {
        dropsGathered++;
    }


    public int getDropGathered() {
        return dropsGathered;
    }

    public int getTimer() {
        return (int) timer;
    }
}
