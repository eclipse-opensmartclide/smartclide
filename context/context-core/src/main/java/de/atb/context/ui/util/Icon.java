package de.atb.context.ui.util;

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
import java.awt.*;

/**
 * @author Giovanni
 */
public enum Icon {

    Cancel_16("16/cancel.png"),

    Cycle_16("16/cycle.png"),

    Collapse_16("16/collapse.png"),

    Connection_16("16/connection.png"),

    Exit_16("16/exit.png"),

    Expand_16("16/expand.png"),

    Next_16("16/next.png"),

    Ok_16("16/ok.png"),

    Valve_16("16/valve.png"),

    Previous_16("16/previous.png"),

    Rdf_16("16/rdf.png"),

    Update_16("16/update.png"),

    Warning_16("16/warning.png"),

    Disconnected_16("32/disconnected_16.png"),

    Connected_16("32/connected_16.png"),

    ProSEco_16("32/application.png"),

    Folder_16("16/Folder-icon_16.png"),

    Add_16("16/plus_16.png"),

    Remove_16("16/remove_16.png"),

    Submit_16("16/Submit_Icon_16.png"),

    Application_32("32/application.png"),

    Connect_32("32/connect.png"),

    Drop_32("32/drop.png"),

    Exit_32("32/exit.png"),

    Settings_32("32/settings.png"),

    Start_32("32/start.png"),

    Stop_32("32/stop.png"),

    Details_32("32/Show.png"),

    Discovery_32("32/discovery.png"),

    DeployerUI_32("32/deployer.png"),

    ServerUI_32("32/Server.png"),

    Update_1_32("32/update-1.png"),

    Update_2_32("32/update-2.png"),

    Update_3_32("32/update-3.png"),

    Disconnected_32("32/disconnected_32.png"),

    Connected_32("32/connected_32.png"),

    ProSEco_32("32/application.png"),

    Folder_32("32/Folder-icon_32.png"),

    Add_32("32/plus_32.png"),

    Cancel_32("32/cancel_32.png"),

    Remove_32("32/remove_32.png"),

    Submit_32("32/Submit_Icon_32.png");

    private String name;

    private Icon(final String path) {
        this.name = path;
    }

    public Image getImage() {
        ImageIcon icon = UIHelper.getIcon(getName());
        if (icon != null) {
            return icon.getImage();
        }
        return null;
    }

    public ImageIcon getIcon() {
        return UIHelper.getIcon(getName());
    }

    public String getName() {
        return this.name;
    }
}
