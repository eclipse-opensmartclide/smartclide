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


import java.io.FilenameFilter;

import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.DataSourceType;
import de.atb.context.monitoring.parser.ExtensionFilenameFilter;

/**
 * FileTripletSystemDataSource
 *
 * @author scholze
 * @version $LastChangedRevision: 156 $
 */
@RdfType("FileTripletSystemDataSource")
@Namespace("http://atb-bremen.de/")
public class FileTripletSystemDataSource extends DataSource {

    /**
     *
     */
    private static final long serialVersionUID = 5781922611932041148L;

    public FileTripletSystemDataSource() {

    }

    public FileTripletSystemDataSource(final DataSource base) {
        this.id = base.getId();
        this.monitor = base.getMonitor();
        this.options = base.getOptions();
        this.type = base.getType().toString();
        this.uri = base.getUri();
    }

    public final FilenameFilter getFilenameFilter() {
        String[] extensions = new String[]{getFirstExtension(), getSecondExtension(), getThirdExtension()};
        return new ExtensionFilenameFilter(extensions);

    }

    @Override
    public final DataSourceType getType() {
        return DataSourceType.FileTripletSystem;
    }

    public final String getMachineId() {
        return this.getOptionValue(FileTripletSystemDataSourceOptions.MachineId);
    }

    public final String getFirstExtension() {
        return this.getOptionValue(FileTripletSystemDataSourceOptions.ExtensionOne);
    }

    public final String getSecondExtension() {
        return this.getOptionValue(FileTripletSystemDataSourceOptions.ExtensionTwo);
    }

    public final String getThirdExtension() {
        return this.getOptionValue(FileTripletSystemDataSourceOptions.ExtensionThree);
    }

}
