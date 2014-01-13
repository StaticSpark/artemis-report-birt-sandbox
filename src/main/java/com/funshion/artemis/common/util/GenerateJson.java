package com.funshion.artemis.common.util;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * To generate json string from the entity
 * 
 * 从实体生成Json的工具程序
 * 
 * @author ?(Richard), jiangxu 
 * Reviewed-by
 */
public class GenerateJson {
	private ObjectMapper objectMapper = new ObjectMapper();

	public GenerateJson(Object o) {

	}

	public GenerateJson() {
	}

	public String generate(Object o) {
		String result = "";
		try {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			result = objectMapper.writeValueAsString(o);
			// System.out.println("result："+result);
		} catch (JsonGenerationException ex) {
			ex.printStackTrace();
		} catch (JsonMappingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public String generateThrowException(Object o) throws JsonGenerationException {
		String result = "";
		try {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			result = objectMapper.writeValueAsString(o);
			// System.out.println("result："+result);
		} catch (JsonGenerationException ex) {
			throw new JsonGenerationException("生成json数据异常",ex);
		} catch (JsonMappingException ex) {
			throw new JsonGenerationException("生成json数据异常",ex);
		} catch (IOException ex) {
			throw new JsonGenerationException("生成json数据异常",ex);
		}
		return result;
	}

	public String generateWithIndentOutput(Object o) {
		String result = "";
		try {
			objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			result = objectMapper.writeValueAsString(o);
			// System.out.println("result："+result);
		} catch (JsonGenerationException ex) {
			ex.printStackTrace();
		} catch (JsonMappingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
