package de.atb.context.ui.modules.table;

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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.infrastructure.ServiceInfo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public class ServicesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -502214863933442554L;
	private final Logger logger = LoggerFactory
			.getLogger(ServicesTableModel.class);

	public static final int ServiceId = 0;
	public static final int host = 1;
	public static final int location = 2;
	public static final int server = 3;
	public static final int proxy = 4;
	public static final int type = 5;
	public static final int status = 6;

	protected String[] columnNames = { "ServiceId", "host", "Location", "Server", "Proxy", "Type", "Status" };

	private List<ServiceInfo> servicesInfos = new ArrayList<>();

	public ServicesTableModel(final List<ServiceInfo> servicesInfos) {
		this.servicesInfos = servicesInfos;
	}

	@Override
	public final String getColumnName(final int i) {
		return this.columnNames[i];
	}

	@Override
	public final boolean isCellEditable(final int i, final int i1) {
		return false;
	}

	@Override
	public final int getRowCount() {
		if (this.servicesInfos != null) {
			return this.servicesInfos.size();
		} else {
			return 0;
		}
	}

	@Override
	public final int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public final Object getValueAt(final int row, final int column) {
		ServiceInfo service = this.servicesInfos.get(row);
		String result = null;
		switch (column) {
		case ServiceId:
			result = service.getId();
			break;
		case host:
			result = service.getHost();
			break;
		case location:
			result = service.getLocation();
			break;
		case server:
			result = service.getServer();
			break;
		case proxy:
			result = service.getProxy();
			break;
		case type:
			result = service.getType();
			break;
		case status:
			result = service.getStatus();
			break;
		default:
			logger.info("invalid Index");
		}
		return result;
	}

}
