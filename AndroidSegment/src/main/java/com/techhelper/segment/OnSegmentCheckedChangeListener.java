package com.techhelper.segment;

import android.widget.CompoundButton;

/**
 * Created by Mayur Aouti on 9/9/15.
 */
public interface OnSegmentCheckedChangeListener {
    /**
     * Called when checked state of segment has changed
     */
    public void onSegmentCheckedChanged(int position, CompoundButton buttonView, boolean isChecked);
}
