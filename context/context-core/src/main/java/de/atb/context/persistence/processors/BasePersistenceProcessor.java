package de.atb.context.persistence.processors;

/*
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
