package de.atb.context.common.io;

/*-
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


import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Giovanni
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory
            .getLogger(FileUtils.class);

    private FileUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserializeObjectFromFile(final String file) {
        return deserializeObjectFromFile(new File(file));
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserializeObjectFromFile(final File file) {
        FileInputStream fIn = null;
        ObjectInputStream oIn = null;

        try {
            fIn = new FileInputStream(file);
            oIn = new ObjectInputStream(fIn);
            return (T) oIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (oIn != null) {
                    oIn.close();
                }
                if (fIn != null) {
                    fIn.close();
                }
            } catch (IOException e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
        return null;
    }

    public static <T> void serializeObjectToFile(final T object, final String file) {
        serializeToFile(object, new File(file));
    }

    public static <T> void serializeToFile(final T object, final File file) {
        try (
            FileOutputStream fOut = new FileOutputStream(file);
            ObjectOutputStream oOut = new ObjectOutputStream(fOut);
        ) {
            oOut.writeObject(object);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static String getExtension(final File file) {
        if (file != null) {
            return FilenameUtils.getExtension(file.getAbsolutePath());
        }
        return null;
    }

    public static String getExtension(final String file) {
        return getExtension(new File(file));
    }

    public static boolean hasValidExtension(final String file) {
        return isValidExtension(getExtension(file));
    }

    public static boolean hasValidExtension(final File file) {
        return isValidExtension(getExtension(file));
    }

    public static boolean isValidExtension(final String fileExt) {
        return (fileExt == null) || "".equals(fileExt);
    }

    public static boolean ensureDirectoryExists(final String filePath) {
        if (filePath == null) {
            throw new NullPointerException("FilePath may not be null");
        }
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            return true;
        } else if (!file.exists()) {
            String path = file.getParent();
            File dir = new File(path);
            if (dir.exists()) {
                return true;
            }
            logger.info(path);
            return new File(path).mkdirs();
        } else if (file.exists()) {
            return true;
        }
        return false;
    }

    // For Service Composition Editor BPMN File management
    public static void writeStringToFile(final String bean, final String file) {
        try (FileOutputStream fOut = new FileOutputStream(file);) {
            fOut.write(bean.getBytes());
            fOut.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
