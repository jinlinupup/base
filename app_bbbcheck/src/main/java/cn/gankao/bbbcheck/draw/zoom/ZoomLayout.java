package cn.gankao.bbbcheck.draw.zoom;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.gankao.common.R;

/**
 * Created by xuliwen on 2019/10/12
 * <p>
 * 简介：与业务无关的缩放 ViewGroup，只能有一个直接子 View
 * <p>
 * 实现过程：结合自身业务需要，参考了 LargeImageView、ScrollView 来实现
 * <p>
 * 作用：
 * 1、单指、多指滑动及惯性滑动
 * 2、双击缩放
 * 3、多指缩放
 * <p>
 * 注意点：
 * 1、如果子 View 宽、高小于 ZoomLayout，会将子 View 在宽、高方向上居中
 */
public class ZoomLayout extends LinearLayout {


    private static final String TAG = "ZoomLayout";

    private float mDoubleClickZoom = 1;
    private float mMinZoom = 1;
    private float mMaxZoom = 1;
    public float mCurrentZoom = 1;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private boolean mScrollBegin; // 是否已经开始滑动

    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    private OverScroller mOverScroller;
    private ScaleHelper mScaleHelper;
    private AccelerateInterpolator mAccelerateInterpolator;
    private DecelerateInterpolator mDecelerateInterpolator;
    private ZoomLayoutGestureListener mZoomLayoutGestureListener;
    private int mLastChildHeight;
    private int mLastChildWidth;
    private int mLastHeight;
    private int mLastWidth;
    private int mLastCenterX;
    private int mLastCenterY;
    private boolean mNeedReScale;

    //add
    private boolean scroll = true;  //默认可以滑动
    public boolean isBiji = false;
    public boolean isHavePrePage = true;
    public boolean isHaveNextPage = true;

    private Animation translateLeftAnimation;//旋转动画（转到反面）
    private Animation translateRightAnimation;//反转动画（转回正面）
    private int verticalMinDistance = 200;
    private int minVelocity = 0;

