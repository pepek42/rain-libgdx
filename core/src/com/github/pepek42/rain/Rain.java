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

    private SpriteBatch batch;
    private BitmapFont font;
    private AssetManager assetManager;



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
}
