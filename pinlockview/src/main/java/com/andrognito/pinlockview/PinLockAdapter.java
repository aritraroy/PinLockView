package com.andrognito.pinlockview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by aritraroy on 31/05/16.
 */
public class PinLockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int DEFAULT_VIBRATE_CLICK = 50;

    private static final int VIEW_TYPE_NUMBER = 0;
    private static final int VIEW_TYPE_DELETE = 1;
    private static final int VIEW_TYPE_LEFT_BUTTON = 2;
    private final Vibrator vibrator;

    private Context mContext;
    private CustomizationOptionsBundle mCustomizationOptionsBundle;
    private OnNumberClickListener mOnNumberClickListener;
    private OnDeleteClickListener mOnDeleteClickListener;
    private OnLeftButtonClickListener mOnLeftButtonClickListener;
    private int mPinLength;

    public PinLockAdapter(Context context) {
        this.mContext = context;
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 10 - 1) {
            return VIEW_TYPE_LEFT_BUTTON;
        }
        if (position == getItemCount() - 1) {
            return VIEW_TYPE_DELETE;
        }
        return VIEW_TYPE_NUMBER;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_LEFT_BUTTON) {
            View view = inflater.inflate(R.layout.layout_left_button_item, parent, false);
            viewHolder = new LeftButtonViewHolder(view);
        } else if (viewType == VIEW_TYPE_DELETE) {
            View view = inflater.inflate(R.layout.layout_delete_item, parent, false);
            viewHolder = new DeleteViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.layout_number_item, parent, false);
            viewHolder = new NumberViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LEFT_BUTTON) {
            LeftButtonViewHolder vh1 = (LeftButtonViewHolder) holder;
            configureLeftButtonHolder(vh1);
        } else if (holder.getItemViewType() == VIEW_TYPE_NUMBER) {
            NumberViewHolder vh2 = (NumberViewHolder) holder;
            configureNumberButtonHolder(vh2, position);
        } else if (holder.getItemViewType() == VIEW_TYPE_DELETE) {
            DeleteViewHolder vh3 = (DeleteViewHolder) holder;
            configureDeleteButtonHolder(vh3);
        }
    }

    private void configureLeftButtonHolder(LeftButtonViewHolder holder) {
        if (mCustomizationOptionsBundle.isShowLeftButton()) {
            holder.mLeftButton.setVisibility(View.VISIBLE);
        } else {
            holder.mLeftButton.setVisibility(View.GONE);
        }

        if (mCustomizationOptionsBundle != null) {
            holder.mLeftButton.setTextColor(mCustomizationOptionsBundle.getTextColor());
            holder.mLeftButton.setText(mCustomizationOptionsBundle.getLeftButtonText());
            holder.mLeftButton.setTypeface(ResourceUtils.findFont(
                    mContext,
                    mCustomizationOptionsBundle.getTextFontName(),
                    null));
            holder.mLeftButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCustomizationOptionsBundle.getLeftButtonTextSize());
        }
    }

    private void configureNumberButtonHolder(NumberViewHolder holder, int position) {
        if (holder != null) {
            if (position == 10) {
                holder.mNumberButton.setText("0");
            } else if (position == 9) {
                holder.mNumberButton.setVisibility(View.INVISIBLE);
            } else {
                holder.mNumberButton.setText(String.valueOf((position + 1) % 10));
            }

            if (mCustomizationOptionsBundle != null) {
                holder.mNumberButton.setTextColor(mCustomizationOptionsBundle.getTextColor());
                if (mCustomizationOptionsBundle.getButtonBackgroundId() != 0) {
                    holder.mNumberButton.setBackgroundResource(mCustomizationOptionsBundle.getButtonBackgroundId());
                }
                holder.mNumberButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCustomizationOptionsBundle.getTextSize());
                holder.mNumberButton.setTypeface(ResourceUtils.findFont(
                        mContext,
                        mCustomizationOptionsBundle.getTextFontName(),
                        null));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mCustomizationOptionsBundle.getButtonSize(), mCustomizationOptionsBundle.getButtonSize());
                holder.mNumberButton.setLayoutParams(params);
            }
        }
    }

    private void configureDeleteButtonHolder(DeleteViewHolder holder) {
        if (holder != null) {
            if (mCustomizationOptionsBundle.isShowDeleteButton()
                    && (mPinLength > 0 || !mCustomizationOptionsBundle.isAutoHideDeleteButton())) {
                holder.mButtonImage.setVisibility(View.VISIBLE);
                if (mCustomizationOptionsBundle.getDeleteButtonDrawable() != null) {
                    holder.mButtonImage.setImageDrawable(mCustomizationOptionsBundle.getDeleteButtonDrawable());
                }
                holder.mButtonImage.setColorFilter(mCustomizationOptionsBundle.getTextColor(), PorterDuff.Mode.SRC_ATOP);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mCustomizationOptionsBundle.getDeleteButtonSize(), mCustomizationOptionsBundle.getDeleteButtonSize());
                holder.mButtonImage.setLayoutParams(params);
            } else {
                holder.mButtonImage.setVisibility(View.GONE);
            }
        }
    }


    public int getPinLength() {
        return mPinLength;
    }

    public void setPinLength(int pinLength) {
        this.mPinLength = pinLength;
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

    public OnLeftButtonClickListener getOnLeftButtonClickListener() {
        return mOnLeftButtonClickListener;
    }

    public void setOnLeftButtonClickListener(OnLeftButtonClickListener onLeftButtonClickListener) {
        this.mOnLeftButtonClickListener = onLeftButtonClickListener;
    }

    public CustomizationOptionsBundle getCustomizationOptions() {
        return mCustomizationOptionsBundle;
    }

    public void setCustomizationOptions(CustomizationOptionsBundle customizationOptionsBundle) {
        this.mCustomizationOptionsBundle = customizationOptionsBundle;
    }

    public class LeftButtonViewHolder extends RecyclerView.ViewHolder {
        Button mLeftButton;

        public LeftButtonViewHolder(final View itemView) {
            super(itemView);
            mLeftButton = (Button) itemView.findViewById(R.id.button);
            mLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLeftButtonClickListener != null) {
                        mOnLeftButtonClickListener.onLeftButtonClicked(LeftButtonViewHolder.this);
                    }
                }
            });

            mLeftButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if( mCustomizationOptionsBundle.isVibrateButtonClick()){
                            mLeftButton.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
                        }
                    }
                    return false;
                }
            });
        }
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {
        Button mNumberButton;

        public NumberViewHolder(final View itemView) {
            super(itemView);
            mNumberButton = (Button) itemView.findViewById(R.id.button);
            mNumberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnNumberClickListener != null) {
                        mOnNumberClickListener.onNumberClicked(NumberViewHolder.this);
                    }
                }
            });

            mNumberButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if( mCustomizationOptionsBundle.isVibrateButtonClick()){
                            mNumberButton.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
                        }
                    }
                    return false;
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
                        mButtonImage.setColorFilter(mCustomizationOptionsBundle.getDeleteButtonPressesColor());
                        if( mCustomizationOptionsBundle.isVibrateButtonClick()){
                            mDeleteButton.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
                        }
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        mButtonImage.clearColorFilter();
                    }
                    if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                        mButtonImage.clearColorFilter();
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                            mButtonImage.clearColorFilter();
                        }
                    }
                    return false;
                }
            });
        }
    }

    public interface OnNumberClickListener {
        void onNumberClicked(NumberViewHolder numberViewHolder);
    }

    public interface OnLeftButtonClickListener {
        void onLeftButtonClicked(LeftButtonViewHolder numberViewHolder);
    }

    public interface OnDeleteClickListener {
        void onDeleteClicked();

        void onDeleteLongClicked();
    }
}
