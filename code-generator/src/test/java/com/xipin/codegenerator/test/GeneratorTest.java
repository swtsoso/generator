package com.xipin.codegenerator.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xipin.codegenerator.config.xml.ConfigurationParser;
import com.xipin.codegenerator.config.xml.model.Configuration;
import com.xipin.codegenerator.generator.CodeGenerator;

public class GeneratorTest {

	private final Logger logger = LoggerFactory.getLogger(GeneratorTest.class);
	
	@Test
	public void CodeGeneratorTest(){
		logger.info("开始执行");
		try{
			ConfigurationParser cp = new ConfigurationParser();
			Configuration conf = cp.parseConfiguration(this.getClass().getClassLoader().getResourceAsStream("generatorConfig3.xml"));
			CodeGenerator generator = new CodeGenerator(conf);
			generator.generate();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		logger.info("执行结束");
	}
	
	
}
