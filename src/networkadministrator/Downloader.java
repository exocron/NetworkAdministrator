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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.StreamUtils;

public class Downloader implements Runnable {
	
	private String url;
	private String filename;
	private Progress p;
	
	public static final Pattern attachmentPattern = Pattern.compile("attachment;filename=(.*)");
	
	public Downloader(String url, Progress p) {
		this.url = url;
		this.p = p;
	}

	@Override
	public void run() {
		try {
			HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
			c.connect();
			// let's check to see if the server gives us a "suggested filename"
			final String disposition = c.getHeaderField("Content-Disposition");
			final Matcher matcher = attachmentPattern.matcher(disposition);
			if (matcher.matches()) {
				filename = matcher.group(1);
			} else { // parse it out of url
				int end = url.indexOf("?");
				filename = (end == -1) ? url.substring(0) : url.substring(0, url.indexOf("?"));
				filename = filename.substring(filename.lastIndexOf("/"));
				if ("".equals(filename)) { // last resort
					filename = "index.htm";
				}
			}
			final int total = Integer.parseInt(c.getHeaderField("Content-Length"));
			InputStream in = c.getInputStream();
			FileOutputStream out = new FileOutputStream(File.createTempFile(filename, ""));
			StreamUtils.streamCopy(in, out, new StreamUtils.Progress() {
				@Override
				public void onProgress(int progress, boolean more) {
					if (more) {
						p.onProgress(filename, progress * 100 / total);
					} else {
						p.onProgress(filename, 100);
					}
				}
			});
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable t) {
			// just in case
			t.printStackTrace();
		}
	}
	
	public interface Progress {
		public abstract void onProgress(final String filename, final int progress);
	}
}
