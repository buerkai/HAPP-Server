package com.happ.webcore.servlet.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import com.happ.webcore.base.utils.HIOUtil;

public class HServletInputStreamWrapper extends ServletInputStream {

	private ServletInputStream inputStream;
	private ByteArrayOutputStream outputStream;
	private StreamColoseListener listener;
	
	

	public HServletInputStreamWrapper(ServletInputStream inputStream) {
		this.inputStream = inputStream;
		outputStream = new ByteArrayOutputStream();
	}

	@Override
	public boolean isFinished() {
		return inputStream.isFinished();
	}

	@Override
	public boolean isReady() {
		return inputStream.isReady();
	}

	@Override
	public void setReadListener(ReadListener paramReadListener) {
		inputStream.setReadListener(paramReadListener);
	}
	@Override
	public int read() throws IOException {
		int tmp = inputStream.read();
		outputStream.write(tmp);
		return tmp;
	}

	/***
	 * 读取数据
	 * 
	 * @return
	 */
	public byte[] getData() {
		try {
			byte[] data = outputStream.toByteArray();
			return data;
		} finally {
			HIOUtil.close(outputStream);
		}
	}

	@Override
	public void close() throws IOException {
		super.close();
		if (this.listener != null) {
			this.listener.doClose();
		}
	}

	public void setStreamColoseListener(StreamColoseListener listener) {
		this.listener = listener;
	}

	public interface StreamColoseListener {
		public void doClose();
	}
}
