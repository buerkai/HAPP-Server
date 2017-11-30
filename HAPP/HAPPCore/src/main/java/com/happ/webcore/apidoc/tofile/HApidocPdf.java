package com.happ.webcore.apidoc.tofile;

import java.io.FileOutputStream;

import com.happ.webcore.apidoc.HApidocImpl;
import com.happ.webcore.apidoc.bean.HApidocMoudleBean;
import com.happ.webcore.apidoc.bean.HApidocParamBean;
import com.happ.webcore.apidoc.bean.HApidocRestApiBean;
import com.happ.webcore.base.utils.HDateFormatUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.ZapfDingbatsList;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class HApidocPdf extends HApidocImpl {

	private BaseFont baseFontChinese;
	private Font font18, font16, font14, font12, font10;
	private Font font12_0, font12_1;

	@Override
	public boolean toFile(String outPath) {

		Document document = new Document();

		PdfWriter writer;
		try {
			String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "SIMHEI.TTF";
			baseFontChinese = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			font18 = new Font(baseFontChinese, 18, Font.NORMAL);
			font16 = new Font(baseFontChinese, 16, Font.NORMAL);
			font14 = new Font(baseFontChinese, 14, Font.NORMAL);
			font12 = new Font(baseFontChinese, 12, Font.NORMAL);
			font10 = new Font(baseFontChinese, 10, Font.NORMAL);
			font12_0 = new Font(baseFontChinese, 12, Font.NORMAL);
			// 9E386B
			font12_0.setColor(0x9e, 0x38, 0x6b);
			font12_1 = new Font(baseFontChinese, 12, Font.NORMAL);
			// 67507A
			font12_1.setColor(0x67, 0x50, 0x7a);

			writer = PdfWriter.getInstance(document, new FileOutputStream(outPath));
			document.open();
			setPdfProperty(document);
			createIndex(document);
			createInterfaces(document);
			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	private void test() {
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);  
        try {  
            // step 2: we create a writer that listens to the document  
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream("c:\\ChapterSection.pdf"));  
            // step 3: we open the document  
            writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);  
            document.open();  
            // step 4: we add content to the document  
            // we define some fonts  
            Font chapterFont = font18;
            Font sectionFont =font16;
            Font subsectionFont = font14;
            // we create some paragraphs  
            Paragraph blahblah = new Paragraph("blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");  
            Paragraph blahblahblah = new Paragraph("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");  
            // this loop will create 7 chapters  
            for (int i = 1; i < 8; i++) {  
                Paragraph cTitle = new Paragraph("This is chapter " + i, chapterFont);  
                Chapter chapter = new Chapter(cTitle, i);  
                // in chapter 4 we change the alignment to ALIGN_JUSTIFIED  
                if (i == 4) {  
                    blahblahblah.setAlignment(Element.ALIGN_JUSTIFIED);  
                    blahblah.setAlignment(Element.ALIGN_JUSTIFIED);  
                    chapter.add(blahblah);  
                }  
                // in chapter 5, the alignment is changed again  
                if (i == 5) {  
                    blahblahblah.setAlignment(Element.ALIGN_CENTER);  
                    blahblah.setAlignment(Element.ALIGN_RIGHT);  
                    chapter.add(blahblah);  
                }  
                // the alignment is changed to ALIGN_JUSTIFIED again  
                if (i == 6) {  
                    blahblahblah.setAlignment(Element.ALIGN_JUSTIFIED);  
                    blahblah.setAlignment(Element.ALIGN_JUSTIFIED);  
                }  
                // in every chapter 3 sections will be added  
                for (int j = 1; j < 4; j++) {  
                    Paragraph sTitle = new Paragraph("This is section " + j + " in chapter " + i, sectionFont);  
                    Section section = chapter.addSection(sTitle, 1);  
                    // for chapters > 2, the outine isn't open by default  
                    if (i > 2) section.setBookmarkOpen(false);  
                    // in all chapters except the 1st one, some extra text is added to section 3  
                    if (j == 3 && i > 1) {  
                        section.setIndentationLeft(72);  
                        section.add(blahblah);  
                        section.add(new Paragraph("test"));  
                    }  
                    // in every section 3 subsections are added  
                    for (int k = 1; k < 4; k++) {  
                        Paragraph subTitle = new Paragraph("This is subsection " + k + " of section " + j, subsectionFont);  
                        Section subsection = section.addSection(subTitle, 3);  
                        // in the first subsection of section 3, extra text is added  
                        if (k == 1 && j == 3) {  
                            subsection.add(blahblahblah);  
                        }  
                        subsection.add(blahblah);  
                    }  
                    // in the section section of every chapter > 2 extra text is added  
                    if (j == 2 && i > 2) {  
                        section.add(blahblahblah);  
                    }  
                    // a new page is added after the second section in Chapter 1  
                    if (j == 2 && i == 1) {  
                        section.add(Chunk.NEXTPAGE);  
                    }  
                }  
                document.add(chapter);  
            }  
        }  
        catch(Exception de) {  
            de.printStackTrace();  
        }  
        // step 5: we close the document  
        document.close();  
   
	}

	private PdfPCell createTableCell(String text, Font font, boolean center, int rowIndex, int cols) {
		Paragraph p = new Paragraph(text, font);
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderColor(BaseColor.BLACK);
		cell.setPaddingLeft(10);
		if (center) {
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setRowspan(rowIndex);
		cell.setColspan(cols);
		return cell;
	}

	private PdfPCell createTableCell(String text, Font font, boolean center) {
		Paragraph p = new Paragraph(text, font);
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderColor(BaseColor.BLACK);
		cell.setPaddingLeft(10);
		if (center) {
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}

	private void createInterfaces(Document doc) throws DocumentException {
		doc.newPage();
		int index = 0;
		for (HApidocMoudleBean item : moudles) {
			index++;
			createAmodule(doc, item, index);
		}

	}

	private void createAmodule(Document doc, HApidocMoudleBean moudle, int moudleIndex) throws DocumentException {
		// doc.add(new Chunk(moudle.getName()).setLocalDestination("" + moudleIndex));
		doc.add(new ListItem("" + moudleIndex + "." + moudle.getName(), font16));
		int index = 0;
		java.util.List<HApidocRestApiBean> list = moudle.getRestApi();
		for (HApidocRestApiBean item : list) {
			index++;
			createAinterface(doc, item, moudleIndex, index);
		}
	}

	private void createAinterface(Document doc, HApidocRestApiBean api, int moudleIndex, int apiIndex)
			throws DocumentException {
		doc.add(new Chunk(api.getName()).setLocalDestination("" + moudleIndex + "." + apiIndex));
		ZapfDingbatsList orderedList1 = new ZapfDingbatsList(43, 30);
		orderedList1.add(new ListItem("" + moudleIndex + "." + apiIndex + " " + api.getName(), font14));
		doc.add(orderedList1);

		PdfPTable table = new PdfPTable(5); // 3 columns.
		table.setWidthPercentage(100); // Width 100%
		table.setSpacingBefore(10f); // Space before table
		table.setSpacingAfter(10f); // Space after table

		table.setWidths(new float[] { 2.0f, 4.0f, 1.5f, 1.5f, 1.0f });

		int index = 0;

		//
		table.addCell(createTableCell("接口地址", font12, false));
		table.addCell(createTableCell(api.getUrl(), font12, false, index, 4));

		// 描述
		index++;
		table.addCell(createTableCell("接口描述", font12, false));
		table.addCell(createTableCell(api.getDescription(), font10, false, index, 4));

		// 输入参数
		index++;
		table.addCell(createTableCell("输入参数", font12_0, false));
		table.addCell(createTableCell("", font12_0, false, index, 4));

		//
		index++;
		table.addCell(createTableCell("参数名称", font12_0, false, index, 1));
		table.addCell(createTableCell("参数描述", font12_0, false, index, 1));
		table.addCell(createTableCell("是否必须", font12_0, false, index, 1));
		table.addCell(createTableCell("类型", font12_0, false, index, 1));
		table.addCell(createTableCell("长度", font12_0, false, index, 1));

		java.util.List<HApidocParamBean> list = api.getInParams();
		for (HApidocParamBean p : list) {
			index++;
			table.addCell(createTableCell(p.getName(), font10, false, index, 1));
			table.addCell(createTableCell(p.getDescription(), font10, false, index, 1));
			table.addCell(createTableCell(p.isRequire() ? "Y" : "N", font10, false, index, 1));
			table.addCell(createTableCell(p.getType().toString(), font10, false, index, 1));
			table.addCell(createTableCell("" + p.getLength(), font10, false, index, 1));
		}

		// 输出参数
		index++;
		table.addCell(createTableCell("输出参数", font12_1, false, index, 1));
		table.addCell(createTableCell(" ", font12_1, false, index, 4));
		index++;
		table.addCell(createTableCell("参数名称", font12_1, false, index, 1));
		table.addCell(createTableCell("参数描述", font12_1, false, index, 1));
		table.addCell(createTableCell("是否必须", font12_1, false, index, 1));
		table.addCell(createTableCell("类型", font12_1, false, index, 1));
		table.addCell(createTableCell("长度", font12_1, false, index, 1));
		list = api.getOutParams();
		for (HApidocParamBean p : list) {
			index++;
			table.addCell(createTableCell(p.getName(), font10, false, index, 1));
			table.addCell(createTableCell(p.getDescription(), font10, false, index, 1));
			table.addCell(createTableCell(p.isRequire() ? "Y" : "N", font10, false, index, 1));
			table.addCell(createTableCell(p.getType().toString(), font10, false, index, 1));
			table.addCell(createTableCell("" + p.getLength(), font10, false, index, 1));
		}
		doc.add(table);

	}

	private void createIndex(Document doc) {
		Paragraph p = new Paragraph("接口文档", font18);
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(400);
		p.setSpacingAfter(100);
		try {
			doc.add(p);
			PdfPTable table = new PdfPTable(2); // 3 columns.
			table.setWidthPercentage(100); // Width 100%
			table.setSpacingBefore(10f); // Space before table
			table.setSpacingAfter(10f); // Space after table

			table.setWidths(new float[] { 1.0f, 4.0f });

			if (author != null) {
				table.addCell(createTableCell("作者", font14, true));
				table.addCell(createTableCell(author, font14, false));
			}
			if (commpany != null) {
				table.addCell(createTableCell("公司", font14, true));
				table.addCell(createTableCell(commpany, font14, false));
			}
			if (mail != null) {
				table.addCell(createTableCell("邮件", font14, true));
				table.addCell(createTableCell(mail, font14, false));
			}

			table.addCell(createTableCell("日期", font14, true));
			table.addCell(createTableCell(HDateFormatUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"), font12, false));
			doc.add(table);

			doc.newPage();
			p = new Paragraph("大纲", font18);
			p.setAlignment(Element.ALIGN_CENTER);
			p.setSpacingBefore(400);
			p.setSpacingAfter(50);
			doc.add(p);
			
			int index1=0,index2=0;
			List orderedList = new List(List.ORDERED);
			for(HApidocMoudleBean item:moudles) {
				index1++;
				orderedList.add(new ListItem(item.getName(), font14));
				List orderedList2 = new List(List.UNORDERED);
				java.util.List<HApidocRestApiBean> list=item.getRestApi();
				index2=0;
				for(HApidocRestApiBean api:list) {
					index2++;
					orderedList2.add(new ListItem(""+index1+"."+index2+" "+api.getName(), font12));
				}
				orderedList.add(orderedList2);
			}
			doc.add(orderedList);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 创建文件属性
	 * 
	 * @param document
	 */
	private void setPdfProperty(Document document) {
		if (author != null) {
			document.addAuthor(author);
		}

		if (mail != null) {
			document.addCreator(mail);
		}
		document.addCreationDate();
		document.addTitle("接口文档");
		if (commpany != null) {
			document.addSubject(commpany);
		}
	}

}
