package com.howardi.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet extends SpriteObject {
    private double life;

    public Bullet(double x, double y, int size, double direction, double speed) {
        this.x = x;
        this.y = y;
        this.collisionLayer = "PLAYER";

        this.dx = Math.cos(direction) * speed;
        this.dy = Math.sin(direction) * speed;

        width = -1;
        height = -1;
        life = 200;
    }

    @Override
    public void update() {
        super.update();
        life -= 1;
        if (life <= 0) {
            dead = true;
        }
    }

    @Override
    public void drawSprite(Canvas canvas, double x, double y) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawCircle((float) x, (float) y, (float) radius, paint);
    }
}
