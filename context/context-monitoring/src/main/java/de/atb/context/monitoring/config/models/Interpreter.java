package de.atb.context.monitoring.config.models;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import de.atb.context.monitoring.parser.ExtensionFilenameFilter;

/**
 * Interpreter
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
@Root(strict = false)
public class Interpreter {

    @Attribute
    protected String id;

    @ElementList(entry = "configuration", inline = true)
    protected List<InterpreterConfiguration> configurations = new ArrayList<>();

    protected FilenameFilter fileNameFilter;

    public static final String ALL_MATCHING_PATTERN = "*";

    public static String getAllMatchingPattern() {
        return ALL_MATCHING_PATTERN;
    }

    public final String getId() {
        return this.id;
    }

    public final void setId(final String id) {
        this.id = id;
    }

    public final List<InterpreterConfiguration> getConfigurations() {
        return this.configurations;
    }

    public final void setConfigurations(final List<InterpreterConfiguration> files) {
        this.configurations = files;
    }

    @Override
    public final String toString() {
        return String.format("'%s' -> %s", this.id, this.configurations);
    }

    @Deprecated
    public final FileFilter toFileFilter() {
        return pathname -> {
            for (InterpreterConfiguration configuration : Interpreter.this.configurations) {
                if (pathname.getAbsoluteFile().toString().endsWith(configuration.getExtension())) {
                    return true;
                }
            }
            return false;
        };
    }

    public final FilenameFilter getFilenameFilter() {
        String[] extensions = new String[Interpreter.this.configurations.size()];
        for (int i = 0; i < this.configurations.size(); i++) {
            extensions[i] = this.configurations.get(i).getExtension();
        }
        return new ExtensionFilenameFilter(extensions);
    }

    public final InterpreterConfiguration getConfiguration(final File pathName) {
        if (pathName == null) {
            return null;
        }
        String ext = FilenameUtils.getExtension(pathName.getAbsolutePath());
        for (InterpreterConfiguration file : Interpreter.this.configurations) {
            if (file.getExtension().equalsIgnoreCase(ext) || file.getExtension().equalsIgnoreCase("*")) {
                return file;
            }
        }
        return null;
    }

    public final InterpreterConfiguration getConfiguration(final String path) { // TODO DRM API?
        return this.getConfiguration(new File(path));
    }

    public final boolean accepts(final File pathname) {
        return accepts(pathname.getAbsolutePath(), null);
    }

    public final boolean accepts(final Pair<File, File> pathnames) {
        return accepts(pathnames.getValue0(), pathnames.getValue1());
    }

    public final boolean accepts(final Triplet<File, File, File> pathnames) {
        return accepts(pathnames.getValue0(), pathnames.getValue1(), pathnames.getValue2());
    }

    public final boolean accepts(final File path1, final File path2) {
        return accepts(path1 == null ? null : path1.getAbsolutePath(), path2 == null ? null : path2.getAbsolutePath(),
            null);
    }

    public final boolean accepts(final File path1, final File path2, final File path3) {
        return accepts(path1 == null ? null : path1.getAbsolutePath(), path2 == null ? null : path2.getAbsolutePath(),
            path3 == null ? null : path3.getAbsolutePath());
    }

    public final boolean accepts(final String path1, final String path2) {
        return accepts(path1, path2, null);
    }

    public final boolean accepts(final String path1, final String path2, final String path3) {
        if (this.fileNameFilter == null) {
            this.fileNameFilter = getFilenameFilter();
        }
        boolean path1Accept = path1 == null || this.fileNameFilter.accept(null, path1);
        boolean path2Accept = path2 == null || this.fileNameFilter.accept(null, path2);
        boolean path3Accept = path3 == null || this.fileNameFilter.accept(null, path3);
        return path1Accept && path2Accept && path3Accept;
    }
}
