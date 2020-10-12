package de.atb.context.ui.starter;

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
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import de.atb.context.starter.StarterComponents;
import de.atb.context.ui.config.ServicesConfigurationDialog;
import de.atb.context.ui.util.Icon;
import de.atb.context.ui.util.JTextAreaAppender;
import de.atb.context.ui.util.UIHelper;
import de.atb.context.ui.util.UIType;
import de.atb.context.ui.util.interfaces.ICommunicationBetweenUIs;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import javax.swing.*;
import javax.swing.border.Border;
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
public class StarterFrame extends JFrame implements ActionListener,
        ICommunicationBetweenUIs {

    private static final long serialVersionUID = 1L;

    private JButton btnExit;
    private JButton btnConfig;
    private JButton btnDeployerUI;
    private JButton btnServerUI;
    private JButton btnStartStopProcess;
    private JLabel lblLed;
    private final JFrame window;
    protected StarterComponents starter;

    protected String type = null;

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     *
     * @param starter
     * @param type
     */
    public StarterFrame(final StarterComponents starter, final String type) {
        this.starter = starter;
        window = new JFrame(type + " Interface");
        this.type = type;
        init();
    }

    @Override
    public final void show() {
        window.setVisible(true);
    }

    @Override
    public final void dispose() {
        window.dispose();
    }

    public final Component getWindow() {
        return this.window;
    }

    public final void init() {
        this.window.setIconImage(de.atb.context.ui.util.Icon.ProSEco_32.getImage());
        // TODO start asynchronous download of heavy resources
        window.setLayout(new BorderLayout());
        FormLayout layout = new FormLayout(
                "p, 4dlu, p, 4dlu, p, 4dlu, p, p:grow, 4dlu, p, 6dlu, p", "p");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.border(Borders.DIALOG);

        Border border = new EmptyBorder(5, 5, 5, 5);

        btnStartStopProcess = new JButton("Start Environment: " + type,
                de.atb.context.ui.util.Icon.Start_32.getIcon());
        btnStartStopProcess.setHorizontalAlignment(SwingConstants.LEFT);
        btnStartStopProcess.setBorder(border);
        builder.add(btnStartStopProcess, CC.xy(1, 1, CC.LEFT, CC.TOP));

        btnConfig = new JButton("Configure", de.atb.context.ui.util.Icon.Settings_32.getIcon());
        btnConfig.setHorizontalAlignment(SwingConstants.LEFT);
        btnConfig.setBorder(border);
        btnConfig.setEnabled(false);
        builder.add(btnConfig, CC.xy(3, 1, CC.LEFT, CC.TOP));

        if (this.type.equals(UIType.Deployer.getName())
                || this.type.equals(UIType.Registry.getName())) {
            btnDeployerUI = new JButton("Deployer UI",
                    de.atb.context.ui.util.Icon.DeployerUI_32.getIcon());
            btnDeployerUI.setHorizontalAlignment(SwingConstants.LEFT);
            btnDeployerUI.setBorder(border);
            builder.add(btnDeployerUI, CC.xy(7, 1, CC.LEFT, CC.TOP));
            btnDeployerUI.setEnabled(false);
            btnServerUI = new JButton("Server UI", de.atb.context.ui.util.Icon.ServerUI_32.getIcon());
            btnServerUI.setHorizontalAlignment(SwingConstants.LEFT);
            btnServerUI.setBorder(border);
            builder.add(btnServerUI, CC.xy(5, 1, CC.LEFT, CC.TOP));
            btnServerUI.setEnabled(false);
        }

        lblLed = new JLabel(UIHelper.getIcon(de.atb.context.ui.util.Icon.Disconnected_32));
        builder.add(lblLed, CC.xy(10, 1, CC.RIGHT, CC.TOP));

        btnExit = new JButton("Exit", de.atb.context.ui.util.Icon.Exit_32.getIcon());
        btnExit.setHorizontalAlignment(SwingConstants.LEFT);
        btnExit.setBorder(border);
        builder.add(btnExit, CC.xy(12, 1, CC.RIGHT, CC.TOP));

        JTextArea text = new JTextArea();
        text.setFont(new Font("Consoles", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(text,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JTextAreaAppender appender = new JTextAreaAppender(text, scroll);
        text.setEditable(false);

        /* Start and Configuration of Log4j */
        org.apache.log4j.Logger logger = LogManager.getRootLogger();
        logger.setLevel(Level.INFO);
        logger.addAppender(appender);

        window.add(builder.getPanel(), BorderLayout.NORTH);
        window.add(scroll, BorderLayout.CENTER);
        window.setMinimumSize(new Dimension(800, 600));
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        initializeListeners();
    }

    public final void startProcess() {
        btnStartStopProcess.setIcon(de.atb.context.ui.util.Icon.Stop_32.getIcon());
        btnStartStopProcess.setText("Stop Environment: " + type);
        btnConfig.setEnabled(false);
        if (this.type.equals(UIType.Deployer.getName())) {
            btnDeployerUI.setEnabled(true);
        } else if (this.type.equals(UIType.Registry.getName())) {
            btnServerUI.setEnabled(true);
        }
    }

    public final void stopProcess() {
        btnStartStopProcess.setIcon(de.atb.context.ui.util.Icon.Start_32.getIcon());
        btnStartStopProcess.setText("Start Environment: " + type);
        btnConfig.setEnabled(false);
        if (this.type.equals(UIType.Deployer.getName())) {
            btnDeployerUI.setEnabled(false);
        }
        if (this.type.equals(UIType.Registry.getName())) {
            btnServerUI.setEnabled(false);
        }
    }

    private void initializeListeners() {
        btnExit.addActionListener(this);
        btnStartStopProcess.addActionListener(this);
        btnConfig.addActionListener(this);
        if (this.type.equals(UIType.Deployer.getName())
                || this.type.equals(UIType.Registry.getName())) {
            btnDeployerUI.addActionListener(this);
            btnServerUI.addActionListener(this);
        }
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                starter.exitClicked(type);
            }
        });
    }

    // TODO overwrite start(), stop() and destroy() methods
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getSource() == btnExit) {
            starter.exitClicked(type);
        } else if (e.getSource() == btnStartStopProcess) {
            starter.startStopClicked(type);
        } else if (e.getSource() == btnConfig) {
            ServicesConfigurationDialog scd = new ServicesConfigurationDialog();
            scd.setComm(this);
            scd.setVisible(true);
        } else if (e.getSource() == btnDeployerUI) {
            starter.startDeployerFrame();
        } else if (e.getSource() == btnServerUI) {
            starter.startServerFrame();
        }
    }

    @Override
    public final void setConnected() {
        this.lblLed.setIcon(UIHelper.getIcon(de.atb.context.ui.util.Icon.Connected_32));
    }

    @Override
    public final void setDisconnected() {
        this.lblLed.setIcon(UIHelper.getIcon(Icon.Disconnected_32));
    }

    @Override
    public final void setConfigFilePath(final String filepath) {
        this.starter.configFilePath = filepath;
    }

}
