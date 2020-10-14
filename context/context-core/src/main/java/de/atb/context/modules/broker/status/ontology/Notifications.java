package de.atb.context.modules.broker.status.ontology;

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


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public class Notifications {
    
    private List<Notify> lNotification = new ArrayList<>();

    public Notifications() {
    }
    
    public Notifications(List<Notify> lNotification) {
        this.lNotification = lNotification;
    }

    public List<Notify> getlNotification() {
        return lNotification;
    }

    public void setlNotification(List<Notify> lNotification) {
        this.lNotification = lNotification;
    }
    
    
}
