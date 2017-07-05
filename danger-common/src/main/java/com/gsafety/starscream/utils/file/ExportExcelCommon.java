package com.gsafety.starscream.utils.file;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @ClassName:ExportExcelCommon
 * @Description:导出Excel数据
 * @Author: duzhigang
 * @Version:1.0
 * @Date:2015/06/23
 */
public class ExportExcelCommon {
	
	 public static void createExcel(OutputStream os,List<Object[]> listObj,String[] title,String labelTitle,String[] width) throws Exception, WriteException{
			
			WritableWorkbook wwbook = Workbook.createWorkbook(os);  //create sheet
			WritableSheet sheet = wwbook.createSheet(labelTitle, 0);   //sheet Name
			
		    WritableFont times10ptBold = new WritableFont(WritableFont.createFont("宋体"), 20, WritableFont.BOLD,false,UnderlineStyle.SINGLE);
		    WritableCellFormat times10BoldFormat = new WritableCellFormat(times10ptBold);
		    times10BoldFormat.setAlignment(Alignment.CENTRE);
		    
		    WritableFont times11ptBold = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE);
		    WritableCellFormat times11BoldFormat = new WritableCellFormat(times11ptBold);
		    times11BoldFormat.setAlignment(Alignment.LEFT);
			
		    
		    WritableFont titleStyle = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD,false,UnderlineStyle.SINGLE);
		    
		    WritableCellFormat threeBorders1 = new WritableCellFormat(titleStyle);
		    threeBorders1.setBorder(Border.ALL, BorderLineStyle.THIN);
		    threeBorders1.setAlignment(Alignment.CENTRE);
		    threeBorders1.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
		    
		    WritableCellFormat threeBorders2 = new WritableCellFormat();
		    threeBorders2.setBorder(Border.ALL, BorderLineStyle.THIN);
		    threeBorders2.setAlignment(Alignment.CENTRE);
		    
		    WritableCellFormat threeBorders3 = new WritableCellFormat();
		    threeBorders3.setBorder(Border.ALL, BorderLineStyle.THIN);
		    threeBorders3.setAlignment(Alignment.LEFT);
		    
		    WritableCellFormat threeBorders4 = new WritableCellFormat();
		    threeBorders4.setBorder(Border.ALL, BorderLineStyle.THIN);
		    threeBorders4.setAlignment(Alignment.RIGHT);
		    
		    WritableCellFormat threeBorders5 = new WritableCellFormat(NumberFormats.FLOAT);
		    threeBorders5.setBorder(Border.ALL, BorderLineStyle.THIN);
		    threeBorders5.setAlignment(Alignment.RIGHT);
		    
		    WritableCellFormat threeBorders6 = new WritableCellFormat(NumberFormats.INTEGER);
		    threeBorders6.setBorder(Border.ALL, BorderLineStyle.THIN);
		    threeBorders6.setAlignment(Alignment.RIGHT);
			
			getTitleWidth(sheet,title.length,width);//列宽
			
		    //2.设置表的标题
		    sheet.mergeCells(0, 0, title.length-1, 0);
		    Label labelTitle00 = new Label(0, 0, labelTitle,times10BoldFormat);
		    sheet.addCell(labelTitle00);
		    SimpleDateFormat format= new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		    Date nowDate=new Date();
		    sheet.mergeCells(0, 1, title.length-1, 1);
		    sheet.setRowView(1,700,false); 
		    Label labelTitle01 = new Label(0, 1,format.format(nowDate),times11BoldFormat);
		    sheet.addCell(labelTitle01);
		    
		    //定义单元格样式
		    WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD, false, 
		    UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		    //单元格定义
		    WritableCellFormat wcf = new WritableCellFormat(wf); 
		    //设置单元格的背景颜色
		    //wcf.setBackground(jxl.format.Colour.YELLOW2);
		    // 设置对齐方式
		    wcf.setAlignment(jxl.format.Alignment.CENTRE); 
		    
		    //定义单元格样式
		    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD, false, 
		    UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		    //单元格定义
		    WritableCellFormat wcf2 = new WritableCellFormat(wf2); 
		    //设置单元格的背景颜色
		    //wcf2.setBackground(jxl.format.Colour.YELLOW2);
		    // 设置对齐方式
		    wcf2.setAlignment(jxl.format.Alignment.LEFT); 
		    
		    //定义单元格样式
		    WritableFont wf3 = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD, false, 
		    UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		    //单元格定义
		    WritableCellFormat wcf3 = new WritableCellFormat(wf3); 
		    //设置单元格的背景颜色
		    //wcf3.setBackground(jxl.format.Colour.YELLOW2);
		    // 设置对齐方式
		    wcf3.setAlignment(jxl.format.Alignment.RIGHT); 
		    
		   //定义单元格样式
		    WritableFont wf4 = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD, false, 
		    UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		    //单元格定义
		    WritableCellFormat wcf4 = new WritableCellFormat(wf4); 
		    // 设置对齐方式
		    wcf4.setAlignment(jxl.format.Alignment.LEFT); 
		    
		    //定义单元格样式
		    WritableFont wf5 = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD, false, 
		    UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		    //单元格定义
		    WritableCellFormat wcf5 = new WritableCellFormat(wf5); 
		    // 设置对齐方式
		    wcf5.setAlignment(jxl.format.Alignment.RIGHT); 
		    
		    //设置表的表头
		    for (int i = 0; i < title.length; i++) {
		    	labelTitle00 = new Label(i, 2, title[i], threeBorders1);
		    	sheet.addCell(labelTitle00);
			}
		    int rowNum =3;
		    //表体
		    for (int i = 0; i < listObj.size(); i++) {
		    	int column = 0;
		    	Object obj[]=listObj.get(i);
		    	//序号
				jxl.write.Number number = new jxl.write.Number(column++, rowNum, (i+1), threeBorders2);
		    	sheet.addCell(number);
		    	for (int j = 0; j < obj.length; j++) {
		    		labelTitle00 = new Label(column++, rowNum,returnStr(obj[j]),threeBorders3);
			    	sheet.addCell(labelTitle00);
				}
		    	rowNum++;
		    }
		    try{
		    	//写入Exel工作表
			    wwbook.write();
			    //关闭Excel工作薄对象
		    	wwbook.close();
		    }catch(Exception e){
		    	e.printStackTrace();
		    }finally{
		    	os.flush();
			    os.close();
		    }
		    
	  }
	 
	 
	 public static String returnStr(Object obj){
		 if(obj==null){
			 return "";
		 }
		 return obj.toString().trim();
	 }
	 
	 
	 public static void getTitleWidth(WritableSheet sheet,int length,String[] width) {
			for (int i =0; i < length; i++) {
				sheet.setColumnView(i, Integer.parseInt(width[i]));//序号后面的三列
			}
		}
	
	public static void setExportExcel(HttpServletResponse response,String exprotName,boolean isAddTime) throws Exception{
		response.reset();//重置
		response.setContentType("application/vnd.ms-excel");//对应文件类型
		Date date = new Date(new java.util.Date().getTime());
		String time = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
		if(isAddTime){
			exprotName = exprotName+time;
		}
		//导出文件名称
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ toUtf8String(exprotName)+ ".xls\"");
	}
	
	/**
	   * @author lw
	   * 转换编码Utf8
	   * @param s String
	   * @return String
	   */
		public static String toUtf8String(String s) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c >= 0 && c <= 255) {
					sb.append(c);
				} else {
					byte[] b;
					try {
						b = Character.toString(c).getBytes("utf-8");
					} catch (Exception ex) {
						System.out.println(ex);
						b = new byte[0];
					}
					for (int j = 0; j < b.length; j++) {
						int k = b[j];
						if (k < 0) {
							k += 256;
						}
						sb.append("%" + Integer.toHexString(k).toUpperCase());
					}
				}
			}
			return sb.toString();
		}
		
		public static boolean constains(String[] title,String str){
			boolean b=false;
			for(int i=0;i<title.length;i++){
				if(title[i].contains(str)){
					b=true;
					break;
				}
			}
			return b;
		}

}
