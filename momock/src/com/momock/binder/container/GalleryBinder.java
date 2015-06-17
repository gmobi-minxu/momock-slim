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
package com.momock.binder.container;

import java.lang.ref.WeakReference;

import android.widget.Gallery;

import com.momock.binder.IItemBinder;
import com.momock.data.IDataList;
import com.momock.event.EventArgs;
import com.momock.event.IEventHandler;
import com.momock.event.ItemEventArgs;
import com.momock.widget.IIndexIndicator;

@SuppressWarnings("deprecation")
public class GalleryBinder extends AdapterViewBinder<Gallery> {
	WeakReference<IIndexIndicator> refIndicator = null;
	public GalleryBinder(IItemBinder binder) {
		super(binder);
		this.getItemSelectedEvent().addEventHandler(new IEventHandler<ItemEventArgs>(){

			@Override
			public void process(Object sender,
					ItemEventArgs args) {
				if (refIndicator != null && refIndicator.get() != null){
					refIndicator.get().setCurrentIndex(args.getIndex());
				}
			}
			
		});
		this.getDataChangedEvent().addEventHandler(new IEventHandler<EventArgs>(){

			@Override
			public void process(Object sender, EventArgs args) {
				if (refIndicator != null && refIndicator.get() != null){
					refIndicator.get().setCount(getAdapter().getCount());
				}
			}
			
		});
	}
	@Override
	public void onBind(Gallery view, IDataList<?> list) {
		super.onBind(view, list);
		if (refIndicator != null && refIndicator.get() != null){
			refIndicator.get().setCount(list.getItemCount());
		}
	}
	public void bind(Gallery view, IDataList<?> list, IIndexIndicator indicator){
		this.refIndicator = new WeakReference<IIndexIndicator>(indicator);
		bind(view, list);
	}
	

}
