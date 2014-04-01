package com.arpith.dmucs;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SituationReport extends SwipeBackActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_situation_report);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.situation_report, menu);
		return true;
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        SwipeBackLayout mSwipeBackLayout;
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

}
