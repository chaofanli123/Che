/*
 Copyright (c) 2012 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.victor.che.touchgallery.TouchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


public class UrlTouchImageView extends RelativeLayout {
    protected ProgressBar mProgressBar;
    protected TouchImageView mImageView;

    protected Context mContext;

    public UrlTouchImageView(Context ctx) {
        super(ctx);
        mContext = ctx;
        init();

    }

    public UrlTouchImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        mContext = ctx;
        init();
    }

    public TouchImageView getImageView() { return mImageView; }

    @SuppressWarnings("deprecation")
    protected void init() {
        mImageView = new TouchImageView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mImageView.setLayoutParams(params);
        this.addView(mImageView);
        mImageView.setVisibility(GONE);

        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyle);
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMargins(30, 0, 30, 0);
        mProgressBar.setLayoutParams(params);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setMax(100);
        this.addView(mProgressBar);
    }

    public void setUrl(String imageUrl) {

        mProgressBar.setProgress(100);
        mProgressBar.setVisibility(VISIBLE);
        Glide.with(mContext)
                .load(imageUrl).asBitmap()
                .thumbnail(0.1f)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                GlideAnimation<? super Bitmap> glideAnimation) {
                        mImageView.setImageBitmap(bitmap);
                        mImageView.setVisibility(VISIBLE);
                        mProgressBar.setVisibility(GONE);
                    }
                });

        //new ImageLoadTask().execute(imageUrl);
    }

    public void setScaleType(ScaleType scaleType) {
        mImageView.setScaleType(scaleType);
    }

    //No caching load
    public class ImageLoadTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
         /*   Bitmap bm = null;
            try {
                double oW = bm.getWidth();
                double oH = bm.getHeight();

                double wid = DisplayUtil.getDisplayWidthPixels(mContext);
                double hid = DisplayUtil.getDisplayheightPixels(mContext);

                if (oW > wid) {
                    bm = Picasso.with(mContext).load(url).config(Bitmap.Config.RGB_565)..get();
                } else {
                    bm = Picasso.with(mContext).load(url).config(Bitmap.Config.RGB_565).resize((int) oW, (int) oH).get();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }*/
          /*  try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                int totalLen = conn.getContentLength();
                InputStreamWrapper bis = new InputStreamWrapper(is, 8192, totalLen);
                bis.setProgressListener(new InputStreamWrapper.InputStreamProgressListener() {
                    @Override
                    public void onProgress(float progressValue, long bytesLoaded,
                                           long bytesTotal) {
                        publishProgress((int) (progressValue * 100));
                    }
                });
                //EaseImageUtils.decodeScaleImage(bis, 320, 320);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return url;
        }

        @Override
        protected void onPostExecute(String bitmap) {
          /*  if (bitmap == null) {
                mImageView.setScaleType(ScaleType.CENTER);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_no);
                mImageView.setImageBitmap(bitmap);
            } else {
                mImageView.setScaleType(ScaleType.MATRIX);
                mImageView.setImageBitmap(bitmap);
            }*/


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }
    }

    public ProgressBar getmProgressBar() {
        return mProgressBar;
    }
}
