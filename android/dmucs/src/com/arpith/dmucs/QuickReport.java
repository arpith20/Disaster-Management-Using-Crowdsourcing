package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class QuickReport extends SwipeBackActivity {
	
	Button Submit_Report;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_report);
		
		
		
		Spinner spinner1 = (Spinner) findViewById(R.id.category_spinner);
        List<String> list = new ArrayList<String>();
        list.add("Earthquake");
        list.add("Tsunami");
        list.add("Flood");
        list.add("Draught");
        list.add("Fire");
         
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                     (this, R.layout.spinner_item,list);
                      
        dataAdapter.setDropDownViewResource
                     (android.R.layout.simple_spinner_dropdown_item);
                      
        spinner1.setAdapter(dataAdapter);
		
		Submit_Report=(Button) findViewById(R.id.qr_submit);
		Submit_Report.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent (QuickReport.this, ReportServer.class);
				startActivity(i);
				finish();
			}
		});
		
		
		
	}

	@Override
    protected void onResume() {
        super.onResume();
        SwipeBackLayout mSwipeBackLayout;
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quick_report, menu);
		return true;
	}

}
