package leduyhung.me.aswitch;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SwitchView extends RelativeLayout {

    private Context mContext;

    private View toggle;

    private int width, height;
    private float widthItem;
    private ArrayList<TextView> arrItem;

    private int numberItem;
    private Drawable backgroundToggle;
    private int textSize;
    private int textColor;

    public SwitchView(Context context) {
        super(context);
        getAttribute(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttribute(context, attrs);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttribute(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.width = getWidth();
        this.height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveToggle(event.getX());
                break;

        }
        return super.onTouchEvent(event);
    }

    private void getAttribute(Context mContext, AttributeSet attrs) {
        this.mContext = mContext;

        if (attrs != null) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.SwitchView);
            backgroundToggle = mContext.getResources().getDrawable(typedArray.getInteger(R.styleable.SwitchView_switch_view_background_toggle, 0));
            textSize = typedArray.getDimensionPixelOffset(R.styleable.SwitchView_switch_view_item_text_size, 0);
            textColor = typedArray.getColor(R.styleable.SwitchView_switch_view_item_text_color, 0);
            typedArray.recycle();
        }
    }

    private void initLayout() {
        arrItem = new ArrayList<>();
        this.widthItem = width / numberItem;
        LayoutParams params = new LayoutParams((int) widthItem, height);
        params.addRule(ALIGN_PARENT_START);
        toggle = new View(mContext);
        toggle.setBackground(backgroundToggle);
        toggle.setLayoutParams(params);
        addView(toggle);
        for (int i = 0; i < numberItem; i++) {
            params = new LayoutParams((int) widthItem, height);
            TextView item = new TextView(mContext);
            float x = widthItem * i;
            item.setId(i);
            item.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            item.setTextColor(textColor);
            item.setX(x);
            item.setGravity(Gravity.CENTER);
            item.setLayoutParams(params);
            arrItem.add(item);
            addView(item);
        }
    }

    private void moveToggle(float x) {
        float pointItem = widthItem;
        int levelPoint = (int) x / (int) pointItem;
        animationToggle(levelPoint * pointItem);
    }

    private void animationToggle(float toX) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(toggle, View.TRANSLATION_X, toX);
        animator.setDuration(150);
        animator.start();
    }

    public void init(int numberItem) {
        this.numberItem = numberItem;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                initLayout();
            }
        });
    }

    public void setTitleItem(int position, String title) {
        if (title != null)
            arrItem.get(position).setText(title);
    }
}
