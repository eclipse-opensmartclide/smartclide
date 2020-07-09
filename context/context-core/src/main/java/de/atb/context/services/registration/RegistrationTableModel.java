package de.atb.context.services.registration;

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
import pt.uninova.context.modules.config.DeployerConfigurationObject;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class RegistrationTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -5022148639334425544L;
	private final Logger logger = LoggerFactory
			.getLogger(RegistrationTableModel.class);
	public static final int Location = 0;
	public static final int State = 1;
	protected String[] columnNames = { "Location", "State" };

	// String[] locs = {"loc1", "loc2"};
	List<DeployerConfigurationObject> dcoList;// = new ArrayList<>();
	Registration_Mngr regMng;
	ArrayList<String> PingList = new ArrayList<>();

	public RegistrationTableModel() {
	}

	public RegistrationTableModel(final Registration_Mngr regMng) {
		this.regMng = regMng;
		this.dcoList = regMng.regBean.dcoList;
		this.PingList = regMng.PingList;
	}

	@Override
	public final String getColumnName(final int i) {
		return this.columnNames[i];
	}

	@Override
	public final int getRowCount() {
		if (dcoList != null && !dcoList.isEmpty()) {
			return dcoList.size();
		}
		return 0;
	}

	@Override
	public final int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public final Object getValueAt(final int row, final int column) {
		DeployerConfigurationObject dco = dcoList.get(row);
		String result = null;
		switch (column) {
		case (0):
			result = dco.getAddress();
			break;
		case (1):
			result = PingList.get(row);
			break;
		default:
			logger.info("invalid Index");
		}
		return result;
	}
}
