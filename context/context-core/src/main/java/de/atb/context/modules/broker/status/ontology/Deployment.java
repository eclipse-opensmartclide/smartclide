package de.atb.context.modules.broker.status.ontology;

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


import java.util.concurrent.TimeUnit;

/**
 *
 * @author Giovanni
 */
public class Deployment {

    private String id;
    private String name;
    private String startTime;
    private String uptime;
    private String timestamp;
    private String status;
    private String type;
    private String author;

    public Deployment() {
        //
    }

    public final String getId() {
        return id;
    }

    public final void setId(final String id) {
        this.id = id;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String Name) {
        this.name = Name;
    }

    public final void setStartTime(final String StartTime) {
        this.startTime = StartTime;
    }

    public final String getStartTime() {
        return startTime;
    }

    public final void setUptime(final String Uptime) {
        this.uptime = Uptime;
    }

    public final String getUptime() {
        return convertTime();
    }

    public final void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public final String getTimestamp() {
        return this.timestamp;
    }

    public final String getStatus() {
        return status;
    }

    public final void setStatus(final String Status) {
        this.status = Status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public final String getAuthor() {
        return author;
    }

    public final void setAuthor(final String author) {
        this.author = author;
    }

    public final String convertTime() {
        long upTime = Long.parseLong(this.startTime);
        upTime = System.currentTimeMillis() - upTime;
        this.uptime = String.format(
                "%02dh%02dmin%02ds",
                TimeUnit.MILLISECONDS.toHours(upTime),
                TimeUnit.MILLISECONDS.toMinutes(upTime)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                        .toHours(upTime)),
                TimeUnit.MILLISECONDS.toSeconds(upTime)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                        .toMinutes(upTime)));
        return this.uptime;
    }

}
