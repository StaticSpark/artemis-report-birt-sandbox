package com.funshion.artemis.common.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 解析项目配置文件
 * @author shenwf
 * Reviewed by
 */
public class PropertiesConfigParse {
	private static ClassPathResource appResource = null;
	private static Properties appProps = null;
    private static Map<String, String> map = new HashMap<String, String>();
	static {
		String[] files = new String[] {"application.properties", "jdbc.properties","log4j.properties","cas.properties"};
		
		try {
			for(String file : files) {
				appResource = new ClassPathResource(file);
				appProps = PropertiesLoaderUtils.loadProperties(appResource);
				Set<Object> set = appProps.keySet();
				Iterator<Object> iterator = set.iterator();
				while(iterator.hasNext()) {
					String key = (String) iterator.next();
					map.put(key, (String)appProps.get(key));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取atlas home url
	 * @return
	 * @throws IOException
	 */
	public static String getAtlasHome() throws IOException {
		return map.get("atlas.home");
	}
	
	public static String getLogHome() {
		return map.get("log4j.logDir");
	}
	
	public static String getArtemisHome() {
		return map.get("artemis.index.url");
	}
}
