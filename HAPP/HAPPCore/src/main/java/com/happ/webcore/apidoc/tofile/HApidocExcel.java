package com.happ.webcore.apidoc.tofile;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.happ.webcore.apidoc.HApidocImpl;
import com.happ.webcore.apidoc.bean.HApidocMoudleBean;
import com.happ.webcore.apidoc.bean.HApidocParamBean;
import com.happ.webcore.apidoc.bean.HApidocRestApiBean;

public class HApidocExcel extends HApidocImpl {

	@Override
	public boolean toFile(String outPath) {
		try {
			XSSFWorkbook book = new XSSFWorkbook();
			createIndex(book);
			for (HApidocMoudleBean item : moudles) {
				createInterFaces(book, item);
			}
			book.write(new FileOutputStream(outPath));
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	


	private void createInterFaces(XSSFWorkbook book, HApidocMoudleBean bean) {
		XSSFSheet sheet = book.createSheet(bean.getName());
		sheet.setColumnWidth(0, 256 * 15);
		sheet.setColumnWidth(1, 256 * 30);
		int startRow = 0;
		for (HApidocRestApiBean item : bean.getRestApi()) {
			startRow=createAInterface(item, startRow, sheet, book);
			startRow ++;
		}
	}

	private int createAInterface(HApidocRestApiBean api, int startRow, XSSFSheet sheet, XSSFWorkbook book) {

		XSSFCellStyle canshu_color = book.createCellStyle();
		canshu_color.setFillForegroundColor(IndexedColors.GREEN.index);
		canshu_color.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFRow row = sheet.createRow(startRow);
		row.setHeight((short) (256 * 2));
		row.createCell(0).setCellValue("接口名称");
		//row.getCell(0).setCellStyle(canshu_color);
		row.createCell(1).setCellValue(api.getUrl());
		//row.getCell(1).setCellStyle(canshu_color);
		CellRangeAddress region = new CellRangeAddress(startRow, startRow, 1, 4); // 参数都是从O开始

		sheet.addMergedRegion(region);

		startRow++;
		row = sheet.createRow(startRow);
		row.setHeight((short) (256 * 1.5f));
		row.createCell(0).setCellValue("描述");
		row.createCell(1).setCellValue(api.getDescription());
		region = new CellRangeAddress(startRow, startRow, 1, 4); // 参数都是从O开始
		sheet.addMergedRegion(region);

		startRow++;
		row.setHeight((short) (256 * 1.5f));
		row = sheet.createRow(startRow);
		region = new CellRangeAddress(startRow, startRow, 0, 4);
		sheet.addMergedRegion(region);

		row.createCell(0).setCellValue("输入参数");
		row.setHeight((short) (256 * 1.5));
		CellStyle cellStyle = book.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		row.getCell(0).setCellStyle(cellStyle);

		// 输入参数
		startRow++;
		row = sheet.createRow(startRow);
		row.createCell(0).setCellValue("参数名称");
		row.createCell(1).setCellValue("参数描述");
		row.createCell(2).setCellValue("是否必须");
		row.createCell(3).setCellValue("类型");
		row.createCell(4).setCellValue("长度");
		for(int i=0;i<5;i++) {
			row.getCell(i).setCellStyle(canshu_color);
		}

		startRow++;
		List<HApidocParamBean> list = api.getInParams();
		for (HApidocParamBean p : list) {
			row = sheet.createRow(startRow);
			row.createCell(0).setCellValue(p.getName());
			row.createCell(1).setCellValue(p.getDescription());
			row.createCell(2).setCellValue(p.isRequire());
			row.createCell(3).setCellValue(p.getType().toString());
			row.createCell(4).setCellValue(p.getLength());
			startRow++;
		}

		startRow++;

		row = sheet.createRow(startRow);
		row.setHeight((short) (256 * 1.5));
		region = new CellRangeAddress(startRow, startRow, 0, 4);
		sheet.addMergedRegion(region);
		row.createCell(0).setCellValue("输出参数");
		cellStyle = book.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.MAROON.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		row.getCell(0).setCellStyle(cellStyle);

		// 输出参数
		startRow++;
		row = sheet.createRow(startRow);
		row.createCell(0).setCellValue("参数名称");
		row.createCell(1).setCellValue("参数描述");
		row.createCell(2).setCellValue("是否必须");
		row.createCell(3).setCellValue("类型");
		row.createCell(4).setCellValue("长度");
		for(int i=0;i<5;i++) {
			row.getCell(i).setCellStyle(canshu_color);
		}
		
		startRow++;
		list = api.getOutParams();
		for (HApidocParamBean p : list) {
			row = sheet.createRow(startRow);
			row.createCell(0).setCellValue(p.getName());
			row.createCell(1).setCellValue(p.getDescription());
			row.createCell(2).setCellValue(p.isRequire());
			row.createCell(3).setCellValue(p.getType().toString());
			row.createCell(4).setCellValue(p.getLength());
			startRow++;
		}
		
		startRow++;
		row = sheet.createRow(startRow);
		
		cellStyle = book.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		row.setRowStyle(cellStyle);
		startRow++;
		return startRow;
	}

	private void createIndex(XSSFWorkbook book) {
		XSSFSheet sheet = book.createSheet("index");
		int startrow = 1;
		if (author != null) {
			XSSFRow row = sheet.createRow(startrow);
			row.setHeightInPoints(30);// 设置行高30
			XSSFCell cellKey = row.createCell(0);
			cellKey.setCellValue("作者");
			CellStyle cellStyle = book.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellKey.setCellStyle(cellStyle);
			row.createCell(1).setCellValue(author);
		}
		startrow++;
		if (commpany != null) {
			XSSFRow row = sheet.createRow(startrow);
			row.setHeightInPoints(30);// 设置行高30
			XSSFCell cellKey = row.createCell(0);
			cellKey.setCellValue("公司");
			CellStyle cellStyle = book.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellKey.setCellStyle(cellStyle);
			row.createCell(1).setCellValue(commpany);
		}
		startrow++;
		if (mail != null) {
			XSSFRow row = sheet.createRow(startrow);
			row.setHeightInPoints(30);// 设置行高30
			XSSFCell cellKey = row.createCell(0);
			cellKey.setCellValue("邮箱");
			CellStyle cellStyle = book.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellKey.setCellStyle(cellStyle);
			row.createCell(1).setCellValue(mail);
		}

		startrow += 2;

		CellRangeAddress region2 = new CellRangeAddress(startrow, startrow, 0, 8); // 参数都是从O开始
		sheet.addMergedRegion(region2);
		XSSFCell cell = sheet.createRow(startrow).createCell(0);
		cell.setCellValue("目录");
		CellStyle cellStyle = book.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cellStyle);

		startrow++;

		sheet.setColumnWidth(0, 256 * 20);
		sheet.setColumnWidth(1, 256 * 15);
		CellStyle indexCellStyle = book.createCellStyle();
		indexCellStyle.setBorderBottom(BorderStyle.MEDIUM); // 下边框边框
		indexCellStyle.setBorderTop(BorderStyle.MEDIUM); // 上边框
		indexCellStyle.setBorderLeft(BorderStyle.MEDIUM); // 左边框
		indexCellStyle.setBorderRight(BorderStyle.MEDIUM); // 有边框
		indexCellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// 目录
		for (HApidocMoudleBean item : moudles) {
			XSSFRow item_row = sheet.createRow(startrow);
			XSSFCell item_cell = item_row.createCell(0);
			String name = item.getName();
			Hyperlink link2 = book.getCreationHelper().createHyperlink(HyperlinkType.DOCUMENT);
			link2.setAddress("'" + name + "'!A1");// 连接到Target Sheet a1单元格
			item_cell.setHyperlink(link2);
			item_cell.setCellValue(name);
			startrow++;
		}

	}

}
