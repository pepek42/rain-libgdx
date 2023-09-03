package com.github.pepek42.rain.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.pepek42.rain.Rain;
import com.github.pepek42.rain.entities.Bucket;
import com.github.pepek42.rain.entities.RainManager;
import com.github.pepek42.rain.enumeration.Difficulty;
import com.github.pepek42.rain.resource.Resource;

public class GameScreen extends ScreenAdapter {

    private final Rain game;
    private final Music backgroudMusic;
    private final Bucket bucket;
    private final RainManager rainManager;
    private final Viewport viewport;
    private final Hud hud;

    public GameScreen(final Rain game, Difficulty difficulty) {
        this.game = game;
        bucket = new Bucket(this.game, Gdx.input);
        rainManager = new RainManager(game, difficulty.getDropSpeed());
        backgroudMusic = game.getResource(Resource.RAIN_MUSIC, Music.class);
        backgroudMusic.setLooping(true);

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Rain.GAME_AREA_WIDTH, Rain.GAME_AREA_HEIGHT);
        viewport = new FitViewport(Rain.GAME_AREA_WIDTH, Rain.GAME_AREA_HEIGHT, camera);
        hud = new Hud(game);
    }

    @Override
    public void render(float delta) {
        game.updateTimer(delta);
        if (game.isGameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        viewport.apply();
        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        renderSprites();
        hud.drawHud();

        updateState(delta);
    }

    private void renderSprites() {
        game.getBatch().begin();
        bucket.renderBucket();
        rainManager.renderRain();
        game.getBatch().end();
    }

    private void updateState(float delta) {
        bucket.update(delta, viewport.getCamera());
        rainManager.update(delta, bucket.getRectangle());
    }

    @Override
    public void show() {
        backgroudMusic.play();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
