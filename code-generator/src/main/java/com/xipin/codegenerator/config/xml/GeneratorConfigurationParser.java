/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.xipin.codegenerator.config.xml;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.PropertyHolder;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.ObjectFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xipin.codegenerator.config.xml.model.Configuration;
import com.xipin.codegenerator.config.xml.model.Tables;
import com.xipin.codegenerator.config.xml.model.Template;
import com.xipin.codegenerator.config.xml.model.Templates;
import com.xipin.codegenerator.constant.XmlConstant;

/**
 * This class parses configuration files into the new Configuration API
 * 
 * @author Jeff Butler
 */
public class GeneratorConfigurationParser {
    private Properties properties;

    public GeneratorConfigurationParser(Properties properties) {
        super();
        if (properties == null) {
            this.properties = System.getProperties();
        } else {
            this.properties = properties;
        }
    }

    public Configuration parseConfiguration(Element rootNode)
            throws XMLParserException {

        Configuration configuration = new Configuration();

        NodeList nodeList = rootNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);

            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if ("properties".equals(childNode.getNodeName())) { 
                parseProperties(configuration, childNode);
            } else if ("property".equals(childNode.getNodeName())) { 
            	parseProperty(childNode);
            }else if ("classPathEntry".equals(childNode.getNodeName())) { 
                parseClassPathEntry(configuration, childNode);
            } else if ("jdbcConnection".equals(childNode.getNodeName())) { 
            	configuration.setJdbcConnectionConfiguration(parseJdbcConnection(childNode));
            }else if("templates".equals(childNode.getNodeName())){//模版
            	configuration.setTemplates(parseTemplates(childNode));
            }else if("tables".equals(childNode.getNodeName())){
            	configuration.setTables(parseTables(childNode));
            }
        }
        configuration.setProperties(properties);
        return configuration;
    }

   
	private void parseProperties(Configuration configuration, Node node)
            throws XMLParserException {
        Properties attributes = parseAttributes(node);
        String resource = attributes.getProperty("resource"); 
        String url = attributes.getProperty("url"); 

        if (!stringHasValue(resource)
                && !stringHasValue(url)) {
            throw new XMLParserException(getString("resource和url都未配置！")); 
        }

        if (stringHasValue(resource)
                && stringHasValue(url)) {
            throw new XMLParserException(getString("resource和url只能配置其中的一个！")); 
        }

        URL resourceUrl;

        try {
            if (stringHasValue(resource)) {
                resourceUrl = ObjectFactory.getResource(resource);
                if (resourceUrl == null) {
                    throw new XMLParserException("properties的配置文件"+resource+"不存在！"); 
                }
            } else {
                resourceUrl = new URL(url);
            }

            InputStream inputStream = resourceUrl.openConnection()
                    .getInputStream();

            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            if (stringHasValue(resource)) {
                throw new XMLParserException("不能从"+resource+"加载properties文件！"); 
            } else {
                throw new XMLParserException("不能从"+url+"加载properties文件！"); 
            }
        }
    }


    private JDBCConnectionConfiguration parseJdbcConnection(Node node) {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();

        Properties attributes = parseAttributes(node);
        String driverClass = attributes.getProperty("driverClass"); 
        String connectionURL = attributes.getProperty("connectionURL"); 
        String userId = attributes.getProperty("userId"); 
        String password = attributes.getProperty("password"); 

        jdbcConnectionConfiguration.setDriverClass(driverClass);
        jdbcConnectionConfiguration.setConnectionURL(connectionURL);

        if (stringHasValue(userId)) {
            jdbcConnectionConfiguration.setUserId(userId);
        }

        if (stringHasValue(password)) {
            jdbcConnectionConfiguration.setPassword(password);
        }

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);

            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if ("property".equals(childNode.getNodeName())) { 
                parseProperty(jdbcConnectionConfiguration, childNode);
            }
        }
        return jdbcConnectionConfiguration;
    }

    private void parseClassPathEntry(Configuration configuration, Node node) {
        Properties attributes = parseAttributes(node);

        configuration.addClasspathEntry(attributes.getProperty("location")); 
    }

    private void parseProperty(PropertyHolder propertyHolder, Node node) {
        Properties attributes = parseAttributes(node);

        String name = attributes.getProperty("name"); 
        String value = attributes.getProperty("value"); 

        propertyHolder.addProperty(name, value);
    }
    
    private void parseProperty(Node node) {
        Properties attributes = parseAttributes(node);

        String name = attributes.getProperty("name"); 
        String value = attributes.getProperty("value"); 

        this.properties.put(name, value);
    }

    private Properties parseAttributes(Node node) {
        Properties attributes = new Properties();
        NamedNodeMap nnm = node.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            Node attribute = nnm.item(i);
            String value = parsePropertyTokens(attribute.getNodeValue());
            attributes.put(attribute.getNodeName(), value);
        }

        return attributes;
    }

    private String parsePropertyTokens(String string) {
        final String OPEN = "${"; 
        final String CLOSE = "}"; 

        String newString = string;
        if (newString != null) {
            int start = newString.indexOf(OPEN);
            int end = newString.indexOf(CLOSE);

            while (start > -1 && end > start) {
                String prepend = newString.substring(0, start);
                String append = newString.substring(end + CLOSE.length());
                String propName = newString.substring(start + OPEN.length(),
                        end);
                String propValue = properties.getProperty(propName);
                if (propValue != null) {
                    newString = prepend + propValue + append;
                }

                start = newString.indexOf(OPEN, end);
                end = newString.indexOf(CLOSE, end);
            }
        }

        return newString;
    }
    
    private Templates parseTemplates(Node node){
    	Templates templates = new Templates();
    	Properties attributes = parseAttributes(node);
    	String overwrite = attributes.getProperty("overwrite");
    	String makeDirIfNotExist = attributes.getProperty("makeDirIfNotExist");
    	templates.setOverwrite(XmlConstant.TRUE.equals(overwrite));
    	templates.setMarkDirIfNotExists(XmlConstant.TRUE.equals(makeDirIfNotExist));
    	templates.setTplDirectory(attributes.getProperty("tplDirectory"));
    	NodeList children = node.getChildNodes();
    	for(int i=0; i < children.getLength(); i++){
    		Node childNode = children.item(i);
    		if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if ("template".equals(childNode.getNodeName())) { 
                 Template template = parseTemplate(childNode);
                 if(template != null){
                	 templates.getTemplates().add(template);
                 }
            }
    	}
    	return templates;
    }
    
    private Template parseTemplate(Node node){
    	Template template = new Template();
    	Properties attributes = parseAttributes(node);
    	template.setId(attributes.getProperty("id"));
    	template.setFileType(attributes.getProperty("fileType"));
    	template.setTargetDirectory(attributes.getProperty("targetDirectory"));
    	template.setTplFile(attributes.getProperty("tplFile"));
    	template.setFileNamePrefix(attributes.getProperty("fileNamePrefix"));
    	template.setFileNameSuffix(attributes.getProperty("fileNameSuffix"));
    	return template;
    }

    private Tables parseTables(Node node) {
		Tables tables = new Tables();
		Properties attributes = parseAttributes(node);
		String exclude = attributes.getProperty("exclude");
		tables.setExclude(exclude);
		String include = attributes.getProperty("include");
		tables.setInclude(include);
		tables.setCatalog(attributes.getProperty("catalog"));
		tables.setSchema(attributes.getProperty("schema"));
		tables.setContainsView(XmlConstant.TRUE.equals(attributes.getProperty("containsView")));
		tables.setForceBigDecimals(XmlConstant.TRUE.equals(attributes.getProperty("forceBigDecimals")));
		tables.parseTable();
		return tables;
	}

}
