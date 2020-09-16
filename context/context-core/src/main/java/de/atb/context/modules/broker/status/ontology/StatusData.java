package de.atb.context.modules.broker.status.ontology;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB
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


/**
 *
 * @author Giovanni
 */
public class StatusData {

    private ServerHealth serverStatus;
    private DeploymentStatus deploymentStatus;
    private Alerts alerts;
    private Notifications notifications;
    private AvailableServices connectedServices;

    public StatusData() {
    }

    public StatusData(final ServerHealth serverStatus,
            final DeploymentStatus deploymentStatus, final Alerts alerts,
            final AvailableServices connectedServices) {
        this.serverStatus = serverStatus;
        this.deploymentStatus = deploymentStatus;
        this.alerts = alerts;
        this.connectedServices = connectedServices;
    }

    public final ServerHealth getServerStatus() {
        return serverStatus;
    }

    public final void setServerStatus(final ServerHealth serverStatus) {
        this.serverStatus = serverStatus;
    }

    public final DeploymentStatus getDeploymentStatus() {
        return deploymentStatus;
    }

    public final void setDeploymentStatus(final DeploymentStatus deploymentStatus) {
        this.deploymentStatus = deploymentStatus;
    }

    public final Alerts getAlerts() {
        return alerts;
    }

    public final void setAlerts(final Alerts alerts) {
        this.alerts = alerts;
    }

    public Notifications getNotifications() {
        return notifications;
    }

    public void setNotifications(Notifications notifications) {
        this.notifications = notifications;
    }

    public final AvailableServices getConnectedServices() {
        return connectedServices;
    }

    public final void setConnectedServices(final AvailableServices connectedServices) {
        this.connectedServices = connectedServices;
    }

}
