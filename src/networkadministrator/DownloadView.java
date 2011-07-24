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

import java.util.ArrayList;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DownloadView extends ViewPart {

	public static final String ID = "networkadministrator.DownloadView"; //$NON-NLS-1$
	
	private ArrayList<DownloadControl> downloaders = new ArrayList<DownloadControl>();

	private Composite top;
	private Composite content;

	public DownloadView() {
	}
	
	/**
	 * Start downloading an HTTP url.
	 * @param url
	 */
	public void addHttpDownload(final String url) {
		// Create DownloadControl
		final DownloadControl download = new DownloadControl(content, url);
		
		// What to do when it is removed
		download.onRemove(new Runnable() {
			@Override
			public void run() {
				int index = downloaders.indexOf(download);
				// shift all others up
				if (index != -1) {
					downloaders.remove(index);
					for (int i = index; i < downloaders.size(); i++) {
						Composite previous = (i == 0) ? top : downloaders.get(i - 1);
						FormData fd_new = new FormData();
						fd_new.bottom = new FormAttachment(previous, 75, SWT.BOTTOM);
						fd_new.top = new FormAttachment(previous);
						fd_new.left = new FormAttachment(0);
						fd_new.right = new FormAttachment(100);
						downloaders.get(i).setLayoutData(fd_new);
					}
				}
				// now, dispose
				download.dispose();
				
				// just in case
				content.layout();
			}
		});
		
		// Add composite to our running list
		downloaders.add(download);
		
		// insert into correct place
		FormData fd_download = new FormData();
		Composite previous = (downloaders.size() == 1) ? top : downloaders.get(downloaders.size() - 2);
		fd_download.bottom = new FormAttachment(previous, 75, SWT.BOTTOM);
		fd_download.top = new FormAttachment(previous);
		fd_download.left = new FormAttachment(0);
		fd_download.right = new FormAttachment(100);
		download.setLayoutData(fd_download);
		
		// required to make our new composite show up
		content.layout();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(final Composite parent) {
		content = new Composite(parent, SWT.NONE);
		content.setLayout(new FormLayout());
		
		top = new Composite(content, SWT.NONE);
		top.setLayout(new FormLayout());
		
		Button btnHttpDownload = new Button(top, SWT.NONE);
		btnHttpDownload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String url = InputBox.open(parent.getShell(), "Enter the URL to download:", "Enter URL");
				if (url != null) {
					addHttpDownload(url);
				}
			}
		});
		FormData fd_btnHttpDownload = new FormData();
		fd_btnHttpDownload.bottom = new FormAttachment(100, -10);
		fd_btnHttpDownload.right = new FormAttachment(50, -16);
		btnHttpDownload.setLayoutData(fd_btnHttpDownload);
		btnHttpDownload.setText("New HTTP Download");
		
		Button btnTorrentDownload = new Button(top, SWT.NONE);
		btnTorrentDownload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO: Implement BitTorrent downloading
				MessageDialog.openError(parent.getShell(), "Error", "Feature not implemented!");
			}
		});
		FormData fd_btnTorrentDownload = new FormData();
		fd_btnTorrentDownload.bottom = new FormAttachment(100, -10);
		fd_btnTorrentDownload.left = new FormAttachment(50, 16);
		btnTorrentDownload.setLayoutData(fd_btnTorrentDownload);
		btnTorrentDownload.setText("New BitTorrent Download");
		
		FormData fd_top = new FormData();
		fd_top.bottom = new FormAttachment(0, 50);
		fd_top.top = new FormAttachment(0);
		fd_top.left = new FormAttachment(0);
		fd_top.right = new FormAttachment(100);
		top.setLayoutData(fd_top);
		
//		DownloadControl downloadControl = new DownloadControl(content, SWT.NONE);
//		FormData fd_downloadControl = new FormData();
//		fd_downloadControl.bottom = new FormAttachment(top, 75, SWT.BOTTOM);
//		fd_downloadControl.top = new FormAttachment(top);
//		fd_downloadControl.left = new FormAttachment(0);
//		fd_downloadControl.right = new FormAttachment(100);
//		downloadControl.setLayoutData(fd_downloadControl);
//		
//		DownloadControl downloadControl_1 = new DownloadControl(content, SWT.NONE);
//		FormData fd_downloadControl_1 = new FormData();
//		fd_downloadControl_1.bottom = new FormAttachment(downloadControl, 75, SWT.BOTTOM);
//		fd_downloadControl_1.top = new FormAttachment(downloadControl);
//		fd_downloadControl_1.left = new FormAttachment(0);
//		fd_downloadControl_1.right = new FormAttachment(100);
//		downloadControl_1.setLayoutData(fd_downloadControl_1);
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
