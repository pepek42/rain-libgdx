package com.github.pepek42.rain.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.pepek42.rain.Rain;
import com.github.pepek42.rain.enumeration.Difficulty;

public class MainMenuScreen extends ScreenAdapter {
    private final Rain game;
    private final Stage stage;

    public MainMenuScreen(final Rain game) {
        this.game = game;

        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new FitViewport(Rain.GAME_AREA_WIDTH, Rain.GAME_AREA_HEIGHT), game.getBatch());
        final Table table = new Table();
        final Button quitButton = new TextButton("Quit", uiSkin);
        final Button easyGameButton = new TextButton("Easy game", uiSkin);
        final Button hardGameButton = new TextButton("Hard game", uiSkin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        easyGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runGame(Difficulty.EASY);
            }
        });
        hardGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runGame(Difficulty.HARD);
            }
        });
        table.setFillParent(true);
        table.add(easyGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(hardGameButton).fillX().uniformX();
        table.row();
        table.add(quitButton).fillX().uniformX();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void runGame(Difficulty difficulty) {
        game.reset();
        game.setScreen(new GameScreen(game, difficulty));
    }
}
