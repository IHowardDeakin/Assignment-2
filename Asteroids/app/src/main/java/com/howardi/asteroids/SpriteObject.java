package com.howardi.asteroids;

import android.graphics.Canvas;

public abstract class SpriteObject {
    public String collisionLayer = "NONE";
    public double x, y;
    public double dx, dy;
    public double radius;
    protected int width, height;
    public boolean dead = false;
    protected GameView game;

    public SpriteObject(GameView game, double x, double y) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return this.getClass().getName() + "(" + Double.toString(x) + "," + Double.toString(y) + ")";
    };

    public abstract void drawSprite(Canvas canvas, double x, double y);

    public void draw(Canvas canvas) {
        if (width == -1 || height == -1) {
            width = canvas.getWidth();
            height = canvas.getHeight();
        }

        drawSprite(canvas, x, y);

        if (x - radius < 0) {
            drawSprite(canvas, x+width, y);
            if (y - radius < 0) {
                drawSprite(canvas, x+width, y+height);
            } else if (y + radius > height) {
                drawSprite(canvas, x+width, y-height);
            }
        }
        if (x + radius > width) {
            drawSprite(canvas, x-width, y);
            if (y - radius < 0) {
                drawSprite(canvas, x-width, y+height);
            } else if (y + radius > height) {
                drawSprite(canvas, x-width, y-height);
            }
        }
        if (y - radius < 0) {
            drawSprite(canvas, x, y+height);
        } else if (y + radius > height) {
            drawSprite(canvas, x, y-height);
        }
    };

    public void update(double elapsed_time) {
        x += dx * elapsed_time;
        y += dy * elapsed_time;
        if (width != -1 && height != -1) {
            if (x < 0) {
                x += width;
            } else if (x >= width) {
                x -= width;
            }
            if (y < 0) {
                y += height;
            } else if (y >= height) {
                y -= height;
            }
        }
    };

    public boolean collide(SpriteObject otherObject) {
        if (collisionLayer.equals(otherObject.collisionLayer)) {
            return false;
        } else if (collisionLayer.equals("NONE") || otherObject.collisionLayer.equals("NONE")) {
            return false;
        } else {
            double x_gap = x - otherObject.x;
            double y_gap = y - otherObject.y;
            double r_gap = radius + otherObject.radius;
            return x_gap * x_gap + y_gap * y_gap < r_gap * r_gap;
        }
    }

    public abstract void onCollide(SpriteObject otherObject);
}
