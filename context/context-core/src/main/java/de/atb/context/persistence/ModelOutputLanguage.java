package de.atb.context.persistence;

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

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.*;

/**
 * ModelOutputLanguage
 *
 * @author scholze
 * @version $LastChangedRevision: 703 $
 *
 */
public enum ModelOutputLanguage {

	DEFAULT(null),

	RDFXML("RDF/XML"),

	N3("N3"),

	N_TRIPLE("N-TRIPLE"),

	RDFXML_ABBREV("RDF/XML-ABBREV"),

	TURTLE("TURTLE"),

	TTL("TTL");

	private final String language;

	ModelOutputLanguage(final String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public String getModelAsString(final Model model) {
		final StringWriter writer = new StringWriter();
		model.write(writer, getLanguage());
		return writer.toString();
	}

	public Model getModelFromString(final String modelString) {
		final Model model = ModelFactory.createDefaultModel();
		final StringReader reader = new StringReader(modelString);
		model.read(reader, null, getLanguage());
		reader.close();
		return model;
	}

	public void writeModelToFile(final Model model, final File file)
			throws IOException { // TODO this should maybe replace by a call to DRM API
		FileWriter writer = null;
		writer = new FileWriter(file);
		model.write(writer, getLanguage());
	}

	@Override
	public String toString() {
		return language;
	}

}
