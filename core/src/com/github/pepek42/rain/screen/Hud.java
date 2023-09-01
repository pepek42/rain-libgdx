package com.github.pepek42.rain.screen;

import com.github.pepek42.rain.Rain;

public class Hud {
    private final Rain game;
    public Hud(final Rain game) {
        this.game = game;
    }

    public void drawHud() {
        game.getBatch().begin();
        // TODO Better hud
        game.getFont().draw(game.getBatch(), "Drops Collected: " + game.getDropGathered(), 0, 480);
        game.getFont().draw(game.getBatch(), "Seconds remaining " + game.getTimer(), 600, 480);
        game.getBatch().end();
    }
}
