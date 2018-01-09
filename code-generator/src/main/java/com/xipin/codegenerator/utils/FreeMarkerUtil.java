package com.xipin.codegenerator.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtil {

	private final static Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);
	
	public static Configuration getFreeMarkerCfg(String ftlPath) {
		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setBooleanFormat("true,false");
		freemarkerCfg.setNumberFormat("#");
		freemarkerCfg.setDefaultEncoding("UTF-8");
		try {
			freemarkerCfg.setDirectoryForTemplateLoading(new File(ftlPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return freemarkerCfg;
	}

	public static boolean generateFile(Configuration cfg, String templateFileName, Map propMap, String relPath,
			String fileName, String rootDir) {
		try {
			Template t = cfg.getTemplate(templateFileName);

			File dir = new File(rootDir + "/" + relPath);
			if (!dir.exists()) {
				dir.mkdir();
			}

			File afile = new File(rootDir + "/" + relPath + "/" + fileName);

			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile), "UTF-8"));

			t.process(propMap, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 生成文件
	 * @param root 根对象
	 * @param tplDirectory 模版目录
	 * @param tplFileName 模版名称
	 * @param outFileName 输出文件的路径
	 * @param makeDirIfNotExist 如果输出文件的目录不存在，是否创建
	 * @param overwriteExistFile 如果输出目标文件已存在，是否覆盖
	 */
	public static void generateFile(Map<?, ?> root,String tplDirectory,
			String tplFileName,String outFileName,boolean makeDirIfNotExist,boolean overwriteExistFile) {
		try {
			tplDirectory = tplDirectory.replace("\\\\", "/");
			tplFileName = tplFileName.replaceAll("\\\\", "");
			outFileName = outFileName.replaceAll("\\\\", "/");
			Configuration config = new Configuration();
			//变量为null则替换为空字符串
			config.setClassicCompatible(true);
			// 设置要解析的模板所在的目录，并加载模板文件
			config.setDirectoryForTemplateLoading(
					new File(tplDirectory));
			// 设置包装器，并将对象包装为数据模型
			config.setObjectWrapper(new DefaultObjectWrapper());

			// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			// 否则会出现乱码
			Template template = config.getTemplate(tplFileName, "UTF-8");
			if(makeDirIfNotExist){
				String outDirectory = outFileName.substring(0,outFileName.lastIndexOf("/"));
				File outFile = new File(outDirectory);
				if(!outFile.exists()){
					outFile.mkdirs();
				}
			}
			File outFile = new File(outFileName);
			boolean outFileIsExist = outFile.exists();
			if(outFileIsExist && !overwriteExistFile){
				logger.info(outFileName+"已存在，未重新生成！");
			}else{
				// 合并数据模型与模板
				FileOutputStream fos = new FileOutputStream(outFileName);
				Writer out = new OutputStreamWriter(fos, "UTF-8");
				template.process(root, out);
				out.flush();
				out.close();
				logger.info("生成"+outFileName);
			}
		} catch (IOException e) {
			logger.error("IO异常", e);
		} catch (TemplateException e) {
			logger.error("模版异常", e);
		}
	}

}
