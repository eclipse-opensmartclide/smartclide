/*
 * @(#)ServiceManager.java
 *
 * $Id: ServiceManager.java 679 2016-11-24 18:29:04Z gsimoes $
 * 
 * $Rev:: 679                  $ 	last change revision
 * $Date:: 2016-11-24 19:29:04#$	last change date
 * $Author:: gsimoes           $	last change author
 * 
 * Copyright 2011-15 Oliver Kotte. All rights reserved.
 *
 */
package de.atb.context.services.manager;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import lombok.Getter;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.modules.Deployer;
import de.atb.context.services.SWServiceContainer;
import de.atb.context.services.config.models.SWService;
import de.atb.context.services.infrastructure.ServiceRegistryService;
import de.atb.context.services.interfaces.IPrimitiveService;

import javax.jws.WebService;
import javax.xml.ws.BindingProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * ServiceManager
 *
 * @author kotte
 * @version $LastChangedRevision: 679 $
 */
public class ServiceManager {

    public static final String PING_RESPONSE = "pong";
    public static final int DEFAULT_SERVICE_PORT = 55666;
    public static final String DEFAULT_SERVICE_HOST = "localhost";

    public static final String SERVICE_PATTERN = "http://%s:%d/%s";
    private static final String WS_ADDRESSING_REPLYTO = "org.apache.cxf.ws.addressing.replyto";

    @Getter
    private static final List<SWServiceContainer> LSWServiceContainer = new ArrayList<>();
    @Getter
    private static Deployer deployer;
    @Getter
    private static de.atb.context.modules.Server server;
    //public static Notifier notifier;

    private static final Logger logger = LoggerFactory
            .getLogger(ServiceManager.class);

    public static synchronized void shutdownServiceAndEngine(final Server server) {
        if (server != null) {
            String address = "";
            if ((server.getEndpoint() != null)
                    && (server.getEndpoint().getEndpointInfo() != null)) {
                address = server.getEndpoint().getEndpointInfo().getAddress();
                server.getEndpoint().getService().getName();
                server.getEndpoint().getService();
            }
            logger.info("Stopping server " + address);
            server.stop();
            server.destroy();

            logger.info("Server " + address + " stopped.");
        } else {
            logger.warn("Server is null!");
        }
    }

