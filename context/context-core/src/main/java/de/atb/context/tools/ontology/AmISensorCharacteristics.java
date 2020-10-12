package de.atb.context.tools.ontology;

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


/**
 * @author scholze
 * @version $LastChangedRevision: 144 $
 */
public class AmISensorCharacteristics {
    private String description;
    private String amISystemType;
    private String dataType;
    private String unit;
    private String signalOrHistogramType;
    private String maximalPossible;
    private String minimumPossible;
    private String resolution;
    private String captureRate;
    private String mainBandwidth;
    private String maxBandwidth;
    private String startTime;
    private String endTime;

    public AmISensorCharacteristics() {
    }

    public AmISensorCharacteristics(String description, String amISystemType, String dataType, String unit, String signalOrHistogramType, String maximalPossible, String minimumPossible, String resolution, String captureRate, String mainBandwidth, String maxBandwidth, String startTime, String endTime) {
        this.description = description;
        this.amISystemType = amISystemType;
        this.dataType = dataType;
        this.unit = unit;
        this.signalOrHistogramType = signalOrHistogramType;
        this.maximalPossible = maximalPossible;
        this.minimumPossible = minimumPossible;
        this.resolution = resolution;
        this.captureRate = captureRate;
        this.mainBandwidth = mainBandwidth;
        this.maxBandwidth = maxBandwidth;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmISystemType() {
        return amISystemType;
    }

    public void setAmISystemType(String amISystemType) {
        this.amISystemType = amISystemType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSignalOrHistogramType() {
        return signalOrHistogramType;
    }

    public void setSignalOrHistogramType(String signalOrHistogramType) {
        this.signalOrHistogramType = signalOrHistogramType;
    }

    public String getMaximalPossible() {
        return maximalPossible;
    }

    public void setMaximalPossible(String maximalPossible) {
        this.maximalPossible = maximalPossible;
    }

    public String getMinimumPossible() {
        return minimumPossible;
    }

    public void setMinimumPossible(String minimumPossible) {
        this.minimumPossible = minimumPossible;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(String captureRate) {
        this.captureRate = captureRate;
    }

    public String getMainBandwidth() {
        return mainBandwidth;
    }

    public void setMainBandwidth(String mainBandwidth) {
        this.mainBandwidth = mainBandwidth;
    }

    public String getMaxBandwidth() {
        return maxBandwidth;
    }

    public void setMaxBandwidth(String maxBandwidth) {
        this.maxBandwidth = maxBandwidth;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
