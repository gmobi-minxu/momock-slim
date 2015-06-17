/*******************************************************************************
 * Copyright 2013 momock.com
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
package com.momock.service;

import com.momock.event.IEventHandler;
import com.momock.util.Logger;

public class ErrorReportService implements IErrorReportService, IEventHandler<Logger.LogEventArgs>{

	@Override
	public void process(Object s, Logger.LogEventArgs args) {
		onError(args.getMessage(), args.getError());
	}

	@Override
	public void start() {
		Logger.addErrorLogHandler(this);
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Class<?>[] getDependencyServices() {
		return null;
	}

	@Override
	public boolean canStop() {
		return true;
	}

	@Override
	public void onError(String errorMessage, Throwable error) {
		
	}

}
