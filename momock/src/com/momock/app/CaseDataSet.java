/*******************************************************************************
 * Copyright 2012 momock.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.momock.app;

import com.momock.data.DataSet;

public class CaseDataSet extends DataSet{
	ICase<?> kase;
	public CaseDataSet(ICase<?> kase){
		this.kase = kase;
	}
	protected IApplication getApplication(){
		return App.get();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getData(String name) {
		Object data = super.getData(name);
		if (data == null)
			data = kase.getParent() == null ? getApplication().getDataSet().getData(name) : kase.getParent().getDataSet().getData(name);
		return (T)data;
	}
}
