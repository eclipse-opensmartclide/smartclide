package de.atb.context.learning.models;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.uninova.context.common.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 * ModelInitializerBase
 *
 * @author scholze
 * @version $LastChangedRevision: 703 $
 */
public abstract class ModelInitializerBase implements IModelInitializer {

    private final Logger logger = LoggerFactory
            .getLogger(ModelInitializerBase.class);

    public abstract String getModelSkeleton();

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.learning.models.IModelInitializer#initializeModel()
     */
    @Override
    public final boolean initializeModel(final String filePath) { // TODO this should maybe replace by a call to DRM API
        if (filePath == null) {
            throw new NullPointerException("FilePath may not be null");
        }
        final File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            return true;
        }

        if (FileUtils.ensureDirectoryExists(filePath)) {
            try (FileWriter writer = new FileWriter(filePath);) {
                writer.write(getModelSkeleton());
                writer.flush();
                return true;
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            try (FileWriter writer = new FileWriter(filePath);) {
                final File source = new File(filePath);
                if (source.createNewFile()) {
                    writer.write(getModelSkeleton());
                    writer.flush();
                }
                return true;
            } catch (final IOException ex) {
                java.util.logging.Logger.getLogger(
                        ModelInitializerBase.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
        return false;
    }
}
