# AndroidSegment
[ ![Download](https://api.bintray.com/packages/mayuraouti/AndroidSegment/AndroidSegment/images/download.svg) ](https://bintray.com/mayuraouti/AndroidSegment/AndroidSegment/_latestVersion)

Add segments to your app easily and dynamically.

![alt text](https://github.com/TechHelper/AndroidSegment/blob/master/app/Android%20Segment%20Widget.png?raw=true)

## Gradle dependency
```
allprojects {
repositories {
    jcenter()
  }
}

implementation 'com.techhelper.segment:AndroidSegment:1.0.0'
```

## Steps to use this library

### 1. Add below code to your layout xml file to add segment widget.
```
<com.techhelper.segment.Segment
        android:id="@+id/segments"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginLeft="15dp"
        android:layout_marginRight="15dp"
        android:layout_marginTop="15dp"
        android:gravity="center_horizontal"
        app:segmentBackgroundColor="#007AF3"
        app:segmentTextColor="@android:color/white"
        app:segmentTextSize="10sp" />
```

### 2. Below code is to setup segment widget.

```
private void setupSegment() {
        Segment segment = (Segment) findViewById(R.id.segments);
        List<String> segmentTitles = Arrays.asList("Seg A", "Seg B", "Seg C");
        segment.setTitles(segmentTitles);
        segment.setSegmentChecked(0, true);

    }
```
### 3. Below code is to set segment change listener.
```
segment.setOnSegmentCheckedChangedListener(this);

@Override
    public void onSegmentCheckedChanged(int position, CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Segment selected " + position, Toast.LENGTH_SHORT).show();
    }
```

## Contributing

Please feel free to contribute by following standard process of reviewing pull requests and other git commands.

## License

This project is licensed under the [Apache-2.0] - see the LICENSE file for details

