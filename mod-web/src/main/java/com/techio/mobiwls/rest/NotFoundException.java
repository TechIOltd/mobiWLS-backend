/*
 * Copyright (c) 2013 TechIO Ltd (http://techio.com)
 * 
 * (http://techio.com/portfolio/mobile-applications)
 *
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
 */

package com.techio.mobiwls.rest;

import javax.ws.rs.core.Response.Status;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */
public class NotFoundException extends AbstractGenericRestException {

	public NotFoundException() {
		super(Status.NOT_FOUND);
	}

	public NotFoundException(String message) {
		super(message, Status.NOT_FOUND);
	}

}
