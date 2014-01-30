package com.arpith.dmucs;

import net.sebastianopoggi.ui.GlowPadBackport.GlowPadView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);

		glowPad.setPointsMultiplier(8);

		glowPad.ping();

		glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
			@Override
			public void onGrabbed(View v, int handle) {
				// Do nothing
			}

			@Override
			public void onReleased(View v, int handle) {
				// Do nothing
			}

			@Override
			public void onTrigger(View v, int target) {
				Toast.makeText(MainActivity.this,
						"Target triggered! ID=" + target, Toast.LENGTH_SHORT)
						.show();
				glowPad.reset(true);
			}

			@Override
			public void onGrabbedStateChange(View v, int handle) {
				// Do nothing
			}

			@Override
			public void onFinishFinalAnimation() {
				// Do nothing
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
