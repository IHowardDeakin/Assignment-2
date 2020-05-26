package com.howardi.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Player extends SpriteObject {
    private double direction;
    private double speed;
    private double max_speed;
    private double cooldown;
    private double cooldownTime;

    public Player(GameView game, double x, double y) {
        super(game, x, y);
        this.collisionLayer = "PLAYER";

        this.radius = 50;
        this.cooldown = 0.5;
        this.cooldownTime = 0;
        direction = 0;
        speed = 0;
        max_speed = 600;
    }

    @Override
    public void update(double elapsed_time) {
        dx = Math.cos(direction) * speed;
        dy = Math.sin(direction) * speed;
        super.update(elapsed_time);

        // Slow down by 25% a second
        speed *= Math.pow(0.75, elapsed_time);

        cooldownTime += elapsed_time;
        if (cooldownTime > cooldown) {
            cooldownTime -= cooldown;
            game.spawn(new Bullet(game, x, y, direction));
        }


    }

    public void accelerateTowards(double p_x, double p_y) {
        double directionTowards = Math.atan2(p_y-y, p_x-x);
        double acceleration = 250;

        dx = Math.cos(direction) * speed + Math.cos(directionTowards) * acceleration;
        dy = Math.sin(direction) * speed + Math.sin(directionTowards) * acceleration;
        speed = Math.min(Math.sqrt(dx*dx+dy*dy), max_speed);
        direction = Math.atan2(dy, dx);
    }

    @Override
    public void drawSprite(Canvas canvas, double x, double y) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);

        canvas.drawLine((float) (x + radius * Math.cos(direction)),
                (float) (y + radius * Math.sin(direction)),
                (float) (x + radius * Math.cos(direction + 0.75 * Math.PI)),
                (float) (y + radius * Math.sin(direction + 0.75 * Math.PI)),
                paint);
        canvas.drawLine((float) (x + radius * Math.cos(direction + 0.75 * Math.PI)),
                (float) (y + radius * Math.sin(direction + 0.75 * Math.PI)),
                (float) (x + radius * Math.cos(direction + Math.PI) * 0.6),
                (float) (y + radius * Math.sin(direction + Math.PI) * 0.6),
                paint);
        canvas.drawLine((float) (x + radius * Math.cos(direction + Math.PI) * 0.6),
                (float) (y + radius * Math.sin(direction + Math.PI) * 0.6),
                (float) (x + radius * Math.cos(direction + 1.25 * Math.PI)),
                (float) (y + radius * Math.sin(direction + 1.25 * Math.PI)),
                paint);
        canvas.drawLine((float) (x + radius * Math.cos(direction + 1.25 * Math.PI)),
                (float) (y + radius * Math.sin(direction + 1.25 * Math.PI)),
                (float) (x + radius * Math.cos(direction)),
                (float) (y + radius * Math.sin(direction)),
                paint);
    }

    @Override
    public void onCollide(SpriteObject otherObject) {

    }
}
