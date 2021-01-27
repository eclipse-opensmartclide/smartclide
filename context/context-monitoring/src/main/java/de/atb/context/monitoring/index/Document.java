package de.atb.context.monitoring.index;

/*-
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2021 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public final class Document implements Serializable {
    List fields = new ArrayList();
    private float boost = 1.0F;

    public Document() {
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public float getBoost() {
        return this.boost;
    }

    public final void add(String field) {
        this.fields.add(field);
    }

    public final void removeField(String name) {
        Iterator it = this.fields.iterator();

        String field;
        do {
            if (!it.hasNext()) {
                return;
            }

            field = (String)it.next();
        } while(!field.equals(name));

        it.remove();
    }

    public final void removeFields(String name) {
        Iterator it = this.fields.iterator();

        while(it.hasNext()) {
            String field = (String)it.next();
            if (field.equals(name)) {
                it.remove();
            }
        }

    }

    public final String getField(String name) {
        for(int i = 0; i < this.fields.size(); ++i) {
            String field = (String)this.fields.get(i);
            if (field.equals(name)) {
                return field;
            }
        }

        return null;
    }

    public String getFieldable(String name) {
        for(int i = 0; i < this.fields.size(); ++i) {
            String field = (String)this.fields.get(i);
            if (field.equals(name)) {
                return field;
            }
        }

        return null;
    }

    public final String get(String name) {
        for(int i = 0; i < this.fields.size(); ++i) {
            String field = (String)this.fields.get(i);
            if (field.equals(name)) {
                return field;
            }
        }

        return null;
    }

    /** @deprecated */
    public final Enumeration fields() {
        return new Enumeration() {
            final Iterator iter;

            {
                this.iter = Document.this.fields.iterator();
            }

            public boolean hasMoreElements() {
                return this.iter.hasNext();
            }

            public Object nextElement() {
                return this.iter.next();
            }
        };
    }

    public final List getFields() {
        return this.fields;
    }

    public final String[] getFields(String name) {
        List result = new ArrayList();

        for(int i = 0; i < this.fields.size(); ++i) {
            String field = (String)this.fields.get(i);
            if (field.equals(name)) {
                result.add(field);
            }
        }

        if (result.size() == 0) {
            return null;
        } else {
            return (String[])((String[])result.toArray(new String[result.size()]));
        }
    }

    public String[] getFieldables(String name) {
        List result = new ArrayList();

        for(int i = 0; i < this.fields.size(); ++i) {
            String field = (String)this.fields.get(i);
            if (field.equals(name)) {
                result.add(field);
            }
        }

        if (result.size() == 0) {
            return null;
        } else {
            return (String[])((String[])result.toArray(new String[result.size()]));
        }
    }

    public final String[] getValues(String name) {
        List result = new ArrayList();

        for(int i = 0; i < this.fields.size(); ++i) {
            String field = (String)this.fields.get(i);
            if (field.equals(name)) {
                result.add(field);
            }
        }

        if (result.size() == 0) {
            return null;
        } else {
            return (String[])((String[])result.toArray(new String[result.size()]));
        }
    }

    public final String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Document<");

        for(int i = 0; i < this.fields.size(); ++i) {
            String field = (String)this.fields.get(i);
            buffer.append(field.toString());
            if (i != this.fields.size() - 1) {
                buffer.append(" ");
            }
        }

        buffer.append(">");
        return buffer.toString();
    }
}
