package suket123.mystery;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by bpate on 2016-12-26.
 */

public class Obstacle implements GameObject{

    private Rect rectangle;
    private int color;
    private Rect rectangle2;

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap){
        this.color = color;
        rectangle = new Rect(0,startY,startX,startY+rectHeight);
        rectangle2 = new Rect(startX+playerGap,startY,Constants.SCREEN_WIDTH,startY+rectHeight);
    }

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementY(float y){
        rectangle.top += y; // build in variable top and bottom LITTTTT
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    public boolean playerCollide(RectPlayer player){
        /*if(rectangle.contains(player.getRectangle().left,player.getRectangle().top)||
                rectangle.contains(player.getRectangle().right,player.getRectangle().top) ||
                rectangle.contains(player.getRectangle().left,player.getRectangle().bottom) ||
                rectangle.contains(player.getRectangle().right,player.getRectangle().bottom)){
            return true;
        }

        return false;*/

        return Rect.intersects(rectangle,player.getRectangle()) || Rect.intersects(rectangle2,player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2,paint);
    }

    @Override
    public void update(){

    }

}
