package com.github.pepek42.rain.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.pepek42.rain.Rain;

public class GameOverScreen extends ScreenAdapter {
    private final Rain game;
    private final OrthographicCamera camera;

    public GameOverScreen(final Rain game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "GAME OVER. You got " + game.getDropGathered() + " drops.", 100, 150);
        game.getFont().draw(game.getBatch(), "Tap anywhere to try again!", 100, 100);
        game.getBatch().end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }
}
