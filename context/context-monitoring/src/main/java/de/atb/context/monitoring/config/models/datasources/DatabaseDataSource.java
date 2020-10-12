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


import de.atb.context.common.authentication.Credentials;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.DataSourceType;
import de.atb.context.monitoring.models.IDatabase;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

import java.net.URI;

/**
 * DatabaseDataSource
 *
 * @author scholze
 * @version $LastChangedRevision: 156 $
 */
@RdfType("DatabaseDataSource")
@Namespace("http://diversity-project.eu/")
public class DatabaseDataSource extends DataSource {

    /**
     *
     */
    private static final long serialVersionUID = 3490943354053238749L;

    public DatabaseDataSource() {

    }

    public DatabaseDataSource(final DataSource base) {
        this.id = base.getId();
        this.monitor = base.getMonitor();
        this.options = base.getOptions();
        this.type = base.getType().toString();
        this.uri = base.getUri();
    }

    @Override
    public final DataSourceType getType() {
        return DataSourceType.Database;
    }

    public final Long getInterval() {
        return (Long) this.getOptionValue(DatabaseDataSourceOptions.PollingInterval, true);
    }

    public final String getUserName() {
        return (String) this.getOptionValue(DatabaseDataSourceOptions.UserName, true);
    }

    public final String getPassword() {
        return (String) this.getOptionValue(DatabaseDataSourceOptions.Password, true);
    }

    public final String getMachineId() {
        return (String) this.getOptionValue(DatabaseDataSourceOptions.MachineId, true);
    }

    public final String getDatabaseDriver() {
        return (String) this.getOptionValue(DatabaseDataSourceOptions.DatabaseDriver, true);
    }

    public final String getDatabaseUri() {
        return (String) this.getOptionValue(DatabaseDataSourceOptions.DatabaseUri, true);
    }

    public final String getDatabaseSelect() {
        return (String) this.getOptionValue(DatabaseDataSourceOptions.DatabaseSelect, true);
    }

    public final Credentials getCredentials() {
        String userName = this.getUserName();
        String password = this.getPassword();
        return new Credentials(userName, password);
    }

    public final Long getStartDelay() {
        return (Long) this.getOptionValue(DatabaseDataSourceOptions.StartDelay, true);
    }

    public final IDatabase toDatabase() {
        final URI myUri = URI.create(uri);
        final Credentials myCredentials = getCredentials();
        return new IDatabase() {

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
