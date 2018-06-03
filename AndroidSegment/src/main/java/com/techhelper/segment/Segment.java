package com.techhelper.segment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.techhelper.segment.util.SegmentUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mayur Aouti on 9/5/2015.
 */
public class Segment extends RadioGroup implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    private int BORDER_WIDTH = 2;
    private int CORNER_RADIUS = 5;
    private int segmentBackgroundColor = Color.parseColor("#000000");
    private int segmentTextColor = Color.parseColor("#ffffff");
    private List<OnSegmentCheckedChangeListener> listeners = new ArrayList<>();
    private List<String> segmentTitles = new ArrayList<>();
    private List<Integer> segmentDrawablesIds = new ArrayList<>();
    private String fontAssetName;
    private String TAG = "SegmentControl";
    private float segmentTextSize = -1;

    public Segment(Context context) {
        super(context);
        initProperties();
        addButtons();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public Segment(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProperties();
        addButtons();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        applyAttributes(context, attrs);
    }

//    public SegmentedControl(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        addButtons();
//    }
//
//    public SegmentedControl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        addButtons();
//    }

    /**
     * Initializes widget properties.
     */
    private void initProperties() {
        setOrientation(RadioGroup.HORIZONTAL);
    }

    private void applyAttributes(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Segment);
        if (a.getString(R.styleable.Segment_segmentFontAssetName) != null) {
            fontAssetName = a.getString(R.styleable.Segment_segmentFontAssetName);
            setCustomTypeface(fontAssetName);
        }

        segmentBackgroundColor = a.getInt(R.styleable.Segment_segmentBackgroundColor, 0);
        segmentTextColor = a.getInt(R.styleable.Segment_segmentTextColor, 0);
        segmentTextSize = a.getDimension(R.styleable.Segment_segmentTextSize, -1);

        updateWidgetUI();
        updateTextColor();
    }

    private void updateWidgetUI() {
        RadioButton button;
        for (int i = 0; i < getChildCount(); i++) {
            button = ((RadioButton) getChildAt(i));
            if (segmentTextSize != -1) {
                button.setTextSize(segmentTextSize);
            }
            GradientDrawable drawable = (GradientDrawable) button.getBackground();
            drawable.setStroke(BORDER_WIDTH, segmentBackgroundColor);
            ((LinearLayout.LayoutParams) button.getLayoutParams()).weight = 100 / segmentTitles.size();
        }

        if (listeners.size() > 0) {
            clearCheck();
            setOnCheckedChangeListener(this);
        } else {
            setOnCheckedChangeListener(null);
        }
    }

    private void updateSegmentDrawable() throws Exception {
        RadioButton button;
        for (int i = 0; i < getChildCount(); i++) {
            button = ((RadioButton) getChildAt(i));

            if (segmentDrawablesIds.size() != 0) {
                Drawable resizedImage = SegmentUtil.getResizedImage(getContext(), segmentDrawablesIds.get(i), Math.round(segmentTextSize), Math.round(segmentTextSize));
                button.setButtonDrawable(resizedImage);
            }
        }
    }

    private void updateTextColor() {
        RadioButton button;
        for (int i = 0; i < getChildCount(); i++) {
            button = ((RadioButton) getChildAt(i));
            button.setTextColor(segmentTextColor);
        }
    }

    /**
     * Sets custom font.
     *
     * @param path - String
     */
    private void setCustomTypeface(String path) {
        try {
            Typeface font = Typeface.createFromAsset(getResources().getAssets(), path);
            RadioButton button;
            for (int i = 0; i < getChildCount(); i++) {
                if (font != null) {
                    button = ((RadioButton) getChildAt(i));
                    button.setTypeface(font);
                    button.setPaintFlags(button.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Changes the checked state of the segment at given position.
     *
     * @param position int
     * @param checked  boolean
     */
    public void setSegmentChecked(int position, boolean checked) {
        clearCheck();
        ((RadioButton) getChildAt(position)).setChecked(checked);
    }

    /**
     * Sets segment titles.
     *
     * @param segmentTitles - List<String>
     */
    public void setTitles(List<String> segmentTitles) {
        this.segmentTitles = segmentTitles;
        addButtons();
        updateWidgetUI();
    }

    /**
     * Sets segment titles.
     *
     * @param segmentDrawablesIds - List<Integer>
     */
    public void setDrawablesIds(List<Integer> segmentDrawablesIds) throws Exception {
        this.segmentDrawablesIds = segmentDrawablesIds;
        updateSegmentDrawable();
    }

    /**
     * Sets segment background color.
     *
     * @param segmentBackgroundColor - int
     */
    public void setSegmentBackgroundColor(int segmentBackgroundColor) {
        this.segmentBackgroundColor = segmentBackgroundColor;
        invalidate();
    }

    /**
     * Sets segment background color.
     *
     * @param segmentBackgroundColor - String
     */
    public void setSegmentBackgroundColor(String segmentBackgroundColor) {
        this.segmentBackgroundColor = Color.parseColor(segmentBackgroundColor);
        invalidate();
    }

    /**
     * Sets segments text color.
     *
     * @param segmentTextColor - String
     */
    public void setSegmentTextColor(int segmentTextColor) {
        this.segmentTextColor = segmentTextColor;
        updateTextColor();
        invalidate();
    }

    /**
     * Registers the listener.
     *
     * @param onSegmentCheckedChangedListener - OnSegmentCheckedChangeListener
     */
    public void setOnSegmentCheckedChangedListener(OnSegmentCheckedChangeListener onSegmentCheckedChangedListener) {
        if (!listeners.contains(onSegmentCheckedChangedListener)) {
            listeners.add(onSegmentCheckedChangedListener);
        }

        updateWidgetUI();
    }

    /**
     * Unregisters the listener.
     *
     * @param onSegmentCheckedChangedListener - OnSegmentCheckedChangeListener
     */
    public void removeOnSegmentCheckedChangedListener(OnSegmentCheckedChangeListener onSegmentCheckedChangedListener) {
        if (listeners.contains(onSegmentCheckedChangedListener)) {
            listeners.remove(onSegmentCheckedChangedListener);
        }
    }

    /**
     * Adds segments.
     */
    private void addButtons() {
        for (int i = 0; i < segmentTitles.size(); i++) {
            RadioButton button;
            if (i == 0) {
                button = getLeftButton(segmentTitles.get(i));
                addView(button);
            } else if (i == (segmentTitles.size() - 1)) {
                button = getRightButton(segmentTitles.get(i));
                addView(button);
            } else {
                button = getMiddleButton(segmentTitles.get(i));
                addView(button);
            }

            button.setId(i);
        }
    }


    /**
     * Creates first segment button with required properties
     *
     * @param buttonText - String
     * @return RadioButton
     */
    private RadioButton getLeftButton(String buttonText) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RadioButton button = (RadioButton) inflater.inflate(R.layout.segmented_radio_button, null);
        button.setText(buttonText);
        button.setTextColor(segmentTextColor);
//        button.setGravity(Gravity.CENTER);
//        button.setButtonDrawable(new StateListDrawable());
//        button.setBackground(getResources().getDrawable(R.drawable.segment_bg));
        GradientDrawable drawable = (GradientDrawable) button.getBackground();
        drawable.setStroke(SegmentUtil.dpToPx(getContext(), BORDER_WIDTH), segmentBackgroundColor);
        float px = SegmentUtil.dpToPx(getContext(), CORNER_RADIUS);
        float[] cornerRadii = new float[]{px, px, 0, 0, 0, 0, px, px};
        drawable.mutate();
        drawable.setCornerRadii(cornerRadii);
        return button;
    }

    /**
     * Creates last segment button with required properties
     *
     * @param buttonText - String
     * @return RadioButton
     */
    private RadioButton getRightButton(String buttonText) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RadioButton button = (RadioButton) inflater.inflate(R.layout.segmented_radio_button, null);
        button.setText(buttonText);
        button.setTextColor(segmentTextColor);
        GradientDrawable drawable = (GradientDrawable) button.getBackground();
//        drawable.setColor(Color.RED);
        drawable.setStroke(SegmentUtil.dpToPx(getContext(), BORDER_WIDTH), segmentBackgroundColor);
        float px = SegmentUtil.dpToPx(getContext(), CORNER_RADIUS);
        float[] cornerRadii = new float[]{0, 0, px, px, px, px, 0, 0,};
        drawable.mutate();
        drawable.setCornerRadii(cornerRadii);
        return button;
    }

    /**
     * Creates middle segment button with required properties
     *
     * @param buttonText - String
     * @return RadioButton
     */
    private RadioButton getMiddleButton(String buttonText) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RadioButton button = (RadioButton) inflater.inflate(R.layout.segmented_radio_button, null);
        button.setText(buttonText);
        button.setTextColor(segmentTextColor);
        GradientDrawable drawable = (GradientDrawable) button.getBackground();
//        drawable.setColor(Color.RED);
        drawable.setStroke(SegmentUtil.dpToPx(getContext(), BORDER_WIDTH), segmentBackgroundColor);
        float[] cornerRadii = new float[]{0, 0, 0, 0, 0, 0, 0, 0,};
        drawable.mutate();
        drawable.setCornerRadii(cornerRadii);
        return button;
    }

    /**
     * Changes segment checked state of all segments. Makes selected segment checked and others unchecked
     */
    private void changeButtonState() {
        RadioButton button;
        for (int i = 0; i < segmentTitles.size(); i++) {
            button = (RadioButton) getChildAt(i);
            if (button.getId() == getCheckedRadioButtonId()) {
                ((GradientDrawable) button.getBackground()).setColor(segmentBackgroundColor);
            } else {
                ((GradientDrawable) button.getBackground()).setColor(Color.TRANSPARENT);
            }
        }
//        if (button.isChecked()) {
//            ((GradientDrawable) button.getBackground()).setColor(Color.RED);
//        } else {
//            ((GradientDrawable) button.getBackground()).setColor(Color.TRANSPARENT);
//        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        changeButtonState();
        RadioButton button = (RadioButton) findViewById(checkedId);
        for (OnSegmentCheckedChangeListener onSegmentCheckedListener : listeners) {
            if (button != null) {
                onSegmentCheckedListener.onSegmentCheckedChanged(button.getId(), button, button.isChecked());
            }
        }
    }


}
