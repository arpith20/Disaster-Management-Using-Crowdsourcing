package com.arpith.dmucs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.arpith.dmucs.SwarmConsts.Leaderboard;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;

public class SubmitScore extends SwarmActivity{
	public static boolean submitscore(Context c, int score) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(c);
		Boolean enabled = pref.getBoolean("gamification", true);
		if (enabled) {
			SharedPreferences uname = c.getSharedPreferences("user", 0);
			int currentPoints = uname.getInt("points", 0);
			int newPoints = currentPoints + score;
			
			SwarmLeaderboard.submitScore(Leaderboard.SCORE_ID, newPoints);
			
			SharedPreferences.Editor unameEdit = uname.edit();
			unameEdit.putInt("points", newPoints);
			unameEdit.commit();
			
			Log.d("Gamification", "Submitted score "+newPoints);
			return true;
		} else {
			Log.d("Gamification", "Option is off");
			return false;
		}
	}
}
