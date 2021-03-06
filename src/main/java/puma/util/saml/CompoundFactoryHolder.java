/*******************************************************************************
 * Copyright 2014 KU Leuven Research and Developement - iMinds - Distrinet 
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    Administrative Contact: dnet-project-office@cs.kuleuven.be
 *    Technical Contact: maarten.decat@cs.kuleuven.be
 *    Author: maarten.decat@cs.kuleuven.be
 ******************************************************************************/
package puma.util.saml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CompoundFactoryHolder<T extends RetrievableCompoundFactory<?>> implements Iterator<T> {
	private Integer pointer;
	private List<T> compoundFactories;
	
	public CompoundFactoryHolder() {
		this.pointer = 0;
		this.compoundFactories = new ArrayList<T>();
	}
	
	public void addFactory(T factory) {
		this.compoundFactories.add(factory);
	}

	@Override
	public boolean hasNext() {
		return this.pointer < this.compoundFactories.size();
	}

	@Override
	public T next() {
		if (!this.hasNext())
			throw new NoSuchElementException();
		return this.compoundFactories.get(this.pointer++);
	}

	@Override
	public void remove() {
		if (this.pointer == 0)
			throw new IllegalStateException();
		this.compoundFactories.remove(this.pointer - 1);
	}
}
