// This file is in the public domain.

package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class StreamUtils {
	
	/**
	 * Fast stream copy. From this blog: http://thomaswabner.wordpress.com/2007/10/09/fast-stream-copy-using-javanio-channels/
	 * @param in InputStream to read from
	 * @param out OutputStream to read to
	 * @param progress callback to monitor progress
	 * @throws IOException If something goes wrong with one of the streams
	 */
	public static void streamCopy(final InputStream in, final OutputStream out, final Progress progress) throws IOException {
		final ReadableByteChannel inChannel = Channels.newChannel(in);
		final WritableByteChannel outChannel = Channels.newChannel(out);
		
		final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		int read = 0;
		int total = 0;
		while ((read = inChannel.read(buffer)) != -1) {
			buffer.flip();
			outChannel.write(buffer);
			buffer.compact();
			total += read;
			if (progress != null) progress.onProgress(total, true);
		}
		
		buffer.flip();
		while (buffer.hasRemaining()) {
			outChannel.write(buffer);
		}
		if (progress != null) progress.onProgress(total, false);
		
		inChannel.close();
		outChannel.close();
	}
	
	public interface Progress {
		public abstract void onProgress(int progress, boolean more);
	}
}
