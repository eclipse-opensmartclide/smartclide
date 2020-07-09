package de.atb.context.persistence.common;

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

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.common.util.IApplicationScenarioProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository
 *
 * @author scholze
 * @version $LastChangedRevision: 703 $
 * @param <T>
 *            the IApplicationScenarioProvider type
 */
public abstract class RepositoryTDB<T extends IApplicationScenarioProvider>
extends Repository<T> {

	protected static final Logger logger = LoggerFactory
			.getLogger(RepositoryTDB.class);

	private final Map<BusinessCase, Dataset> datasets = new HashMap<>();

	protected RepositoryTDB(final String baseLocation) {
		super(baseLocation);
	}

	protected final synchronized boolean clearBaseDirectory() { // TODO this should maybe replace by a call to DRM API
		for (final BusinessCase bc : BusinessCase.values()) {
			clearBusinessCaseDirectory(bc);
		}
		return RepositoryTDB.clearDirectory(new File(basicLocation));
	}

	protected final synchronized boolean clearBusinessCaseDirectory( // TODO this should maybe replace by a call to DRM API
			final BusinessCase businessCase) {
		return RepositoryTDB.clearDirectory(new File(
				getLocationForBusinessCase(businessCase)));
	}

	protected static synchronized boolean clearDirectory(final File dir) { // TODO this should maybe replace by a call to DRM API
		if (dir.isDirectory()) {
			boolean oneFailed = false;
			for (final String file : dir.list()) {
				final boolean success = RepositoryTDB.clearDirectory(new File(
						dir, file));
				if (!success) {
					oneFailed = true;
					RepositoryTDB.logger.warn("Could not delete %s", dir.toString() + System.getProperty("file.separator") + file);
				}
			}
			RepositoryTDB.logger.info("Cleared directory "
					+ dir.getAbsolutePath());
			if (!oneFailed) {
				return true;
			}
		} else if (dir.isFile()) {
			return dir.delete();
		} else {
			RepositoryTDB.logger.warn("File or directory "
					+ dir.getAbsolutePath()
					+ " does not exist, creating directory!");
			return dir.mkdirs();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.atb.context.persistence.RepositoryBase#shuttingDown()
	 */
	@Override
	protected final void shuttingDown() {
		for (final Dataset set : datasets.values()) {
			TDB.sync(set);
			if (set.getDefaultModel() != null) {
				TDB.sync(set.getDefaultModel());
				set.getDefaultModel().close();
			}
			set.close();
		}
		datasets.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.atb.context.persistence.RepositoryBase#reset(de.atb.context
	 * .common.util.BusinessCase)
	 */
	@Override
	public final boolean reset(final BusinessCase bc) {
		final Dataset set = this.datasets.remove(bc);
		if (set != null) {
			TDB.sync(set);
			if (set.getDefaultModel() != null) {
				TDB.sync(set.getDefaultModel());
				set.getDefaultModel().removeAll();
				TDB.sync(set.getDefaultModel());
				set.getDefaultModel().close();
				set.asDatasetGraph().close();
			}
			set.close();
			TDB.closedown();
		}
		initializeDataset(bc);
		return true;
	}

	@Override
	public final synchronized Dataset getDataSource(final BusinessCase bc) {
		Dataset ds = datasets.get(bc);
		if (ds == null) {
			ds = initializeDataset(bc);
		}
		return ds;
	}

	private synchronized Dataset initializeDataset(final BusinessCase bc) {
		final Dataset set = TDBFactory
				.createDataset(getLocationForBusinessCase(bc));
		datasets.put(bc, set);
		return set;
	}
}
