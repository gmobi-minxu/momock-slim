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
package com.momock.outlet.tab;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabWidget;

import com.momock.data.IDataList;
import com.momock.holder.TabHolder;
import com.momock.holder.ViewHolder;
import com.momock.outlet.IPlug;
import com.momock.outlet.Outlet;
import com.momock.util.Logger;

public class PagerTabOutlet extends Outlet implements ITabOutlet{
	IDataList<IPlug> plugs;
	TabHolder target;

	public void attach(TabHolder target) {
		Logger.check(target != null, "Parameter target cannot be null!");
		Logger.check(target.getTabContent() instanceof ViewPager, "The PagerTabOutlet must contains a ViewPager content!");
		this.target = target;
		final TabHost tabHost = target.getTabHost();
		final ViewPager tabContent = (ViewPager) target.getTabContent();
		plugs = getPlugs();
		
		ViewPager pager = (ViewPager)target.getTabContent();
		for(int i = 0; i < plugs.getItemCount(); i++){
			ITabPlug plug = (ITabPlug)plugs.getItem(i);
			Logger.check(plug.getContent() instanceof ViewHolder, "The plug of PagerTabOutlet must include a ViewHolder!");
			((ViewHolder)plug.getContent()).reset(); 
		}
		pager.setAdapter(new PagerAdapter(){

			@Override
			public int getCount() {
				return plugs.getItemCount();
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ITabPlug plug = (ITabPlug)plugs.getItem(position);
				View view = ((ViewHolder)plug.getContent()).getView();
	            container.addView(view);
				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView((View)object);
			}
		});

		tabContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						TabWidget widget = tabHost.getTabWidget();
						int oldFocusability = widget
								.getDescendantFocusability();
						widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
						tabHost.setCurrentTab(position);
						widget.setDescendantFocusability(oldFocusability);
					}

					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {

					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});
		tabHost.setup();
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				int index = tabHost.getCurrentTab();
				ITabPlug plug = (ITabPlug)plugs.getItem(index);
				setActivePlug(plug);
				tabContent.setCurrentItem(index, true);
			}
		});
		for (int i = 0; i < plugs.getItemCount(); i++) {
			final ITabPlug plug = (ITabPlug)plugs.getItem(i);
			Logger.check(plug.getContent() instanceof ViewHolder,
					"Plug in PagerTabOutlet must contains a ViewHolder content!");

			TabHost.TabSpec spec = tabHost.newTabSpec("" + i);
			target.setTabIndicator(spec, plug);
			spec.setContent(new TabContentFactory() {

				@Override
				public View createTabContent(String tag) {
					View v = new View(tabHost.getContext());
					v.setMinimumWidth(0);
					v.setMinimumHeight(0);
					return v;
				}

			});
			tabHost.addTab(spec);
	        if (getActivePlug() == plug)
	        	tabHost.setCurrentTab(i);
		}
	}

	@Override
	public void onActivate(IPlug plug) {
		if (((ITabPlug)plug).getContent() != null){
			TabHost tabHost = target.getTabHost();
			tabHost.setCurrentTab(getIndexOf(plug));
		} else {
			Logger.debug("The plug of PagerTabOutlet has not been attached !");			
		}
	}
}
