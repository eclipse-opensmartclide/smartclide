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


import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 *
 * @author Guilherme
 */
public class RegCellRender implements TableCellRenderer {

	@Override
	public final Component getTableCellRendererComponent(final JTable jtable, final Object o,
			final boolean isSelected, final boolean hasFocus, final int row, final int col) {

		// Component c = super.getTableCellRendererComponent(jtable, o,
		// hasFocus, hasFocus, row, col);
		JLabel c = new JLabel();
		c.setOpaque(true);
		if (isSelected) {
			c.setBackground(Color.GRAY);
		} else {
			c.setBackground(Color.WHITE);
		}
		c.setText((String) o);
		if (col == 1) {
			if (((String) o).equals("Offline")) {
				c.setForeground(Color.RED);
			} else if (((String) o).equals("Online")) {
				c.setForeground(Color.GREEN);
			} else {
				c.setForeground(Color.BLACK);
			}
		}
		return c;
	}
}
