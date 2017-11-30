package com.happ.webcore.apidoc;

public interface HApidoc {

	public boolean addClass(Class<?> testClass);

	public boolean toFile(String outPath);

	public void setAuthorInfo(String author, String commpany, String mail);

}
