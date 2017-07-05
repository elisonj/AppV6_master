package com.wslibrary.bg7.ws;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import com.wslibrary.bg7.util.ImageLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

	public static final int THUMB_HEIGHT = 150;
	public static final int THUMB_WIDTH = 150;

	/**
	 * Compress to format JPEG
	 * 
	 * @param bitmap
	 * @param quality
	 * @return
	 */
	public static ByteArrayOutputStream compressBitmapFormatJPEG(Bitmap bitmap, int quality) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, quality, bos);
		return bos;
	}

	
	public static int getOrientation(Context context, Uri photoUri) {
	    /* it's on the external media. */
	    Cursor cursor = context.getContentResolver().query(photoUri,
	            new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

	    if (cursor.getCount() != 1) {
	        return -1;
	    }

	    cursor.moveToFirst();
	    return cursor.getInt(0);
	}
	
	
	public static Drawable getCorrectlyOrientedImage(Context context, Uri photoUri) throws IOException {
	    InputStream is = context.getContentResolver().openInputStream(photoUri);
	    BitmapFactory.Options dbo = new BitmapFactory.Options();
	    dbo.inJustDecodeBounds = true;
	    BitmapFactory.decodeStream(is, null, dbo);
	    is.close();

	    int rotatedWidth, rotatedHeight;
	    int orientation = getOrientation(context, photoUri);

	    if (orientation == 90 || orientation == 270) {
	        rotatedWidth = dbo.outHeight;
	        rotatedHeight = dbo.outWidth;
	    } else {
	        rotatedWidth = dbo.outWidth;
	        rotatedHeight = dbo.outHeight;
	    }

	    Bitmap srcBitmap;
	    is = context.getContentResolver().openInputStream(photoUri);
	    if (rotatedWidth > THUMB_WIDTH || rotatedHeight > THUMB_HEIGHT) {
	        float widthRatio = ((float) rotatedWidth) / ((float) THUMB_WIDTH);
	        float heightRatio = ((float) rotatedHeight) / ((float) THUMB_HEIGHT);
	        float maxRatio = Math.max(widthRatio, heightRatio);

	        // Create the bitmap from file
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = (int) maxRatio;
	        srcBitmap = BitmapFactory.decodeStream(is, null, options);
	    } else {
	        srcBitmap = BitmapFactory.decodeStream(is);
	    }
	    is.close();

	    /*
	     * if the orientation is not 0 (or -1, which means we don't know), we
	     * have to do a rotation.
	     */
	    if (orientation > 0) {
	        Matrix matrix = new Matrix();
	        matrix.postRotate(orientation);

	        srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
	                srcBitmap.getHeight(), matrix, true);
	    }
	    
	    
	    Bitmap circle = ImageUtil.getCircularBitmap(srcBitmap);

		Drawable drawable = new BitmapDrawable(context.getResources(), circle);

	    return drawable;
	}
	
	
	/**
	 * Crop bitmap in circle shape
	 * 
	 * @param bitmap
	 * @return circle bitmap
	 */
	public static Bitmap getCircularBitmap(Bitmap bitmap) {
		Bitmap output;

		if (bitmap.getWidth() > bitmap.getHeight()) {
			output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Config.ARGB_8888);
		} else {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		float r = 0;

		if (bitmap.getWidth() > bitmap.getHeight()) {
			r = bitmap.getHeight() / 2;
		} else {
			r = bitmap.getWidth() / 2;
		}

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(r, r, r, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * Compress to format PNG
	 * 
	 * @param bitmap
	 * @param quality
	 * @return
	 */
	public static ByteArrayOutputStream compressBitmapFormatPNG(Bitmap bitmap, int quality) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, quality, bos);
		return bos;
	}

	/**
	 * Convert Bitmap image in to {@link byte[]}
	 * 
	 */
	public static byte[] BitmapToByteArray(Bitmap bitmap, int quality) {
		ByteArrayOutputStream bos = ImageUtil.compressBitmapFormatJPEG(bitmap, quality);
		byte[] bitmapdata = bos.toByteArray();
		return bitmapdata;
	}

	/**
	 * Convert {@link byte[]} image in to Bitmap
	 * 
	 */
	public static Bitmap ByteArrayToBitmapMemorySafe(byte[] image) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[64 * 1024];
		// options.inSampleSize = 2; //This reduces memory consumption.
		options.inPurgeable = true;

		InputStream is = new ByteArrayInputStream(image);
		Bitmap bmp = null;

		try {
			bmp = BitmapFactory.decodeStream(is, null, options);
			is.close();
			is = null;
		} catch (Exception e) {
		}
		return bmp;
	}

	/**
	 * Convert {@link InputStream} image in to Bitmap
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap InputStreamToBitmapSafeMemory(InputStream image) {

		if (image != null) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inTempStorage = new byte[64 * 1024];
			// options.inSampleSize = 2; //This reduces memory consumption.
			options.inPurgeable = true;
			Bitmap b = BitmapFactory.decodeStream(image, null, options);
			try {
				image.close();
				image = null;
			} catch (IOException e) {
				LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, e);
			}

			return b;
		}
		return null;
	}

	/**
	 * Convert {@link File} image in to Bitmap
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap fileToBitmapSafeMemory(String path) {

		File f = new File(path);
		if (f.exists()) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inTempStorage = new byte[64 * 1024];
			// options.inSampleSize = 2; //This reduces memory consumption.
			options.inPurgeable = true;
			return BitmapFactory.decodeFile(path, options);
		}
		return null;
	}

	/**
	 * Invoke {@link Bitmap#recycle()}.
	 * 
	 * @param bitmap
	 *            {@link Bitmap} to be recycled, if null nothing happens.
	 */
	public static final void recycle(Bitmap bitmap) {
		if (bitmap != null)
			bitmap.recycle();
	}

	/**
	 * ScalingLogic defines how scaling should be carried out if source and
	 * destination image has different aspect ratio.
	 * 
	 * CROP: Scales the image the minimum amount while making sure that at least
	 * one of the two dimensions fit inside the requested destination area.
	 * Parts of the source image will be cropped to realize this.
	 * 
	 * FIT: Scales the image the minimum amount while making sure both
	 * dimensions fit inside the requested destination area. The resulting
	 * destination dimensions might be adjusted to a smaller size than
	 * requested.
	 */
	public static enum ScalingLogic {
		CROP, FIT
	}

	/**
	 * Utility function for creating a scaled version of an existing bitmap
	 * 
	 * @param unscaledBitmap
	 *            Bitmap to scale
	 * @param dstWidth
	 *            Wanted width of destination bitmap
	 * @param dstHeight
	 *            Wanted height of destination bitmap
	 * @param scalingLogic
	 *            Logic to use to avoid image stretching
	 * @return New scaled bitmap object
	 */
	public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
		Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
		Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaledBitmap);
		canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
		canvas = null;

		return scaledBitmap;
	}

	/**
	 * Calculates source rectangle for scaling bitmap
	 * 
	 * @param srcWidth
	 *            Width of source image
	 * @param srcHeight
	 *            Height of source image
	 * @param dstWidth
	 *            Width of destination area
	 * @param dstHeight
	 *            Height of destination area
	 * @param scalingLogic
	 *            Logic to use to avoid image stretching
	 * @return Optimal source rectangle
	 */
	public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.CROP) {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;

			if (srcAspect > dstAspect) {
				final int srcRectWidth = (int) (srcHeight * dstAspect);
				final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
				return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
			} else {
				final int srcRectHeight = (int) (srcWidth / dstAspect);
				final int scrRectTop = (int) (srcHeight - srcRectHeight) / 2;
				return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
			}
		} else {
			return new Rect(0, 0, srcWidth, srcHeight);
		}
	}

	/**
	 * Calculates destination rectangle for scaling bitmap
	 * 
	 * @param srcWidth
	 *            Width of source image
	 * @param srcHeight
	 *            Height of source image
	 * @param dstWidth
	 *            Width of destination area
	 * @param dstHeight
	 *            Height of destination area
	 * @param scalingLogic
	 *            Logic to use to avoid image stretching
	 * @return Optimal destination rectangle
	 */
	public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.FIT) {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;

			if (srcAspect > dstAspect) {
				return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
			} else {
				return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
			}
		} else {
			return new Rect(0, 0, dstWidth, dstHeight);
		}
	}

	/**
	 * Metodo usado para limpar o cache do imageLoader utilizado
	 * 
	 * @param imageLoader
	 */
	public static void clearImageLoaderCache(ImageLoader imageLoader) {
		try {
			if (imageLoader != null) {
				imageLoader.clearCache();
			}
		} catch (Exception e) {
			LibraryUtil.PrintLog(LibraryUtil.TypeLog.E, LibraryUtil.TAG, e);
		}
	}
}