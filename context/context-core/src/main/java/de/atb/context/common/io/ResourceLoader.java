package de.atb.context.common.io;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.common.io.FileUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * ResourceLoader
 *
 * @author scholze
 * @version $LastChangedRevision: 703 $
 *
 */
public class ResourceLoader {

	private static final Logger logger = LoggerFactory
			.getLogger(ResourceLoader.class);

	private ResourceLoader() {}

	public static boolean copyFileResource(final String resourcePath,
			final File file) {
		final InputStream is = ResourceLoader.getResourceStream(resourcePath);
		if (is == null) {
			return false;
		}
		FileUtils.ensureDirectoryExists(file.getAbsolutePath());

		try (FileOutputStream fos = new FileOutputStream(file);) {

			final byte[] by = new byte[4096];
			int bytesRead = 0;
			while ((bytesRead = is.read(by)) != -1) {
				fos.write(by, 0, bytesRead);
			}
		} catch (IOException e) {
			ResourceLoader.logger.error(e.getMessage(), e);
		}
		return true;
	}

	public static boolean resourceExists(final String resourcePath) {
		return ResourceLoader.getResourceURI(resourcePath) != null;
	}

	public static URI getResourceURI(final String resourcePath) {
		final URL path = Thread.currentThread().getContextClassLoader()
				.getResource(resourcePath);
		if (path != null) {
			try {
				return path.toURI();
            } catch (final URISyntaxException e) {
				ResourceLoader.logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public static File getResource(final String resourcePath) {	// TODO this should maybe replace by a call to DRM API
		final URI uri = ResourceLoader.getResourceURI(resourcePath);
		if (uri != null) {
			return new File(uri);
		}

		return null;
	}

	public static InputStream getResourceStream(final String resourcePath) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(resourcePath);
	}
}
