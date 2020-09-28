package de.atb.context.common.authentication;

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

import de.atb.context.common.util.Hashing;

/**
 * Credentials
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public class Credentials {

	protected String userName;
	protected String password;

	/**
	 * Creates empty Credentials which initializes username as well as password
	 * with a {@code null} value.
	 */
	public Credentials() {
		// default empty constructor
	}

	public Credentials(final String userName, final String password) {
		this.userName = userName;
		this.password = password;
	}

	public final String getUserName() {
		return userName;
	}

	public final void setUserName(final String userName) {
		this.userName = userName;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(final String password) {
		this.password = password;
	}

	public final String getMD5HashedPassword() {
		return Hashing.getMD5Hash(password);
	}

	public final String getSHA1HashedPassword() {
		return Hashing.getSHA1Hash(password);
	}

	public final String getSHA256HashedPassword() {
		return Hashing.getSHA256Hash(password);
	}

	@Override
	public final String toString() {
		return "('" + userName + "' // '" + password + "')";
	}

}
