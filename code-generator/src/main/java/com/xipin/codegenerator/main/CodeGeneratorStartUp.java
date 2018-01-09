package com.xipin.codegenerator.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.mybatis.generator.internal.util.messages.Messages;
import org.mybatis.generator.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xipin.codegenerator.config.xml.ConfigurationParser;
import com.xipin.codegenerator.config.xml.model.Configuration;
import com.xipin.codegenerator.generator.CodeGenerator;

/**
 * ClassName:CodeGeneratorStartUp <br/>
 * Function: TODO 代码生成工具，主类. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年5月10日 下午5:58:09 <br/>
 * 
 * @author ding.song
 * @version
 * @since JDK 1.7.0_17
 * @see
 */
public class CodeGeneratorStartUp {
	private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorStartUp.class);
	private static final String CONFIG_FILE = "-configfile";
	  private static final String OVERWRITE = "-overwrite";
	  private static final String CONTEXT_IDS = "-contextids";
	  private static final String TABLES = "-tables";
	  private static final String VERBOSE = "-verbose";
	  private static final String FORCE_JAVA_LOGGING = "-forceJavaLogging";
	  private static final String HELP_1 = "-?";
	  private static final String HELP_2 = "-h";

	public static void main(String[] args) {
		if (args.length == 0) {
		      usage();
		      System.exit(0);
		      return;
		    }

		    Map arguments = parseCommandLine(args);

		    if (arguments.containsKey("-?")) {
		      usage();
		      System.exit(0);
		      return;
		    }

		    if (!arguments.containsKey("-configfile")) {
		      writeLine(Messages.getString("RuntimeError.0"));
		      return;
		    }

		    List warnings = new ArrayList();

		    String configfile = (String)arguments.get("-configfile");
		    File configurationFile = new File(configfile);
		    if (!configurationFile.exists()) {
		      writeLine(Messages.getString("RuntimeError.1", configfile));
		      return;
		    }

		    Set fullyqualifiedTables = new HashSet();
		    if (arguments.containsKey("-tables")) {
		      StringTokenizer st = new StringTokenizer((String)arguments.get("-tables"), ",");
		      while (st.hasMoreTokens()) {
		        String s = st.nextToken().trim();
		        if (s.length() > 0) {
		          fullyqualifiedTables.add(s);
		        }
		      }
		    }

		    Set contexts = new HashSet();
		    if (arguments.containsKey("-contextids")) {
		      StringTokenizer st = new StringTokenizer((String)arguments.get("-contextids"), ",");

		      while (st.hasMoreTokens()) {
		        String s = st.nextToken().trim();
		        if (s.length() > 0) {
		          contexts.add(s);
		        }
		      }
		    }
		logger.info("代码生成开始");
		try {
			ConfigurationParser cp = new ConfigurationParser(warnings);
			logger.info("配置文件路径："+configurationFile.getAbsolutePath());
			Configuration conf = cp.parseConfiguration(configurationFile);
			CodeGenerator generator = new CodeGenerator(conf);
			generator.generate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		logger.info("代码生成结束");
	}
	
	private static Map<String, String> parseCommandLine(String[] args) {
	    List<String> errors = new ArrayList<>();
	    Map<String,String> arguments = new HashMap<>();

	    for (int i = 0; i < args.length; i++) {
	      if ("-configfile".equalsIgnoreCase(args[i])) {
	        if (i + 1 < args.length)
	          arguments.put("-configfile", args[(i + 1)]);
	        else {
	          errors.add(Messages.getString("RuntimeError.19", "-configfile"));
	        }

	        i++;
	      } else if ("-overwrite".equalsIgnoreCase(args[i])) {
	        arguments.put("-overwrite", "Y");
	      } else if ("-verbose".equalsIgnoreCase(args[i])) {
	        arguments.put("-verbose", "Y");
	      } else if ("-?".equalsIgnoreCase(args[i])) {
	        arguments.put("-?", "Y");
	      } else if ("-h".equalsIgnoreCase(args[i]))
	      {
	        arguments.put("-?", "Y");
	      } else if ("-forceJavaLogging".equalsIgnoreCase(args[i])) {
	        LogFactory.forceJavaLogging();
	      } else if ("-contextids".equalsIgnoreCase(args[i])) {
	        if (i + 1 < args.length)
	          arguments.put("-contextids", args[(i + 1)]);
	        else {
	          errors.add(Messages.getString("RuntimeError.19", "-contextids"));
	        }

	        i++;
	      } else if ("-tables".equalsIgnoreCase(args[i])) {
	        if (i + 1 < args.length)
	          arguments.put("-tables", args[(i + 1)]);
	        else {
	          errors.add(Messages.getString("RuntimeError.19", "-tables"));
	        }
	        i++;
	      } else {
	        errors.add(Messages.getString("RuntimeError.20", args[i]));
	      }
	    }

	    if (!errors.isEmpty()) {
	      for (String error : errors) {
	        writeLine(error);
	      }

	      System.exit(-1);
	    }

	    return arguments;
	  }
	
	private static void usage() {
	    String lines = Messages.getString("Usage.Lines");
	    int iLines = Integer.parseInt(lines);
	    for (int i = 0; i < iLines; i++) {
	      String key = "Usage." + i;
	      writeLine(Messages.getString(key));
	    }
	  }

	  private static void writeLine(String message) {
	    System.out.println(message);
	  }

	  private static void writeLine() {
	    System.out.println();
	  }

}
