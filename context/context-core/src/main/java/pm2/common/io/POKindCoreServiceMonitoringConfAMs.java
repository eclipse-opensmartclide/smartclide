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

public interface POKindCoreServiceMonitoringConfAMs {
    
    boolean POKindCoreServiceConfOpenAM(String name, String path, ObjectKinds kind);
    
    boolean POKindCoreServiceConfCloseAM(String name, ObjectKinds kind);
    
    byte[] POKindCoreServiceConfReadAM(String name, String path, ObjectKinds kind,
                                       int count, int offset);
    
    boolean POKindCoreServiceConfWriteAM(String name, ObjectKinds kind, byte[] buf);

}