    public ZoomLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ZoomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZoomLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        initAnim();
        mScaleDetector = new ScaleGestureDetector(context, mSimpleOnScaleGestureListener);
        mGestureDetector = new GestureDetector(context, mSimpleOnGestureListener);
        mOverScroller = new OverScroller(getContext());
        mScaleHelper = new ScaleHelper();
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        setWillNotDraw(false);
        
    }

    private ScaleGestureDetector.SimpleOnScaleGestureListener mSimpleOnScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (!isEnabled()) {
                return false;
            }
           
            float newScale;
            newScale = mCurrentZoom * detector.getScaleFactor();
            if (newScale > mMaxZoom) {
                newScale = mMaxZoom;
            } else if (newScale < mMinZoom) {
                newScale = mMinZoom;
            }
            setScale(newScale, (int) detector.getFocusX(), (int) detector.getFocusY());
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
           
            if (mZoomLayoutGestureListener != null) {
                mZoomLayoutGestureListener.onScaleGestureBegin();
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    };

    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {


        @Override
        public boolean onDown(MotionEvent e) {
            
            if (!mOverScroller.isFinished()) {
                mOverScroller.abortAnimation();
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            
            if (mZoomLayoutGestureListener != null) {
                mZoomLayoutGestureListener.onSingleTap(e.getX() * mCurrentZoom + officeX, e.getY() * mCurrentZoom + officeY);
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            float newScale;
            if (mCurrentZoom < 1) {
                newScale = 1;
            } else if (mCurrentZoom < mDoubleClickZoom) {
                newScale = mDoubleClickZoom;
            } else {
                newScale = 1;
            }
            smoothScale(newScale, (int) e.getX(), (int) e.getY());
            if (mZoomLayoutGestureListener != null) {
                mZoomLayoutGestureListener.onDoubleTap();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            actionDownTimestamp = 0;
            if (!isEnabled()) {
                return false;
            }
            if (!scroll) {
                return false;
            }

            if (!mScrollBegin) {
                mScrollBegin = true;
                if (mZoomLayoutGestureListener != null) {
                    mZoomLayoutGestureListener.onScrollBegin();
                }
            }
            processScroll((int) distanceX, (int) distanceY, getScrollRangeX(), getScrollRangeY());
            return true;
        }

        /**
         *
         * @param velocityX 滑动的速度 = 滑动的距离(滑动的起点 - 滑动的终点) / 滑动的时长，所以向上滑是负的，向下滑是正的
         * @param velocityY 同上
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (!isEnabled()) {
                return false;
            }

            if (!scroll) {
                return false;
            }


            fling((int) -velocityX, (int) -velocityY);
            return true;
        }
    };


    private boolean fling(int velocityX, int velocityY) {
        if (Math.abs(velocityX) < mMinimumVelocity) {
            velocityX = 0;
        }
        if (Math.abs(velocityY) < mMinimumVelocity) {
            velocityY = 0;
        }
        final int scrollY = getScrollY();
        final int scrollX = getScrollX();
        // 只有在能够滚动的时候，才需要处理 Fling
        final boolean canFlingX = scrollX > 0 && scrollX < getScrollRangeX();
        final boolean canFlingY = scrollY > 0 && scrollY < getScrollRangeY();
        boolean canFling = canFlingY || canFlingX;
        if (canFling) {
            // 下面两行代码的作用是将 Fling 速度限制在  [-mMaximumVelocity, mMaximumVelocity] 之间
            velocityX = Math.max(-mMaximumVelocity, Math.min(velocityX, mMaximumVelocity));
            velocityY = Math.max(-mMaximumVelocity, Math.min(velocityY, mMaximumVelocity));
            int height = getHeight() - getPaddingBottom() - getPaddingTop();
            int width = getWidth() - getPaddingRight() - getPaddingLeft();
            int bottom = getContentHeight();
            int right = getContentWidth();
            // getScrollX(), getScrollY() 是 fling 开始的位置
            // velocityX, velocityY 滚动速度
            // 0, Math.max(0, right - width), 0, Math.max(0, bottom - height)。是滚动的范围
            // 0, 0 是可以往外滚动的距离，这里不支持往外滚动，直接传 0
            mOverScroller.fling(getScrollX(), getScrollY(), velocityX, velocityY, 0, Math.max(0, right - width), 0,
                    Math.max(0, bottom - height), 0, 0);
            notifyInvalidate();
            return true;
        }
        return false;
    }

    public void smoothScale(float newScale, int centerX, int centerY) {
        if (mCurrentZoom > newScale) {
            if (mAccelerateInterpolator == null) {
                mAccelerateInterpolator = new AccelerateInterpolator();
            }
            mScaleHelper.startScale(mCurrentZoom, newScale, centerX, centerY, mAccelerateInterpolator);
        } else {
            if (mDecelerateInterpolator == null) {
                mDecelerateInterpolator = new DecelerateInterpolator();
            }
            mScaleHelper.startScale(mCurrentZoom, newScale, centerX, centerY, mDecelerateInterpolator);
        }
        notifyInvalidate();
    }

    private void notifyInvalidate() {
        // 效果和 invalidate 一样，但是会使得动画更平滑
        ViewCompat.postInvalidateOnAnimation(this);
    }


    public void setScale(float scale, int centerX, int centerY) {
        mLastCenterX = centerX;
        mLastCenterY = centerY;
        float preScale = mCurrentZoom;
        mCurrentZoom = scale;
        int sX = getScrollX();
        int sY = getScrollY();
        int dx = (int) ((sX + centerX) * (scale / preScale - 1));
        int dy = (int) ((sY + centerY) * (scale / preScale - 1));
        if (getScrollRangeX() < 0) {
            child().setPivotX(child().getWidth() / 2);
            child().setTranslationX(0);
        } else {
            child().setPivotX(0);
            int willTranslateX = -(child().getLeft());
            child().setTranslationX(willTranslateX);
        }
        if (getScrollRangeY() < 0) {
            child().setPivotY(child().getHeight() / 2);
            child().setTranslationY(0);
        } else {
            int willTranslateY = -(child().getTop());
            child().setTranslationY(willTranslateY);
            child().setPivotY(0);
        }
        child().setScaleX(mCurrentZoom);
        child().setScaleY(mCurrentZoom);
        processScroll(dx, dy, getScrollRangeX(), getScrollRangeY());
        notifyInvalidate();
    }


    private void processScroll(int deltaX, int deltaY,
                               int scrollRangeX, int scrollRangeY) {
        int oldScrollX = getScrollX();
        int oldScrollY = getScrollY();
        int newScrollX = oldScrollX + deltaX;
        int newScrollY = oldScrollY + deltaY;
        final int left = 0;
        final int right = scrollRangeX;
        final int top = 0;
        final int bottom = scrollRangeY;

        if (newScrollX > right) {
            newScrollX = right;
        } else if (newScrollX < left) {
            newScrollX = left;
        }

        if (newScrollY > bottom) {
            newScrollY = bottom;
        } else if (newScrollY < top) {
            newScrollY = top;
        }
        if (newScrollX < 0) {
            newScrollX = 0;
        }
        if (newScrollY < 0) {
            newScrollY = 0;
        }
        Log.e(TAG, "newScrollX = " + newScrollX + " ,newScrollY = " + newScrollY + " ,scale = " + mCurrentZoom);
        officeX = newScrollX;
        officeY = newScrollX;
        scrollTo(newScrollX, newScrollY);
        if (mZoomLayoutGestureListener != null) {
            mZoomLayoutGestureListener.onChangeScale(newScrollX, newScrollY);
        }

    }


    private int getScrollRangeX() {
        final int contentWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        return (getContentWidth() - contentWidth);
    }

    private int getContentWidth() {
        return (int) (child().getWidth() * mCurrentZoom);
    }

    public int getScrollRangeY() {
        final int contentHeight = getHeight() - getPaddingBottom() - getPaddingTop();
        return getContentHeight() - contentHeight;
    }

    private int getContentHeight() {
        return (int) (child().getHeight() * mCurrentZoom);
    }

    private View child() {
        return getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mNeedReScale) {
            // 需要重新刷新，因为宽高已经发生变化
            setScale(mCurrentZoom, mLastCenterX, mLastCenterY);
            mNeedReScale = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        child().setClickable(true);
        if (child().getHeight() < getHeight() || child().getWidth() < getWidth()) {
            setGravity(Gravity.CENTER);
        } else {
            setGravity(Gravity.TOP);
        }
        if (mLastChildWidth != child().getWidth() || mLastChildHeight != child().getHeight() || mLastWidth != getWidth()
                || mLastHeight != getHeight()) {
            // 宽高变化后，记录需要重新刷新，放在下次 onLayout 处理，避免 View 的一些配置：比如 getTop() 没有初始化好
            // 下次放在 onLayout 处理的原因是 setGravity 会在 onLayout 确定完位置，这时候去 setScale 导致位置的变化就不会导致用户看到
            // 闪一下的问题
            mNeedReScale = true;
        }
        mLastChildWidth = child().getWidth();
        mLastChildHeight = child().getHeight();
        mLastWidth = child().getWidth();
        mLastHeight = getHeight();
        if (mNeedReScale) {
            notifyInvalidate();
        }
    }

    /**
     * 通常配合 Scroller、OverScroller 实现平滑滚动。如 Fling 的时候进行平滑滚动。
     * Scroller、OverScroller 负责计算一段时间内的 ScrollX、ScrollY 的平滑变化
     * 然后调用 ViewCompat.postInvalidateOnAnimation(this); 之后就可以在
     * computeScroll() 不断去获取 ScrollX、ScrollY 的变化了，再通过 ScrollTo 设置给 View
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScaleHelper.computeScrollOffset()) {
            setScale(mScaleHelper.getCurScale(), mScaleHelper.getStartX(), mScaleHelper.getStartY());
        }
        if (mOverScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mOverScroller.getCurrX();
            int y = mOverScroller.getCurrY();
            if (oldX != x || oldY != y) {
                final int rangeY = getScrollRangeY();
                final int rangeX = getScrollRangeX();
                processScroll(x - oldX, y - oldY, rangeX, rangeY);
            }
            if (!mOverScroller.isFinished()) {
                notifyInvalidate();
            }
        }
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MotionEvent.ACTION_DOWN) {
                long interval = System.currentTimeMillis() - actionDownTimestamp;
                if (actionDownTimestamp != 0 && interval >= 1000) {
                    isLongPress = true;
                }
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        calculateFocusCoordinates(ev);
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // 最后一根手指抬起的时候，重置 mScrollBegin 为 false
            mScrollBegin = false;
            mHandler.removeMessages(MotionEvent.ACTION_DOWN);
            if (isLongPress) {
                isLongPress = false;
            } else {
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            actionDownTimestamp = System.currentTimeMillis();
            mHandler.sendEmptyMessageDelayed(MotionEvent.ACTION_DOWN, 1000);
        }

        if (isLongPress) {
            onLongPressAndScrollInterceptor(ev);
            mLastFocusX = focusX;
            mLastFocusY = focusY;
            return true;
        } else {
            mGestureDetector.onTouchEvent(ev);
            mScaleDetector.onTouchEvent(ev);
            mLastFocusX = focusX;
            mLastFocusY = focusY;
            return super.dispatchTouchEvent(ev);
        }
    }

    private boolean isLongPress;
    private float mLastFocusX;
    private float mLastFocusY;
    private float mDownFocusX;
    private float mDownFocusY;
    private float focusX;
    private float focusY;
    private long actionDownTimestamp = 0;

    private void calculateFocusCoordinates(MotionEvent ev) {
        final int action = ev.getAction();
        final boolean pointerUp =
                (action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP;
        final int skipIndex = pointerUp ? ev.getActionIndex() : -1;

        float sumX = 0, sumY = 0;
        final int count = ev.getPointerCount();
        for (int i = 0; i < count; i++) {
            if (skipIndex == i) continue;
            sumX += ev.getX(i);
            sumY += ev.getY(i);
        }
        final int div = pointerUp ? count - 1 : count;
        focusX = sumX / div;
        focusY = sumY / div;
    }

    private void onLongPressAndScrollInterceptor(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            actionDownTimestamp = 0;
            if (isLongPress) {
                isLongPress = false;
            } else {
            }
        }

        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                mDownFocusX = mLastFocusX = focusX;
                mDownFocusY = mLastFocusY = focusY;
                break;
            case MotionEvent.ACTION_MOVE:
                final float scrollX = mLastFocusX - focusX;
                final float scrollY = mLastFocusY - focusY;

                final int deltaX = (int) (focusX - mDownFocusX);
                final int deltaY = (int) (focusY - mDownFocusY);
                int distance = (deltaX * deltaX) + (deltaY * deltaY);

                if (distance > 0) {
                    int scrollRangeX = getScrollRangeX();
                    int scrollRangeY = getScrollRangeY();
                    int oldScrollX = getScrollX();
                    int oldScrollY = getScrollY();
                    int newScrollX = oldScrollX + (int) scrollX;
                    int newScrollY = oldScrollY + (int) scrollY;
                    final int left = 0;
                    final int right = scrollRangeX;
                    final int top = 0;
                    final int bottom = scrollRangeY;

                    if (newScrollX > right) {
                        newScrollX = right;
                    } else if (newScrollX < left) {
                        newScrollX = left;
                    }

                    if (newScrollY > bottom) {
                        newScrollY = bottom;
                    } else if (newScrollY < top) {
                        newScrollY = top;
                    }
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    }
                    if (newScrollY < 0) {
                        newScrollY = 0;
                    }
                    scrollTo(newScrollX, newScrollY);
                }
                break;
        }
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
                                           int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin
                        + widthUsed, lp.width);
        final int usedTotal = getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin +
                heightUsed;
        final int childHeightMeasureSpec;
        if (lp.height == WRAP_CONTENT) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    Math.max(0, MeasureSpec.getSize(parentHeightMeasureSpec) - usedTotal),
                    MeasureSpec.UNSPECIFIED);
        } else {
            childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                    getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin
                            + heightUsed, lp.height);
        }
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }


    /**
     * 是否可以在水平方向上滚动
     * 举例: ViewPager 通过这个方法判断子 View 是否可以水平滚动，从而解决滑动冲突
     */
    @Override
    public boolean canScrollHorizontally(int direction) {
        if (direction > 0) {
            return getScrollX() < getScrollRangeX();
        } else {
            return getScrollX() > 0 && getScrollRangeX() > 0;
        }
    }

    /**
     * 是否可以在竖直方向上滚动
     * 举例: ViewPager 通过这个方法判断子 View 是否可以竖直滚动，从而解决滑动冲突
     */
    @Override
    public boolean canScrollVertically(int direction) {
        if (direction > 0) {
            return getScrollY() < getScrollRangeY();
        } else {
            return getScrollY() > 0 && getScrollRangeY() > 0;
        }
    }

    public void setZoomLayoutGestureListener(ZoomLayoutGestureListener zoomLayoutGestureListener) {
        mZoomLayoutGestureListener = zoomLayoutGestureListener;
    }

    public interface ZoomLayoutGestureListener {
        void onScrollBegin();

        void onScaleGestureBegin();

        void onDoubleTap();

        void onSingleTap(float x, float y);

        void onChangeScale(float x, float y);

        void onFling(int type);

        void onAnimationEnd(int type);
    }

    //add
    //传入true可滑动，传入false不可滑动
    public boolean isScroll() {
        return scroll;
    }

    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }

    public int officeX;
    public int officeY;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.w(TAG, "l :" + l + "  t :" + t + "  oldl:" + oldl + "  oldt:" + oldt);
        officeX = l;
        officeY = t;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!scroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void initAnim() {
        translateLeftAnimation = new TranslateAnimation(0, -3000, 0, 0);//设置平移的起点和终点
        translateLeftAnimation.setDuration(600);//动画持续的时间为1s
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        translateLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mZoomLayoutGestureListener.onFling(2);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mZoomLayoutGestureListener.onAnimationEnd(2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        translateRightAnimation = new TranslateAnimation(0, +3000, 0, 0);//设置平移的起点和终点
        translateRightAnimation.setDuration(600);//动画持续的时间为1s
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        translateRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mZoomLayoutGestureListener.onFling(1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mZoomLayoutGestureListener.onAnimationEnd(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private long last2ClickTime;

    public boolean isFast2Click() {
        long time = System.currentTimeMillis();
        if (time - last2ClickTime < 1000) {
            return true;
        }
        last2ClickTime = time;
        return false;
    }
}