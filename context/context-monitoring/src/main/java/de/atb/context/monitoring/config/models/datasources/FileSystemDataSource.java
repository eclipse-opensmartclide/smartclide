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


import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.DataSourceType;

/**
 * FileSystemDataSource
 *
 * @author scholze
 * @version $LastChangedRevision: 156 $
 */
@RdfType("FileSystemDataSource")
@Namespace("http://atb-bremen.de/")
public class FileSystemDataSource extends DataSource {

    /**
     *
     */
    private static final long serialVersionUID = -5527817462760994068L;

    public FileSystemDataSource() {

    }

    public FileSystemDataSource(final DataSource base) {
        this.id = base.getId();
        this.monitor = base.getMonitor();
        this.options = base.getOptions();
        this.type = base.getType().toString();
        this.uri = base.getUri();
    }

    @Override
    public final DataSourceType getType() {
        return DataSourceType.FileSystem;
    }

    public final Boolean includeHiddenFiles() {
        return (Boolean) this.getOptionValue(FileSystemDataSourceOptions.IncludeHiddenFiles);
    }
}
