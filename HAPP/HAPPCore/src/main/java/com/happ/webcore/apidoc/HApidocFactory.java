package com.happ.webcore.apidoc;

import com.happ.webcore.apidoc.tofile.HApidocExcel;
import com.happ.webcore.apidoc.tofile.HApidocPdf;
import com.happ.webcore.apidoc.tofile.HApidocWord;

public class HApidocFactory {

	public static  HApidoc getDocWord() {
		return new HApidocWord();
	}

	public static HApidoc getDocExcel() {
		return new HApidocExcel();
	}

	public static HApidoc getDocPdf() {
		return new HApidocPdf();
	}

	public static HApidoc getDocHtml() {
		return new HApidocPdf();
	}
}
