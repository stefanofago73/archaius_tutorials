package it.fago.archaius.customclass;

import static it.fago.archaius.util.Utils.logger;
import static it.fago.archaius.util.Utils.setupCustomDeploymentFactoryClass;

import java.io.IOException;

import org.slf4j.Logger;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DeploymentContext;

/**
 * 
 * @author Stefano
 * 
 */
public class UseCustomDeployContextFactory {

	public static void main(String[] args) throws IOException {

		Logger logger = logger(UseCustomDeployContextFactory.class);
		
		setupCustomDeploymentFactoryClass();
		
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
	}

}//END