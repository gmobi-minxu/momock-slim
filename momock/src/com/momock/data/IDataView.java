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
package com.momock.data;

import java.util.Comparator;

public interface IDataView<T> {
	public static interface IFilter<T> {
		boolean check(T item);
	}

	public static interface IOrder<T> extends Comparator<T> {
	}

	IDataList<T> getData();
	
	IFilter<T> getFilter();

	void setFilter(IFilter<T> filter);

	IOrder<T> getOrder();

	void setOrder(IOrder<T> order);

	int getOffset();

	void setOffset(int offset);

	int getLimit();

	void setLimit(int limit);
	
	void refresh();
}
