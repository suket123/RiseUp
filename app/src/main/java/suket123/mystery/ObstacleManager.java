package suket123.mystery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by bpate on 2016-12-26.
 */

public class ObstacleManager {

    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score = 0;
    public static int SCORE_COPY = 0;

    public ObstacleManager(int playerGap,int obstacleGap,int obstacleHeight,int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();
        populateObstacles();
    }

    private void populateObstacles(){
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while(currY < 0){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight,color,xStart,currY,playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public int getScore(){
        return score;
    }

    public boolean playerCollide(RectPlayer player){
        for (Obstacle ob:obstacles){
            if (ob.playerCollide(player)){
                return true;
            }
        }
        return false;
    }

    public void update (){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1+(startTime-initTime)/1000))*Constants.SCREEN_HEIGHT/(10000.0f);
        for (Obstacle ob: obstacles){
            ob.incrementY(speed*elapsedTime);
        }
        if(obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0,new Obstacle(obstacleHeight,color,xStart,obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size()-1);
            score++;
        }
        SCORE_COPY = score;
    }

    public void draw (Canvas canvas){
        for (Obstacle ob:obstacles){
            ob.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText(""+score,50,50+paint.descent()-paint.ascent(),paint);
        canvas.drawText(""+GameplayScene.HIGH_SCORE,Constants.SCREEN_WIDTH*0.9f,50+paint.descent()-paint.ascent(),paint);
    }

}
