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
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.victor.che.util.StringUtil;
import com.victor.che.util.StringUtils;

import java.io.File;


public class FileTouchImageView extends UrlTouchImageView {

    public FileTouchImageView(Context ctx) {
        super(ctx);

    }

    public FileTouchImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    @Override
    public void setUrl(String imagePath) {
        if (StringUtils.isBlank(imagePath)) {
            return;
        }
        if (imagePath.startsWith("http")) {
            Glide.with(mContext)
                    .load(imagePath).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {
                            mImageView.setImageBitmap(bitmap);
                            mImageView.setVisibility(VISIBLE);
                            mProgressBar.setVisibility(GONE);
                        }
                    });
        } else {
            Glide.with(mContext)
                    .load(new File(imagePath)).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {
                            mImageView.setImageBitmap(bitmap);
                            mImageView.setVisibility(VISIBLE);
                            mProgressBar.setVisibility(GONE);
                        }
                    });
        }

    }


}
