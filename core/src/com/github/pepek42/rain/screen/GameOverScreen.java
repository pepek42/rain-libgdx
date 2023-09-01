package com.github.pepek42.rain.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.github.pepek42.rain.Rain;

public class GameOverScreen extends ScreenAdapter {
    private final Rain game;

    public GameOverScreen(final Rain game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        game.getFont().draw(
                game.getBatch(),
                "GAME OVER. You got " + game.getDropGathered() + " drops",
                Gdx.graphics.getWidth() / 2.f,
                Gdx.graphics.getHeight() / 2.f
        );
        game.getBatch().end();
        if (Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }
}
