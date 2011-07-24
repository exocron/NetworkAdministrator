package networkadministrator;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class InputBox extends Dialog {

	protected Object result;
	protected Shell shell;
	private String label = "Text";
	private String input = null;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public InputBox(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public String open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return input;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 171);
		shell.setText(getText());
		shell.setLayout(new FormLayout());
		
		Label lblText = new Label(shell, SWT.NONE);
		FormData fd_lblText = new FormData();
		fd_lblText.top = new FormAttachment(0, 35);
		fd_lblText.left = new FormAttachment(0, 35);
		fd_lblText.right = new FormAttachment(100, -35);
		lblText.setLayoutData(fd_lblText);
		lblText.setText(label);
		
		final Text text = new Text(shell, SWT.BORDER);
		fd_lblText.bottom = new FormAttachment(text, -6);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(100, -35);
		fd_text.left = new FormAttachment(0, 35);
		text.setLayoutData(fd_text);
		if (input != null) text.setText(input);
		input = null;
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				input = null;
				shell.close();
			}
		});
		fd_text.bottom = new FormAttachment(btnCancel, -6);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.right = new FormAttachment(100, -35);
		fd_btnCancel.bottom = new FormAttachment(100, -10);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				input = text.getText();
				shell.close();
			}
		});
		FormData fd_btnOk = new FormData();
		fd_btnOk.bottom = new FormAttachment(btnCancel, 0, SWT.BOTTOM);
		fd_btnOk.right = new FormAttachment(btnCancel, -23);
		btnOk.setLayoutData(fd_btnOk);
		btnOk.setText("OK");

	}

	public String getInput() {
		return input;
	}

	public void setInput(String text) {
		input = text;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String text) {
		label = text;
	}
	
	/**
	 * Convenience method for opening an InputBox.
	 * @param shell parent shell
	 * @param message the text in the InputBox
	 * @return the input given by the user, or null if cancel was clicked or the dialog was closed
	 */
	public static String open(Shell parent, String message) {
		return open(parent, message, "Input", null);
	}
	
	/**
	 * Convenience method for opening an InputBox.
	 * @param shell parent shell
	 * @param message the text in the InputBox
	 * @param title title of the InputBox
	 * @return the input given by the user, or null if cancel was clicked or the dialog was closed
	 */
	public static String open(Shell parent, String message, String title) {
		return open(parent, message, title, null);
	}
	
	/**
	 * Convenience method for opening an InputBox.
	 * @param shell parent shell
	 * @param message the text in the InputBox
	 * @param title title of the InputBox
	 * @param input default value for the text box
	 * @return the input given by the user, or null if cancel was clicked or the dialog was closed
	 */
	public static String open(Shell parent, String message, String title, String input) {
		InputBox inputBox = new InputBox(parent, SWT.NONE);
		inputBox.setLabel(message);
		inputBox.setText(title);
		inputBox.setInput(input);
		return inputBox.open();
	}
}
