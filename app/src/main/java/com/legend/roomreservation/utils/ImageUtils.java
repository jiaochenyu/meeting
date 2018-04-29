package com.legend.roomreservation.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by Administrator on 2016/7/7.
 */
public class ImageUtils {

    public static void setImage(ImageView imageView, String path) {
        Glide.with(MyApplication.getInst())
                .load(path).centerCrop()
                .error(MyApplication.getInst().getResources().getDrawable(R.drawable.pic_fail))
                .into(imageView);
    }

    //这个需要加上前缀，需要的时候可以直接饮用
    public static void setPICImage(final ImageView imageView, String path) {
//        Glide.with(MyApplication.getInst()).load(Constants.Urls.PIC_URL+path).into(imageView);
        if (path != null) {
            Glide.with(MyApplication.getInst())
                    .load("" + path)
                    .asBitmap()
                    .centerCrop()
                    .error(MyApplication.getInst().getResources().getDrawable(R.drawable.pic_fail))
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MyApplication.getInst().getResources(), resource);
                            circularBitmapDrawable.setCornerRadius(4);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        }
    }

    public static void setCircleImage(final ImageView imageView, String path) {
        Glide.with(MyApplication.getInst()).load(path).asBitmap().centerCrop().error(MyApplication.getInst().getResources().getDrawable(R.drawable.login_tupian_def)).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MyApplication.getInst().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setRoundTransFormImage(final ImageView imageView, String path, int round, int def) {
        Glide.with(MyApplication.getInst()).load(path).transform(new GlideRoundTransForm(MyApplication.getInst(), round)).placeholder(def).error(def).into(imageView);
    }

    public static void setCircleImage(final ImageView imageView, int def, String path) {
        Glide.with(MyApplication.getInst())
                .load(path)
                .asBitmap()
                .centerCrop()
                .placeholder(def)
                .error(MyApplication.getInst().getResources().getDrawable(def)).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MyApplication.getInst().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    public static void setConrnerImage(final ImageView imageView, String path) {
        Glide.with(MyApplication.getInst()).load(path).asBitmap().centerCrop().placeholder(R.drawable.login_tupian_def).error(MyApplication.getInst().getResources().getDrawable(R.drawable.login_tupian_def)).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MyApplication.getInst().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(4);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setCircleDefImage(ImageView image, String path, int def) {
        Glide.with(MyApplication.getInst())
                .load(path)
                .placeholder(def)
                .fitCenter()
                .transform(new GlideCircleTransform(MyApplication.getInst()))
                .into(image);
    }

    public static void setAutoSizeConrnerImage(final ImageView imageView, String path) {
        Glide.with(MyApplication.getInst()).load(path).asBitmap().centerCrop().error(MyApplication.getInst().getResources().getDrawable(R.drawable.login_tupian_def)).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MyApplication.getInst().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(4);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setHeadImage(final ImageView imageView, String path) {
        if (path != null) {
            Glide.with(MyApplication.getInst())
                    .load(path)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.pic_fail)
                    .error(R.drawable.pic_fail)
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MyApplication.getInst().getResources(), resource);
                            circularBitmapDrawable.setCornerRadius(4);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        }
    }

    public static void setDefaultImage(ImageView imageView, String path, int defaultRes) {
        Glide.with(MyApplication.getInst())
                .load(path)
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
//                .fitCenter()
                .placeholder(MyApplication.getInst().getResources().getDrawable(defaultRes))
                .error(MyApplication.getInst().getResources().getDrawable(defaultRes))
                .into(imageView);
    }

    public static void setDefaultNormalImage(ImageView imageView, String path, int defaultRes) {
        Glide.with(MyApplication.getInst())
                .load(path)
                .placeholder(MyApplication.getInst().getResources().getDrawable(defaultRes))
                .error(MyApplication.getInst().getResources().getDrawable(defaultRes))
                .into(imageView);
    }




    public static synchronized Drawable byteToDrawable(String icon) {

        byte[] img = Base64.decode(icon.getBytes(), Base64.DEFAULT);
        Bitmap bitmap;
        if (img != null) {


            bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bitmap);

            return drawable;
        }
        return null;

    }

    public static synchronized String drawableToByte(Drawable drawable) {
        if (drawable != null) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;

            // 创建一个字节数组输出流,流的大小为size
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            // 将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();

            String icon = Base64.encodeToString(imagedata, Base64.DEFAULT);
            return icon;
        }
        return null;
    }


    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    public static void setBackgroundImage(final ImageView imageView, int drawable) {
        imageView.setBackgroundResource(drawable);
    }


    //该方法设置的默认图片的大图的默认图片
    public static void setImagePhoto(final ImageView img, String path) {
        Glide.with(MyApplication.getInst()).load(path).asBitmap().placeholder(R.drawable.login_tupian_def).error(R.drawable.login_tupian_def).into(new BitmapImageViewTarget(img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MyApplication.getInst().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(5);
                img.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
