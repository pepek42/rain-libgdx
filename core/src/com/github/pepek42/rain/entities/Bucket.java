package com.github.pepek42.rain.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.pepek42.rain.Rain;
import com.github.pepek42.rain.resource.Resource;

public class Bucket {
    private static final int TEXTURE_WIDTH = 64;
    private static final int TEXTURE_HEIGHT = 64;
    private static final int BUCKET_SPEED = 500;

    private final Texture texture;
    private final Rectangle rectangle;
    private final Input input;
    private final Rain game;

    public Bucket(final Rain game, Input input) {
        this.game = game;
        this.input = input;
        texture = game.getTexture(Resource.BUCKET_TEXTURE);

        rectangle = new Rectangle(
                Rain.GAME_AREA_WIDTH / 2.f - 64 / 2.f,
                20,
                TEXTURE_WIDTH,
                TEXTURE_HEIGHT
        );
    }

    public void update(float delta, Camera camera) {
        if (input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(input.getX(), input.getY(), 0);
            camera.unproject(touchPos);
            rectangle.x = touchPos.x - TEXTURE_WIDTH / 2.f;
        }
        if (input.isKeyPressed(Input.Keys.LEFT))
            rectangle.x -= BUCKET_SPEED * delta;
        if (input.isKeyPressed(Input.Keys.RIGHT))
            rectangle.x += BUCKET_SPEED * delta;


        if (rectangle.x < 0)
            rectangle.x = 0;
        if (rectangle.x > Rain.GAME_AREA_WIDTH - TEXTURE_WIDTH)
            rectangle.x = Rain.GAME_AREA_WIDTH - TEXTURE_HEIGHT;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void renderBucket() {
        game.getBatch().draw(texture, rectangle.x, rectangle.y);
    }
}
