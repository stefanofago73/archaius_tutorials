package it.fago.archaius.customclass;

import static it.fago.archaius.util.Utils.logger;
import static it.fago.archaius.util.Utils.setupCustomDeploymentClass;

import java.io.IOException;

import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DeploymentContext;

/**
 * 
 * @author Stefano
 * 
 */
public class UseCustomDeployContext {

	public static void main(String[] args) throws IOException {


		Logger logger = logger(UseCustomDeployContext.class);
		
		setupCustomDeploymentClass();
		
		ConfigurationManager.loadCascadedPropertiesFromResources("newconfig");

		// ========================================================================
		//
		// how to obtain DeploymentContext
		//
		// ========================================================================

		DeploymentContext dc = ConfigurationManager.getDeploymentContext();

		logger.info(
				"[FROM DEPLOYMENT CONTEXT] app id {} server id {} env {} stack {} datacenter {} region {}",
				dc.getApplicationId(), 
				dc.getDeploymentServerId(),
				dc.getDeploymentEnvironment(), 
				dc.getDeploymentStack(),
				dc.getDeploymentDatacenter(), 
				dc.getDeploymentRegion());

		// ========================================================================
		//
		// DeploymentContext informations can be obtained from Configuration...
		//
		// ========================================================================

		AbstractConfiguration config = ConfigurationManager.getConfigInstance();
		String appId = config.getString(DeploymentContext.ContextKey.appId
				.getKey());
		String serverId = config
				.getString(DeploymentContext.ContextKey.serverId.getKey());
		String environment = config
				.getString(DeploymentContext.ContextKey.environment.getKey());
		String stack = config.getString(DeploymentContext.ContextKey.stack
				.getKey());
		String datacenter = config
				.getString(DeploymentContext.ContextKey.datacenter.getKey());
		String region = config.getString(DeploymentContext.ContextKey.region
				.getKey());

		logger.info(
				"[FROM CONFIG] app id {} server id {} env {} stack {} datacenter {} region {}",
				appId, 
				serverId, 
				environment, 
				stack, 
				datacenter, 
				region);

	}

}//END