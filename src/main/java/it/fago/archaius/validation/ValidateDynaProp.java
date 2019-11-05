package it.fago.archaius.validation;

import it.fago.archaius.callback.DynaPropWithCallback;

import java.net.URL;
import java.util.Random;

import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.sources.URLConfigurationSource;
import com.netflix.config.validation.PropertyChangeValidator;
import com.netflix.config.validation.ValidationException;

public class ValidateDynaProp {

	public static void main(String[] args) {
		// What follow are the parameter as System properties to set timing
		//
		System.setProperty("archaius.fixedDelayPollingScheduler.delayMills",
				"1000");
		System.setProperty(
				"archaius.fixedDelayPollingScheduler.initialDelayMills",
				"10000");

		//
		// Setup external file
		// This File has priority on invoked newconfig.properties that follow...
		// The System Property is configured by the String:
		// "archaius.configurationSource.additionalUrls"
		//
		System.setProperty(URLConfigurationSource.CONFIG_URL, alternativeURL()
				.toExternalForm());

		Logger logger = logger();
		AbstractConfiguration config = ConfigurationManager.getConfigInstance();
		
		//
		// property setup
		//
		DynamicStringProperty p1 = new DynamicStringProperty("stringprop", ""){
			//
			// it's possible to validate also internally 
			//
			protected void validate(String newValue){
				logger().info("\nI'm the Dyna Prop! Someone trying to set my value to: {} ",newValue );
			}
		};
		
		p1.addValidator(validator1());
		p1.addValidator(validator2());
		p1.addValidator(validator3());
		p1.addCallback(callaback(p1));
		
		while (true) {
			logger.info("config: {}\nproperty: {}", config, p1);
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static final Logger logger() {
		return LoggerFactory.getLogger(DynaPropWithCallback.class);
	}

	private static final URL alternativeURL() {
		return DynaPropWithCallback.class.getClassLoader().getResource(
				"example.properties");
	}

	public static final PropertyChangeValidator validator1() {
		return new PropertyChangeValidator() {

			public void validate(String newValue) throws ValidationException {
				logger().info("\nValidator1! Received: {} ", newValue);
			}
		};
	}

	public static final PropertyChangeValidator validator2() {
		return new PropertyChangeValidator() {

			public void validate(String newValue) throws ValidationException {
				logger().info("\nValidator2! Received: {} ", newValue);
				if ("BOOM".equals(newValue)) {
					logger().info("Validator2! I Can't Accept: {} ", newValue);
					throw new ValidationException("Validator2 don't like it!");
				}
			}
		};
	}

	public static final PropertyChangeValidator validator3() {
		return new PropertyChangeValidator() {

			public void validate(String newValue) throws ValidationException {
				logger().info("\nValidator3! Received: {} ", newValue);
			}
		};
	}

	public static final Runnable callaback(final DynamicStringProperty source) {
		return new Runnable() {
			public void run() {
				logger().info("\n{} is changed at: {} with value: {} ",
						source.getName(), source.getChangedTimestamp(),
						source.get());
				if(new Random().nextInt(5)>3){
					throw new RuntimeException("DUMMY ERROR!");
				}
			}
		};
	}
}// END