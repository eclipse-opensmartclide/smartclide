package de.atb.context.monitoring.config.models.datasources;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import java.net.URI;

import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.DataSourceType;
import de.atb.context.monitoring.models.IWebService;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import de.atb.context.common.authentication.Credentials;

/**
 * WebServiceDataSource
 *
 * @author scholze
 * @version $LastChangedRevision: 156 $
 */
@RdfType("WebServiceDataSource")
@Namespace("http://atb-bremen.de/")
public class WebServiceDataSource extends DataSource {

    /**
     *
     */
    private static final long serialVersionUID = 3490943354053238739L;

    public WebServiceDataSource() {

    }

    public WebServiceDataSource(final DataSource base) {
        this.id = base.getId();
        this.monitor = base.getMonitor();
        this.options = base.getOptions();
        this.type = base.getType().toString();
        this.uri = base.getUri();
    }

    @Override
    public final DataSourceType getType() {
        return DataSourceType.WebService;
    }

    public final Long getInterval() {
        return (Long) this.getOptionValue(WebServiceDataSourceOptions.PollingInterval, true);
    }

    public final String getUserName() {
        return (String) this.getOptionValue(WebServiceDataSourceOptions.UserName, true);
    }

    public final String getPassword() {
        return (String) this.getOptionValue(WebServiceDataSourceOptions.Password, true);
    }

    public final String getMachineId() {
        return (String) this.getOptionValue(WebServiceDataSourceOptions.MachineId, true);
    }

    public final Credentials getCredentials() {
        String userName = this.getUserName();
        String password = this.getPassword();
        return new Credentials(userName, password);
    }

    public final Long getStartDelay() {
        return (Long) this.getOptionValue(WebServiceDataSourceOptions.StartDelay, true);
    }

    public final IWebService toWebService() {
        final URI myUri = URI.create(uri);
        final Credentials myCredentials = getCredentials();
        return new IWebService() {

            @Override
            public URI getURI() {
                return myUri;
            }

            @Override
            public Credentials getCredentials() {
                return myCredentials;
            }
        };
    }
}
