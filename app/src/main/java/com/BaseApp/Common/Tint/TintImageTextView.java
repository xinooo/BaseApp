package com.BaseApp.Common.Tint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BaseApp.R;

/**
 * 根据不同状态给文字和图片设置不同颜色值的LinearLayout控件
 */
public class TintImageTextView extends LinearLayout {

    private Context mContext;
    /**
     * 默认背景色
     */
    private int mBgNormalColor;
    /**
     * 选中背景色
     */
    private int mBgSelectColor;
    /**
     * 图片控件
     */
    private TintImageView mImageView;
    /**
     * 文字控件
     */
    private TintTextView mTextView;
    /**
     * 图片默认颜色
     */
    private int mImageNormalColor;
    /**
     * 图片选中颜色
     */
    private int mImageSelectColor;
    /**
     * 文字默认颜色
     */
    private int mTextNormalColor;
    /**
     * 文字选中颜色
     */
    private int mTextSelectColor;
    /**
     * 文字大小
     */
    private float mTextSize;
    /**
     * 文字和图片间距
     */
    private float mPadding;
    /**
     * 1,2,3,4分别表示上下左右
     */
    private int mImageDirection;
    /**
     * 图片资源
     */
    private int mSrc;
    /**
     * 文字
     */
    private String mTitle;
    /**
     * 类型，是需要点击效果还是选中效果
     */
    private int mType = TintTextView.CLICK_TYPE;

    /**
     * 图片是否渲染
     */
    private boolean rendering = true;

    public TintImageTextView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public TintImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        obtainAttributeSet(context, attrs);
        init();
    }

    public TintImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        obtainAttributeSet(context, attrs);
        init();
    }

    /**
     * 一些属性设置
     *
     * @param c
     * @param attrs
     */
    private void obtainAttributeSet(Context c, AttributeSet attrs) {
        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.TintView);

        mType = a.getInt(R.styleable.TintView_click_type, TintTextView.CLICK_TYPE);
        mBgNormalColor = a.getColor(R.styleable.TintView_bg_normal_color, 0);
        mBgSelectColor = a.getColor(R.styleable.TintView_bg_select_color, 0);
        mImageNormalColor = a.getColor(R.styleable.TintView_image_normal_color, 0);
        mImageSelectColor = a.getColor(R.styleable.TintView_image_select_color, 0);
        mTextNormalColor = a.getColor(R.styleable.TintView_text_normal_color, 0);
        mTextSelectColor = a.getColor(R.styleable.TintView_text_select_color, 0);
        mPadding = a.getDimension(R.styleable.TintView_image_padding, 0);
        mTextSize = a.getDimensionPixelSize(R.styleable.TintView_text_size, 0);
        mImageDirection = a.getInteger(R.styleable.TintView_image_direction, 1);
        mSrc = a.getResourceId(R.styleable.TintView_image_src, 0);
        mTitle = a.getString(R.styleable.TintView_text_title);
        rendering = a.getBoolean(R.styleable.TintView_image_rendering, true);
        a.recycle();
    }

    private void init() {
        if (mBgNormalColor != 0) {
            setBackgroundColor(mBgNormalColor);
        }
        setGravity(Gravity.CENTER);
        mImageView = new TintImageView(mContext);
        mImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mImageView.setColorValue(rendering ? mImageNormalColor : 0, rendering ? mImageSelectColor : 0);
        mImageView.setDuplicateParentStateEnabled(true);
        if (mSrc != 0) {
            mImageView.setImageResource(mSrc);
        }
        mImageView.setClickType(mType);
        mTextView = new TintTextView(mContext);
        mTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mTextView.setColorValue(mTextNormalColor, mTextSelectColor);
        mTextView.setDuplicateParentStateEnabled(true);
        mTextView.setTextColor(mTextNormalColor);
        mTextView.setTextSize(mTextSize);
        mTextView.setText(mTitle);
        mTextView.setClickType(mType);

        //1,2,3,4，上下左右
        if (mImageDirection == 1) {
            setOrientation(LinearLayout.VERTICAL);
            addView(mImageView);
            mTextView.setPadding(0, (int) mPadding, 0, 0);
            addView(mTextView);
        } else if (mImageDirection == 2) {
            setOrientation(LinearLayout.VERTICAL);
            mTextView.setPadding(0, 0, 0, (int) mPadding);
            addView(mTextView);
            addView(mImageView);
        } else if (mImageDirection == 3) {
            setOrientation(LinearLayout.HORIZONTAL);
            addView(mImageView);
            mTextView.setPadding((int) mPadding, 0, 0, 0);
            addView(mTextView);
        } else if (mImageDirection == 4) {
            setOrientation(LinearLayout.HORIZONTAL);
            mTextView.setPadding(0, 0, (int) mPadding, 0);
            addView(mTextView);
            addView(mImageView);
        }

    }


    /**
     * 设置图片选中和未选中状态的颜色值
     *
     * @param normal
     * @param select
     */
    public void setColorValue(int normal, int select) {
        mImageView.setColorValue(normal, select);
        mTextView.setColorValue(normal, select);
    }

    /**
     * 返回图片对象
     *
     * @return
     */
    public ImageView getImageView() {
        return mImageView;
    }

    public TintTextView getTextView() {
        return mTextView;
    }

    /**
     * 设置title文案
     *
     * @param id
     */
    public void setText(int id) {
        mTextView.setText(getContext().getResources().getString(id));
    }

    /**
     * 设置title文案
     *
     * @param str
     */
    public void setText(String str) {
        mTextView.setText(str);
    }

    /**
     * 设置图片资源
     *
     * @param id
     */
    public void setImageResource(int id) {
        if (id == 0) {
            mImageView.setVisibility(GONE);
        } else {
            mImageView.setImageResource(id);
            mImageView.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置文字控件显示隐藏状态
     *
     * @param visible
     */
    public void setTextVisible(int visible) {
        mTextView.setVisibility(visible);
    }

    /**
     * 设置图片控件显示隐藏状态
     *
     * @param visible
     */
    public void setImageVisible(int visible) {
        mImageView.setVisibility(visible);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        mImageView.setPressed(pressed);
        mTextView.setPressed(pressed);
        if (pressed) {
            if (mBgSelectColor != 0) {
                setBackgroundColor(mBgSelectColor);
            }
        } else {
            if (!isSelected()) {
                if (mBgNormalColor != 0) {
                    setBackgroundColor(mBgNormalColor);
                }
            }
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mImageView.setSelected(selected);
        mTextView.setSelected(selected);
        if (selected) {
            if (mBgSelectColor != 0) {
                setBackgroundColor(mBgSelectColor);
            }
        } else {
            if (mBgNormalColor != 0) {
                setBackgroundColor(mBgNormalColor);
            }
        }
    }

    public void onNightMode(boolean isNight) {
        mImageView.onNightMode(isNight);
        mTextView.onNightMode(isNight);
    }

    public void setTypeface(Typeface typeface) {
        if (mTextView != null) {
            mTextView.setTypeface(typeface);

        }
    }

    public void setTextSize(float size) {
        if (mTextView != null) {
            mTextView.setTextSize(size);
        }
    }
}
