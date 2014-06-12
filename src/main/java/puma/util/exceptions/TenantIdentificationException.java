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
package puma.util.exceptions;

import java.util.List;

public class TenantIdentificationException extends Exception {
	private static final long serialVersionUID = -8124689443198376391L;

	public TenantIdentificationException(List<String> possibilities) {
		super("Could not find a matching tenant for the given possibilities: " + foldPossibilities(possibilities) + "");
	}

	private static String foldPossibilities(List<String> possibilities) {
		String separator = ", ";
		String result = "";
		for (String next: possibilities) {
			result = result + next + separator;
		}
		return result.substring(0, (result.length() - separator.length()));
	}
}
