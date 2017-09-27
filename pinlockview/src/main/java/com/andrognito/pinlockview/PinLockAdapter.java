package com.andrognito.pinlockview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Typeface;

/**
 * Created by aritraroy on 31/05/16.
 */
public class PinLockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_NUMBER = 0;
    private static final int VIEW_TYPE_DELETE = 1;

    private Context mContext;
    private CustomizationOptionsBundle mCustomizationOptionsBundle;
    private OnNumberClickListener mOnNumberClickListener;
    private OnDeleteClickListener mOnDeleteClickListener;
    private int mPinLength;

    private int[] mKeyValues;

    public PinLockAdapter(Context context) {
        this.mContext = context;
        this.mKeyValues = getAdjustKeyValues(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_NUMBER) {
            View view = inflater.inflate(R.layout.layout_number_item, parent, false);
            viewHolder = new NumberViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.layout_delete_item, parent, false);
            viewHolder = new DeleteViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_NUMBER) {
            NumberViewHolder vh1 = (NumberViewHolder) holder;
            configureNumberButtonHolder(vh1, position);
        } else if (holder.getItemViewType() == VIEW_TYPE_DELETE) {
            DeleteViewHolder vh2 = (DeleteViewHolder) holder;
            configureDeleteButtonHolder(vh2);
        }
    }

    private void configureNumberButtonHolder(NumberViewHolder holder, int position) {
        if (holder != null) {
            if (position == 9) {
                holder.mNumberButton.setVisibility(View.GONE);
                holder.number.setVisibility(View.GONE);
                holder.letters.setVisibility(View.GONE);
            } else {
                holder.number.setText(String.valueOf(mKeyValues[position]));
                holder.mNumberButton.setVisibility(View.VISIBLE);
                holder.number.setVisibility(View.VISIBLE);
                holder.mNumberButton.setTag(mKeyValues[position]);

            }

            if (mCustomizationOptionsBundle != null) {
                // If using deprecated color options, then text color and size affects
                // both the numbers and text. Otherwise, the number and text colors are
                // assigned separately
                if(mCustomizationOptionsBundle.getUseDeprecated()){
                    holder.number.setTextColor(mCustomizationOptionsBundle.getTextColor());
                    holder.letters.setTextColor(mCustomizationOptionsBundle.getTextColor());

                    holder.number.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            mCustomizationOptionsBundle.getTextSize());
                    holder.letters.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            mCustomizationOptionsBundle.getTextSize());
                } else {
                    // Set text colors
                    holder.number.setTextColor(mCustomizationOptionsBundle.getNumbersTextColor());
                    holder.letters.setTextColor(mCustomizationOptionsBundle.getLettersTextColor());

                    // Set text sizes
                    holder.number.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            mCustomizationOptionsBundle.getNumbersTextSize());
                    holder.letters.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            mCustomizationOptionsBundle.getLettersTextSize());
                }

                // Set up letters
                if (mCustomizationOptionsBundle.getShowLetters()) {
                    holder.letters.setVisibility(View.VISIBLE);
                    holder.letters.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                    if (holder != null && position != 9) {
                        switch (mKeyValues[position]) {
                            case 1:
                                holder.letters.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                holder.letters.setText(mContext.getResources().getString(R.string.button_two_text));
                                break;
                            case 3:
                                holder.letters.setText(mContext.getResources().getString(R.string.button_three_text));
                                break;
                            case 4:
                                holder.letters.setText(mContext.getResources().getString(R.string.button_four_text));
                                break;
                            case 5:
                                holder.letters.setText(mContext.getResources().getString(R.string.button_five_text));
                                break;
                            case 6:
                                holder.letters.setText(mContext.getResources().getString(R.string.button_six_text));
                                break;
                            case 7:
                                holder.letters.setText(mContext.getResources().getString(R.string.button_seven_text));
                                break;
                            case 8:
                                holder.letters.setText(mContext.getResources().getString(R.string.button_eight_text));
                                break;
                            case 9:
                                holder.letters.setText(mContext.getResources().getString(R.string.button_nine_text));
                                break;
                            case 0:
                                holder.letters.setVisibility(View.GONE);
                                break;
                        }
                    }
                }

                // Set boldness of text
                if (mCustomizationOptionsBundle.getIsNumbersTextBold()) {
                    holder.number.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                }

                if (mCustomizationOptionsBundle.getIsLettersTextBold()) {
                    holder.letters.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                }

                // Set button backgrounds
                if (mCustomizationOptionsBundle.getButtonBackgroundDrawable() != null) {
                    if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        holder.mNumberButton.setBackgroundDrawable(
                                mCustomizationOptionsBundle.getButtonBackgroundDrawable());
                    } else {
                        holder.mNumberButton.setBackground(
                                mCustomizationOptionsBundle.getButtonBackgroundDrawable());
                    }
                }

                // Set button sizes
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        mCustomizationOptionsBundle.getButtonSize(),
                        mCustomizationOptionsBundle.getButtonSize());
                holder.mNumberButton.setLayoutParams(params);
            }
        }
    }

    private void configureDeleteButtonHolder(DeleteViewHolder holder) {
        if (holder != null) {
            if (mCustomizationOptionsBundle.isShowDeleteButton() && mPinLength > 0) {
                holder.mDeleteButton.setVisibility(View.VISIBLE);
                if (mCustomizationOptionsBundle.getDeleteButtonDrawable() != null) {
                    holder.mButtonImage.setImageDrawable(mCustomizationOptionsBundle.getDeleteButtonDrawable());
                }
                if (mCustomizationOptionsBundle.getDeleteButtonDefault()) {
                    holder.mButtonImage.setColorFilter(mCustomizationOptionsBundle.getNumbersTextColor(),
                        PorterDuff.Mode.SRC_ATOP);
                } else {
                    holder.mButtonImage.setColorFilter(mCustomizationOptionsBundle.getDeleteButtonColor(),
                        PorterDuff.Mode.SRC_ATOP);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        mCustomizationOptionsBundle.getButtonSize(),
                        mCustomizationOptionsBundle.getButtonSize());
                holder.mDeleteButton.setLayoutParams(params);
            } else {
                holder.mDeleteButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return VIEW_TYPE_DELETE;
        }
        return VIEW_TYPE_NUMBER;
    }

    public int getPinLength() {
        return mPinLength;
    }

    public void setPinLength(int pinLength) {
        this.mPinLength = pinLength;
    }

    public int[] getKeyValues() {
        return mKeyValues;
    }

    public void setKeyValues(int[] keyValues) {
        this.mKeyValues = getAdjustKeyValues(keyValues);
        notifyDataSetChanged();
    }

    private int[] getAdjustKeyValues(int[] keyValues) {
        int[] adjustedKeyValues = new int[keyValues.length + 1];
        for (int i = 0; i < keyValues.length; i++) {
            if (i < 9) {
                adjustedKeyValues[i] = keyValues[i];
            } else {
                adjustedKeyValues[i] = -1;
                adjustedKeyValues[i + 1] = keyValues[i];
            }
        }
        return adjustedKeyValues;
    }

    public OnNumberClickListener getOnItemClickListener() {
        return mOnNumberClickListener;
    }

    public void setOnItemClickListener(OnNumberClickListener onNumberClickListener) {
        this.mOnNumberClickListener = onNumberClickListener;
    }

    public OnDeleteClickListener getOnDeleteClickListener() {
        return mOnDeleteClickListener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.mOnDeleteClickListener = onDeleteClickListener;
    }

    public CustomizationOptionsBundle getCustomizationOptions() {
        return mCustomizationOptionsBundle;
    }

    public void setCustomizationOptions(CustomizationOptionsBundle customizationOptionsBundle) {
        this.mCustomizationOptionsBundle = customizationOptionsBundle;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mNumberButton;
        TextView number;
        TextView letters;

        public NumberViewHolder(final View itemView) {
            super(itemView);
            mNumberButton = (LinearLayout) itemView.findViewById(R.id.button);
            number = (TextView) itemView.findViewById(R.id.number);
            letters = (TextView) itemView.findViewById(R.id.letters);
            mNumberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnNumberClickListener != null) {
                        mOnNumberClickListener.onNumberClicked((Integer) v.getTag());
                    }
                }
            });
        }
    }

    public class DeleteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mDeleteButton;
        ImageView mButtonImage;

        public DeleteViewHolder(final View itemView) {
            super(itemView);
            mDeleteButton = (LinearLayout) itemView.findViewById(R.id.button);
            mButtonImage = (ImageView) itemView.findViewById(R.id.buttonImage);

            if (mCustomizationOptionsBundle.isShowDeleteButton() && mPinLength > 0) {
                mDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnDeleteClickListener != null) {
                            mOnDeleteClickListener.onDeleteClicked();
                        }
                    }
                });

                mDeleteButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mOnDeleteClickListener != null) {
                            mOnDeleteClickListener.onDeleteLongClicked();
                        }
                        return true;
                    }
                });

                mDeleteButton.setOnTouchListener(new View.OnTouchListener() {
                    private Rect rect;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            mButtonImage.setColorFilter(mCustomizationOptionsBundle
                                    .getDeleteButtonPressesColor());
                            rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (mCustomizationOptionsBundle.getDeleteButtonDefault()) {
                                mButtonImage.clearColorFilter();
                            } else {
                                mButtonImage.setColorFilter(mCustomizationOptionsBundle.getDeleteButtonColor(),
                                    PorterDuff.Mode.SRC_ATOP);
                            }
                        }
                        if (event.getAction() == MotionEvent.ACTION_MOVE) {
                            if (!rect.contains(v.getLeft() + (int) event.getX(),
                                    v.getTop() + (int) event.getY())) {
                                if (mCustomizationOptionsBundle.getDeleteButtonDefault()) {
                                    mButtonImage.clearColorFilter();
                                } else {
                                    mButtonImage.setColorFilter(mCustomizationOptionsBundle.getDeleteButtonColor(),
                                        PorterDuff.Mode.SRC_ATOP);
                                }
                            }
                        }
                        return false;
                    }
                });
            }
        }
    }

    public interface OnNumberClickListener {
        void onNumberClicked(int keyValue);
    }

    public interface OnDeleteClickListener {
        void onDeleteClicked();

        void onDeleteLongClicked();
    }
}
