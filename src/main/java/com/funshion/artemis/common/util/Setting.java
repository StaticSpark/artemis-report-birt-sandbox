package com.funshion.artemis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 系统设置信息
 * @author shenwf
 * Reviewed-by jiangxu
 */
public class Setting {
	protected transient Log logger = LogFactory.getLog(getClass());
	private Properties props = new Properties();
	private static final String SETTING_FILE = "jdbc.properties";
	private static final String KEY_FILE_SPARATOR = "file.separator";
	private String settingFilePath;
	
	/**
	 * 获取数据库驱动
	 * @return
	 */
	public String getDbDriver() {
		return props.getProperty("jdbc.driver", "");
	}

	/**
	 * 得到数据库连接
	 */
	public String getDbUrl() {
		return props.getProperty("jdbc.url", "");
	}

	/**
	 * 得到数据库用户
	 */
	public String getDbUsername() {
		return props.getProperty("jdbc.username", "");
	}

	/**
	 * 得到数据库密码
	 */
	public String getDbPassword() {
		return props.getProperty("jdbc.password", "");
	}
	
	/**
	 * Jndi 名字
	 * @return
	 */
	public String getJndiName() {
		return props.getProperty("jndi.name", "");
	}

	public Setting() {
		settingFilePath = new JarUtil(JarUtil.class).getJarPath();
		
		File file = new File(settingFilePath);
		File parentFile = file.getParentFile().getParentFile().getParentFile().getParentFile();
		settingFilePath = parentFile.getAbsolutePath();
		
		if (!settingFilePath.endsWith(System.getProperty(KEY_FILE_SPARATOR))) {
			settingFilePath += System.getProperty(KEY_FILE_SPARATOR);
		}
		settingFilePath += SETTING_FILE;

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(settingFilePath);
			props.load(fis);
		} catch (Exception e) {
			logger.error("Fail to load properties from file \"" + settingFilePath + "\"", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void save() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(settingFilePath);
			props.store(fos, "");
		} catch (Exception e) {
			logger.error("Fail to save properties to file \"" + settingFilePath + "\"", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
