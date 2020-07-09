package de.atb.context.services.interfaces;

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


import java.util.List;

/**
 *
 * @author Giovanni
 */
public class Input {

	private List<DataInput> dataInput;

	public Input() {
	}

	public Input(final List<DataInput> dataInput) {
		this.dataInput = dataInput;
	}

	public final List<DataInput> getDataInput() {
		return dataInput;
	}

	public final void setDataInput(final List<DataInput> dataInput) {
		this.dataInput = dataInput;
	}
        
        public final boolean addDataInput(DataInput dataInput){
            return this.dataInput.add(dataInput);
        }

}
