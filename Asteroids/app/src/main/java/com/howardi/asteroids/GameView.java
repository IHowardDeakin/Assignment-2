package com.howardi.asteroids;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private List<SpriteObject> spriteObjects;
    public int score;
    public int wave;
    public Random rng;
    public double pointerX, pointerY;
    public boolean touching;
    public int lives;

    private GameActivity context;

    public GameView(Context context) {
        super(context);
        this.context = (GameActivity) context;

        getHolder().addCallback(this);
        setFocusable(true);
        gameThread = new GameThread(getHolder(), this);
        spriteObjects = new ArrayList<SpriteObject>();
        score = 0;
        rng = new Random();
        pointerX = 0;
        pointerY = 0;
        touching = false;
        lives = 3;
        wave = 1;

        start(wave + 2);
    }

    public void start(int asteroids) {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        spriteObjects.clear(); // Reset every time a new wave starts

        spawn(new Player(this, width/2, height/2));
        double x, y;
        for (int i = 0; i < asteroids; i++) {
            x = rng.nextDouble() * width;
            y = rng.nextDouble() * height;
            // If it's too close, retry
            if ((x-width/2)*(x-width/2) + (y-height/2)*(y-height/2) < 300) {
                i--;
                continue;
            }
            spawn(new Asteroid(this, x, y, Math.min(1 + rng.nextInt(wave+1), 3),
                    rng.nextDouble() * Math.PI * 2, 200 + rng.nextInt(20 * wave)));
        }
    }

    public void spawn(SpriteObject sprite) {
        spriteObjects.add(sprite);
    }

    public void update(long elapsed_time) {
        double seconds = elapsed_time/1000.0;
        for (SpriteObject object: spriteObjects) {
            for (SpriteObject object2: spriteObjects) {
                if (!object.equals(object2) && !object.dead && !object2.dead && object.collide(object2)) {
                    object.onCollide(object2);
                    object2.onCollide(object);
                }
            }
            object.update(seconds);
        }

        int asteroids = 0;
        for (int i = spriteObjects.size() - 1; i >= 0; i--) {
            if (spriteObjects.get(i).dead) {
                if (spriteObjects.get(i) instanceof Player) {
                    lives -= 1;
                    start(wave+2);
                    if (lives <= 0) {
                        SharedPreferences sharedPref = getContext().getSharedPreferences(getContext().getString(R.string.high_score_key),
                                Context.MODE_PRIVATE);
                        String scores = sharedPref.getString(getContext().getString(R.string.high_score_key), "2000 Bruce|1000 Lee");
                        scores += "|" + Integer.toString(score);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getContext().getString(R.string.high_score_key), scores);
                        editor.commit();
                        context.finish();
                    }
                    return; // Exit the entire function, just abandon the rest of the update process as the entire wave resets
                }
                spriteObjects.remove(i);
            } else if (spriteObjects.get(i) instanceof Asteroid) {
                asteroids++;
            }
        }

        if (asteroids == 0) {
            wave += 1;
            score += 1000*wave;
            start(wave + 2);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (SpriteObject object: spriteObjects) {
            object.draw(canvas);
        }
        Paint scoreText = new Paint();
        scoreText.setColor(Color.WHITE);
        scoreText.setTextSize(50);
        canvas.drawText("SCORE: " + Integer.toString(score), 50, 100, scoreText);
        canvas.drawText("LIVES: " + Integer.toString(lives), 50, 180, scoreText);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointerX = event.getX();
                pointerY = event.getY();
                touching = true;
                break;
            case MotionEvent.ACTION_MOVE:
                pointerX = event.getX();
                pointerY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touching = false;
        }

        return true;
    }
}
