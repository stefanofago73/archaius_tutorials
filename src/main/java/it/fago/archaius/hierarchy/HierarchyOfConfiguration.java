package it.fago.archaius.hierarchy;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConcurrentMapConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;

public class HierarchyOfConfiguration {

	public static void setup() throws ConfigurationException {
		// configuration from local properties file
		String fileName = "example.properties";
		ConcurrentMapConfiguration configFromPropertiesFile = new ConcurrentMapConfiguration(
				new PropertiesConfiguration(fileName));
		// configuration from system properties
		ConcurrentMapConfiguration configFromSystemProperties = new ConcurrentMapConfiguration(
				new SystemConfiguration());
		// configuration from a dynamic source
		PolledConfigurationSource source = new DummyPollingSource(false);
		AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(0,
				10, false);
		DynamicConfiguration dynamicConfiguration = new DynamicConfiguration(
				source, scheduler);

		// create a hierarchy of configuration that makes
		// 1) dynamic configuration source override system properties and,
		// 2) system properties override properties file
		ConcurrentCompositeConfiguration finalConfig = new ConcurrentCompositeConfiguration();
		finalConfig.addConfiguration(dynamicConfiguration, "dynamicConfig");
		finalConfig.addConfiguration(configFromSystemProperties, "systemConfig");
		finalConfig.addConfiguration(configFromPropertiesFile, "fileConfig");

		// install with ConfigurationManager so that finalConfig
		// becomes the source of dynamic properties
		ConfigurationManager.install(finalConfig);
	}

	public static void main(String[] args) throws ConfigurationException {
		setup();
	}
	
}// END
