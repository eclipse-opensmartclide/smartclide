package de.atb.context.services.interfaces;

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


import de.atb.context.infrastructure.ServiceInfo;

/**
 *
 * @author Giovanni
 */
public class DataOutput {

    private String resultId;
    private String type;
    private ServiceInfo reposData;

    public DataOutput() {
    }

    public DataOutput(final String ResultId, final String type) {
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

    public ServiceInfo getReposData() {
        return reposData;
    }

    public void setReposData(ServiceInfo reposData) {
        this.reposData = reposData;
    }

}
