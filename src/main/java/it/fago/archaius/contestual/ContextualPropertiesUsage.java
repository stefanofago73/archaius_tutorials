package it.fago.archaius.contestual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DeploymentContext;
import com.netflix.config.DynamicContextualProperty;

public class ContextualPropertiesUsage {

	public static void main(String[] args) throws Exception {

		//
		// USING SYSTEM PROPS
		// System.setProperty("archaius.configurationSource.defaultFileName",
		// "newconfig.properties");
		//
		Logger logger = LoggerFactory
				.getLogger(ContextualPropertiesUsage.class);

		String jsonData = "[{ \"if\": " + "{\"@environment\":[\"prod\"],"
				+ "\"@region\":[\"us-east-1\"]}," + "\"value\":5},"
				+ "{\"if\":" + "{\"@environment\":[\"test\", \"dev\"]},"
				+ "\"value\":10}," + "{\"value\":2}]}";

		DynamicContextualProperty<Integer> ctxProp = new DynamicContextualProperty<Integer>(
				"DCTX1", -1);

		ConfigurationManager.getConfigInstance().setProperty("DCTX1", jsonData);

		// ottengo valore 10
		ConfigurationManager.getConfigInstance().setProperty(
				DeploymentContext.ContextKey.environment.getKey(), "test");
		logger.info("CTX Prop : {} ", ctxProp.getValue());

		// ottengo valore 2
		ConfigurationManager.getConfigInstance().setProperty(
				DeploymentContext.ContextKey.environment.getKey(), "prod");
		logger.info("CTX Prop : {} ", ctxProp.getValue());

		// ottengo valore 5
		ConfigurationManager.getConfigInstance().setProperty(
				DeploymentContext.ContextKey.environment.getKey(), "prod");
		ConfigurationManager.getConfigInstance().setProperty(
				DeploymentContext.ContextKey.region.getKey(), "us-east-1");
		logger.info("CTX Prop : {} ", ctxProp.getValue());
	}

}// END