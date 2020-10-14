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


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * WebServiceDataSourceOptions
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public enum WebServiceDataSourceOptions implements IDataSourceOptionValue {

    PollingInterval("interval", Long.class),

    UserName("username", String.class),

    Password("password", String.class),

    StartDelay("startDelay", Long.class),

    MachineId("machineId", String.class),

    ;

    private final static Map<String, Class<? extends Serializable>> keysToClasses = new HashMap<>();
    private String key;
    private Class<? extends Serializable> valueType;

    static {
        for (WebServiceDataSourceOptions option : WebServiceDataSourceOptions.values()) {
            keysToClasses.put(option.key, option.valueType);
        }
    }

    WebServiceDataSourceOptions(final String optionKey, final Class<? extends Serializable> valueType) {
        this.key = optionKey;
        this.valueType = valueType;
    }

    @Override
    public String getKeyName() {
        return this.key;
    }

    @Override
    public Class<? extends Serializable> getValueType() {
        return this.valueType;
    }
}
