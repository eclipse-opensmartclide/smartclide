package de.atb.context.monitoring;

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


import de.atb.context.monitoring.models.IMonitoringDataModel;

/**
 * IMonitoringMapper
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public interface IMonitoringDataProvider<M extends IMonitoringDataModel<?, ?>, D extends IMonitoringData<?>> {

    D convertMonitoringDataModel(M model);

}
