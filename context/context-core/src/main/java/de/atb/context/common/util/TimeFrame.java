package de.atb.context.common.util;

/*
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

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * TimeFrame
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public class TimeFrame {

	protected Date startTime;
	protected Date endTime;

	protected static final String XSD_FORMAT_STRING = "\"%s\"^^xsd:dateTime";

	public TimeFrame() {
	}

	public TimeFrame(final Date start, final Date end) {
		if ((end != null) && (start != null)
				&& (end.getTime() < start.getTime())) {
			throw new IllegalArgumentException(
					"End date may not be smaller than start date!");
		}
		if (start != null) {
			startTime = (Date) start.clone();
		} else {
			startTime = null;
		}
		if (end != null) {
			endTime = (Date) end.clone();
		} else {
			endTime = null;
		}
	}

	public final Date getEndTime() {
		if (endTime != null) {
			return (Date) endTime.clone();
		} else {
			return null;
		}
	}

	public final Date getStartTime() {
		if (startTime != null) {
			return (Date) startTime.clone();
		} else {
			return null;
		}
	}

	public final void setEndTime(final Date endTime) {
		if (endTime != null) {
			this.endTime = (Date) endTime.clone();
		} else {
			this.endTime = null;
		}
	}

	public final void setStartTime(final Date startTime) {
		if (startTime != null) {
			this.startTime = (Date) startTime.clone();
		} else {
			this.startTime = null;
		}
	}

	public final Calendar getCalendarForStartTime() {
		if (startTime == null) {
			return null;
		}
		final Calendar startCal = Calendar.getInstance(TimeZone
				.getTimeZone("UTC"));
		startCal.setTime(startTime);
		return startCal;
	}

	public final Calendar getCalendarForEndTime() {
		if (endTime == null) {
			return null;
		}
		final Calendar startCal = Calendar.getInstance(TimeZone
				.getTimeZone("UTC"));
		startCal.setTime(endTime);
		return startCal;
	}

	public final String getXSDLexicalFormForStartTime() {
		final Calendar cal = getCalendarForStartTime();
		if (cal == null) {
			return null;
		}
		return String.format(TimeFrame.XSD_FORMAT_STRING,
				new XSDDateTime(cal).toString());
	}

	public final String getXSDLexicalFormForEndTime() {
		final Calendar cal = getCalendarForEndTime();
		if (cal == null) {
			return null;
		}
		return String.format(TimeFrame.XSD_FORMAT_STRING,
				new XSDDateTime(cal).toString());
	}

	public final boolean contains(final Date time) {
		if (time != null) {
			return (time.getTime() >= startTime.getTime())
					&& (time.getTime() <= endTime.getTime());
		}
		return false;
	}

}
