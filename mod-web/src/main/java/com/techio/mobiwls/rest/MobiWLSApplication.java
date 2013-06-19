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

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.techio.mobiwls.rest.resources.DomainResource;
import com.techio.mobiwls.rest.resources.JMSServerResource;
import com.techio.mobiwls.rest.resources.ServerResource;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */
@ApplicationPath("/*")
public class MobiWLSApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(DomainResource.class);
		s.add(ServerResource.class);
		s.add(JMSServerResource.class);
		return s;
	}
}
