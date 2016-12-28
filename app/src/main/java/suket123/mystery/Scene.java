package suket123.mystery;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by bpate on 2016-12-27.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveTouch(MotionEvent event);
}
