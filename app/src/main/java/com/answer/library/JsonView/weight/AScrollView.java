package com.answer.library.JsonView.weight;
import android.widget.ScrollView;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.view.View;
import android.content.Context;

/**
 * Author: Answer.Dev.
 * Powered By XiaoMi Corporation.
 **/

public class AScrollView extends ScrollView {
    
    private View inner;
    private float b = 0.0f;
    private float c = 0.0f;
    private float d = 0.0f;
    private float e = 0.0f;
    private float f = 0.0f;
    private boolean g = false;
    private boolean h = false;
    private Rect i = new Rect();

    public AScrollView(Context context){
        super(context);
    }
    

    @Override
    public void addView(View child) {
        super.addView(child);
        inner = child;
    }
    
    private void a(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int i2 = 0;
        if (action == 1) {
            this.h = false;
            if (isNeedAnimation()) {
                animation();
            }
            b();
        } else if (action == 2) {
            float f2 = this.f;
            float y = motionEvent.getY();
            int i3 = (int) (y - f2);
            this.f = y;
            if (this.h) {
                i2 = i3;
            }
            this.h = true;
            if (a()) {
                if (this.i.isEmpty()) {
                    this.i.set(this.inner.getLeft(), this.inner.getTop(), this.inner.getRight(), this.inner.getBottom());
                }
                View view = this.inner;
                int i4 = i2 / 4;
                view.layout(view.getLeft(), this.inner.getTop() + i4, this.inner.getRight(), this.inner.getBottom() + i4);
            }
        }
    }

    private boolean a() {
        int measuredHeight = this.inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == measuredHeight || scrollY == 0;
    }

    private void b() {
        this.d = 0.0f;
        this.e = 0.0f;
        this.g = false;
    }

    public void animation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) this.inner.getTop(), (float) this.i.top);
        translateAnimation.setDuration(200);
        this.inner.startAnimation(translateAnimation);
        this.inner.layout(this.i.left, this.i.top, this.i.right, this.i.bottom);
        this.i.setEmpty();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.b = motionEvent.getX();
        this.c = motionEvent.getY();
        if (motionEvent.getAction() == 2) {
            float f2 = this.b - this.d;
            float f3 = this.c - this.e;
            if (Math.abs(f2) < Math.abs(f3) && Math.abs(f3) > 20.0f) {
                this.g = true;
            }
        }
        this.d = this.b;
        this.e = this.c;
        if (this.g && this.inner != null) {
            a(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean isNeedAnimation() {
        return !this.i.isEmpty();
    }
    
    
}
