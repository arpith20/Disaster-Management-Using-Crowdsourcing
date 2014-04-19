package com.arpith.dmucs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DonateFragment extends Fragment {
	EditText id;
	int amount = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_donate, container,
				false);

		final Button submit = (Button) rootView.findViewById(R.id.submit);
		id = (EditText) rootView.findViewById(R.id.id);
		Button qr = (Button) rootView.findViewById(R.id.qr);
		Button add = (Button) rootView.findViewById(R.id.add);
		Button sub = (Button) rootView.findViewById(R.id.sub);
		qr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {

					Intent intent = new Intent(
							"com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

					startActivityForResult(intent, 0);

				} catch (Exception e) {

					Uri marketUri = Uri
							.parse("market://details?id=com.google.zxing.client.android");
					Intent marketIntent = new Intent(Intent.ACTION_VIEW,
							marketUri);
					startActivity(marketIntent);

				}

			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SubmitScore.submitscore(getActivity(), SwarmConsts.Scores.DONATE_MONEY);
				SharedPreferences uname = getActivity().getSharedPreferences(
						"user", 0);
				String user = uname.getString("name", "null");
				String uid = id.getText().toString();

				Intent i = new Intent(getActivity(), WriteQueryDatabase.class);
				String query = "insert into donate_money (phone,phone_by,amount)values (\"" + uid
						+ "\",\"" + user + "\"," + amount
						+ ");|update donate set amount=amount+" + amount
						+ " where uniqueid=\"" + uid + "\";";
				Bundle bundle = new Bundle();
				bundle.putString("query", query);
				bundle.putString("text", "Donated an amount of INR " + amount
						+ "\nThank You");
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				amount += 1;
				submit.setText("Donate INR " + amount);
			}
		});
		sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (amount > 0) {
					amount -= 1;
					submit.setText("Donate INR " + amount);
				}

			}
		});
		add.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Enter amount");
				builder.setMessage("We thank you for this donation!");

				final EditText input = new EditText(getActivity());
				// Specify the type of input expected; this, for example, sets
				// the input as a password, and will mask the text
				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				builder.setView(input);

				// Set up the buttons
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								amount = Integer.parseInt(input.getText()
										.toString());
								String m_Text = "Donate INR "
										+ input.getText().toString();
								submit.setText(m_Text);
							}
						});
				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});

				builder.show();
				return true;
			}
		});
		sub.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				amount = 0;
				submit.setText("Donate INR " + amount);
				return true;
			}
		});

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {

			if (resultCode == -1) {
				String contents = data.getStringExtra("SCAN_RESULT");
				id.setText(contents);
			} else {
				Toast.makeText(getActivity(),
						"QR code not detected\nEnter UniqueID manually",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
