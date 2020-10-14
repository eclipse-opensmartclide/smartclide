package de.atb.context.monitoring.parser;

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
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.atb.context.common.io.FileUtils;

/**
 * ExtensionFilenameFilter
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public class ExtensionFilenameFilter implements FilenameFilter {

    private final Set<String> validExtensions;

    public ExtensionFilenameFilter(final String[] extensions) {
        this(Arrays.asList(extensions));
    }

    public ExtensionFilenameFilter(final Collection<String> extensions) {
        this.validExtensions = new HashSet<>(extensions);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    @Override
    public final boolean accept(final File dir, final String name) {
        if (this.validExtensions == null) {
            return false;
        }
        String fileExt;
        if (dir != null) {
            fileExt = FileUtils.getExtension(dir.getAbsolutePath() + System.getProperty("file.separator") + name);
        } else {
            fileExt = FileUtils.getExtension(name);
        }
        if ((fileExt == null) || fileExt.equals("") || (fileExt.length() >= name.length()) || (fileExt.length() > 12)) {
            return false;
        }

        return this.validExtensions.contains(fileExt) || ((this.validExtensions.size() == 0) || (this.validExtensions.contains("*")));
    }

}
