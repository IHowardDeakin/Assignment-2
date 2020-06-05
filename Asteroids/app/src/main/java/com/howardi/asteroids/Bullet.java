package com.howardi.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet extends SpriteObject {
    private double life;
    private double speed = 800;

    public Bullet(GameView game, double x, double y, double direction) {
        super(game, x, y);
        this.x = x;
        this.y = y;
        this.collisionLayer = "PLAYER";

        this.dx = Math.cos(direction) * speed;
        this.dy = Math.sin(direction) * speed;

        width = -1;
        height = -1;
        life = 200;
        radius = 10;
    }

    @Override
    public void update(double elapsed_time) {
        super.update(elapsed_time);
        life -= 1;
        if (life <= 0) {
            dead = true;
        }
    }

    @Override
    public void onCollide(SpriteObject otherObject) {
        if (otherObject instanceof Asteroid) dead = true;
    }

    @Override
    public void drawSprite(Canvas canvas, double x, double y) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawCircle((float) x, (float) y, (float) radius, paint);
    }
}
