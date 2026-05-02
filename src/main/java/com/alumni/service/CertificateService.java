package com.alumni.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class CertificateService {
    public byte[] generateCertificate(String donorName, String projectTitle, String amount, String certificateNo, String date) {
        try {
            Document doc = new Document(PageSize.A4.rotate());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, baos);
            doc.open();
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bf, 36, Font.BOLD);
            Font subFont = new Font(bf, 20);
            Font smallFont = new Font(bf, 14);
            doc.add(new Paragraph(" ", titleFont));
            doc.add(new Paragraph(" ", titleFont));
            doc.add(new Paragraph("捐 赠 证 书", titleFont));
            doc.add(new Paragraph(" ", subFont));
            doc.add(new Paragraph(" ", subFont));
            doc.add(new Paragraph("兹证明 " + donorName + " 先生/女士", subFont));
            doc.add(new Paragraph("向燕山大学 " + projectTitle + " 项目", subFont));
            doc.add(new Paragraph("捐赠人民币 " + amount + " 元", subFont));
            doc.add(new Paragraph(" ", subFont));
            doc.add(new Paragraph("证书编号：" + certificateNo, smallFont));
            doc.add(new Paragraph("捐赠日期：" + date, smallFont));
            doc.add(new Paragraph(" ", subFont));
            doc.add(new Paragraph("特此致谢！", subFont));
            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("证书生成失败", e);
        }
    }
}
