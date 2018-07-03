package com.andrognito.pinlockview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class InputField extends AppCompatEditText {

    private static final char PASSWORD_DOT = '\u2022';
    private static final int DEFAULT_DOT_DELAY_MILLIS = 1500;

    private TextWatcher pinWatcher;

    public InputField(Context context) {
        super(context);

        initView(context);
    }

    public InputField(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    public InputField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_LTR);

        disableKeyboard(context);
        setupPasswordDots();
    }

    private void disableKeyboard(Context context) {
        setCursorVisible(true);
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setShowSoftInputOnFocus(false);
        }
        setTextIsSelectable(true);
        setInputType(InputType.TYPE_NULL);
        setKeyListener(null);
        setRawInputType(InputType.TYPE_CLASS_TEXT);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* do nothing */
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /* do nothing */
                return true;
            }
        });
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                /* do nothing */
                return true;
            }
        });
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /* do nothing */
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /* do nothing */
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        /* do nothing */
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /* do nothing */
        return true;
    }

    private void setupPasswordDots() {
        setupPasswordDots(DEFAULT_DOT_DELAY_MILLIS);
    }

    private void setupPasswordDots(final int dotDelayMillis) {
        if (pinWatcher != null) {
            removeTextChangedListener(pinWatcher);
        }

        pinWatcher = new TextWatcher() {
            private Handler pinHandler = new Handler();

            private int oldLength = 0;
            private AtomicBoolean isCurrentlyEditing = new AtomicBoolean(false);

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isCurrentlyEditing.get()) {
                    return; // Do nothing if editing field within the watcher, to prevent infinite loops
                }

                pinHandler.removeCallbacksAndMessages(null);

                Runnable hideText = new Runnable() {
                    @Override
                    public void run() {
                        isCurrentlyEditing.set(true);
                        InputField.this.setText(hideString(InputField.this.getText().toString()));
                        InputField.this.setSelection(InputField.this.getText().length());
                        isCurrentlyEditing.set(false);
                    }
                };

                if (oldLength < s.toString().length()) { // Briefly display last digit if PIN increases
                    oldLength = s.length();

                    isCurrentlyEditing.set(true);
                    InputField.this.setText(almostHideString(InputField.this.getText().toString()));
                    InputField.this.setSelection(InputField.this.getText().length());
                    isCurrentlyEditing.set(false);

                    pinHandler.postDelayed(hideText, dotDelayMillis);
                } else { // Otherwise just hide PIN if deleting
                    oldLength = s.length();

                    hideText.run();
                }
            }

        };

        addTextChangedListener(pinWatcher);
    }

    private String almostHideString(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length() - 1; ++i) {
            stringBuilder.append(PASSWORD_DOT);
        }
        stringBuilder.append(s.charAt(s.length() - 1));

        return stringBuilder.toString();
    }

    private String hideString(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            stringBuilder.append(PASSWORD_DOT);
        }

        return stringBuilder.toString();
    }
}
