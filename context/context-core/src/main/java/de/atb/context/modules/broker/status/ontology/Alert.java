/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Alert extends Message {
	private String Priority;
	private String Name;
	private String TimeStamp;
	private String Type;
	private String Component;
	private String Description;
        private String PESId;

	public Alert() {
	}

	public final String getPriority() {
		return Priority;
	}

	public final void setPriority(final String Priority) {
		this.Priority = Priority;
	}

	public final String getName() {
		return Name;
	}

	public final void setName(final String Name) {
		this.Name = Name;
	}

	public final String getTimeStamp() {
		return TimeStamp;
	}

	public final void setTimeStamp(final String TimeStamp) {
		this.TimeStamp = TimeStamp;
	}

	public final String getType() {
		return Type;
	}

	public final void setType(final String Type) {
		this.Type = Type;
	}

	public final String getComponent() {
		return Component;
	}

	public final void setComponent(final String Component) {
		this.Component = Component;
	}

	public final String getDescription() {
		return Description;
	}

	public final void setDescription(final String Description) {
		this.Description = Description;
	}

    public String getPESId() {
        return PESId;
    }

    public void setPESId(String PESId) {
        this.PESId = PESId;
    }

}
