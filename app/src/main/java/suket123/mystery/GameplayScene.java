package suket123.mystery;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by bpate on 2016-12-27.
 */

public class GameplayScene implements Scene{

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private long gameOverTime;
    private Rect r = new Rect();
    public static int HIGH_SCORE = 0;

    public static boolean gamePlayReset = false;

    public GameplayScene(){
        //player = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255,0,0));
        player = new RectPlayer(new Rect(50,50,200,200), Color.rgb(255,0,0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        //obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        obstacleManager = new ObstacleManager(400,450,75,Color.BLACK);
    }

    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        //obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        obstacleManager = new ObstacleManager(400,450,75,Color.BLACK);
        movingPlayer = false;
    }

    @Override
    public void terminate(){
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        obstacleManager.draw(canvas);

        if (gameOver) {
            GameoverScene.GAME_OVER_TIME = System.currentTimeMillis();
            if (obstacleManager.getScore() > HIGH_SCORE) {
                HIGH_SCORE = obstacleManager.getScore();
            }
            reset();
            gameOver = false;
            SceneManager.ACTIVE_SCENE = 2;
        }
    }

    @Override
    public void update() {

        if (gamePlayReset == true){
            reset();
            gamePlayReset = false;
        }
        if (!gameOver) {
            player.update(playerPoint);
            obstacleManager.update();

            if (player.getRectangle().bottom > Constants.SCREEN_HEIGHT) {
                gameOver = true;
            }

            if (obstacleManager.playerCollide(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }

    }

    @Override
    public void receiveTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: // down means when pressed, up means when released
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000){
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer)
                    playerPoint.set((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
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
