package com.example.administrator.testa.cropImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * ImageCompress 提供用户将大图片按比例压缩
 * 
 * @author lilong
 * 
 */
public class ImageUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 设置圆角
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param bitmap
	* @param @param roundPixels
	* @param @return    设定文件 
	* @return Bitmap    返回类型 
	* @throws
	 */
	public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels)
    {
        //创建一个和原始图片一样大小位图
        Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
        //创建带有位图roundConcerImage的画布
        Canvas canvas = new Canvas(roundConcerImage);
        //创建画笔
        Paint paint = new Paint();
        //创建一个和原始图片一样大小的矩形
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        // 去锯齿
        paint.setAntiAlias(true);
        //画一个和原始图片一样大小的圆角矩形
        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
        //设置相交模式
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        //把图片画到矩形去
        canvas.drawBitmap(bitmap, null, rect, paint);
        return roundConcerImage;
    }
	
	/**
	 * @Description 计算图片的缩放值,一般手机的分辨率为 480*800(可在reqWidth与reqHeight设定分辨率)
	 * @param options
	 * @param reqWidth
	 *            宽度设定
	 * @param reqHeight
	 *            高度设定
	 * @return int
	 * @author ll
	 * @date 2013-7-22
	 * @modified by
	 * @update-time 2013-7-22 上午11:35:51
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {

		// 计算压缩比例
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		/*
		 * heightRatio是图片原始高度与压缩后高度的倍数，widthRatio是图片原始宽度与压缩后宽度的倍数。
		 * inSampleSize为heightRatio与widthRatio中最小的那个，inSampleSize就是缩放值。
		 * inSampleSize为1表示宽度和高度不缩放，为2表示压缩后的宽度与高度为原来的1/2
		 */
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * @Description 根据原图路径获得图片并压缩，返回bitmap用于显示，并保存路径
	 * @param filePath
	 *            原图目录
	 * @param outPaths
	 *            压缩后目录
	 * @param reqWidth
	 *            图片宽
	 * @param reqHeight
	 *            图片高
	 * @return
	 * @throws FileNotFoundException
	 * @author ll
	 * @date 2013-8-27
	 * @modified by
	 * @update-time 2013-8-27 下午01:35:48
	 */
	public static Bitmap getSmallBitmap(String filePath, String outPaths,
			int reqWidth, int reqHeight) throws FileNotFoundException {
		try {
			Bitmap bitmap = null;
			final BitmapFactory.Options options = new BitmapFactory.Options();
			// inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, options);
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqWidth);
			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(filePath, options);
			// 此时返回bitmap为空

			if (bitmap == null) {
				return null;
			}

			try {
				File file = new File(outPaths);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				// if (bitmap.isRecycled() == false) {// 如果没有回收
				// bitmap.recycle();
				// System.gc(); // 提醒系统及时回收
				// }
			} catch (IOException e) {
				e.printStackTrace();
			}

			return bitmap;
		} catch (OutOfMemoryError e) {
			Log.e("MYTAG",
					"decodeSampledBitmapFromResource内存溢出，如果频繁出现这个情况 可以尝试配置增加内存缓存大小");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Description 只保存图片不压缩
	 * @param bitmap
	 * @return
	 * @throws FileNotFoundException
	 * @author ll
	 * @date 2013-8-6
	 * @modified by
	 * @update-time 2013-8-6 下午05:06:47
	 */
	public static String getBigBitmap(Bitmap bitmap, Context context,
			String filePath) {
		String path = null;
		try {
			File pictureFile = FileUtils.getOutputFile(FileUtils.CREATE_FILE,
					context);
			if (pictureFile != null) {
				path = pictureFile.getPath().toString();
				FileOutputStream out = new FileOutputStream(path);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return path;
	}

	/**
	 * @Description 根据路径获得图片并压缩，返回bitmap用于显示
	 * @param filePath
	 *            原图目录
	 * @return
	 * @throws FileNotFoundException
	 * @author ll
	 * @date 2013-7-29
	 * @modified by
	 * @update-time 2013-7-29 上午09:48:38
	 */
	public static Bitmap getSmallBitmap(String filePath, int reqWidth,
			int reqHeight) throws FileNotFoundException {
		try {
			Bitmap bitmap = null;
			final BitmapFactory.Options options = new BitmapFactory.Options();
			// inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, options);
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(filePath, options);

			if (bitmap == null) {
				return null;
			}
			ByteArrayOutputStream baos = null;
			try {

				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 60, baos);// 60
																		// 是压缩率，表示压缩40%;
																		// 如果不压缩是100，表示压缩率为0
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return bitmap;
		} catch (OutOfMemoryError e) {
			Log.e("MYTAG",
					"decodeSampledBitmapFromResource内存溢出，如果频繁出现这个情况 可以尝试配置增加内存缓存大小");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Description byte转换 保存路径，并返回bitmap
	 * @param filePath
	 *            原图目录
	 * @param outPath
	 *            输出目录
	 * @return bitmap
	 * @throws FileNotFoundException
	 * @author ll
	 * @date 2013-7-22
	 * @modified by
	 * @update-time 2013-7-22 下午02:51:34
	 */
	public static Bitmap getSmallBitmap(byte[] byteData, String outPaths,
			int reqWidth, int reqHeight) throws FileNotFoundException {

		try {
			Bitmap bitmap = null;
			final BitmapFactory.Options options = new BitmapFactory.Options();
			// inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
			options.inJustDecodeBounds = true;
			BitmapFactory
					.decodeByteArray(byteData, 0, byteData.length, options);
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			options.inTempStorage = new byte[5 * 1024]; // 设置16MB的临时存储空间
			bitmap = BitmapFactory.decodeByteArray(byteData, 0,
					byteData.length, options);
			// 此时返回bitmap为空

			if (bitmap == null) {
				return null;
			}
			try {

				File file = new File(outPaths);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
				Log.d("MYTAG", "压缩完毕");
				if (bos != null) {
					bos.flush();
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return bitmap;
		} catch (OutOfMemoryError e) {
			Log.e("MYTAG",
					"decodeSampledBitmapFromResource内存溢出，如果频繁出现这个情况 可以尝试配置增加内存缓存大小");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将Bitmap转换成指定大小
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

	/**
	 * Drawable 转 Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * @Description Drawable 转 Bitmap
	 * @param drawable
	 * @return
	 * @author ll
	 * @date 2013-8-29
	 * @modified by
	 * @update-time 2013-8-29 下午01:18:38
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap 转 Drawable
	 *
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * byte[] 转 bitmap
	 *
	 * @param b
	 * @return
	 */
	public static Bitmap bytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * bitmap 转 byte[]
	 *
	 * @param bm
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * Byte to bitmap
	 *
	 * @param bytes
	 * @param opts
	 * @return
	 */
	public static Bitmap getBitmapFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null) {
			if (opts != null) {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			} else {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			}
		}

		return null;
	}

	/**
	 * InputStream to byte
	 *
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public byte[] readInputStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}

		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();

		return data;
	}

	/** 水平方向模糊度 */
	private static float hRadius = 5;
	/** 竖直方向模糊度 */
	private static float vRadius = 5;
	/** 模糊迭代度 */
	private static int iterations = 7;

	/**
	 * 高斯模糊
	 *
	 * @param bmp
	 * @return
	 * @author LiLong
	 * @date 2014-4-21
	 * @modified by
	 * @update-time 2014-4-21 下午02:34:29
	 */
	public static Drawable BoxBlurFilter(Bitmap bmp) {

		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);

		bmp.getPixels(inPixels, 0, width, 0, 0, width, height);

		for (int i = 0; i < iterations; i++) {
			blur(inPixels, outPixels, width, height, hRadius);
			blur(outPixels, inPixels, height, width, vRadius);
		}

		blurFractional(inPixels, outPixels, width, height, hRadius);
		blurFractional(outPixels, inPixels, height, width, vRadius);
		bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * 核心算法
	 */
	public static void blur(int[] in, int[] out, int width, int height,float radius) {

		int widthMinus1 = width - 1;
		int r = (int) radius;
		int tableSize = 2 * r + 1;
		int divide[] = new int[256 * tableSize];
		for (int i = 0; i < 256 * tableSize; i++)
			divide[i] = i / tableSize;
		int inIndex = 0;
		for (int y = 0; y < height; y++) {
			int outIndex = y;
			int ta = 0, tr = 0, tg = 0, tb = 0;
			for (int i = -r; i <= r; i++) {
				int rgb = in[inIndex + clamp(i, 0, width - 1)];
				ta += (rgb >> 24) & 0xff;
				tr += (rgb >> 16) & 0xff;
				tg += (rgb >> 8) & 0xff;
				tb += rgb & 0xff;
			}
			for (int x = 0; x < width; x++) {
				out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16)
				| (divide[tg] << 8) | divide[tb];
				int i1 = x + r + 1;
				if (i1 > widthMinus1)
					i1 = widthMinus1;
				int i2 = x - r;
				if (i2 < 0)
					i2 = 0;
				int rgb1 = in[inIndex + i1];
				int rgb2 = in[inIndex + i2];

				ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
				tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
				tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
				tb += (rgb1 & 0xff) - (rgb2 & 0xff);
				outIndex += height;
			}
			inIndex += width;
		}
	}
	
	public static void blurFractional(int[] in, int[] out, int width,int height, float radius) {

        radius -= (int) radius;
        float f = 1.0f / (1 + 2 * radius);
        int inIndex = 0;

        for (int y = 0; y < height; y++) {
        	
            int outIndex = y;
            out[outIndex] = in[0];
            outIndex += height;

            for (int x = 1; x < width - 1; x++) {
            	
            	int i = inIndex + x;
                int rgb1 = in[i - 1];
                int rgb2 = in[i];
                int rgb3 = in[i + 1];
                int a1 = (rgb1 >> 24) & 0xff;
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;
                int a2 = (rgb2 >> 24) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;
                int a3 = (rgb3 >> 24) & 0xff;
                int r3 = (rgb3 >> 16) & 0xff;
                int g3 = (rgb3 >> 8) & 0xff;
                int b3 = rgb3 & 0xff;

                a1 = a2 + (int) ((a1 + a3) * radius);
                r1 = r2 + (int) ((r1 + r3) * radius);
                g1 = g2 + (int) ((g1 + g3) * radius);
                b1 = b2 + (int) ((b1 + b3) * radius);
                a1 *= f;
                r1 *= f;
                g1 *= f;
                b1 *= f;
                out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
                outIndex += height;
            }
            out[outIndex] = in[width - 1];
            inIndex += width;

        }
    }
	
	public static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }
}
