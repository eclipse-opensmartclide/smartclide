package de.atb.context.monitoring.events;

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


import java.util.List;

import de.atb.context.monitoring.index.Document;

/**
 * MonitoringProgressListener
 *
 * @param <P> the type of input that was parsed by a parser.
 * @param <A> the type of output that is returned after analysing an input.
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public interface MonitoringProgressListener<P, A> {

    /**
     * Informs subscribers about a newly indexed document.
     *
     * @param indexId  the Identifier of the Index the given document was added to.
     * @param document the Document that was recently added to the Index with the
     *                 given Id.
     */
    void documentIndexed(String indexId, Document document);

    /**
     * Informs subscribers about a newly parsed document.
     *
     * @param parsed   the documents input that was parsed.
     * @param document the Document that was recently parsed.
     */
    void documentParsed(P parsed, Document document);

    /**
     * Informs subscribers about a newly parsed document.
     *
     * @param analysed the documents output that created from analyising.
     * @param parsed   the documents output that created from analyising.
     * @param document the Document that was recently parsed.
     */
    void documentAnalysed(List<A> analysed, P parsed, Document document);

}
