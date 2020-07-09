package de.atb.context.ui.modules;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Giovanni
 */
public class DeployPopUp extends JDialog implements ActionListener {

	private static final long serialVersionUID = 4400246774187060315L;
	private final Logger logger = LoggerFactory.getLogger(DeployPopUp.class);
	private JButton btnOk;

	// private JLabel lbl1, lbl2;

	public DeployPopUp(final DeployerFrame df) {
		super(df, true); // make RegPop always on top of cdd
		init();
	}

	@Override
	public final void actionPerformed(final ActionEvent ae) {
		if (ae.getSource() == btnOk) {
			this.dispose();
		}
	}

	private void init() {

		btnOk = new JButton("Ok", Icon.Ok_16.getIcon());
		btnOk.addActionListener(this);

		setLayout(new BorderLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		FormLayout layout = new FormLayout("4dlu, p:grow, 4dlu",
				"20dlu, p, 20dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		JLabel label = (JLabel) builder.add(new JLabel(
				"No connected services found!"), CC.xy(2, 2));
		label.setForeground(Color.RED);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		builder.addSeparator("", CC.xyw(1, 3, 3, CC.FILL, CC.FILL));
		builder.add(btnOk, CC.xy(2, 4));
		builder.border(new EmptyBorder(4, 4, 4, 4));

		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("synthetic-access")
			public void windowClosing(final WindowEvent e) {
				dispose();
			}
		});

		setResizable(false);
		// setMinimumSize(new Dimension(300, 200));
		add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
	}

}
