package com.happ.webcore.servlet.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

import com.happ.webcore.base.utils.HIOUtil;

public class HServletOutputStreamWrapper extends ServletOutputStream {

	private ByteArrayOutputStream byteoutputStream;
	private ServletOutputStream outputStream;

	public HServletOutputStreamWrapper(ServletOutputStream outputStream) {
		byteoutputStream = new ByteArrayOutputStream();
		this.outputStream = outputStream;
	}

	@Override
	public boolean isReady() {
		return outputStream.isReady();
	}

	@Override
	public void setWriteListener(WriteListener paramWriteListener) {
		outputStream.setWriteListener(paramWriteListener);
	}

	@Override
	public void write(int paramInt) throws IOException {
		byteoutputStream.write(paramInt);
		outputStream.write(paramInt);
	}

	/***
	 * 读取数据
	 * 
	 * @return
	 */
	public byte[] getData() {
		try {
			byte[] data = byteoutputStream.toByteArray();
			return data;
		} finally {
			HIOUtil.close(byteoutputStream);
		}
	}

	@Override
	public void close() throws IOException {
		super.close();
	}
}
