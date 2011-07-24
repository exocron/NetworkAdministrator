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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * When run, this action will show a message dialog.
 */
public class MessagePopupAction extends Action {

	private final IWorkbenchWindow window;

	MessagePopupAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_OPEN_MESSAGE);
		// Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("NetworkAdministrator", "/icons/sample3.gif"));
	}

	public void run() {
		MessageDialog.openInformation(window.getShell(), "Open", "Open Message Dialog!");
	}
}
