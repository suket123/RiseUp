package suket123.mystery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by bpate on 2016-12-26.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private SceneManager manager;

    /*public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;*/

    public GamePanel (Context context){
        super (context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);

        manager = new SceneManager();
        setFocusable(true);

        /*sharedPreferences = context.getSharedPreferences("highscore",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("hs",0);
        GamePanel.editor.commit();
        GamePanel.editor.apply();*/
    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated (SurfaceHolder holder){
        thread = new MainThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(true){
            try{
                thread.setRunning(true);
                thread.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        manager.receiveTouch(event);

        return true;
    }

    public void update(){
        manager.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        manager.draw(canvas);
    }





}
