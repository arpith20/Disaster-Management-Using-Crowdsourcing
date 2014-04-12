package com.arpith.dmucs;

import android.os.Bundle;
import android.widget.Toast;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;

public class Gamification extends SwarmActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamification);

		Swarm.init(this, SwarmConsts.App.APP_ID, SwarmConsts.App.APP_AUTH);
		SubmitScore.submitscore(getBaseContext(), 0);

		Bundle b = getIntent().getExtras();
		String task = b.getString("task");

		if (task.equals("DASHBOARD")) {
			Swarm.showDashboard();
		} else if (task.equals("LEADERBOARD")) {
			Swarm.showLeaderboards();
		} else if (task.equals("LOGOUT")) {
			Swarm.logOut();
			Toast.makeText(getBaseContext(), "You have been successfully logged out",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getBaseContext(), "Cannot determine Task",
					Toast.LENGTH_SHORT).show();
		}
		finish();

	}
}
