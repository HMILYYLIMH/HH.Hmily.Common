package com.hmily.common.util;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 文本工具类。
 * 
 * @author hehui
 *
 */
public class TextUtils {

	private static final String TAG = "TextUtils";

	/***
	 * 直接将EditText的光标定位到最后一个位置。
	 * 
	 * @param editTextList
	 */
	public static void setSelectionLast(EditText... editTextList) {
		for (EditText e : editTextList) {
			CharSequence text = e.getText();
			if (text instanceof Spannable) {
				Spannable spanText = (Spannable) text;
				Selection.setSelection(spanText, text.length());
			}
		}
	}

	/**
	 * 设置默认字体样式：这里支持粗体样式、下划线样式。
	 * 
	 * @param textView
	 *            ：{@link TextView } 控件。
	 * @param textSize
	 *            ：字体大小。
	 * @param content
	 *            ：控件需要显示的内容。
	 * @param textStyle
	 *            ：    {@link Typeface#NORMAL }    {@link Typeface#BOLD }    
	 *            {@link Typeface#ITALIC }    {@link Typeface#BOLD_ITALIC }
	 * @param isUnderline
	 *            ：是否需要在字体内容下方设置下划线     {@code true } : 有下划线；     {@code false }
	 *            ： 没有下划线。
	 */
	public static void setTextStyle(TextView textView, int textSize, String content, int textStyle, boolean isUnderline) {
		SpannableString msp = null;
		msp = new SpannableString(content);
		msp.setSpan(new AbsoluteSizeSpan(textSize), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new StyleSpan(textStyle), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (isUnderline) {
			msp.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		textView.setText(msp);
	}

	/**
	 * 给 {@linkplain TextView} 控件划线。
	 * 
	 * @param textView
	 * @param flag
	 *            ：    划中线 --> {@linkplain Paint#STRIKE_THRU_TEXT_FLAG}；     下划线
	 *            --> {@linkplain Paint#UNDERLINE_TEXT_FLAG}；     0 --> 取消所有划线；
	 */
	public static void drawLine(TextView textView, int flag) {
		if (textView == null) {
			return;
		}

		TextPaint textPaint = textView.getPaint();
		if (textPaint == null) {
			return;
		}

		if (flag == 0) {
			textPaint.setFlags(0);
		} else {
			textPaint.setFlags(flag | Paint.ANTI_ALIAS_FLAG);
		}
	}

	/**
	 * 给 {@linkplain TextView} 控件画中线。
	 * 
	 * @param textView
	 * @param isDrawMiddleLine
	 *            ：{@code true}：划中线；{@code false}：取消所有划线。
	 */
	public static void drawMiddleLine(TextView textView, boolean isDrawMiddleLine) {
		if (textView == null) {
			return;
		}

		TextPaint textPaint = textView.getPaint();
		if (textPaint == null) {
			return;
		}

		if (isDrawMiddleLine) {
			textPaint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		} else {
			textPaint.setFlags(0);
		}
	}

	/**
	 * 键盘聚焦并使光标显示；
	 * 
	 * @param editText
	 */
	public static void requestFocusWithEdittext(EditText editText) {
		editText.setVisibility(View.VISIBLE);
		editText.requestFocus();
		editText.setCursorVisible(true);
	}

	/**
	 * 清除文本内容。
	 * 
	 * @param editableView
	 */
	@SuppressWarnings("unchecked")
	public static <T extends TextView> void clearText(T editableView) {
		if (editableView == null) {
			return;
		}

		Editable editable = ((T) editableView).getEditableText();
		if (editable == null) {
			return;
		}

		editable.clear();
	}

	/**
	 * 隐藏或显示密码。
	 * 
	 * @param editText
	 *            ：编辑框控件对象；
	 * @param isShowPassword
	 *            ：{@code true}：显示密码；{@code false}：隐藏密码。
	 */
	public static void togglePassword(EditText editText, boolean isShowPassword) {
		if (editText == null) {
			return;
		}

		Editable editable = editText.getEditableText();
		if (editable == null) {
			return;
		}

		boolean hasFocus = editText.hasFocus();
		int selStart = 0;
		int selEnd = 0;
		if (hasFocus) {
			selStart = editText.getSelectionStart();
			selEnd = editText.getSelectionEnd();
		}

		editText.setInputType(isShowPassword ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
				: (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));

		if (hasFocus) {
			editText.setSelection(selStart, selEnd);
		}
	}

	/**
	 * 设置控件是否显示粗体。
	 * 
	 * @param editableView
	 *            ：编辑框控件对象；
	 * @param isFakeBold
	 *            ：{@code true}：显示 粗体；{@code false}：不显示粗体。
	 */
	@SuppressWarnings("unchecked")
	public static <T extends TextView> void setFakeBoldText(T editableView, boolean isFakeBold) {
		if (editableView == null) {
			return;
		}

		TextPaint tp = editableView.getPaint();
		if (tp == null) {
			return;
		}

		tp.setFakeBoldText(true);
	}

//	/**
//	 * 设置文本编辑框游标颜色。
//	 *
//	 * @param editText
//	 *            ：编辑框控件对象；
//	 * @param color
//	 *            ：颜色值。
//	 */
//	public static void setEditTextCursorDrawable(final EditText editText, final int color) {
//		if (editText == null) {
//			throw new IllegalArgumentException("editText is null.");
//		}
//
//		try {
//			Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
//			fCursorDrawableRes.setAccessible(true);
//			int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
//			if (mCursorDrawableRes <= 0) {
//				return;
//			}
//
//			Field fEditor = TextView.class.getDeclaredField("mEditor");
//			fEditor.setAccessible(true);
//
//			Object editor = fEditor.get(editText);
//			Class<?> clazz = editor.getClass();
//
//			String className = clazz.getName();
//			// android.widget.Editor 系统默认的编辑对象类名
//			// huawei.com.android.internal.widget.HwEditor 华为定制的编辑对象类名
//			if (Util.isNullOrEmpty(className)) {
//				Logger.w(TAG, "class name is null or empty.");
//				return;
//			}
//
//			Field fCursorDrawable = null;
//			// 华为机型特意对编辑框做了特殊定制，所以要想修改华为编辑框的光标颜色，得找到HwEditor父类方法Editor修改属性
//			if ("huawei.com.android.internal.widget.HwEditor".equals(className)) {
//				fCursorDrawable = ReflectionUtils.getDeclaredField(editor, "mCursorDrawable");
//			} else {
//				fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
//			}
//
//			fCursorDrawable.setAccessible(true);
//			Drawable cursorDrawable = editText.getContext().getResources().getDrawable(mCursorDrawableRes);
//			if (cursorDrawable == null) {
//				return;
//			}
//
//			Drawable tintDrawable = tintDrawable(cursorDrawable, ColorStateList.valueOf(color));
//			Drawable[] drawables = new Drawable[] { tintDrawable, tintDrawable };
//			fCursorDrawable.set(editor, drawables);
//		} catch (Exception e) {
//			Logger.w(TAG, "Failed to set edittext cursor drawable.", e);
//		}
//	}

//	/**
//	 * 着色器。
//	 *
//	 * @param drawable
//	 * @param colors
//	 * @return
//	 */
//	public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
//		final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//		DrawableCompat.setTintList(wrappedDrawable, colors);
//		return wrappedDrawable;
//	}
}
