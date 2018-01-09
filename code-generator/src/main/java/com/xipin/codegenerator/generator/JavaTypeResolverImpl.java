package com.xipin.codegenerator.generator;

import java.math.BigDecimal;
import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import com.xipin.codegenerator.generator.model.Column;

public class JavaTypeResolverImpl extends JavaTypeResolverDefaultImpl{

	/**
	 * 列的JdbcTypeInformation信息
	 * @param column
	 */
	public void calculateJdbcTypeInformation(Column column) {
		String jdbcTypeName = calculateJdbcTypeName(column.getDataType());
	    FullyQualifiedJavaType fullyQualifiedJavaType = calculateJavaType(column);
		JdbcTypeInformation answer = new JdbcTypeInformation(jdbcTypeName, fullyQualifiedJavaType);
		column.setJdbcTypeInformation(answer);
		
	}
	
	public String calculateJdbcTypeName(int dataType){
		String answer;
        JdbcTypeInformation jdbcTypeInformation = typeMap
                .get(dataType);

        if (jdbcTypeInformation == null) {
            switch (dataType) {
            case Types.DECIMAL:
                answer = "DECIMAL"; 
                break;
            case Types.NUMERIC:
                answer = "NUMERIC"; 
                break;
            default:
                answer = null;
                break;
            }
        } else {
            answer = jdbcTypeInformation.getJdbcTypeName();
        }

        return answer;
	}
	
	public FullyQualifiedJavaType calculateJavaType(Column column) {
        FullyQualifiedJavaType answer;
        JdbcTypeInformation jdbcTypeInformation = typeMap
                .get(column.getDataType());

        if (jdbcTypeInformation == null) {
            switch (column.getDataType()) {
            case Types.DECIMAL:
            case Types.NUMERIC:
                if (column.getDecimalDigits() > 0
                        || column.getColumnSize() > 18
                        || forceBigDecimals) {
                    answer = new FullyQualifiedJavaType(BigDecimal.class
                            .getName());
                } else if (column.getColumnSize() > 9) {
                    answer = new FullyQualifiedJavaType(Long.class.getName());
                } else if (column.getColumnSize() > 4) {
                    answer = new FullyQualifiedJavaType(Integer.class.getName());
                } else {
                    answer = new FullyQualifiedJavaType(Short.class.getName());
                }
                break;

            default:
                answer = null;
                break;
            }
        } else {
            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
        }

        return answer;
    }
	
	/**
	 * 数值类型是否强制转化为BigDecimal类型
	 */
	public boolean isForceBigDecimals() {
		return forceBigDecimals;
	}
	/**
	 * 数值类型是否强制转化为BigDecimal类型
	 */
	public void setForceBigDecimals(boolean forceBigDecimals) {
		this.forceBigDecimals = forceBigDecimals;
	}
}
