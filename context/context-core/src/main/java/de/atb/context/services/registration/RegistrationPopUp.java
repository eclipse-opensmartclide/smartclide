package de.atb.context.services.registration;

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


import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import de.atb.context.ui.config.ConfigurationDeployerDialog;
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
 * @author Guilherme
 */
public class RegistrationPopUp extends JDialog implements ActionListener {

	private static final long serialVersionUID = 4400246774187060315L;
	private final Logger logger = LoggerFactory
			.getLogger(RegistrationPopUp.class);
	ConfigurationDeployerDialog cdd;
	Registration_Mngr mng;
	private JButton btnYes, btnNo;

	// private JLabel lbl1, lbl2;

	public RegistrationPopUp(final ConfigurationDeployerDialog cdd,
			final Registration_Mngr mng) {
		super(cdd, true); // make RegPop always on top of cdd
		this.cdd = cdd;
		this.mng = mng;
		init();
	}

	@Override
	public final void actionPerformed(final ActionEvent ae) {
		if (ae.getSource() == btnYes) {
			if (mng.autoConnect()) {
				cdd.dispose();
			}
			dispose();
		} else if (ae.getSource() == btnNo) {
			dispose();
		}
	}

	private void init() {

		btnYes = new JButton("Yes", de.atb.context.ui.util.Icon.Ok_16.getIcon());
		btnYes.addActionListener(this);
		btnNo = new JButton("No", Icon.Cancel_16.getIcon());
		btnNo.addActionListener(this);

		setLayout(new BorderLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		FormLayout layout = new FormLayout("40dlu, 80dlu, 8dlu, 80dlu, 40dlu",
				"p, 10dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		JComponent comp = builder.addSeparator(
				"Auto registration for Server communication:",
				CC.xyw(1, 1, 5, CC.FILL, CC.FILL));
		comp.getComponent(0).setFont(comp.getFont().deriveFont(Font.BOLD));

		builder.add(new JLabel("Previous entries on registry found!"),
				CC.xyw(1, 3, 5));
		builder.add(
				new JLabel("Would you like to try automatic registration?"),
				CC.xyw(1, 5, 5));
		builder.add(btnYes, CC.xy(2, 9));
		builder.add(btnNo, CC.xy(4, 9));
		JComponent comp2 = builder.addSeparator("",
				CC.xyw(1, 7, 5, CC.FILL, CC.FILL));
		builder.border(new EmptyBorder(4, 4, 4, 4));

		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("synthetic-access")
			@Override
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
