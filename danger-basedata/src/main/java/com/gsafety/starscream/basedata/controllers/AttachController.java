package com.gsafety.starscream.basedata.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.image.utils.EasyImage;
import com.gsafety.image.utils.FileUploadUtils;
import com.gsafety.starscream.basedata.model.Attach;
import com.gsafety.starscream.basedata.service.AttachService;
import com.gsafety.starscream.basedata.view.AttachDTO;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.utils.file.PropertiesUtils;

@Path("attach")
public class AttachController {

	@Resource
	private Invocation inv;
	
	@Resource
	private AttachService attachService;
	
	private static final String imgRootDir = PropertiesUtils.getPropertiesValue("config.properties", "IMG_UPLOAD_ROOT_DIR");
	private static final String fileRootDir = PropertiesUtils.getPropertiesValue("config.properties", "FILE_UPLOAD_ROOT_DIR");
	
	public static final String IMAGE_SHOW_PATH = "/images";
	public static final String FILE_SHOW_PATH = "/files";
	
	private final String IMAGE_ORIGINAL_PREFIX = "original_";
	private final String IMAGE_THUMBNAIL_PREFIX = "thumbnail_";
	private final int IMAGE_THUMBNAIL_LIMIT_WIDTH = 100;
	private final int IMAGE_THUMBNAIL_LIMIT_HEIGHT = 100;
	
	private final String FILE_UPLOAD_PREFIX = "upfile_";
	
	private final String IMAGE_EXTENSION_LIST = "jpg|jpeg|png|gif";
	
	/**
	 * 上传附件
	 * @param multipartFile
	 * @return
	 * @param type 用于uediter图片上传 add by raomengwen type=1 ,
	 * @throws Exception 
	 */
	@Post("/uploadfile")
	public String attach_uploadFile(@Param("upfile") MultipartFile multipartFile ,@Param("type") Integer type) throws Exception{
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		
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
			
			//设置缩略图显示路径
			rtn.put("thumbnailPath", BasedataConstant.CONTEXT_PATH + StringUtils.substringAfter(thumbnailPath, imgRootDir));
			rtn.put("filePath", BasedataConstant.CONTEXT_PATH + uploadFilePath);
		
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
			
			//设置文件下载路径
			rtn.put("filePath", BasedataConstant.CONTEXT_PATH + uploadFilePath);
		}
		
		//将附件信息保存到数据库
		Attach attach = new Attach();
		attach.setAttachPath(uploadFilePath);         //文件保存路径
		attach.setCreateTime(new Date());
		attach.setName(originalFileName);
		attach.setSuffix(extension);
		attach = attachService.save(attach);
		
		//设置前端返回信息
		rtn.put("id", attach.getId());
		rtn.put("name", originalFileName);
		rtn.put("attachExt", extension);
		
		AttachDTO attachDTO = new AttachDTO(attach);
		// 用于区分uediter的图片上传
		if(type != null && type==1){
			attachDTO.setState("SUCCESS");
//			rtn.put("state", "SUCCESS");
			return AjaxUtils.printJson(attachDTO,inv);	
		}else{
			return AjaxUtils.printSuccessJson(attachDTO, inv);
		}
		
	}
	
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	@Post("/deletefile/{id:[0-9]+}")
	public String attach_deleteFile(@Param("id") String id){
		//查询附件
		Attach attach = attachService.findById(id);
		
		if(attach==null) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"附件不存在", inv);
		}
		
		//从文件系统中删除文件
		String filePath = null;
		
		if(IMAGE_EXTENSION_LIST.contains(attach.getSuffix())){
			filePath = imgRootDir + attach.getAttachPath();
		} else {
			filePath = fileRootDir + attach.getAttachPath();
		}
		
		//删除原文件
		File file = new File(filePath);
		file.delete();
		
		//判断文件是否为图片
		if(filePath.contains(IMAGE_ORIGINAL_PREFIX)){
			File thumbnailFile = new File(filePath.replace(IMAGE_ORIGINAL_PREFIX, IMAGE_THUMBNAIL_PREFIX));
			thumbnailFile.delete();
		}
		//从数据库中删除该附件记录
		attachService.delete(id);
		
		return AjaxUtils.printSuccessJson(null,"成功删除附件", inv);
	}
	

	
	/**
	 * 根据关联ID获取附件列表
	 * @param referId
	 * @return
	 */
	@Post("/find")
	public String attach_find(@Param("referId") String referId){
		List<AttachDTO> attachDTOs = new ArrayList<AttachDTO>();
		
		List<Attach> attachs = attachService.findByReferId(referId);
		if (attachs != null && attachs.size() > 0) {
			for (Attach attach : attachs){
				attachDTOs.add(new AttachDTO(attach));
			}
		}

		return AjaxUtils.printSuccessJson(attachDTOs, inv);
		
	}
}
