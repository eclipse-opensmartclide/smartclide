/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
