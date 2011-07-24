/*******************************************************************************
 * Copyright (c) 2011 Isaac Tepper and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Isaac Tepper - initial implementation
 ******************************************************************************/

package networkadministrator;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

	public static final String CMD_OPEN = "NetworkAdministrator.open";
	public static final String CMD_OPEN_MESSAGE = "NetworkAdministrator.openMessage";

}
