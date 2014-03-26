package me.arpith.dmucscom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Alerts {
	public static void showInfoDialog(Context context, String title, String information) {     
		new AlertDialog.Builder(context)
		.setMessage(information)
		.setTitle(title)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();       
	}

}



