package de.atb.context.extraction.util;

/*
 * #%L
 * ATB Context Extraction Core Service
 * %%
 * Copyright (C) 2018 - 2019 ATB
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.util.FileManager;

/**
 * OntologyLocator
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
public class OntologyLocator {

	private static final FileManager fileManager;

	static {
        fileManager = OntDocumentManager.getInstance().getFileManager();
	}

	/**
	 * <p>
	 * Add an entry for an alternative copy of the document with the given
	 * document URI.
	 * </p>
	 * 
	 * @param docURI
	 *            The public URI of the ontology document
	 * @param locationURL
	 *            A locally resolvable URL where an alternative copy of the
	 *            ontology document can be found
	 */
	public final void addAltEntry(String docURI, String locationURL) {
        OntologyLocator.fileManager.getLocationMapper().addAltEntry(docURI, locationURL);
	}

	/**
	 * <p>
	 * Remove an entry for an alternative copy of the document with the given
	 * document URI.
	 * </p>
	 * 
	 * @param docURI
	 *            The public URI of the ontology document
	 */
	public final void removeAltEntry(String docURI) {
        OntologyLocator.fileManager.getLocationMapper().removeAltEntry(docURI);
	}
}
