package com.happ.webcore.apidoc.tofile;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.presentationml.x2006.main.CTBackground;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTHdrFtrImpl;

import com.happ.webcore.apidoc.HApidocImpl;
import com.happ.webcore.apidoc.bean.HApidocMoudleBean;
import com.happ.webcore.apidoc.bean.HApidocParamBean;
import com.happ.webcore.apidoc.bean.HApidocRestApiBean;
import com.happ.webcore.base.utils.HDateFormatUtil;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class HApidocWord extends HApidocImpl {

	final String color = "222222";
	protected XWPFDocument document;
	@Override
	public boolean toFile(String outPath) {
		try {
			document = new XWPFDocument();
			createIndex(document);
			createInterfaces(document);
			document.write(new FileOutputStream(outPath));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void setTableWidth(XWPFTable table, int width) {
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? (CTTblPr) ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		CTJc cTJc = tblPr.addNewJc();
		cTJc.setVal(STJc.Enum.forString("center"));
		tblWidth.setW(BigInteger.valueOf(width));
		tblWidth.setType(STTblWidth.DXA);
	}

	public void setCellText(XWPFTableCell cell, String text, int width, String txtClor, boolean isBold, int fontSize) {
		CTTc cttc = cell.getCTTc();
		CTTcPr ctPr = cttc.isSetTcPr() ? cttc.getTcPr() : (CTTcPr) cttc.addNewTcPr();
		if (width > 0) {
			ctPr.addNewTcW().setW(BigInteger.valueOf(width));
		}

		CTShd ctshd = ctPr.isSetShd() ? ctPr.getShd() : ctPr.addNewShd();
		if (txtClor != null) {
			ctshd.setColor(txtClor);
		}

		ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
		XWPFParagraph pg;
		if ((pg = cell.getParagraphArray(0)) == null) {
			pg = cell.addParagraph();
		}
		XWPFRun run = null;
		List<XWPFRun> runs = pg.getRuns();
		if (runs == null || runs.isEmpty()) {
			run = pg.createRun();
		} else {
			run = runs.get(0);
		}
		run.setColor(txtClor);
		run.setBold(isBold);
		run.setText(text);
		run.setFontSize(fontSize);
	}

	public void setCellText2(XWPFTableCell cell, String text, int width, String txtClor, boolean isBold, int fontSize,
			int shadowType, String shadowColor) {
		CTTc cttc = cell.getCTTc();
		CTTcPr ctPr = cttc.isSetTcPr() ? cttc.getTcPr() : (CTTcPr) cttc.addNewTcPr();
		if (width > 0) {
			ctPr.addNewTcW().setW(BigInteger.valueOf(width));
		}

		CTShd ctshd = ctPr.isSetShd() ? ctPr.getShd() : ctPr.addNewShd();
		if (txtClor != null) {
			ctshd.setColor(shadowColor);
			if (shadowType > 0 && shadowType <= 38) {
				ctshd.setVal(STShd.Enum.forInt(shadowType));
			}
		}

		ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
		XWPFParagraph pg;
		if ((pg = cell.getParagraphArray(0)) == null) {
			pg = cell.addParagraph();
		}
		XWPFRun run = null;
		List<XWPFRun> runs = pg.getRuns();
		if (runs == null || runs.isEmpty()) {
			run = pg.createRun();
		} else {
			run = runs.get(0);
		}
		run.setColor(txtClor);
		run.setBold(isBold);
		run.setText(text);
		run.setFontSize(fontSize);
	}

	/**
	 * @Description: 跨列合并
	 */
	public void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
		for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
			XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
			if (cellIndex == fromCell) {
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

	/**
	 * @Description: 跨行合并
	 */
	public void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
		for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
			XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
			if (rowIndex == fromRow) {
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
			} else {
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

	private void setRowHeight(XWPFTableRow row, int height) {
		row.setHeight(height);
	}

	// 创建目录
	protected void createIndex(XWPFDocument document) {

		document.createFooter(HeaderFooterType.DEFAULT).createParagraph().createRun().setText(commpany);

		XWPFParagraph p = document.createParagraph();// 新建一个段落
		p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
		XWPFRun r = p.createRun();// 创建段落文本
		r.setText("接口文档");
		r.setFontSize(20);
		r.setBold(true);// 设置为粗体

		p = document.createParagraph();// 新建一个段落
		p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
		p.createRun().setText("");
		p.setSpacingAfterLines(1000);

		XWPFTable table = document.createTable(4, 2);
		setTableWidth(table, 9000);
			
		setRowHeight(table.getRow(0), 400);
		setCellText(table.getRow(0).getCell(0), "作者", 4000, color, true, 18);
		setCellText(table.getRow(0).getCell(1), "" + author, 0, color, false, 20);

		setRowHeight(table.getRow(1), 400);
		setCellText(table.getRow(1).getCell(0), "公司", 4000, color, true, 18);
		setCellText(table.getRow(1).getCell(1), "" + commpany, 0, color, false, 20);

		setRowHeight(table.getRow(2), 400);
		setCellText(table.getRow(2).getCell(0), "邮箱", 4000, color, true, 18);
		setCellText(table.getRow(2).getCell(1), "" + mail, 0, color, false, 20);
		
		setRowHeight(table.getRow(3), 400);
		setCellText(table.getRow(3).getCell(0), "日期", 4000, color, true, 18);
		setCellText(table.getRow(3).getCell(1), "" + HDateFormatUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"), 0, color, false, 20);
		
		p = document.createParagraph();// 新建一个段落
		p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
		p.setPageBreak(true);
		
		
		 p = document.createParagraph();// 新建一个段落
		p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
		 r = p.createRun();// 创建段落文本
		r.setText("目录");
		r.setFontSize(20);
		r.setBold(true);// 设置为粗体
		
	}

	protected void createInterfaces(XWPFDocument document) {
		int index = 0;
		for (HApidocMoudleBean item : moudles) {
			index++;
			createAModule(document, item, index);
		}
	}

	private void createAModule(XWPFDocument document, HApidocMoudleBean item, int index) {
		XWPFParagraph p = document.createParagraph();// 新建一个段落
		XWPFRun run = p.createRun();
		run.setText("" + index + "." + item.getName());
		run.setFontSize(18);
		p.setSpacingAfterLines(200);

		List<HApidocRestApiBean> list = item.getRestApi();
		int count = 0;
		for (HApidocRestApiBean itembean : list) {
			count++;
			createAInterfaceDoc(document, itembean, index, count);
		}
		p = document.createParagraph();// 新建一个段落
		run = p.createRun();
		run.setText("");
		run.setFontSize(18);
		p.setSpacingAfterLines(200);

	}

	private void createAInterfaceDoc(XWPFDocument document, HApidocRestApiBean item, int modelIndex, int index) {
		XWPFParagraph p = document.createParagraph();// 新建一个段落
		XWPFRun run = p.createRun();
		run.setText("" + modelIndex + "." + index + " " + item.getName());
		run.setFontSize(18);
		p.setSpacingAfterLines(200);

		int rowNum = item.getInParams().size() + item.getOutParams().size() + 6;

		XWPFTable table = document.createTable(rowNum, 5);
		setTableWidth(table, 10000);
		setRowHeight(table.getRow(0), 400);

		int rowIndex = 0;
		mergeCellsHorizontal(table, rowIndex, 1, 4);
		setCellText(table.getRow(rowIndex).getCell(0), "接口地址", 2000, color, false, 14);
		setCellText(table.getRow(rowIndex).getCell(1), item.getUrl(), 0, color, false, 14);

		// 接口描述
		rowIndex++;
		mergeCellsHorizontal(table, rowIndex, 1, 4);
		setCellText(table.getRow(rowIndex).getCell(0), "接口描述", 2000, color, false, 14);
		setCellText(table.getRow(rowIndex).getCell(1), item.getDescription(), 0, color, false, 12);

		String color1 = "9E386B";
		rowIndex++;
		mergeCellsHorizontal(table, rowIndex, 1, 4);
		setCellText(table.getRow(rowIndex).getCell(0), "输入参数", 2000, color1, false, 14);
		setCellText(table.getRow(rowIndex).getCell(1), "", 0, "", false, 14);

		// 输入参数
		rowIndex++;
		XWPFTableRow row = table.getRow(rowIndex);
		setCellText2(row.getCell(0), "参数名称", 2000, color1, false, 14, 16, color);
		setCellText2(row.getCell(1), "参数描述", 5000, color1, false, 14, 16, color);
		setCellText2(row.getCell(2), "是否必须", 1000, color1, false, 14, 16, color);
		setCellText2(row.getCell(3), "类型", 1000, color1, false, 14, 16, color);
		setCellText2(row.getCell(4), "长度", 1000, color1, false, 14, 16, color);

		List<HApidocParamBean> params = item.getInParams();
		for (HApidocParamBean pitem : params) {
			rowIndex++;
			row = table.getRow(rowIndex);
			setCellText(row.getCell(0), pitem.getName(), 2000, color, false, 14);
			setCellText(row.getCell(1), pitem.getDescription(), 5000, color, false, 14);
			setCellText(row.getCell(2), pitem.isRequire() ? "Y" : "N", 1000, color, false, 14);
			setCellText(row.getCell(3), pitem.getType().toString(), 1000, color, false, 14);
			setCellText(row.getCell(4), "" + pitem.getLength(), 1000, color, false, 14);
		}

		params = item.getOutParams();
		if (params != null && !params.isEmpty()) {
			String color2 = "67507A";
			rowIndex++;
			mergeCellsHorizontal(table, rowIndex, 1, 4);
			setCellText(table.getRow(rowIndex).getCell(0), "输出参数", 2000, color2, false, 14);
			setCellText(table.getRow(rowIndex).getCell(1), "", 0, "", false, 14);

			rowIndex++;
			row = table.getRow(rowIndex);
			setCellText2(row.getCell(0), "参数名称", 2000, color2, false, 14, 18, color);
			setCellText2(row.getCell(1), "参数描述", 5000, color2, false, 14, 18, color);
			setCellText2(row.getCell(2), "是否必须", 1000, color2, false, 14, 18, color);
			setCellText2(row.getCell(3), "类型", 1000, color2, false, 14, 18, color);
			setCellText2(row.getCell(4), "长度", 1000, color2, false, 14, 18, color);

			for (HApidocParamBean pitem : params) {
				rowIndex++;
				row = table.getRow(rowIndex);
				setCellText(row.getCell(0), pitem.getName(), 2000, color, false, 14);
				setCellText(row.getCell(1), pitem.getDescription(), 5000, color, false, 14);
				setCellText(row.getCell(2), pitem.isRequire() ? "Y" : "N", 1000, color, false, 14);
				setCellText(row.getCell(3), pitem.getType().toString(), 1000, color, false, 14);
				setCellText(row.getCell(4), "" + pitem.getLength(), 1000, color, false, 14);
			}
		}
		
		p = document.createParagraph();// 新建一个段落
		run = p.createRun();
		run.setText("");
		run.setFontSize(18);
		p.setSpacingAfterLines(200);

		
	}

	private XWPFParagraph createHeaderFooter(XWPFDocument document, String title) {
		CTP ctp = CTP.Factory.newInstance();
		CTR ctr = ctp.addNewR();
		CTText textt = ctr.addNewT();
		textt.setStringValue(title);
		XWPFParagraph codePara = new XWPFParagraph(ctp, document);
		codePara.setAlignment(ParagraphAlignment.CENTER);
		codePara.setVerticalAlignment(TextAlignment.CENTER);

		XWPFRun r1 = codePara.createRun();
		r1.setText("第");
		r1.setFontSize(11);
		CTRPr rpr = r1.getCTR().isSetRPr() ? (CTRPr) r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		r1 = codePara.createRun();
		CTFldChar fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

		r1 = codePara.createRun();
		CTText ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("PAGE  \\* MERGEFORMAT");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

		r1 = codePara.createRun();
		r1.setText("页 总共");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		r1 = codePara.createRun();
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

		r1 = codePara.createRun();
		ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

		r1 = codePara.createRun();
		r1.setText("页");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		codePara.setAlignment(ParagraphAlignment.CENTER);
		codePara.setVerticalAlignment(TextAlignment.CENTER);
		codePara.setBorderTop(Borders.THICK);

		XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
		newparagraphs[0] = codePara;
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
		headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, newparagraphs);
		headerFooterPolicy.createHeader(STHdrFtr.DEFAULT, newparagraphs);
		return codePara;
	}

}
