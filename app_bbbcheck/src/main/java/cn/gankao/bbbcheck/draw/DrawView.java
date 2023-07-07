package cn.gankao.bbbcheck.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class DrawView extends View {

    Paint mPaint;
    Paint mDrawpaint;
    Context context;
    private StrokeOnlyDrawer mStrokeDrawer;
    private Rect mDrawRect;
    private Bitmap bitmap;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initDraw();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        return false;
//    }

    public void Init(int sw_width, int sw_height) {
        mStrokeDrawer.createDrawer((int) sw_width, (int) sw_height);
        mDrawRect = new Rect(0, 0, (int) sw_width, (int) sw_height);

        bitmap = Bitmap.createBitmap((int) sw_width, (int) sw_height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG |
                Paint.FILTER_BITMAP_FLAG));
        canvas.setBitmap(bitmap);
    }


    public void initDraw() {
        mPaint = new Paint();
        mPaint.setColor(Color.TRANSPARENT);
//        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mStrokeDrawer = new StrokeOnlyDrawer();
        mDrawpaint = new Paint(Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
        mDrawpaint.setStyle(Paint.Style.STROKE);
        mDrawpaint.setStrokeWidth(1);
        mDrawpaint.setColor(Color.BLACK);
        mDrawpaint.setAntiAlias(true); // 抗锯齿
        mDrawpaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawpaint.setStrokeCap(Paint.Cap.ROUND);

//        DisplayMetrics dm = getResources().getDisplayMetrics();

    }

    public void clearAll() {
        if (mStrokeDrawer != null) {
            mStrokeDrawer.clearDrawer();
        }
    }


    public void drawBmpPoint(float x, float y,  int index) {
        if (mStrokeDrawer.getStrokeBitmap() == null
                || mStrokeDrawer.getStrokeBitmap().isRecycled())
            return;

        mStrokeDrawer.drawDot(index, 80, x, y);


    }

    private boolean mIsVisible = false;

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        if (isVisible) {
            Observable.timer(200, TimeUnit.MILLISECONDS)
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            mIsVisible = true;
                        }
                    });
        } else {
            mIsVisible = false;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsVisible) {
            this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            canvas.setMatrix(null);
            this.setLayerType(View.LAYER_TYPE_NONE, null);
        }

        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, 0, 0, mPaint);
        }

        if (mStrokeDrawer.getStrokeBitmap() != null
                && !mStrokeDrawer.getStrokeBitmap().isRecycled()) {
            canvas.drawBitmap(mStrokeDrawer.getStrokeBitmap(),
                    mDrawRect, mDrawRect, mDrawpaint);
        }

    }

    public Bitmap view2Bitmap() {
        int w = this.getWidth();
        int h = this.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        this.layout(0, 0, w, h);
        this.draw(canvas);
        return bitmap;
    }

}


