package com.zmzing.highlighttextview;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 关键字高亮 TextView
 */
public class HighlightTextView extends TextView {

    private static final String DEFAULT_TAG_LEFT  = "[";
    private static final String DEFAULT_TAG_RIGHT = "]";

    private int    mHighlightColor;// 高亮颜色
    private String mTagL;// 左标记
    private String mTagR;// 右标记

    public HighlightTextView(Context context) {
        this(context, null);
    }

    public HighlightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHighlightColor = getResources().getColor(R.color.base_highlight_color);
        mTagL = DEFAULT_TAG_LEFT;
        mTagR = DEFAULT_TAG_RIGHT;
    }

    /**
     * 设置高亮标记
     *
     * @param tagLeft  左标记
     * @param tagRight 右标记
     */
    public void setTag(String tagLeft, String tagRight) {
        if (!TextUtils.isEmpty(tagLeft)) {
            mTagL = tagLeft;
        }
        if (!TextUtils.isEmpty(tagRight)) {
            mTagR = tagRight;
        }
    }

    public void setHighlightColor(int highlightColor) {
        mHighlightColor = highlightColor;
    }

    /**
     * @param text 文本内容
     */
    public void setText(String text) {
        setText(text, mHighlightColor);
    }

    /**
     * @param text      文本内容
     * @param highLight 是否高亮？true 时使用默认高亮颜色
     */
    public void setText(String text, boolean highLight) {
        if (highLight) {
            setText(text, mHighlightColor);
        } else {
            setText(text);
        }
    }

    /**
     * @param text           文本内容
     * @param highlightColor 高亮颜色
     */
    public void setText(String text, int highlightColor) {
        if (text == null) {
            text = "";
        }

        // 左右标记的长度
        int tagLenL = mTagL.length();
        int tagLenR = mTagR.length();

        // 关键字开始和结束
        int offsetStart = text.indexOf(mTagL, 0);
        int offsetEnd = text.indexOf(mTagR, offsetStart + tagLenL);

        //文字中没有高亮标签，直接返回
        if (offsetStart == -1 || offsetEnd == -1) {
            super.setText(text);
            return;
        }

        // 遍历原始text，截取并拼接出最终的串
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int start = 0; start < text.length(); start = offsetEnd + tagLenR) {

            offsetStart = text.indexOf(mTagL, start);
            offsetEnd = text.indexOf(mTagR, offsetStart + tagLenL);

            // 若找不到标记则跳出
            if (offsetStart == -1 || offsetEnd == -1) {
                ssb.append(text.subSequence(start, text.length()));
                break;
            } else {
                // 左标记之前的串
                CharSequence csBefore = text.subSequence(start, offsetStart);
                ssb.append(csBefore);
                // 左右标记之间的串
                CharSequence csBetween = text.subSequence(offsetStart + tagLenL, offsetEnd);
                ssb.append(csBetween);
                // LogUtil.d("csBefore = " + csBefore);
                // LogUtil.d("csBetween = " + csBetween);

                int spanEnd = ssb.length();
                int spanStart = spanEnd - csBetween.length();
                // LogUtil.d("spanEnd = " + spanEnd);
                // LogUtil.d("spanStart = " + spanStart);

                ssb.setSpan(new ForegroundColorSpan(highlightColor), spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        setText(ssb, BufferType.SPANNABLE);
    }

    /**
     * 使用自定义高亮关键字设置文本内容
     *
     * @param text    文本内容
     * @param keyword 自定义关键字
     */
    private void setTextWithKeyword(String text, String keyword) {
        if (text == null) {
            text = "";
        }
        if (TextUtils.isEmpty(keyword)) {
            setText(text);
        } else {
            int offset = text.indexOf(keyword, 0);
            Spannable word2Span = new SpannableString(text);
            for (int start = 0; start < text.length() && offset != -1; start = offset + 1) {
                offset = text.indexOf(keyword, start);
                if (offset == -1) {
                    break;
                } else {
                    word2Span.setSpan(new ForegroundColorSpan(mHighlightColor), offset, offset + keyword.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            setText(word2Span, BufferType.SPANNABLE);
        }
    }

}
