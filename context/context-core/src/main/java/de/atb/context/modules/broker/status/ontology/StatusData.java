package pt.uninova.context.modules.broker.status.ontology;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2016 - 2020 ATB
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
