package suket123.mystery;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by bpate on 2016-12-27.
 */

public class MainScene implements Scene {

    private Rect r = new Rect();

    @Override
    public void terminate(){
        GameplayScene.gamePlayReset = true;
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        drawCenterText(canvas,paint,"CLICK ANYWHERE TO START");
    }

    @Override
    public void update() {

    }

    @Override
    public void receiveTouch(MotionEvent event){
        terminate();
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
