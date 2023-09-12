package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class GeneratePdf {
	
	public void generate(String client, Invoice i) {
		double subtotal = 0;
		
		try {
			String fileName = "C:\\Users\\ahamm\\Eclipse-workspace\\proj\\pdf\\testInvoice.pdf";
			Document document = new Document();
			
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
			document.open();
			
			document.add(new Paragraph(client));
			document.add(new Paragraph("\n\n"));
			
			PdfPTable table = new PdfPTable(5);
			PdfPCell c1 = new PdfPCell(new Phrase("ID"));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Name"));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Unit Price"));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Qty"));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Total"));
			table.addCell(c1);
			
			table.setHeaderRows(1);
			
			table.addCell(i.getPid());
			table.addCell(i.getPname());
			table.addCell(String.valueOf(i.getPrice()));
			table.addCell(String.valueOf(i.getQuantity()));
			
			subtotal = subtotal + i.getTotal();
			
			table.addCell(String.valueOf(i.getTotal()));
			
			document.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
