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

import java.util.Date;

import org.eclipse.rwt.lifecycle.UICallBack;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DownloadControl extends Composite {
	
	private String url;
	private Runnable remove = null;
	private Label lblFilename;
	private ProgressBar progressBar;
	private long uid;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DownloadControl(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		lblFilename = new Label(this, SWT.NONE);
		FormData fd_lblFilename = new FormData();
		fd_lblFilename.top = new FormAttachment(0, 10);
		lblFilename.setLayoutData(fd_lblFilename);
		lblFilename.setText("Filename");
		
		progressBar = new ProgressBar(this, SWT.NONE);
		fd_lblFilename.left = new FormAttachment(progressBar, 0, SWT.LEFT);
		FormData fd_progressBar = new FormData();
		fd_progressBar.right = new FormAttachment(100, -10);
		fd_progressBar.bottom = new FormAttachment(100, -10);
		fd_progressBar.left = new FormAttachment(0, 10);
		progressBar.setLayoutData(fd_progressBar);
		
		Button btnClose = new Button(this, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO stop download when clicked
				if (remove != null) {
					remove.run();
				}
			}
		});
		FormData fd_btnClose = new FormData();
		fd_btnClose.top = new FormAttachment(0, 10);
		fd_btnClose.right = new FormAttachment(100, -10);
		btnClose.setLayoutData(fd_btnClose);
		btnClose.setText("X");
		
		Button btnPause = new Button(this, SWT.NONE);
		fd_lblFilename.right = new FormAttachment(btnPause, -319);
		FormData fd_btnPause = new FormData();
		fd_btnPause.top = new FormAttachment(0, 10);
		fd_btnPause.right = new FormAttachment(btnClose, -6);
		btnPause.setLayoutData(fd_btnPause);
		btnPause.setText("| |");
		// TODO add "pause download" functionality

	}

	public DownloadControl(Composite content, final String url) {		
		this(content, SWT.NONE);
		this.url = url;
		uid = new Date().getTime();
		UICallBack.activate("DownloadControl " + uid);
		Thread t = new Thread(new Downloader(url, new Downloader.Progress() {
			@Override
			public void onProgress(final String filename, final int progress) {
				// update filename and progress
				getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						lblFilename.setText(filename);
						progressBar.setSelection(progress);
					}
				});
				// deactivate callback when finished
				if (progress == 100) {
					getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							UICallBack.deactivate("DownloadControl " + uid);
						}
					});
				}
			}
		}));
		t.start();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void onRemove(Runnable remove) {
		this.remove = remove;
	}
}
