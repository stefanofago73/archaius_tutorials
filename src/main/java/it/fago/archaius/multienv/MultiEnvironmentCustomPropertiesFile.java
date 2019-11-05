package it.fago.archaius.multienv;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DeploymentContext;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringListProperty;
import com.netflix.config.DynamicStringMapProperty;
import com.netflix.config.DynamicStringProperty;

public class MultiEnvironmentCustomPropertiesFile {

	public static void main(String[] args) throws Exception {

		//
		// USING SYSTEM PROPS
		// System.setProperty("archaius.configurationSource.defaultFileName",
		// "newconfig.properties");
		//
		Logger logger = LoggerFactory
				.getLogger(MultiEnvironmentCustomPropertiesFile.class);
		//
		// USING SYSTEM PROPS
		//
		// E' possibile definire un DeploymentContext Custom
		// con:
		//
		// archaius.default.deploymentContext.class
		//
		// questa classe deve realizzare l'interfaccia DeploymentContext
		//
		//
		// E' anche possibile definire una Factory
		// che deve avere un metodo getInstance
		//
		// archaius.default.deploymentContext.factory
		//
		// e deve restituire una qualsiasi realizzazione di
		// AbstractConfiguration
		//
		// DEPLOYMENT CONTEXT WITH SYSTEM PROPERTIES
		// archaius.deployment.environment - Es.
		// System.setProperty("archaius.deployment.environment", "test");
		// archaius.deployment.region
		// archaius.deployment.datacenter
		// archaius.deployment.applicationId
		// archaius.deployment.serverId
		// archaius.deployment.stack
		//
		//

		ConfigurationManager.getConfigInstance().setProperty(
				DeploymentContext.ContextKey.environment.getKey(), "INT");
		ConfigurationManager.loadCascadedPropertiesFromResources("demo");
		DeploymentContext dc = ConfigurationManager.getDeploymentContext();

		logger.info(
				"app id {} server id {} env {} stack {} datacenter {} region {}",
				dc.getApplicationId(), dc.getDeploymentServerId(),
				dc.getDeploymentEnvironment(), dc.getDeploymentStack(),
				dc.getDeploymentDatacenter(), dc.getDeploymentRegion());

		DynamicStringProperty p1 = DynamicPropertyFactory.getInstance()
				.getStringProperty("stringprop", "");

		DynamicStringListProperty p2 = new DynamicStringListProperty(
				"listprop", Collections.<String> emptyList());

		DynamicStringMapProperty p3 = new DynamicStringMapProperty("mapprop",
				Collections.<String, String> emptyMap());

		DynamicLongProperty p4 = DynamicPropertyFactory.getInstance()
				.getLongProperty("longprop", 1000);

		logger.info("\n{}\n{}\n{}\n{}\n", p1.get(), p2.get(), p3.getMap(),
				p4.get());
		
		logger.info("\nBefore Env Change[ENV={}] --> string prop: {}  long prop: {} \n",dc.getDeploymentEnvironment(),p1.get(),p2.get());
		
		
		//
		//
		// ...Cambio variabile di ambiente
		// ma per vedere i nuovi valori devo
		// usare un'altra primitiva...
		//
		//
		ConfigurationManager.getConfigInstance().setProperty(
				DeploymentContext.ContextKey.environment.getKey(), "test");
		ConfigurationManager.loadAppOverrideProperties("demo");
		
		
		logger.info("\nAfter Env Change[ENV={}] --> string prop: {}  long prop: {} \n",dc.getDeploymentEnvironment(),p1.get(),p2.get());
	}
}
