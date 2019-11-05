package it.fago.archaius.customclass;

import com.netflix.config.SimpleDeploymentContext;

/**
 * 
 * @author Stefano
 *
 */
public class CustomDeployContext extends SimpleDeploymentContext{

	public CustomDeployContext(){
		setApplicationId("UNKNOWN");
		setDeploymentServerId("LOCALHOST");
		setDeploymentStack("LOCAL");
		setDeploymentEnvironment("TEST");
		setDeploymentDatacenter("NONE");
		setDeploymentRegion("NONE");
	}
	
}//END
