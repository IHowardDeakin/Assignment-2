package com.howardi.asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private List<SpriteObject> spriteObjects;
    public int score;
    public Random rng;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        setFocusable(true);
        gameThread = new GameThread(getHolder(), this);
        spriteObjects = new ArrayList<SpriteObject>();
        score = 0;
        rng = new Random();
        spriteObjects.add(new Bullet(this, 100, 100, 3));
    }

    public void spawn(SpriteObject sprite) {
        spriteObjects.add(sprite);
    }

    public void update(long elapsed_time) {
        double seconds = elapsed_time/1000.0;
        for (SpriteObject object: spriteObjects) {
            for (SpriteObject object2: spriteObjects) {
                if (!object.equals(object2) && object.collide(object2)) {
                    object.onCollide(object2);
                    object2.onCollide(object);
                }
            }
            object.update(seconds);
        }
        for (int i = spriteObjects.size() - 1; i >= 0; i--) {
            if (spriteObjects.get(i).dead) {
                spriteObjects.remove(i);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (SpriteObject object: spriteObjects) {
            object.draw(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}
