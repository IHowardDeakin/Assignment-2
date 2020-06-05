package com.howardi.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Asteroid extends SpriteObject {
    private List<Double> offsets;
    private Double rotation;
    private Integer size;
    private double speed;

    public Asteroid(GameView game, double x, double y, int size, double direction, double speed) {
        super(game, x, y);

        offsets = new ArrayList<Double>();
        for (int i = 5 + game.rng.nextInt(6); i >= 0; i--) {
            offsets.add(0.7 + game.rng.nextDouble()*0.5);
        }

        this.rotation = 0.;
        this.collisionLayer = "ASTEROID";

        this.radius = 40 * Math.pow(1.8, size);
        this.size = size;

        this.dx = Math.cos(direction) * speed;
        this.dy = Math.sin(direction) * speed;
        this.speed = speed;

        width = -1;
        height = -1;
    }

    @Override
    public void update(double elapsed_time) {
        super.update(elapsed_time);
        rotation += 0.01;
    }

    @Override
    public void onCollide(SpriteObject otherObject) {
        if (otherObject instanceof Bullet) {
            dead = true;
            game.score += (int) (100 * Math.pow(2, 2-size));
            if (size > 0) {
                game.spawn(new Asteroid(game, x, y, size-1,
                        game.rng.nextDouble() * 2 * Math.PI,
                        speed + game.rng.nextDouble() * 0.3));
                game.spawn(new Asteroid(game, x, y, size-1,
                        game.rng.nextDouble() * 2 * Math.PI,
                        speed + game.rng.nextDouble() * 0.3));
                if (game.rng.nextDouble() < 0.1 * size) {
                    game.spawn(new Asteroid(game, x, y, size-1,
                            game.rng.nextDouble() * 2 * Math.PI,
                            speed * 1.1 + game.rng.nextDouble() * 0.4));
                }
            }
        }
    }

    @Override
    public void drawSprite(Canvas canvas, double ast_x, double ast_y) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        double angle = Math.PI*2/offsets.size();
        for (int i = 0; i < offsets.size()-1; i++) {

            canvas.drawLine((float) (ast_x + radius * Math.cos(angle*i + rotation) * offsets.get(i)),
                    (float) (ast_y + radius * Math.sin(angle*i + rotation) * offsets.get(i)),
                    (float) (ast_x + radius * Math.cos(angle*(i+1) + rotation) * offsets.get(i+1)),
                    (float) (ast_y + radius * Math.sin(angle*(i+1) + rotation) * offsets.get(i+1)),
                    paint);
        }
        int finalIndex = offsets.size()-1;
        canvas.drawLine((float) (ast_x + radius * Math.cos(angle*finalIndex + rotation) * offsets.get(finalIndex)),
                (float) (ast_y + radius * Math.sin(angle*finalIndex + rotation) * offsets.get(finalIndex)),
                (float) (ast_x + radius * Math.cos(rotation) * offsets.get(0)),
                (float) (ast_y + radius * Math.sin(rotation) * offsets.get(0)),
                paint);
    }
}
