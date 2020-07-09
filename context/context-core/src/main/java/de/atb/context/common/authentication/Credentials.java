package de.atb.context.common.authentication;

/*
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
