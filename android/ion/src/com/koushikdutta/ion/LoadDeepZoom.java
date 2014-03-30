package com.koushikdutta.ion;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.libcore.DiskLruCache;
import com.koushikdutta.async.http.libcore.IoUtils;
import com.koushikdutta.ion.bitmap.BitmapInfo;
import com.koushikdutta.ion.gif.GifAction;
import com.koushikdutta.ion.gif.GifDecoder;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by koush on 1/5/14.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class LoadDeepZoom extends LoadBitmapEmitter implements FutureCallback<File> {
    DiskLruCache diskLruCache;
    public LoadDeepZoom(Ion ion, String urlKey, boolean animateGif, IonRequestBuilder.EmitterTransform<File> emitterTransform, DiskLruCache diskLruCache) {
        super(ion, urlKey, true, animateGif, emitterTransform);
        this.diskLruCache = diskLruCache;
    }

    @Override
    public void onCompleted(Exception e, final File file) {
        if (e != null) {
            report(e, null);
            return;
        }

        if (ion.bitmapsPending.tag(key) != this) {
//            Log.d("IonBitmapLoader", "Bitmap load cancelled (no longer needed)");
            return;
        }

        Ion.getBitmapLoadExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                FileInputStream fin = null;
                DiskLruCache.Editor editor = null;
                try {
                    if (diskLruCache != null) {
                        editor = diskLruCache.edit(key);
                        editor.set(1, "");
                        editor.commit(true);
                    }

                    BitmapFactory.Options options = ion.getBitmapCache().prepareBitmapOptions(file, 0, 0);
                    if (options == null)
                        throw new Exception("BitmapFactory.Options failed to load");
                    final Point size = new Point(options.outWidth, options.outHeight);
                    if (animateGif && TextUtils.equals("image/gif", options.outMimeType)) {
                        fin = new FileInputStream(file);
                        GifDecoder decoder = new GifDecoder(fin, new GifAction() {
                            @Override
                            public boolean parseOk(boolean parseStatus, int frameIndex) {
                                return animateGif;
                            }
                        });
                        decoder.run();
                        if (decoder.getFrameCount() == 0)
                            throw new Exception("failed to load gif");
                        Bitmap[] bitmaps = new Bitmap[decoder.getFrameCount()];
                        int[] delays = decoder.getDelays();
                        for (int i = 0; i < decoder.getFrameCount(); i++) {
                            Bitmap bitmap = decoder.getFrameImage(i);
                            if (bitmap == null)
                                throw new Exception("failed to load gif frame");
                            bitmaps[i] = bitmap;
                        }
                        BitmapInfo info = new BitmapInfo(key, options.outMimeType, bitmaps, size);
                        info.delays = delays;
                        if (emitterTransform != null)
                            info.loadedFrom = emitterTransform.loadedFrom();
                        else
                            info.loadedFrom = Loader.LoaderEmitter.LOADED_FROM_CACHE;
                        report(null, info);
                        return;
                    }

                    BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(file.toString(), false);
                    Bitmap bitmap = decoder.decodeRegion(new Rect(0, 0, size.x, size.y), options);
                    if (bitmap == null)
                        throw new Exception("unable to load decoder");
                    Bitmap[] bitmaps = new Bitmap[] { bitmap };

                    BitmapInfo info = new BitmapInfo(key, options.outMimeType, bitmaps, size);
                    info.decoder = decoder;
                    info.decoderFile = file;
                    info.loadedFrom = Loader.LoaderEmitter.LOADED_FROM_NETWORK;
                    report(null, info);
                } catch (Exception e) {
                    report(e, null);
                    try {
                        if (editor != null)
                            editor.abort();
                    }
                    catch (Exception ex) {
                    }
                }
                finally {
                    IoUtils.closeQuietly(fin);
                }
            }
        });
    }
}
