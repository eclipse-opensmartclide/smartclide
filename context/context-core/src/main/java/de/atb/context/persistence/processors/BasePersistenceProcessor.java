package de.atb.context.persistence.processors;

/*
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

import java.util.UUID;

/**
 * BasePersisteProcessor
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 * @param <T>
 *            T
 */
public abstract class BasePersistenceProcessor<T> implements
IPersistenceProcessor<T> {

	protected String id;

	public BasePersistenceProcessor() {
		id = UUID.randomUUID().toString();
	}

	public BasePersistenceProcessor(final String id) {
		this.id = id;
	}

	@Override
	public final void setId(final String id) {
		this.id = id;
	}

	@Override
	public final String getId() {
		return id;
	}

}
