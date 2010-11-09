package net.emforge.activiti;

import java.util.Map;

import net.emforge.activiti.identity.LiferayIdentitySessionFactory;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.IdentitySession;
import org.activiti.engine.impl.cfg.ProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.ProcessEngineFactoryBean;

/** Custom implementation of ProcessEngineFactoryBean to set own IdentitySession
 * and customize other things
 * 
 * @author akakunin
 *
 */
public class LiferayProcessEngineFactoryBean extends ProcessEngineFactoryBean {
	@Override
	public ProcessEngine getObject() throws Exception {
		// preconfigure process engine to use our identity session
		Map<Class< ? >, SessionFactory> sessionFactories = processEngineConfiguration.getSessionFactories();
		sessionFactories.put(IdentitySession.class, new LiferayIdentitySessionFactory());
		
		// set history level
		processEngineConfiguration.setHistoryLevel(ProcessEngineConfiguration.HISTORYLEVEL_FULL);
		
		// Add Liferay Script Engine Factory
		processEngineConfiguration.getScriptingEngines().addScriptEngineFactory(new LiferayScriptEngineFactory());
		return super.getObject();
	}
}