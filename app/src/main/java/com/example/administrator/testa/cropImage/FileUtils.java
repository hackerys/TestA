package com.example.administrator.testa.cropImage;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 文件工具类
 * 
 * @author LiLong
 * @date 2013-11-4 下午03:25:30
 * @update-time
 * @modified by
 */
public class FileUtils
{
	/** 创建文件 */
	public static final int CREATE_FILE = 1;
	/** 根目录 */
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/";

	/**
	 * 获取文件uri
	 * @param file
	 * @return
	 * @author LiLong
	 * @date 2013-11-4
	 * @modified by
	 * @update-time 2013-11-4 下午03:34:07
	 */
	public static Uri getOutputFileUri(File file)
	{
		return Uri.fromFile(file);
	}

	/**
	 * 得到输出的文件
	 * @param type
	 * @param context
	 * @return
	 * @author LiLong
	 * @date 2013-11-4
	 * @modified by
	 * @update-time 2013-11-4 下午03:37:13
	 */
	public static File getOutputFile(int type, Context context)
	{
		File mediaStorageDir = new File(SDPATH);
		if (!mediaStorageDir.exists())
		{
			mediaStorageDir.mkdirs();
			if (!mediaStorageDir.mkdirs())
			{
				Log.d("T", "路径失败");
				return null;
			}
		}
		File mediaFile = null;
		String timeStamp = String.valueOf(new Date().getTime());
		if (type == CREATE_FILE)
		{
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG" + timeStamp + ".png");
		}
		return mediaFile;
	}

	/**
	 * @Description 创建文件目录
	 * @param dirName
	 * @return
	 * @author ll
	 * @date 2013-8-7
	 * @modified by
	 * @update-time 2013-8-7 上午10:32:38
	 */
	public static File createSDDir(String dirName)
	{
		File dir = new File(dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * @Description 删除文件
	 * @param dirName
	 * @author ll
	 * @date 2013-8-7
	 * @modified by
	 * @update-time 2013-8-7 上午10:32:14
	 */
	public static void deleFile(String dirName)
	{
		File dir = new File(dirName);
		dir.delete();
	}

	/**
	 * @Description 重命名
	 * @param oldPath
	 * @param newPath
	 * @author ll
	 * @date 2013-9-6
	 * @modified by
	 * @update-time 2013-9-6 下午02:56:11
	 */
	public static void renameFile(String oldPath, String newPath)
	{
		File file = new File(oldPath);
		file.renameTo(new File(newPath));
	}

	/**
	 * @Description 判断文件是否存在
	 * @param fileName
	 * @return
	 * @author ll
	 * @date 2013-8-7
	 * @modified by
	 * @update-time 2013-8-7 上午10:31:39
	 */
	public static boolean isFileExist(String fileName)
	{
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * @Description 获取SD卡目录
	 * @return
	 * @author ll
	 * @date 2013-7-22
	 * @modified by
	 * @update-time 2013-7-22 下午03:06:36
	 */
	public static String getSDPath()
	{
		File sdDir = null;
		if (isSDExist())
		{
			sdDir = Environment.getExternalStorageDirectory();
		}
		return sdDir.toString();
	}

	/**
	 * @Description 判断SD卡是否存在
	 * @return
	 * @author ll
	 * @date 2013-7-22
	 * @modified by
	 * @update-time 2013-7-22 下午03:07:19
	 */
	public static boolean isSDExist()
	{
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}

	/**
	 * @Description 获取文件夹大小
	 * @param file
	 *            实例
	 * @return long 单位为M
	 * @throws Exception
	 * @author ll
	 * @date 2013-9-22
	 * @modified by
	 * @update-time 2013-9-22 下午02:57:23
	 */
	public static long getFolderSize(java.io.File file) throws Exception
	{
		long size = 0;
		java.io.File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++)
		{
			if (fileList[i].isDirectory())
			{
				size = size + getFolderSize(fileList[i]);
			} else
			{
				size = size + fileList[i].length();
			}
		}
		return size / 1048576;
	}

	/**
	 * @Description 统计没有上传的文件大小
	 * @param pathList
	 * @return long 单位为M
	 * @author ll
	 * @date 2013-9-22
	 * @modified by
	 * @update-time 2013-9-22 下午03:11:19
	 */
	public static long getNotUploadFilesSize(List pathList)
	{
		long size = 0;
		for (int i = 0; i < pathList.size(); i++)
		{
			File file = new File(pathList.get(i).toString());
			FileInputStream f = null;
			try
			{
				f = new FileInputStream(file);

				if (file.isDirectory())
				{
					size = f.available();
				} else
				{
					size = size + file.length();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return size / 1048576;
	}

	/**
	 * @Description 文件大小单位转换
	 * @param size
	 * @return
	 * @author ll
	 * @date 2013-9-22
	 * @modified by
	 * @update-time 2013-9-22 下午02:58:05
	 */
	public static String setFileSize(long size)
	{
		DecimalFormat df = new DecimalFormat("###.##");
		float f = ((float) size / (float) (1024 * 1024));

		if (f < 1.0)
		{
			float f2 = ((float) size / (float) (1024));
			return df.format(new Float(f2).doubleValue()) + "KB";

		} else
		{
			return df.format(new Float(f).doubleValue()) + "M";
		}
	}

	/**
	 * @Description 删除指定目录下文件及目录
	 * @param filePath
	 *            文件目录
	 * @param deleteThisPath
	 *            是否删除
	 * @throws IOException
	 * @author ll
	 * @date 2013-9-22
	 * @modified by
	 * @update-time 2013-9-22 下午02:59:22
	 */
	public void deleteFolderFile(String filePath, boolean deleteThisPath)
			throws IOException
	{

		if (!TextUtils.isEmpty(filePath))
		{
			File file = new File(filePath);

			if (file.isDirectory())
			{
				// 处理目录
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++)
				{
					deleteFolderFile(files[i].getAbsolutePath(), true);
				}
			}

			if (deleteThisPath)
			{
				if (!file.isDirectory())
				{
					// 如果是文件，删除
					file.delete();
				} else
				{
					// 目录
					if (file.listFiles().length == 0)
					{
						// 目录下没有文件或者目录，删除
						file.delete();
					}
				}
			}
		}
	}
}
