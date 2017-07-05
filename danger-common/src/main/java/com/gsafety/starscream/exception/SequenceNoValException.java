package com.gsafety.starscream.exception;

/**
 * 定义数据库函数GetSequence中没有值得情况
 * @author chenwenlong
 *
 */
public class SequenceNoValException extends Exception {

	private static final long serialVersionUID = 4985984692345948289L;

	public SequenceNoValException(){
		super("从数据库取序列无法识别Key值，函数GetSequence调用异常");
	}
	
	public SequenceNoValException(String msg){
		super(msg);
	}
}
