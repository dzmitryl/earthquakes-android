package org.dzlebedzeu.earthquakelite.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.dzlebedzeu.earthquakelite.R;

public class BlockingSpinnerView extends FrameLayout {

    public BlockingSpinnerView(Context context) {
        super(context);

        init();
    }

    public BlockingSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public BlockingSpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        inflate(getContext(), R.layout.view_blocking_spinner, this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // intercepting click event
            }
        });
    }
}
