package de.atb.context.services.interfaces;

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


import de.atb.context.infrastructure.ServiceInfo;

/**
 *
 * @author Giovanni
 */
public class DataInput {

    private String resultId;
    private String type;
    private ServiceInfo repositoryDetails;

    public DataInput() {
    }

    public DataInput(final String ResultId, final String type) {
        this.resultId = ResultId;
        this.type = type;
    }

    public final String getResultId() {
        return resultId;
    }

    public final void setResultId(final String bean) {
        this.resultId = bean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ServiceInfo getRepositoryDetails() {
        return repositoryDetails;
    }

    public void setRepositoryDetails(ServiceInfo repositoryDetails) {
        this.repositoryDetails = repositoryDetails;
    }

}
