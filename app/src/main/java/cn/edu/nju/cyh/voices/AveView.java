package cn.edu.nju.cyh.voices;
/**
 * This file was used for signal analysis
 * Comments removed are not Significant
 * @auther cyh
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewTreeObserver;

public class AveView extends View {
    Paint paint;
    int audioSampleNum = 44100;
    int widthPixels;
    int heightPixels;
    float points[];
    short  audio[];

    public AveView(Context context){
        super(context);
        paint=new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(1);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                widthPixels=getWidth();
                heightPixels=getHeight();
            }
        });
    }

    protected void drawWave(Canvas canvas, short audio[]) {
        for (int i = 0; i < audioSampleNum-1; i++){
            points[4*i] = (float)i/audioSampleNum * widthPixels;
            points[4*i+1] = heightPixels/2 + (float)audio[i]/128 * heightPixels/2;
            points[4*i+2] = (float)(i+1)/audioSampleNum * widthPixels;
            points[4*i+3] = heightPixels/2 + (float)audio[i+1]/128 * heightPixels/2;
        };
        canvas.drawLines(points, paint);
    }

    protected void onDraw(Canvas canvas) {
        if (points == null) {
            points = new float[audioSampleNum * 4];
            audio = new short[audioSampleNum];
        }
        canvas.drawCircle(100, 100, 90, paint);
        drawWave(canvas, audio);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 200);
    }
}
