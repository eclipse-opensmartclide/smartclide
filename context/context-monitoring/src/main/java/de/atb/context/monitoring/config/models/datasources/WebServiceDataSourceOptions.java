package de.atb.context.monitoring.config.models.datasources;

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
