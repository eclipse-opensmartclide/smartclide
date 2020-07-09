package de.atb.context.monitoring.models;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import java.util.Date;

import de.atb.context.monitoring.IMonitoringData;
import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.monitoring.config.models.DataSource;

/**
 * IMonitoringDataModel
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public interface IMonitoringDataModel<T, D extends DataSource> extends IMonitoringData<T> {

    String getIdentifier();

    void setIdentifier(String identifier);

    String getMonitoringDataVersion();

    Date getMonitoredAt();

    String getDocumentIndexId();

    String getDocumentUri();

    String getImplementingClassName();

    String getContextIdentifierClassName();

    D getDataSource();

    void setDataSource(D dataSource);

    BusinessCase getBusinessCase();

    @Override
    ApplicationScenario getApplicationScenario();

    boolean triggersContextChange();

    void initialize();


}
