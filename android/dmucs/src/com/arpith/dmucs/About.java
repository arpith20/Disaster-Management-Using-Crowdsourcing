package com.arpith.dmucs;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

public class About extends Activity {

	private GoogleCardsAdapter mGoogleCardsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_googlecards);
		
		Toast.makeText(getBaseContext(), "Names are displayed in alphabetical order", Toast.LENGTH_SHORT).show();

		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

		mGoogleCardsAdapter = new GoogleCardsAdapter(this);
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(mGoogleCardsAdapter);
		swingBottomInAnimationAdapter.setAbsListView(listView);

		listView.setAdapter(swingBottomInAnimationAdapter);

		mGoogleCardsAdapter.addAll(getItems());
	}

	private ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			items.add(i);
		}
		return items;
	}

	

	private static class GoogleCardsAdapter extends ArrayAdapter<Integer> {

		private Context mContext;
		private LruCache<Integer, Bitmap> mMemoryCache;

		public GoogleCardsAdapter(Context context) {
			mContext = context;

			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

			// Use 1/8th of the available memory for this memory cache.
			final int cacheSize = maxMemory;
			mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(Integer key, Bitmap bitmap) {
					// The cache size will be measured in kilobytes rather than
					// number of items.
					return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				}
			};
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(R.layout.activity_googlecards_card, parent, false);

				viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) view.findViewById(R.id.activity_googlecards_card_textview);
				viewHolder.textView_mail = (TextView) view.findViewById(R.id.activity_googlecards_card_mail);
				viewHolder.textView_git = (TextView) view.findViewById(R.id.activity_googlecards_card_github);
				view.setTag(viewHolder);

				viewHolder.imageView = (ImageView) view.findViewById(R.id.activity_googlecards_card_imageview);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			viewHolder.textView.setText("This is card " + (getItem(position) + 1));
			switch(position){
			case 0:
				viewHolder.textView.setText("Arpith");
				viewHolder.textView_mail.setText("arpith@live.com");
				viewHolder.textView_git.setText("github.com/arpith20");
				break;
			case 1:
				viewHolder.textView.setText("Kumar Visahl");
				viewHolder.textView_mail.setText("null@example.com");
				viewHolder.textView_git.setText("github.com/null");
				break;
			case 2:
				viewHolder.textView.setText("Vinutha");
				viewHolder.textView_mail.setText("null@example.com");
				viewHolder.textView_git.setText("github.com/null");
				break;
			case 3:
				viewHolder.textView.setText("Mani");
				viewHolder.textView_mail.setText("null@example.com");
				viewHolder.textView_git.setText("github.com/null");
				break;

			}
			
			setImageView(viewHolder, position);

			return view;
		}
		

		private void setImageView(ViewHolder viewHolder, int position) {
			int imageResId;
			switch (getItem(position) % 5) {
				case 0:
					imageResId = R.drawable.ic_launcher;
					break;
				case 1:
					imageResId = R.drawable.ic_launcher;
					break;
				case 2:
					imageResId = R.drawable.ic_launcher;
					break;
				case 3:
					imageResId = R.drawable.ic_launcher;
					break;
				default:
					imageResId = R.drawable.ic_launcher;
			}

			Bitmap bitmap = getBitmapFromMemCache(imageResId);
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageResId);
				addBitmapToMemoryCache(imageResId, bitmap);
			}
			viewHolder.imageView.setImageBitmap(bitmap);
		}

		private void addBitmapToMemoryCache(int key, Bitmap bitmap) {
			if (getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap);
			}
		}

		private Bitmap getBitmapFromMemCache(int key) {
			return mMemoryCache.get(key);
		}

		private static class ViewHolder {
			public TextView textView_git;
			public TextView textView_mail;
			TextView textView;
			ImageView imageView;
		}
	}

}