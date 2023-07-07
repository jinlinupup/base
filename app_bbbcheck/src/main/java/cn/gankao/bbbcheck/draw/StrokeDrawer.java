package cn.gankao.bbbcheck.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;

import androidx.annotation.ColorInt;

@SuppressWarnings("FieldCanBeLocal")
public class StrokeDrawer {
    private Paint mPaint;
    private Paint mLinePaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    private Path mPath;
    private float mScale;

    private float g_x0, g_x1, g_x2, g_x3;
    private float g_y0, g_y1, g_y2, g_y3;
    private float g_p0, g_p1, g_p2, g_p3;
    private float g_vx01, g_vy01, g_n_x0, g_n_y0;
    private float g_vx21, g_vy21;
    private float g_norm;
    private float g_n_x2, g_n_y2;

    private boolean isCreate = false;
    private String color = "";

    private int mmwidth;
    private int mmheight;
    public StrokeDrawer() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mLinePaint = new Paint();
        mLinePaint.setDither(true);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setStrokeWidth(4f);
        mLinePaint.setColor(Color.BLUE);

        mPath = new Path();
        mScale = 1F;
    }

    public void createDrawer(int width, int height) {
        if (mmwidth == width && mmheight == height){
            //大小没有改变，不切换画布
            return;
        }
        mmwidth = width;
        mmheight = height;
        isCreate = true;
        destroyDrawer();

        mPaint.setStrokeWidth(Math
                .min(width, height) / 600F);
        mBitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_4444);
        mCanvas = new Canvas(mBitmap);
    }


    public boolean isCreate() {
        return isCreate;
    }

    public void clearDrawer() {
        if (mCanvas == null
                || mBitmap == null
                || mBitmap.isRecycled())
            return;
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void destroyDrawer() {
        if (mCanvas != null)
            mCanvas.setBitmap(null);
        if (mBitmap != null &&
                !mBitmap.isRecycled())
            mBitmap.recycle();
        mCanvas = null;
        mBitmap = null;
    }

    public Bitmap getStrokeBitmap() {
        return mBitmap;
    }

    public void setStrokeScale(float scale) {
        mScale = scale;
    }

    public void setStrokeColor(@ColorInt int color) {
        mPaint.setColor(color);
    }
    public void setStrokeColor(String mColor) {
        if (mColor== null ||mColor.isEmpty()){
            color = "#000000";
            mPaint.setColor(Color.parseColor(color));
        } else  {
            if (!mColor.contains("#")){
                mColor = "#"+mColor;
            }
            if (!color.equals(mColor)){
                mPaint.setColor(Color.parseColor(mColor));
            }
        }


    }

    public void drawLine(float x,float y, float x1,float y1){
        if (mCanvas == null
                || mBitmap == null
                || mBitmap.isRecycled())
            return;

        mCanvas.drawLine(x,y,x1,y1,mLinePaint);
    }

    public void drawDot(int index, int lineWidth,int force, float x, float y) {
        if (mCanvas == null
                || mBitmap == null
                || mBitmap.isRecycled())
            return;

//        EventBus.getDefault().post(new Event.StrokeDrawBean(x,y));
        switch (index) {
            case 0:
                g_x0 = x;
                g_y0 = y;
                g_p0 = getPenWidth(lineWidth, force) * mScale;
                mCanvas.drawCircle(g_x0, g_y0, 0.5F, mPaint);
                break;
            case 1:
                g_x1 = x;
                g_y1 = y;
                g_p1 = getPenWidth(lineWidth, force) * mScale;

                g_vx01 = g_x1 - g_x0;
                g_vy01 = g_y1 - g_y0;
                g_norm = (float) Math.sqrt(g_vx01 * g_vx01 + g_vy01 * g_vy01 + 0.0001F) * 2F;
                g_vx01 = g_vx01 / g_norm * g_p0;
                g_vy01 = g_vy01 / g_norm * g_p0;
                g_n_x0 = g_vy01;
                g_n_y0 = -g_vx01;
                break;
            default:
                g_x3 = x;
                g_y3 = y;
                g_p3 = getPenWidth(lineWidth, force) * mScale;

                g_x2 = (g_x1 + g_x3) / 2F;
                g_y2 = (g_y1 + g_y3) / 2F;
                g_p2 = (g_p1 + g_p3) / 2F;
                g_vx21 = g_x1 - g_x2;
                g_vy21 = g_y1 - g_y2;
                g_norm = (float) Math.sqrt(g_vx21 * g_vx21 + g_vy21 * g_vy21 + 0.0001F) * 2F;

                g_vx21 = g_vx21 / g_norm * g_p2;
                g_vy21 = g_vy21 / g_norm * g_p2;
                g_n_x2 = -g_vy21;
                g_n_y2 = g_vx21;

                mPath.rewind();
                mPath.moveTo(g_x0 + g_n_x0, g_y0 + g_n_y0);
                mPath.cubicTo(
                        g_x1 + g_n_x0, g_y1 + g_n_y0,
                        g_x1 + g_n_x2, g_y1 + g_n_y2,
                        g_x2 + g_n_x2, g_y2 + g_n_y2);
                mPath.cubicTo(
                        g_x2 + g_n_x2 - g_vx21, g_y2 + g_n_y2 - g_vy21,
                        g_x2 - g_n_x2 - g_vx21, g_y2 - g_n_y2 - g_vy21,
                        g_x2 - g_n_x2, g_y2 - g_n_y2);
                mPath.cubicTo(
                        g_x1 - g_n_x2, g_y1 - g_n_y2,
                        g_x1 - g_n_x0, g_y1 - g_n_y0,
                        g_x0 - g_n_x0, g_y0 - g_n_y0);
                mPath.cubicTo(
                        g_x0 - g_n_x0 - g_vx01, g_y0 - g_n_y0 - g_vy01,
                        g_x0 + g_n_x0 - g_vx01, g_y0 + g_n_y0 - g_vy01,
                        g_x0 + g_n_x0, g_y0 + g_n_y0);
                mCanvas.drawPath(mPath, mPaint);

                g_x0 = g_x2;
                g_y0 = g_y2;
                g_p0 = g_p2;
                g_x1 = g_x3;
                g_y1 = g_y3;
                g_p1 = g_p3;
                g_vx01 = -g_vx21;
                g_vy01 = -g_vy21;
                g_n_x0 = g_n_x2;
                g_n_y0 = g_n_y2;
                break;
        }

    }


    private float getPenWidth(int penWidth, int force) {
        float mPenWidth = 1.0F;
        if (penWidth == 1) {
            if (force > 70 && force <= 120) {
                mPenWidth = 1.5F;
            } else if (force > 120 && force <= 170) {
                mPenWidth = 2.0F;
            } else if (force > 170 && force <= 210) {
                mPenWidth = 2.5F;
            } else if (force > 210) {
                mPenWidth = 3.0F;
            }
        } else if (penWidth == 2) {
            if (force >= 0 && force <= 30) {
                mPenWidth = 1.5F;
            } else if (force > 30 && force <= 80) {
                mPenWidth = 2.0F;
            } else if (force > 80 && force <= 110) {
                mPenWidth = 3.0F;
            } else if (force > 110 && force <= 170) {
                mPenWidth = 4.0F;
            } else if (force > 170 && force <= 190) {
                mPenWidth = 4.5F;
            } else if (force > 190 && force <= 210) {
                mPenWidth = 5.0F;
            } else if (force > 210) {
                mPenWidth = 5.5F;
            }
        } else if (penWidth == 3) {
            if (force >= 0 && force <= 30) {
                mPenWidth = 3.0F;
            } else if (force > 30 && force <= 80) {
                mPenWidth = 4.0F;
            } else if (force > 80 && force <= 110) {
                mPenWidth = 5.0F;
            } else if (force > 110 && force <= 170) {
                mPenWidth = 6.0F;
            } else if (force > 170 && force <= 190) {
                mPenWidth = 6.5F;
            } else if (force > 190 && force <= 210) {
                mPenWidth = 7.0F;
            } else if (force > 210) {
                mPenWidth = 7.5F;
            }
        } else if (penWidth == 4) {
            if (force >= 0 && force <= 30) {
                mPenWidth = 4.0F;
            } else if (force > 30 && force <= 40) {
                mPenWidth = 5.0F;
            } else if (force > 40 && force <= 50) {
                mPenWidth = 6.0F;
            } else if (force > 50 && force <= 70) {
                mPenWidth = 7.0F;
            } else if (force > 70 && force <= 90) {
                mPenWidth = 8.0F;
            } else if (force > 90 && force <= 110) {
                mPenWidth = 9.0F;
            } else if (force > 110 && force <= 170) {
                mPenWidth = 10.0F;
            } else if (force > 170 && force <= 190) {
                mPenWidth = 10.5F;
            } else if (force > 190 && force <= 210) {
                mPenWidth = 11.0F;
            } else if (force > 210) {
                mPenWidth = 11.5F;
            }
        } else if (penWidth == 5) {
            if (force >= 0 && force <= 30) {
                mPenWidth = 4.0F;
            } else if (force > 30 && force <= 40) {
                mPenWidth = 5.0F;
            } else if (force > 40 && force <= 50) {
                mPenWidth = 7.0F;
            } else if (force > 50 && force <= 70) {
                mPenWidth = 8.0F;
            } else if (force > 70 && force <= 90) {
                mPenWidth = 9.0F;
            } else if (force > 90 && force <= 110) {
                mPenWidth = 10.0F;
            } else if (force > 110 && force <= 170) {
                mPenWidth = 11.0F;
            } else if (force > 170 && force <= 190) {
                mPenWidth = 11.5F;
            } else if (force > 190 && force <= 210) {
                mPenWidth = 12.0F;
            } else if (force > 210) {
                mPenWidth = 12.5F;
            }
        } else if (penWidth == 6) {
            if (force >= 0 && force <= 30) {
                mPenWidth = 4.0F;
            } else if (force > 30 && force <= 40) {
                mPenWidth = 6.0F;
            } else if (force > 40 && force <= 50) {
                mPenWidth = 8.0F;
            } else if (force > 50 && force <= 70) {
                mPenWidth = 9.0F;
            } else if (force > 70 && force <= 90) {
                mPenWidth = 10.0F;
            } else if (force > 90 && force <= 110) {
                mPenWidth = 11.0F;
            } else if (force > 110 && force <= 170) {
                mPenWidth = 12.0F;
            } else if (force > 170 && force <= 190) {
                mPenWidth = 12.5F;
            } else if (force > 190 && force <= 210) {
                mPenWidth = 13.0F;
            } else if (force > 210) {
                mPenWidth = 13.5F;
            }
        } else {
            if (force >= 0 && force <= 30) {
                mPenWidth = 6.0F;
            } else if (force > 30 && force <= 40) {
                mPenWidth = 7.0F;
            } else if (force > 40 && force <= 50) {
                mPenWidth = 8.0F;
            } else if (force > 50 && force <= 70) {
                mPenWidth = 9.0F;
            } else if (force > 70 && force <= 90) {
                mPenWidth = 10.0F;
            } else if (force > 90 && force <= 110) {
                mPenWidth = 11.0F;
            } else if (force > 110 && force <= 170) {
                mPenWidth = 12.0F;
            } else if (force > 170 && force <= 190) {
                mPenWidth = 12.5F;
            } else if (force > 190 && force <= 210) {
                mPenWidth = 13.0F;
            } else if (force > 210) {
                mPenWidth = 13.5F;
            }
        }
        return mPenWidth;
    }


}
