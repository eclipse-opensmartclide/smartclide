package de.atb.context.ui.config;

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


import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import de.atb.context.ui.util.Icon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Giovanni
 */
public class ConfigurationDialog extends JDialog {

	private static final long serialVersionUID = -870766341631236775L;
	private final Logger logger = LoggerFactory
			.getLogger(ConfigurationDialog.class);

	private JButton btnConfigureServices;

	public ConfigurationDialog() {
		initializeComponents();
	}

	protected final void initializeComponents() {
		setTitle("Configuration");
		setIconImage(Icon.Application_32.getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout(4, 4));

		btnConfigureServices = new JButton("Configure Services",
				Icon.Settings_32.getIcon());
		btnConfigureServices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				ServicesConfigurationDialog scd = new ServicesConfigurationDialog();
				scd.setModalityType(DEFAULT_MODALITY_TYPE);
				scd.setVisible(true);
			}
		});
		FormLayout layout = new FormLayout("p, 4dlu, p", "p");
		PanelBuilder builder = new PanelBuilder(layout);

		builder.border(new EmptyBorder(4, 4, 4, 4));
		builder.add(btnConfigureServices, CC.xy(1, 1, CC.FILL, CC.FILL));
		final ConfigurationDialog parent = this;
		add(builder.getPanel(), BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
	}

	// public static void main(String[] args) {
	// ConfigurationDialog c = new ConfigurationDialog();
	// c.setVisible(true);
	// }

}
