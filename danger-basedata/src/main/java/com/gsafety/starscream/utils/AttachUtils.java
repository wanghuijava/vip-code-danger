package com.gsafety.starscream.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gsafety.image.utils.EasyImage;
import com.gsafety.image.utils.FileUploadUtils;
import com.gsafety.starscream.basedata.model.Attach;
import com.gsafety.starscream.utils.file.PropertiesUtils;

public class AttachUtils {

	public static final String imgRootDir = PropertiesUtils.getPropertiesValue("config.properties", "IMG_UPLOAD_ROOT_DIR");
	public static final String fileRootDir = PropertiesUtils.getPropertiesValue("config.properties", "FILE_UPLOAD_ROOT_DIR");
	
	public static final String IMAGE_SHOW_PATH = "/images";
	public static final String FILE_SHOW_PATH = "/files";
	
	private static final String IMAGE_ORIGINAL_PREFIX = "original_";
	private static final String IMAGE_THUMBNAIL_PREFIX = "thumbnail_";
	private static final int IMAGE_THUMBNAIL_LIMIT_WIDTH = 100;
	private static final int IMAGE_THUMBNAIL_LIMIT_HEIGHT = 100;
	
	private static final String FILE_UPLOAD_PREFIX = "upfile_";

	//图片格式文件
	private static final  String IMAGE_EXTENSION_LIST = "jpg|jpeg|png|gif";
	
	//视频格式文件
	private static final String VIDEO_EXTENSION_LIST = "mp4|avi|wmv";
	
	//音频格式文件
	private static final String AUDIO_EXTENSION_LIST = "mp3|wma";
	
	/**
	 * 根据文件后缀判断文件类型
	 * @param suffix
	 * @return
	 */
	public static boolean isImage(String suffix){
		if(IMAGE_EXTENSION_LIST.indexOf(suffix)!=-1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据原文件路径取得缩略图路径
	 * @param suffix
	 * @return
	 */
	public static String getThumbnailPath(String suffix, String imgFilePath){
		if(suffix != null && IMAGE_EXTENSION_LIST.indexOf(suffix)!=-1) {
			String thumbnailPath = imgFilePath.replace(IMAGE_ORIGINAL_PREFIX, IMAGE_THUMBNAIL_PREFIX);
			return thumbnailPath;
		}
		
		return null;
	}
	
	/**
	 * 根据文件后缀判断文件类型
	 * @param suffix
	 * @return
	 */
	public static String getFileType(String suffix){
		if(IMAGE_EXTENSION_LIST.indexOf(suffix)!=-1) {
			return "image";
		}else if(VIDEO_EXTENSION_LIST.indexOf(suffix)!=-1) {
			return "video";
		}else if(AUDIO_EXTENSION_LIST.indexOf(suffix)!=-1) {
			return "audio";
		}
		return "file";
	}
	
	/**
	 * 保存附件至文件夹
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	public static Attach uploadFile(MultipartFile multipartFile) throws Exception{
		//获取原文件名
		String originalFileName = multipartFile.getOriginalFilename();
		//获取文件扩展名
		String extension = FilenameUtils.getExtension(originalFileName.toLowerCase());
		//初始化文件上传保存路径
		String uploadFilePath = null;
		//图片文件处理
		if(FileUploadUtils.isValidImage(multipartFile)){
			//原图上传到图片服务器路径
			String imgPath = FileUploadUtils.uploadImg(multipartFile, imgRootDir + IMAGE_SHOW_PATH, IMAGE_ORIGINAL_PREFIX, 0);
			//缩略图定义
			String thumbnailPath = imgPath.replace(IMAGE_ORIGINAL_PREFIX, IMAGE_THUMBNAIL_PREFIX);
			//图片等比缩放
			EasyImage.resize(imgPath, thumbnailPath, IMAGE_THUMBNAIL_LIMIT_WIDTH, IMAGE_THUMBNAIL_LIMIT_HEIGHT);
			
			//设置文件上传保存路径
			uploadFilePath = StringUtils.substringAfter(imgPath, imgRootDir);
		} else {//文件上传处理
			//初始化文件上传路径
			String filePath = FileUploadUtils.getFilePath(fileRootDir + FILE_SHOW_PATH, FILE_UPLOAD_PREFIX, extension);
			//写文件到指定路径下
			FileOutputStream fos = null;
			try {
				byte[] contents = multipartFile.getBytes();
				fos = new FileOutputStream(filePath);
				fos.write(contents);
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fos!=null) {
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//设置文件上传保存路径
			uploadFilePath = StringUtils.substringAfter(filePath, fileRootDir);
		}
		
		Attach attach = new Attach();
		attach.setAttachPath(uploadFilePath);         //文件保存路径
		attach.setCreateTime(new Date());
		attach.setName(originalFileName);
		attach.setSuffix(extension);
		return attach;
	}
}
