package com.happ.webcore.apidoc;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/***
 * 参数类型
 * @author Administrator
 *
 */
public enum HApidocParamType {
	
	
	STRING, INT, LONG, FLOAT, STRING_ARRAY, INT_ARRAY, LONG_ARRAY, FLOAT_ARRAY, JSONOBJECT, JSONARRAY

	;
	
	@Override
	public String toString() {
		switch (this) {
		case STRING:
			return "String";
		case INT:
			return "Int";
		case LONG:
			return "Long";
		case FLOAT:
			return "Float";
		case STRING_ARRAY:
			return "String array";
		case INT_ARRAY:
			return "Int array";
		case LONG_ARRAY:
			return "Long array";
		case FLOAT_ARRAY:
			return "Float array";
		case JSONOBJECT:
			return "jsonobject";
		case JSONARRAY:
			return "jsonarray";
		default:
			return "";
		}
	}
	
	
}
