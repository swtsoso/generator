package com.xipin.codegenerator.config.xml.model;

/**
 * 模版文件
 * @author songchangyou
 *
 */
public class Template {

	/**
	 * 标识
	 */
	private String id;
	/**
	 * 模版文件名称
	 */
	private String tplFile;
	/**
	 * 生成文件的存放路径
	 */
	private String targetDirectory;
	/**
	 * 生成文件的类型(后缀）
	 */
	private String fileType;
	
	/**
	 * 文件名前缀
	 */
	private String fileNamePrefix;
	/**
	 * 文件名后缀
	 */
	private String fileNameSuffix;
	/**
	 * 文件名后缀
	 */
	public String getFileNameSuffix() {
		return fileNameSuffix;
	}
	/**
	 * 文件名后缀
	 */
	public void setFileNameSuffix(String fileNameSuffix) {
		this.fileNameSuffix = fileNameSuffix;
	}
	/**
	 * 文件名前缀
	 */
	public String getFileNamePrefix() {
		return fileNamePrefix;
	}
	/**
	 * 文件名前缀
	 */
	public void setFileNamePrefix(String fileNamePrefix) {
		this.fileNamePrefix = fileNamePrefix;
	}
	/**
	 * 标识
	 */
	public String getId() {
		return id;
	}
	/**
	 * 标识
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 模版文件名称
	 */
	public String getTplFile() {
		return tplFile;
	}
	/**
	 * 模版文件名称
	 */
	public void setTplFile(String tplFile) {
		this.tplFile = tplFile;
	}

	/**
	 * 生成文件的存放路径
	 * @return
	 */
	public String getTargetDirectory() {
		return targetDirectory;
	}

	/**
	 * 生成文件的存放路径
	 */
	public void setTargetDirectory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	/**
	 * 生成文件的类型(后缀）
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * 生成文件的类型(后缀）
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}
