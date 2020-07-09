package de.atb.context.ui.modules.table;

/*-
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
