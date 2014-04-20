package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import net.sebastianopoggi.ui.GlowPadBackport.GlowPadView;
import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;

public class MainActivity extends SwarmActivity {

	private MenuDrawer mMenuDrawer;

	TextView tv;

	private MenuAdapter mAdapter;
	private ListView mList;

	private int mActivePosition = -1;

	// EndDrawer-------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Swarm.preload(MainActivity.this, SwarmConsts.App.APP_ID,
				SwarmConsts.App.APP_AUTH);

		Parse.initialize(this, "6LFiTT4PXBrL6xdDtWQZetifXsDPH2tO1i4GG5Xa",
				"sGACSZocjP0YQNGuEvDEUYktRSMA5W10S9zwzW1B");
		PushService.setDefaultPushCallback(this, DMUCSNotification.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.subscribe(getBaseContext(), "disaster",
				DMUCSNotification.class);

		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT,
				Position.LEFT);
		mMenuDrawer.setContentView(R.layout.activity_main);

		List<Object> items = new ArrayList<Object>();
		items.add(new Item("Home", R.drawable.ic_launcher));

		items.add(new Category("Incident Module"));
		items.add(new Item("Report an incident", R.drawable.icon_report));
		items.add(new Item("Report List", R.drawable.icon_list));

		items.add(new Category("Missing Person Module"));
		items.add(new Item("Report missing person", R.drawable.icon_missing));
		items.add(new Item("Missing People list", R.drawable.icon_miss_list));

		items.add(new Category("Donation"));
		items.add(new Item("Donate", R.drawable.icon_donate));

		items.add(new Category("Settings"));
		items.add(new Item("General", R.drawable.icon_setting));
		items.add(new Item("Advanced", R.drawable.icon_advance));
		items.add(new Item("Account Information", R.drawable.icon_account));

		items.add(new Category("Gamification"));
		items.add(new Item("Dashboard", R.drawable.icon_dash));
		items.add(new Item("Leaderboards", R.drawable.icon_leaderboard));
		items.add(new Item("Logout", R.drawable.icon_logout));

		items.add(new Category("Other"));
		items.add(new Item("Scatternet", R.drawable.icon_scatter));
		items.add(new Item("Maps", R.drawable.icon_map));
		items.add(new Item("Notifications", R.drawable.icon_notification));
		items.add(new Item("About us", R.drawable.icon_about));

		// A custom ListView is needed so the drawer can be notified when it's
		// scrolled. This is to update the position
		// of the arrow indicator.
		mList = new ListView(this);
		mAdapter = new MenuAdapter(items);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(mItemClickListener);

		mMenuDrawer.setMenuView(mList);

		// EndDrawer------------------------------------------------------

		tv = (TextView) findViewById(R.id.textView1);

		final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);
		glowPad.setPointsMultiplier(8);
		glowPad.ping();
		glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
			@Override
			public void onGrabbed(View v, int handle) {
				tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				tv.setText("Swipe right to report an incident\nSwipe left to report a missing person");
			}

			@Override
			public void onReleased(View v, int handle) {
				tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
				tv.setText("Perform Quick action");
			}

			@Override
			public void onTrigger(View v, int target) {
				Intent i;
				switch (target) {
				case 0:
					Toast.makeText(MainActivity.this, "Report Disaster",
							Toast.LENGTH_SHORT).show();
					i = new Intent(MainActivity.this, ReportIncident.class);
					startActivity(i);
					break;
				case 2:
					Toast.makeText(getBaseContext(), "Please wait..",
							Toast.LENGTH_LONG).show();
					i = new Intent(MainActivity.this, MissingPersonReport.class);
					startActivity(i);
					break;
				}
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

	// Drawer---------------------------------------------------------------
	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mActivePosition = position;
			mMenuDrawer.setActiveView(view, position);

			Intent i;
			switch (position) {

			case 0:
				i = new Intent(MainActivity.this, MainActivity.class);
				startActivity(i);
				finish();
				break;
			case 2:
				i = new Intent(MainActivity.this, QuickReport.class);
				startActivity(i);
				break;
			case 5:
				i = new Intent(MainActivity.this, MissingPersonReport.class);
				startActivity(i);
				break;
			case 6:
				i = new Intent(MainActivity.this, MissingPersonList.class);
				i.putExtra("within", "10");
				startActivity(i);
				break;
			case 8:
				i = new Intent(MainActivity.this, Donate.class);
				i.putExtra("within", "20");
				startActivity(i);
				break;
			case 12:
				i = new Intent(MainActivity.this, Account.class);
				startActivity(i);
				break;
			case 10:
				i = new Intent(MainActivity.this, Preference.class);
				startActivity(i);
				break;
			case 11:
				i = new Intent(MainActivity.this, APreference.class);
				startActivity(i);
				break;
			case 19:
				i = new Intent(MainActivity.this, Maps.class);
				startActivity(i);
				break;
			case 21:
				i = new Intent(MainActivity.this, About.class);
				startActivity(i);
				break;
			case 3:
				i = new Intent(MainActivity.this, ReportList.class);
				i.putExtra("within", "20");
				startActivity(i);
				break;
			case 14:
				Intent activity_dash = new Intent(MainActivity.this,
						Gamification.class);
				activity_dash.putExtra("task", "DASHBOARD");
				startActivity(activity_dash);
				break;
			case 15:
				Intent activity_leader = new Intent(MainActivity.this,
						Gamification.class);
				activity_leader.putExtra("task", "LEADERBOARD");
				startActivity(activity_leader);
				break;
			case 20:
				i = new Intent(MainActivity.this, DMUCSNotification.class);
				startActivity(i);
				break;
			case 18:
				startActivity(new Intent("me.arpith.dmucscom.SCATTERACTIVITY"));
				break;
			case 16:
				Intent activity_logout = new Intent(MainActivity.this,
						Gamification.class);
				activity_logout.putExtra("task", "LOGOUT");
				startActivity(activity_logout);
				break;
			}
			mMenuDrawer.closeMenu();
		}
	};

	private static class Item {

		String mTitle;
		int mIconRes;

		Item(String title, int iconRes) {
			mTitle = title;
			mIconRes = iconRes;
		}
	}

	private static class Category {

		String mTitle;

		Category(String title) {
			mTitle = title;
		}
	}

	private class MenuAdapter extends BaseAdapter {

		private List<Object> mItems;

		MenuAdapter(List<Object> items) {
			mItems = items;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return getItem(position) instanceof Item ? 0 : 1;
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public boolean isEnabled(int position) {
			return getItem(position) instanceof Item;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			Object item = getItem(position);

			if (item instanceof Category) {
				if (v == null) {
					v = getLayoutInflater().inflate(R.layout.menu_row_category,
							parent, false);
				}

				((TextView) v).setText(((Category) item).mTitle);

			} else {
				if (v == null) {
					v = getLayoutInflater().inflate(R.layout.menu_row_item,
							parent, false);
				}

				TextView tv = (TextView) v;
				tv.setText(((Item) item).mTitle);
				tv.setCompoundDrawablesWithIntrinsicBounds(
						((Item) item).mIconRes, 0, 0, 0);
			}

			v.setTag(R.id.mdActiveViewPosition, position);

			if (position == mActivePosition) {
				mMenuDrawer.setActiveView(v, position);
			}

			return v;
		}

	}

	// EndDrawer-------------------------------------------------------------
}
