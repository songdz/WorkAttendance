package com.songdz.fielddatacheck.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 一个像java swing的JPanel控件一样可以带标题边框的布局，可以指定标题位置、颜色、字体大小。
 * 另外还可以控制边框大小和边框的颜色。但是，子控件指定垂直布局适应的时候，子控件看起来并不在布局中间，
 * 因为标题高度占用了布局的一部分垂直空间，使用时子控件需要指定Margin Top，否则可能和标题重叠。
 *
 * This is a layout with title border, you can set a title just like Java-Swing JPanel.
 * and you can control the position, the color and the size of the title. In addition,
 * the border's color and size are always controlled.
 *
 * !FIXME: The title has its own height, so when the children's gravity set as {@link android.view.Gravity#CENTER}
 * or {@link android.view.Gravity#CENTER_VERTICAL} do not work well.
 *
 * @date 2013/09/24
 * @author Wison
 */
public class TitleBorderLayout extends LinearLayout {

    /**  默认情况下标题在总长度的1/10处显示  */
    private static float DEFAULT_TITLE_POSITION_SCALE = 0.1f;
    public static int DEFAULT_BORDER_SIZE = 1;
    public static int DEFAULT_BORDER_COLOR = Color.GRAY;
    public static int DEAFULT_TITLE_COLOR = Color.BLACK;

    /** 边框面板的高度 */
    private int mBorderPaneHeight ;

    private Paint mBorderPaint;
    private float mBorderSize;

    private TextPaint mTextPaint;
    private CharSequence mTitle;
    private int mTitlePosition;

    public TitleBorderLayout(Context context) {
        this(context, null);
    }

    /**
     * Construct a new TitleBorderLayout with default style, overriding specific style
     * attributes as requested.
     * @param context
     * @param attrs
     */
    public TitleBorderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        Resources res = getResources();
        mTextPaint.density = res.getDisplayMetrics().density;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBorderLayout);

        mTitle = a.getText(R.styleable.TitleBorderLayout_titleText);
        int titleColor = a.getColor(R.styleable.TitleBorderLayout_titleTextColor, DEAFULT_TITLE_COLOR);
        mTextPaint.setColor(titleColor);

        float titleTextSize = a.getDimension(R.styleable.TitleBorderLayout_titleTextSize, 0);
        if (titleTextSize > 0) {
            mTextPaint.setTextSize(titleTextSize);
        }

        mTitlePosition = a.getDimensionPixelSize(R.styleable.TitleBorderLayout_titlePosition, -1);

        mBorderSize = a.getDimensionPixelSize(R.styleable.TitleBorderLayout_borderSize, DEFAULT_BORDER_SIZE);

        int borderColor = a.getColor(R.styleable.TitleBorderLayout_borderColor, DEFAULT_BORDER_COLOR);
        mBorderPaint.setColor(borderColor);

        a.recycle();
    }

    /**
     * Get the color of border.
     * @return
     */
    public int getBorderColor() {
        return mBorderPaint.getColor();
    }

    /**
     * Set the color of border.
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        mBorderPaint.setColor(borderColor);
        requestLayout();
    }

    /**
     * Get the size of border.
     * @return
     */
    public float getBorderSize() {
        return mBorderSize;
    }

    /**
     * Set the size of border.
     * @param borderSize
     */
    public void setBorderSize(float borderSize) {
        mBorderSize = borderSize;
        requestLayout();
    }

    /**
     * Get the color of title.
     * @return
     */
    public int getTitleColor() {
        return mTextPaint.getColor();
    }

    /**
     * Set the color of title.
     * @param titleColor
     */
    public void setTitleColor(int titleColor) {
        mTextPaint.setColor(titleColor);
        requestLayout();
    }

    /**
     * Get the size of title.
     * @return
     */
    public float getTitleTextSize() {
        return mTextPaint.getTextSize();
    }

    /**
     * Set the size of title.
     * @param titleTextSize
     */
    public void setTitleTextSize(float titleTextSize) {
        mTextPaint.setTextSize(titleTextSize);
        requestLayout();
    }

    /**
     * Get the title.
     * @return
     */
    public CharSequence getTitle() {
        return mTitle;
    }

    /**
     * Set the title which will be shown on the top of border pane.
     * @param title
     */
    public void setTitle(CharSequence title) {
        mTitle = title;
        requestLayout();
    }

    /**
     * Get the position of title.
     * @return
     */
    public int getTitlePosition() {
        return mTitlePosition;
    }

    /**
     * Set the position of title where the paint will start to draw.
     * @param titlePosition
     */
    public void setTitlePosition(int titlePosition) {
        mTitlePosition = titlePosition;
        requestLayout();
    }

    /**
     * Get the height of border pane, it's different from the layout height!
     * @return
     */
    public int getBorderPaneHeight() {
        return mBorderPaneHeight;
    }

    /**
     * Draw the title border
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        final float titleHeight =  fm.descent - fm.ascent;

        final CharSequence titleText = (mTitle == null) ? "" : mTitle;
        final float titleWidth = Layout.getDesiredWidth(titleText, mTextPaint);

        final int width = getWidth();
        final int height = getHeight();

        if (mTitlePosition <= 0 || mTitlePosition + titleWidth > width) {
            mTitlePosition = (int) (DEFAULT_TITLE_POSITION_SCALE * width);
        }

        final float topBorderStartY = titleHeight / 3f - mBorderSize / 2;

        mBorderPaneHeight = (int) Math.ceil(height - topBorderStartY);
	    /*  画标题边框  */
        // 上
        canvas.drawRect(0, topBorderStartY, mTitlePosition, topBorderStartY + mBorderSize, mBorderPaint);
        canvas.drawText(titleText.toString(), mTitlePosition, titleHeight / 3 * 2f, mTextPaint); // 标题
        canvas.drawRect(mTitlePosition + titleWidth, topBorderStartY, width, topBorderStartY + mBorderSize, mBorderPaint);
        // 左
        canvas.drawRect(0, topBorderStartY, mBorderSize, height, mBorderPaint);
        // 右
        canvas.drawRect(width - mBorderSize, topBorderStartY, width, height, mBorderPaint);
        // 下
        canvas.drawRect(0, height - mBorderSize, width, height, mBorderPaint);
    }

}

