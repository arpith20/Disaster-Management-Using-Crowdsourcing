package com.koushikdutta.ion.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by koush on 2/1/14.
 */
public class DeepZoomSample extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoView photoView = new PhotoView(this);
        photoView.setMaximumScale(16);
        setContentView(photoView);

        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setTitle("Loading...");
        dlg.setIndeterminate(false);
        dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dlg.show();

        // this is going to load a 30mb download...
        Ion.with(this)
        .load("https://raw2.github.com/koush/ion/master/ion-sample/telescope.jpg")
        .progressDialog(dlg)
        .withBitmap()
        .deepZoom()
        .intoImageView(photoView)
        .setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                dlg.cancel();
            }
        });
    }
}
