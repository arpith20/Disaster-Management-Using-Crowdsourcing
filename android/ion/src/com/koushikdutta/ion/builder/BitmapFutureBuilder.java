package com.koushikdutta.ion.builder;

import android.graphics.Bitmap;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.ion.bitmap.BitmapInfo;

/**
* Created by koush on 5/30/13.
*/
public interface BitmapFutureBuilder {
    /**
     * Perform the request and get the result as a Bitmap
     * @return
     */
    public Future<Bitmap> asBitmap();

    /**
     * Attempt to immediately retrieve the cached Bitmap info
     * @return
     */
    public BitmapInfo asCachedBitmap();
}
