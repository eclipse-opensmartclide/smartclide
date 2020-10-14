package pm2.common.io;

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
import pm2.common.application.ObjectKinds;

public interface POKindCoreServiceAppScenConfAMs {
    
    boolean POKindCoreServiceAppScenConfOpenAM(String name, String path, ObjectKinds kind);
    
    boolean POKindCoreServiceAppScenConfCloseAM(String name, ObjectKinds kind);
    
    byte[] POKindCoreServiceAppScenConfReadAM(String name, String path, ObjectKinds kind,
                                              int count, int offset);
    
    boolean POKindCoreServiceAppScenConfWriteAM(String name, ObjectKinds kind, byte[] buf);

}
