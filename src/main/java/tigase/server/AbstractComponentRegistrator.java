/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2007 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */
package tigase.server;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Describe class AbstractComponentRegistrator here.
 *
 *
 * Created: Tue Nov 22 22:57:44 2005
 *
 * @param <E> 
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public abstract class AbstractComponentRegistrator<E extends ServerComponent>
	extends BasicComponent implements ComponentRegistrator {

	private long packetId = 0;
	protected Map<String, E> components = new LinkedHashMap<String, E>();


	/**
	 * Creates a new <code>AbstractComponentRegistrator</code> instance.
	 *
	 */
	public AbstractComponentRegistrator() {}

	public abstract boolean isCorrectType(ServerComponent component);

	@SuppressWarnings("unchecked")
	@Override
	public boolean addComponent(ServerComponent component) {
		if (isCorrectType(component)) {
			components.put(component.getName(), (E)component);
			componentAdded((E)component);
			return true;
		} else {
			return false;
		}
	}

	public E getComponent(String name) {
		return components.get(name);
	}

	public abstract void componentAdded(E component);

  /**
   *
	 * @param component
	 * @return tigase.server.ServerComponent
   */
  @SuppressWarnings("unchecked")
	@Override
	public boolean deleteComponent(ServerComponent component) {
		components.remove(component.getName());
		componentRemoved((E)component);
		return true;
	}

	@Override
	public void release() {}

	public String newPacketId(String prefix) {
		StringBuilder sb = new StringBuilder(32);
		if (prefix != null) {
			sb.append(prefix).append("-");
		}
		sb.append(getName()).append(++packetId);
		return sb.toString();
	}

	public abstract void componentRemoved(E component);

} // AbstractComponentRegistrator
