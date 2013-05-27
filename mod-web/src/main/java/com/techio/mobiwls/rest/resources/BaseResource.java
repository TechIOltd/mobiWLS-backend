/**
 * 
 */
package com.techio.mobiwls.rest.resources;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.AttributeNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author slavikos
 *
 */
public class BaseResource {
	
	public static final String EMPTY_STRING = "";

	public static final String JSON_CONTENT_TYPE = "application/json";

	protected static final String MEMORY_CACHE = "default-cache";

	protected CacheManager cacheManager = null;
	
	protected byte[] computeHash(Object object) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(String.valueOf(object).getBytes());
		return md.digest();
	}

	protected String convertByteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}
	@PreDestroy
	protected void destroy() {
		if (cacheManager != null) {
			cacheManager.shutdown();
		}
	}

	protected Object getAttribute(MBeanServer mbeanServer,
			ObjectName objectName, String attributeName) {

		try {
			return mbeanServer.getAttribute(objectName, attributeName);
		} catch (AttributeNotFoundException nsa) {
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(String.format(
					"failed to retrieve attribute '%s' from objectName '%s'",
					attributeName, objectName), ex);
		}

	}
	
	protected String getStringAttribute(MBeanServer mbeanServer,
			ObjectName objectName, String attributeName) {
		Object attribute = getAttribute(mbeanServer, objectName, attributeName);
		if (attribute != null)
			return String.valueOf(attribute);
		else
			return null;

	}

	@PostConstruct
	protected void initialize() {
		cacheManager = CacheManager.getInstance();
		Cache memoryCache = new Cache(MEMORY_CACHE, 5000, false, false, 5, 5);
		cacheManager.addCache(memoryCache);
		System.err.println("post construct!!!!!");
	}

}
