package it.fago.archaius.util;

import it.fago.archaius.callback.DynaPropWithCallback;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.sources.URLConfigurationSource;

/**
 * 
 * @author Stefano
 * 
 */
public class Utils {
	//
	private static boolean exceptionsActivated;

	public static final void activateException() {
		exceptionsActivated = true;
	}

	public static final void deactivateException() {
		exceptionsActivated = true;
	}

	public static final boolean exceptionsActivated() {
		return exceptionsActivated;
	}

	public static final Logger logger(Class<?> type) {
		return LoggerFactory.getLogger(type.getName());
	}

	public static final URL alternativeURL() {
		return DynaPropWithCallback.class.getClassLoader().getResource(
				"example.properties");
	}

	public static final void pauseFor(final long waitTimeMillis) {
		try {
			Thread.sleep(waitTimeMillis);
		} catch (InterruptedException e) {
			logger(Utils.class).warn("{} interrupted! ... ",
					Thread.currentThread().getName());
		}
	}

	public static final void anyKeyToStop(Logger logger) {
		try {
			logger.info("any key to stop...");
			System.in.read();
		} catch (Exception ex) {
			logger.warn("[anyKeyToStop] Something Wrong in sysin... {} ",
					ex.toString());
		}
	}

	public static final void systemPolling(final int initialDelayMillis,
			final int delayMillis) {
		System.setProperty("archaius.fixedDelayPollingScheduler.delayMills",
				String.valueOf(delayMillis));
		System.setProperty(
				"archaius.fixedDelayPollingScheduler.initialDelayMills",
				String.valueOf(initialDelayMillis));
	}

	public static final void disableDefaultConfiguration() {
		System.setProperty("archaius.dynamicProperty.disableDefaultConfig",
				"true");
	}

	public static final void enableDefaultConfiguration() {
		System.setProperty("archaius.dynamicProperty.disableDefaultConfig",
				"false");
	}

	public static final void setupForExternalUrl() {
		System.setProperty(URLConfigurationSource.CONFIG_URL, alternativeURL()
				.toExternalForm());
	}

	public static final void setupNewDefaultConfigFileName() {
		System.setProperty("archaius.configurationSource.defaultFileName",
				"newconfig.properties");
	}

	public static final void setupCustomDeploymentClass() {
		System.setProperty("archaius.default.deploymentContext.class",
				"it.fago.archaius.customclass.CustomDeployContext");
	}

	public static final void setupCustomDeploymentFactoryClass() {
		System.setProperty("archaius.default.deploymentContext.factory",
				"it.fago.archaius.customclass.CustomDeployContextFactory");
	}

	public static final void enableJmx() {
		System.setProperty(
				"archaius.dynamicPropertyFactory.registerConfigWithJMX", "true");
	}

	public static final void disableJmx() {
		System.setProperty(
				"archaius.dynamicPropertyFactory.registerConfigWithJMX",
				"false");
	}

}// END