    // public static synchronized void shutdownBus(JaxWsServerFactoryBean sfb) {
    // sfb.getBus().shutdown(true);
    // }
    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final SWServiceContainer service) {
        if (service.getLocation() != null) {
            int port = service.getLocation().getPort() == -1 ? DEFAULT_SERVICE_PORT
                    : service.getLocation().getPort();

            String host = String.valueOf(service.getLocation().getHost());
            host = (host.equals("null") || (host.trim().length() == 0)) ? DEFAULT_SERVICE_HOST
                    : host;

            String name = service.getName();
            name = (name.equals("null") || (name.trim().length() == 0)) ? getServiceNameFromClass(service
                    .getServerClass()) : name;

            return registerWebservice(host, port, name,
                    createInstance(service.getServerClass()),
                    service.getServerClass(), service);
        }
        return null;
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final Class<? extends T> serviceClass) {
        return registerWebservice(DEFAULT_SERVICE_HOST, DEFAULT_SERVICE_PORT,
                createInstance(serviceClass), serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final String host, final Class<? extends T> serviceClass) {
        return registerWebservice(host, DEFAULT_SERVICE_PORT,
                createInstance(serviceClass), serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final int port, final Class<? extends T> serviceClass) {
        return registerWebservice(DEFAULT_SERVICE_HOST, port,
                createInstance(serviceClass), serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final String host, final int port, final Class<? extends T> serviceClass) {
        return registerWebservice(host, port, createInstance(serviceClass),
                serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final T serviceBean, final Class<? extends T> serviceClass) {
        return registerWebservice(DEFAULT_SERVICE_HOST, DEFAULT_SERVICE_PORT,
                serviceBean, serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final String host, final T serviceBean, final Class<? extends T> serviceClass) {
        return registerWebservice(host, DEFAULT_SERVICE_PORT, serviceBean,
                serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final int port, final T serviceBean, final Class<? extends T> serviceClass) {
        return registerWebservice(DEFAULT_SERVICE_HOST, port, serviceBean,
                serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final String host, final int port, final T serviceBean,
            final Class<? extends T> serviceClass) {
        String serviceName = getServiceNameFromClass(serviceClass);
        return registerWebservice(host, port, serviceName, serviceBean,
                serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final String host, final int port, final String serviceName, final T serviceBean,
            final Class<? extends T> serviceClass) {
        String address = String.format(SERVICE_PATTERN, host,
                Integer.valueOf(port), serviceName);
        logger.info("Trying to create Service '" + serviceName + "' at " + host
                + ":" + port);
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();

        svrFactory.setServiceClass(serviceClass);
        svrFactory.setAddress(address);
        svrFactory.setServiceBean(serviceBean);
        svrFactory.getInInterceptors().add(new LoggingInInterceptor());
        svrFactory.getOutInterceptors().add(new LoggingOutInterceptor());
        // svrFactory.getFeatures().add(new WSAddressingFeature());
        Server server = svrFactory.create();
        logger.info("Created Service '" + serviceName + "' at " + host + ":"
                + port);
        logger.info("Created Service '" + serviceName + "' at " + host + ":"
                + port + " is of type " + server.getClass());
        //server.start();
        logger.info("Service '" + serviceName + "' at " + host + ":" + port
                + " started.");
        return server;
    }

    public static synchronized <T extends IPrimitiveService> Server registerWebservice(
            final String host, final int port, final String serviceName, final T serviceBean,
            final Class<? extends T> serviceClass, final SWServiceContainer service) {

        // WebService annotation = serviceClass.getAnnotation(WebService.class);
        String address = String.format(SERVICE_PATTERN, host,
                Integer.valueOf(port), serviceName);
        // String teste = annotation.wsdlLocation();
        logger.info("Trying to create Service '" + serviceName + "' at " + host
                + ":" + port);
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
        svrFactory.setServiceClass(serviceClass);
        svrFactory.setAddress(address);
        svrFactory.setServiceBean(serviceBean);
        svrFactory.getInInterceptors().add(new LoggingInInterceptor());
        svrFactory.getOutInterceptors().add(new LoggingOutInterceptor());
        // svrFactory.getFeatures().add(new WSAddressingFeature());
        Server server = svrFactory.create();
        logger.info("Created Service '" + serviceName + "' at " + host + ":"
                + port);
        logger.info("Created Service '" + serviceName + "' at " + host + ":"
                + port + " is of type " + server.getClass());
        //server.start();
        logger.info("Service '" + serviceName + "' at " + host + ":" + port
                + " started.");
        logger.info("Add a new Service Container to Service Manager: "
                + serviceName);
        logger.info("Added a new Service COntainer to Service Manager: "
                + serviceName);
        return server;
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T extends IPrimitiveService> T getWebservice(
            final SWServiceContainer service) {
        int port = service.getLocation().getPort() == -1 ? DEFAULT_SERVICE_PORT
                : service.getLocation().getPort();

        String host = String.valueOf(service.getLocation().getHost());
        host = (host.equals("null") || (host.trim().length() == 0)) ? DEFAULT_SERVICE_HOST
                : host;

        String name = service.getName();
        name = (name.equals("null") || (name.trim().length() == 0)) ? getServiceNameFromClass(service
                .getProxyClass()) : name;

        return (T) getWebservice(host, port, name, service.getProxyClass());
    }

    public static synchronized <T extends IPrimitiveService> T getWebservice(
            final Class<? extends T> serviceClass) {
        return getWebservice(DEFAULT_SERVICE_HOST, DEFAULT_SERVICE_PORT,
                serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> T getWebservice(
            final String host, final Class<? extends T> serviceClass) {
        return getWebservice(host, DEFAULT_SERVICE_PORT, serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> T getWebservice(
            final int port, final Class<? extends T> serviceClass) {
        return getWebservice(DEFAULT_SERVICE_HOST, port, serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> T getWebservice(
            final String host, final int port, final Class<? extends T> serviceClass) {
        return getWebservice(host, port, getServiceNameFromClass(serviceClass),
                serviceClass);
    }

    public static synchronized <T extends IPrimitiveService> T getWebservice(
            final URL wsdl, final Class<? extends T> serviceClass) {
        return getWebservice(wsdl.getHost(), wsdl.getPort(), getServiceNameFromClass(serviceClass),
                serviceClass);
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T extends IPrimitiveService> T getWebservice(
            final String host, final int port, final String serviceName,
            final Class<? extends T> serviceClass) {
        String address = String.format(SERVICE_PATTERN, host,
                Integer.valueOf(port), serviceName);
        logger.info("Trying to receive Service '" + serviceName + "' at "
                + host + ":" + port);
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.setServiceClass(serviceClass);
        factory.setAddress(address);
        T service = (T) factory.create();
        logger.info("Received Service at '%s' at %s:%s", serviceName, address, port);
        setClientPolicies(service);
        return service;
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T extends IPrimitiveService> T getWebservice(
            final String address, final String serviceName, final Class<? extends T> serviceClass) {
        logger.info("Trying to receive Service '%s' at %s", serviceName, address);
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.setServiceClass(serviceClass);
        factory.setAddress(address);
        T service = (T) factory.create();
        logger.info("Received Service '%s' at %s", serviceName, address);
        setClientPolicies(service);
        return service;
    }

    public static synchronized Client getDynamicWebservice(final String wsdl_address) {
        try {
            DynamicClientFactory dcf = DynamicClientFactory.newInstance();
            URL domain = new URL(wsdl_address);
            return dcf.createClient(domain.toExternalForm());
        } catch (MalformedURLException ex) {
            logger.debug(ex.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T extends IPrimitiveService> T getWebserviceDecoupledMode(
            final String address, final String serviceName, final Class<? extends T> serviceClass) {
        logger.info("Trying to receive Service '%s' at %s", serviceName, address);
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        ClientFactoryBean cfb = factory.getClientFactoryBean();
        WSAddressingFeature wsAddressingFeature = new WSAddressingFeature();
        wsAddressingFeature.initialize(cfb, cfb.getBus());
        LoggingFeature loggingfeature = new LoggingFeature();
        loggingfeature.initialize(cfb.getBus());
        String[] responseAddressArray = address.split(":");
        String responseAddress = responseAddressArray[2].replaceAll("[^0-9]",
                "");
        String hostAddress = responseAddressArray[1].replaceAll(
                "^[^\\w]+|[^\\w]+$", "");
        int responseAddressInt = Integer.parseInt(responseAddress) + 100;

        String addressResponse = String.format(SERVICE_PATTERN, hostAddress,
                Integer.valueOf(responseAddressInt), serviceName);

        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.setServiceClass(serviceClass);
        factory.setAddress(address);
        T service = (T) factory.create();
        ((BindingProvider) service).getRequestContext().put(
                WS_ADDRESSING_REPLYTO, addressResponse);
        logger.info("Received Service '%s' at %s", serviceName, address);
        setClientPolicies(service);
        return service;
    }

    public static synchronized <T extends IPrimitiveService> boolean isPingable(
            final T service) {
        if (service == null) {
            throw new NullPointerException("Given service is null.");
        }
        try {
            return service.ping() != null;
        } catch (Throwable t) {
            logger.warn("Service is not pingable, it may be down.");
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    protected static <T extends IPrimitiveService> T createInstance(
            final Class<? extends T> serviceClass) {
        int modifier = serviceClass.getModifiers();

        if (!Modifier.isAbstract(modifier) && !Modifier.isInterface(modifier)
                && !Modifier.isStatic(modifier)) {
            try {
                Constructor<?> constructor = serviceClass.getConstructor();
                T instance = (T) constructor.newInstance();
                if (instance instanceof ServiceRegistryService) {
                    ServiceRegistryService server = (ServiceRegistryService) instance;
                    ServiceManager.server = server.getServerInstance();
                }
                return instance;
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(String.format(
                        "Error while instantiating class [%s].",
                        serviceClass.getName()), e);
            }
        } else {
            throw new RuntimeException(String.format(
                    "Can't instantiate Class [%s]!", serviceClass.getName()));
        }
    }

    public static <T extends IPrimitiveService> String getServiceNameFromClass(
            final Class<? extends T> serviceClass) {
        String serviceName = serviceClass.getSimpleName();
        WebService annotation = serviceClass.getAnnotation(WebService.class);
        if (annotation != null) {
            return annotation.name();
        }

        for (Class<?> clazz : serviceClass.getInterfaces()) {
            annotation = clazz.getAnnotation(WebService.class);
            if (annotation != null) {
                return annotation.name();
            }
        }
        return serviceName;
    }

    public static Class<?> getServiceClassFromName(final String ClassName) {
        return null;
    }

    private static SWService getSWService(final String address) {
        for (SWServiceContainer container : ServiceManager.LSWServiceContainer) {
            String url = container.getService().getLocation() + "/"
                    + container.getService().getName();
            if (url.equals(address)) {
                return container.getService();
            }
        }
        return null;
    }

    private static String getNamespaceURI(final Server server, final Service service) {
        List<ServiceInfo> infos = server.getEndpoint().getService()
                .getServiceInfos();
        infos.get(0).getSchemas().get(0);
        Object object = service.get("endpoint.class");
        Class<?> serviceClazz = (Class<?>) object;
        Annotation[] annotations = serviceClazz.getDeclaredAnnotations();
        annotations[0].toString();
        for (SchemaInfo element : infos.get(0).getSchemas()) {
            for (Annotation annotation : annotations) {
                if (annotation.toString().contains(element.getNamespaceURI())) {
                    return element.getNamespaceURI();
                }
            }
        }
        return null;
    }

    private static synchronized <T extends IPrimitiveService> void setClientPolicies(T service) {
        Client client = ClientProxy.getClient(service);
        client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(10);
        httpClientPolicy.setReceiveTimeout(10);
    }

}
