package pt.uninova.context.modules.broker.status.ontology;

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
