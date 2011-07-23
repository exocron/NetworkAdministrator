package networkadministrator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Opens an &quot;About RAP&quot; message dialog.
 */
public class AboutAction extends Action {

	private final IWorkbenchWindow window;

	public AboutAction(IWorkbenchWindow window) {
		super("About");
		setId(this.getClass().getName());
		this.window = window;
	}

	public void run() {
		if(window != null) {	
			String title = "About RAP Network Administrator";
			String msg =   "RAP Network Administrator is free software.\n\n"
			             + "For more information, visit: "
			             + "http://www.github.com/Isaac356/RAPNetworkAdministrator";
			MessageDialog.openInformation(window.getShell(), title, msg);
		}
	}
}
