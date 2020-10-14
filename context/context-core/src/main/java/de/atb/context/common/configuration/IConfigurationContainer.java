package de.atb.context.common.configuration;

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

/**
 * IConfigurationContainer
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 * @param <B>
 *            B
 *
 */
public interface IConfigurationContainer<B extends IConfigurationBean> {

	void reset();

}
