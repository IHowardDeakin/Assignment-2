package com.howardi.asteroids;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private long last_time;
    private long now_time;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();

        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean running) {
        this.running = running;
        last_time = SystemClock.uptimeMillis();
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    now_time = SystemClock.uptimeMillis();
                    this.gameView.update(now_time - last_time);
                    last_time = now_time;
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
