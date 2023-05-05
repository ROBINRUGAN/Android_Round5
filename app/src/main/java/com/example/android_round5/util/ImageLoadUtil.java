package com.example.android_round5.util;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.view.SimpleDraweeView;

//自定义封装了加载图片的工具类
public final class ImageLoadUtil {
    private static final int OPENGL_MAX_SIZE = 0;

    /**
     * 最基础的图片加载方法
     *
     * @param imageView 需要加载图片的控件
     * @param uri       图片的路径
     *
     * @link 支持的Uri{https://www.fresco-cn.org/docs/supported-uris.html}
     */
    public static void loadImgByUri(SimpleDraweeView imageView, String uri) {
        if (imageView != null && !TextUtils.isEmpty(uri)) {
            if (uri.startsWith("file://")) {
                loadImgByFile(imageView, uri.substring("file://".length()));
            } else {
                imageView.setImageURI(uri);
            }
        }
    }

    public static void loadImgByFile(SimpleDraweeView imageView, String path) {
        if (imageView != null && !TextUtils.isEmpty(path)) {

            imageView.setImageURI(Uri.parse("file://" + path));
        }
    }

    public static void loadImgByNet(SimpleDraweeView imageView, String url) {
        if (imageView != null && !TextUtils.isEmpty(url)) {
            imageView.setImageURI(Uri.parse(url));
        }
    }

    public static void loadImgByContentProvider(SimpleDraweeView imageView, String path) {
        if (imageView != null && !TextUtils.isEmpty(path)) {
            imageView.setImageURI(Uri.parse("content://" + path));
        }
    }

    public static void loadImgByAsset(SimpleDraweeView imageView, String path) {
        if (imageView != null && !TextUtils.isEmpty(path)) {
            imageView.setImageURI(Uri.parse("asset://" + path));
        }
    }

    public static void loadImgByResources(SimpleDraweeView imageView, int resourcesId) {
        if (imageView != null) {
            imageView.setImageURI(Uri.parse("res://aaa/" + resourcesId));
        }
    }

    public static void loadImgByBase64(SimpleDraweeView imageView, String base64, String mimeType) {
        if (imageView != null && !TextUtils.isEmpty(mimeType) && !TextUtils.isEmpty(base64)) {
            imageView.setImageURI(Uri.parse("data:" + mimeType + ";" + base64));
        }
    }
}