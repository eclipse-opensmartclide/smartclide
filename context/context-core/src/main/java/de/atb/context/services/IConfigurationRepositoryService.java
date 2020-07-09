package pt.uninova.context.services;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2016 - 2020 ATB
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.services.faults.ContextFault;
import pt.uninova.context.services.interfaces.IRepositoryService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * IConfigurationRepositoryService
 * 
 * @author scholze
 * @version $LastChangedRevision: 176 $
 * 
 */
@WebService(name = "ConfigurationRService", targetNamespace = "http://atb-bremen.de")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IConfigurationRepositoryService extends
		IRepositoryService {

	@WebMethod(operationName = "getConfiguration")
    String getConfiguration(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario)
			throws ContextFault;

	@WebMethod(operationName = "getConfigurationWithClazzName")
    String getConfiguration(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "class-name") String clazzName)
			throws ContextFault;

	@WebMethod(operationName = "persistConfiguration")
    void persistConfiguration(
            @WebParam(name = "configuration") String serializeConfiguration,
            @WebParam(name = "class-name") String clazzName)
			throws ContextFault;

	@WebMethod(operationName = "clearBusinessCaseDirectory")
    boolean clearBusinessCaseDirectory(
            @WebParam(name = "business-case") BusinessCase businessCase)
			throws ContextFault;

	@WebMethod(operationName = "deleteApplicationScenarioConfiguration")
    boolean deleteApplicationScenarioConfiguration(
            @WebParam(name = "application-scenario") ApplicationScenario scenario)
			throws ContextFault;

	@WebMethod(operationName = "clearBaseDirectory")
    boolean clearBaseDirectory() throws ContextFault;

}
