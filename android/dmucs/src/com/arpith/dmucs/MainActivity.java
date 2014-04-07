package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import net.sebastianopoggi.ui.GlowPadBackport.GlowPadView;
import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;

public class MainActivity extends SwarmActivity {

	// Drawer-------------
	private static final int MENU_OVERFLOW = 1;
	private static final String STATE_ACTIVE_POSITION = "net.simonvt.menudrawer.samples.ContentSample.activePosition";
	private static final String STATE_CONTENT_TEXT = "net.simonvt.menudrawer.samples.ContentSample.contentText";

	private MenuDrawer mMenuDrawer;

	private MenuAdapter mAdapter;
	private ListView mList;

	private int mActivePosition = -1;

	// EndDrawer-------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Drawer---------------------------------------------------------
		if (savedInstanceState != null) {
			mActivePosition = savedInstanceState.getInt(STATE_ACTIVE_POSITION);
		}

		
		Swarm.init(this, SwarmConsts.App.APP_ID, SwarmConsts.App.APP_AUTH);
		
		Parse.initialize(this, "6LFiTT4PXBrL6xdDtWQZetifXsDPH2tO1i4GG5Xa", "sGACSZocjP0YQNGuEvDEUYktRSMA5W10S9zwzW1B");
		PushService.setDefaultPushCallback(this, DMUCSNotification.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.subscribe(getBaseContext(), "disaster", DMUCSNotification.class);
		
		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT,
				Position.LEFT);
		mMenuDrawer.setContentView(R.layout.activity_main);

		List<Object> items = new ArrayList<Object>();
		items.add(new Item("Home", R.drawable.ic_launcher));
		items.add(new Item("Missing Person", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Donate", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Account", R.drawable.ic_action_refresh_dark));
		items.add(new Category("Settings"));
		items.add(new Item("General", R.drawable.ic_action_refresh_dark));
		items.add(new Item("Advanced", R.drawable.ic_action_select_all_dark));
		items.add(new Category("Information"));
		items.add(new Item("Maps", R.drawable.ic_action_select_all_dark));
		items.add(new Item("About us", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Report List", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Dashboard", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Leaderboards", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Notifications", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Scatternet", R.drawable.ic_action_select_all_dark));

		// A custom ListView is needed so the drawer can be notified when it's
		// scrolled. This is to update the position
		// of the arrow indicator.
		mList = new ListView(this);
		mAdapter = new MenuAdapter(items);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(mItemClickListener);

		mMenuDrawer.setMenuView(mList);

		// EndDrawer------------------------------------------------------

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
				Intent i;
				switch (target) {
				case 0:
					Toast.makeText(MainActivity.this, "Report Disaster",
							Toast.LENGTH_SHORT).show();
					i = new Intent(MainActivity.this, QuickReport.class);
					startActivity(i);
					break;
				case 2:
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem overflowItem = menu.add(0, MENU_OVERFLOW, 0, null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			overflowItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		overflowItem.setIcon(R.drawable.ic_menu_moreoverflow_normal_holo_light);
		return true;
	}

	// This method is called once the menu is selected

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_OVERFLOW:
			mMenuDrawer.toggleMenu();
			return true;
		}
		return true;
	}

	// Drawer---------------------------------------------------------------
	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mActivePosition = position;
			mMenuDrawer.setActiveView(view, position);
			Toast.makeText(getBaseContext(), "Position" + position,
					Toast.LENGTH_SHORT).show();
			Intent i;
			switch (position) {

			case 0:
				i = new Intent(MainActivity.this, MainActivity.class);
				startActivity(i);
				finish();
				break;
			case 1:
				i = new Intent(MainActivity.this, MissingPersonList.class);
				startActivity(i);
				break;
			case 2:
				i = new Intent(MainActivity.this, Donate.class);
				startActivity(i);
				break;
			case 3:
				i = new Intent(MainActivity.this, Account.class);
				startActivity(i);
				break;
			case 5:
				i = new Intent(MainActivity.this, Preference.class);
				startActivity(i);
				break;
			case 6:
				i = new Intent(MainActivity.this, APreference.class);
				startActivity(i);
				break;
			case 8:
				i = new Intent(MainActivity.this, Maps.class);
				startActivity(i);
				break;
			case 9:
				i = new Intent(MainActivity.this, About.class);
				startActivity(i);
				break;
			case 10:
				i = new Intent(MainActivity.this, ReportList.class);
				startActivity(i);
				break;
			case 11:
				//TODO: WOW...romove this
				
				SwarmLeaderboard.submitScore(SwarmConsts.Leaderboard.SCORE_ID, 10);
				Swarm.showDashboard();
				break;
			case 12:
				Swarm.showLeaderboards();
				break;
			case 13:
				i = new Intent(MainActivity.this, DMUCSNotification.class);
				startActivity(i);
				break;
			case 14:
				startActivity(new Intent("me.arpith.dmucscom.SCATTERACTIVITY"));
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
