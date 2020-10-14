package de.atb.context.common.util;

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

import de.atb.context.services.faults.ContextFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class ClasspathHelper {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClasspathHelper.class);

	private ClasspathHelper() {}

	public static List<Class<?>> getAllClasses() {
		return ClasspathHelper.getAllClasses(false);
	}

	public static List<Class<?>> getAllClasses(final boolean includeJars) {
		return ClasspathHelper.getAllClasses(null, includeJars);
	}

	public static List<Class<?>> getMatchingClasses(final Class<?> classOrIface) {
		return ClasspathHelper.getMatchingClasses(null, classOrIface, false);
	}

	public static List<Class<?>> getMatchingClasses(final String packagePrefix) {
		return ClasspathHelper.getAllClasses(packagePrefix, false);
	}

	public static List<Class<?>> getMatchingClasses(final String packagePrefix,
			final boolean includeJars) {
		return ClasspathHelper.getAllClasses(packagePrefix, includeJars);
	}

	public static List<Class<?>> getMatchingClasses(
			final Class<?> classOrIface, final boolean includeJars) {
		return ClasspathHelper.getMatchingClasses(null, classOrIface,
				includeJars);
	}

	public static List<Class<?>> getMatchingClasses(final String packagePrefix,
			final Class<?> classOrIface) {
		return ClasspathHelper.getMatchingClasses(packagePrefix, classOrIface,
				false);
	}

	public static List<Class<?>> getMatchingClasses(final String packagePrefix,
			final Class<?> classOrIface, final boolean includeJars) {
		final List<Class<?>> matchingClasses = new ArrayList<>();
		final List<Class<?>> classes = ClasspathHelper.getAllClasses(
				packagePrefix, includeJars);
		ClasspathHelper.LOGGER.debug(String.format("checking %s classes",
				Integer.valueOf(classes.size())));
		for (final Class<?> clazz : classes) {
			if (classOrIface.isAssignableFrom(clazz)) {
				matchingClasses.add(clazz);
				ClasspathHelper.LOGGER.debug(String.format(
						"class %s is assignable from %s", classOrIface, clazz));
			}
		}
		return matchingClasses;
	}

	public static List<File> getClassLocationsForCurrentClasspath() { // TODO this should maybe replace by a call to DRM API
		final List<File> files = new ArrayList<>();
		final String javaClassPath = System.getProperty("java.class.path");
		if (javaClassPath != null) {
			for (final String path : javaClassPath.split(File.pathSeparator)) {
				files.add(new File(path));
			}
		}
		return files;
	}

	private static List<Class<?>> getAllClasses(final String packagePrefix,
			final boolean includeJars) {
		final List<Class<?>> classFiles = new ArrayList<>();
		final List<File> classLocations = ClasspathHelper
				.getClassLocationsForCurrentClasspath();
		for (final File file : classLocations) {
			classFiles.addAll(ClasspathHelper.getClassesFromPath(file,
					packagePrefix, includeJars));
		}
		return classFiles;
	}

	private static Collection<? extends Class<?>> getClassesFromPath(
			final File path, final String packagePrefix,
			final boolean includeJars) {
		if (path.isDirectory()) {
			return ClasspathHelper.getClassesFromDirectory(path, packagePrefix,
					includeJars);
		} else {
			if (includeJars) {
				return ClasspathHelper.getClassesFromJar(path, packagePrefix);
			} else {
				return Collections.emptyList();
			}
		}
	}

	private static String getClassName(final String fileName) {
		return fileName.substring(0, fileName.length() - 6).replaceAll(
				"/|\\\\", "\\.");
	}

	private static List<Class<?>> getClassesFromJar(final File path,
			final String packagePrefix) { // TODO this should maybe replace by a call to DRM API
		final List<Class<?>> classes = new ArrayList<>();
		ClasspathHelper.LOGGER.debug(String.format("Getting classes for %s",
				path));
		try (JarFile jar = new JarFile(path);) {
			if (!path.canRead()) {
				return classes;
			}

			final Enumeration<JarEntry> en = jar.entries();
			while (en.hasMoreElements()) {
				final JarEntry entry = en.nextElement();
				if (!entry.getName().endsWith("class")) {
					continue;
				}

				final String className = ClasspathHelper.getClassName(entry
						.getName());
				if (packagePrefix != null) {
					if (className.startsWith(packagePrefix)) {
						ClasspathHelper.LOGGER.debug(String.format("found %s",
								className));
						ClasspathHelper.loadClass(classes, className);
					}
				} else {
					ClasspathHelper.LOGGER.debug(String.format("found %s",
							className));
					ClasspathHelper.loadClass(classes, className);
				}
			}
		} catch (final Exception e) {
			throw new ContextFault("Failed to read classes from jar file: "
					+ path, e);
		}

		return classes;
	}

	private static List<Class<?>> getClassesFromDirectory(final File path,
			final String packagePrefix, final boolean includeJars) {
		final List<Class<?>> classes = new ArrayList<>();
		ClasspathHelper.LOGGER.debug(String.format("Getting classes for %s",
				path));

		if (includeJars) {
			final List<File> jarFiles = ClasspathHelper.getFileList(path,
                    (dir, name) -> name.endsWith(".jar"), false);
			for (final File file : jarFiles) {
				classes.addAll(ClasspathHelper.getClassesFromJar(file,
						packagePrefix));
			}
		}

		final List<File> classFiles = ClasspathHelper.getFileList(path,
                (dir, name) -> name.endsWith(".class"), true);

		final int substringBeginIndex = path.getAbsolutePath().length() + 1;
		for (final File classfile : classFiles) {
			String className = classfile.getAbsolutePath().substring(
					substringBeginIndex);
			className = ClasspathHelper.getClassName(className);
			if (packagePrefix != null) {
				if (className.startsWith(packagePrefix)) {
					ClasspathHelper.LOGGER.debug(String.format(
							"Found class %s in path %s: ", className, path));
					ClasspathHelper.loadClass(classes, className);
				}
			} else {
				ClasspathHelper.LOGGER.debug(String.format(
						"Found class %s in path %s: ", className, path));
				ClasspathHelper.loadClass(classes, className);
			}
		}

		return classes;
	}

	private static List<File> getFileList(final File directory,
			final FilenameFilter filter, final boolean recursive) {
		final List<File> fileList = new ArrayList<>();
		final File[] files = directory.listFiles();

		for (final File file : files) {
			if ((filter == null) || filter.accept(directory, file.getName())) {
				fileList.add(file);
			}
			if (recursive && file.isDirectory()) {
				fileList.addAll(ClasspathHelper.getFileList(file, filter,
						recursive));
			}
		}

		return fileList;
	}

	private static void loadClass(final List<Class<?>> classes,
			final String className) {
		try {
			final Class<?> clazz = Class.forName(className, false,
					ClassLoader.getSystemClassLoader());
			classes.add(clazz);
		} catch (final ClassNotFoundException cnfe) {
			ClasspathHelper.LOGGER.error(cnfe.getMessage(), cnfe);
		} catch (final NoClassDefFoundError e) {
			ClasspathHelper.LOGGER.error(e.getMessage(), e);
		} catch (final VerifyError ve) {
			ClasspathHelper.LOGGER.error(ve.getMessage(), ve);
		}
	}
}
