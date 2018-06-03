package com.example.segment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.techhelper.segment.OnSegmentCheckedChangeListener;
import com.techhelper.segment.Segment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSegmentCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTabs();
    }

    private void setupTabs() {
        Segment segment = (Segment) findViewById(R.id.segments);
        List<String> segmentTitles = Arrays.asList("Seg A", "Seg B", "Seg C");
        segment.setTitles(segmentTitles);
        segment.setOnSegmentCheckedChangedListener(this);
        segment.setSegmentChecked(0, true);

    }

    @Override
    public void onSegmentCheckedChanged(int position, CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Segment selected " + position, Toast.LENGTH_SHORT).show();
    }
}
