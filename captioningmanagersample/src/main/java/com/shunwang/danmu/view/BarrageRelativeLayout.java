/*
 *  Copyright (C) 2017 The  sxxxxxxxxxu's  Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.shunwang.danmu.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.Random;

/**
 * Fun:该弹幕随机批量展示
 * Created by sxx.xu on 5/18/2017.
 */

public class BarrageRelativeLayout extends RelativeLayout {

    private static final long BARRAGE_GAP_MIN_DURATION = 1000;//两个弹幕的最小间隔时间
    private static final long BARRAGE_GAP_MAX_DURATION = 2000;//两个弹幕的最大间隔时间
    public static int RANDOM_SHOW = 0x0a1;
    public static int SEQ_SHOW = 0x0a2;
    private Context mContext;
    private int maxSpeed = 10000;//速度，ms
    private int minSpeed = 5000;//速度，ms
    private int maxSize = 30;//文字大小，dp
    private int minSize = 15;//文字大小，dp
    private int totalHeight = 0;
    private int lineHeight = 0;//每一行弹幕的高度
    private int totalLine = 0;//弹幕的行数
    private Random random = new Random(System.currentTimeMillis());
    private LinkedList<String> texts = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == RANDOM_SHOW) {
                String text = texts.get(random.nextInt(texts.size()));
                BarrageTextItem item = new BarrageTextItem(text);
                showBarrageItem(item);

                int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION)
                        * Math.random());
                this.sendEmptyMessageDelayed(RANDOM_SHOW, duration);
            }
        }
    };

    public BarrageRelativeLayout(Context context) {
        this(context, null);

    }

    public BarrageRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BarrageRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        texts = new LinkedList<>();
    }

    public void show(int type) {
        int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION)
                * Math.random());
        mHandler.sendEmptyMessageDelayed(type, duration);
    }

    public void setBarrageTexts(LinkedList<String> texts) {
        this.texts = texts;
    }

    //头部第一个位置追加，最新的。
    public void addBarrageText(String text) {
        this.texts.add(0, text);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        totalHeight = getMeasuredHeight();
        lineHeight = getLineHeight();
        totalLine = totalHeight / lineHeight;
    }

    private void showBarrageItem(final BarrageTextItem item) {
        int leftMangin = this.getRight() - this.getLeft() - this.getPaddingLeft();
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.topMargin = item.verticalPos;
        this.addView(item.textView, params);
        Animation anim = generateTranslateAnim(item, leftMangin);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                item.textView.clearAnimation();
                BarrageRelativeLayout.this.removeView(item.textView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        item.textView.startAnimation(anim);
    }

    private TranslateAnimation generateTranslateAnim(BarrageTextItem item, int leftMargin) {
        TranslateAnimation anim = new TranslateAnimation(leftMargin, -item.textMeasuredWidth, 0, 0);
        anim.setDuration(item.moveSpeed);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setFillAfter(true);
        return anim;
    }

    public float getTextWidth(BarrageTextItem item, String text, float size) {
        Rect bounds = new Rect();
        TextPaint paint = item.textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    private int getLineHeight() {
        BarrageTextItem item = new BarrageTextItem();
        String tx = "no null data";
        item.textView = new TextView(mContext);
        item.textView.setText(tx);
        item.textView.setTextSize(maxSize);

        Rect bounds = new Rect();
        TextPaint paint = item.textView.getPaint();
        paint.getTextBounds(tx, 0, tx.length(), bounds);
        return bounds.height();

    }

    class BarrageTextItem {
        public TextView textView;
        public int textColor;
        public String text;
        public int textSize;
        public int moveSpeed;
        public int verticalPos;
        public int textMeasuredWidth;

        public BarrageTextItem() {
        }

        public BarrageTextItem(String text) {
            this.text = text;
            this.textSize = (int) (minSize + (maxSize - minSize) * Math.random());
            this.textColor = Color.rgb(random.nextInt(256), random.nextInt(256),
                    random.nextInt(256));
            textView = new TextView(mContext);
            textView.setText(text);
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
            textMeasuredWidth = (int) getTextWidth(this, text, textSize);
            moveSpeed = (int) (minSpeed + (maxSpeed - minSpeed) * Math.random());
            if (totalLine == 0) {
                totalHeight = getMeasuredHeight();
                lineHeight = getLineHeight();
                totalLine = totalHeight / lineHeight;
            }
            verticalPos = random.nextInt(totalLine) * lineHeight;
        }
    }
}