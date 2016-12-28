package suket123.mystery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by bpate on 2016-12-27.
 */

public class GameoverScene implements Scene{
    private Rect r = new Rect();
    public static long GAME_OVER_TIME;

    @Override
    public void terminate(){
        GameplayScene.gamePlayReset = true;
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(200);
        paint.setColor(Color.MAGENTA);
        drawCenterText(canvas,paint,"GAME OVER !");
        paint.setTextSize(100);
        canvas.drawText("Score: "+ObstacleManager.SCORE_COPY, Constants.SCREEN_WIDTH*0.5f-100,Constants.SCREEN_HEIGHT/2+225,paint);
        /*if (ObstacleManager.SCORE_COPY > GamePanel.sharedPreferences.getInt("hs",0)){
            GameplayScene.HIGH_SCORE = ObstacleManager.SCORE_COPY;
            GamePanel.editor.putInt("hs",ObstacleManager.SCORE_COPY);
            GamePanel.editor.commit();
            GamePanel.editor.apply();
        }*/
        canvas.drawText("High Score: "+GameplayScene.HIGH_SCORE,Constants.SCREEN_WIDTH*0.5f-200,Constants.SCREEN_HEIGHT/2+400,paint);
    }

    @Override
    public void update() {

    }

    @Override
    public void receiveTouch(MotionEvent event){
        if (System.currentTimeMillis() - GAME_OVER_TIME > 1000){
            terminate();
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
