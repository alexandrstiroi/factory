package org.shtiroy.factory.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Класс для создания PDF отчета.
 */
@Component
public class PdfCreate {
    private static final Logger LOGGER = LogManager.getLogger(PdfCreate.class.getName());
    @Value("${factory.files}")
    private String filesPath;

    /**
     * Пробел в параграфе.
     * @param paragraph - Абзац pdf файла.
     */
    public void paragraphAddSpace(Paragraph paragraph){
        paragraph.add(" ");
    }

    /**
     * Пустая строка в документе.
     * @param number - количество пустых строк
     * @return - paragraph c пустыми строками.
     */
    public Paragraph addEmptyLine(int number) {
        Paragraph result = new Paragraph();
        for (int i = 0; i < number; i++) {
            result.add(new Paragraph(" "));
        }
        return result;
    }

    /**
     * Текст курсивом.
     * @param paragraph - параграф pdf файла.
     * @param text - текст.
     */
    public void paragraphAddItalicText(Paragraph paragraph, String text){
        try {
            BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251",BaseFont.EMBEDDED);
            Font italicFont = new Font(times, 12, Font.UNDERLINE | Font.ITALIC);
            paragraph.add(new Chunk(text,italicFont));
        } catch (DocumentException ex){
            LOGGER.info("error read PDF");
        } catch (IOException ex){
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Текст обычный.
     * @param paragraph - параграф pdf файла.
     * @param text - текст.
     */
    public void paragraphAddNormalText(Paragraph paragraph, String text){
        try {
            BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251",BaseFont.EMBEDDED);
            Font normalFont = new Font(times, 14, Font.NORMAL);
            paragraph.add(new Chunk(text,normalFont));
        } catch (DocumentException ex){
            LOGGER.info("error read PDF");
        } catch (IOException ex){
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Добавить фото.
     * @param paragraph
     * @param imagePath
     */
    public void paragraphAddImage(Paragraph paragraph, String imagePath){
        try {
            Image image = Image.getInstance("C:\\Temp\\Projects\\Java\\factory\\"+imagePath);
            image.scalePercent(30);
            Paragraph paragraphImage = new Paragraph();
            paragraphImage.add(image);
            paragraphImage.setAlignment(Element.ALIGN_RIGHT);
            paragraph.add(paragraphImage);
        } catch (DocumentException ex){
            LOGGER.info("error read PDF");
        } catch (IOException ex){
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Логотип мануфактуры.
     * @param document - pdf документ куда надо добавить.
     */
    public void logo(Document document){
        try {
            BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf", "cp1251", BaseFont.EMBEDDED);
            Font normalFont = new Font(times, 18, Font.NORMAL);
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            paragraph.add(new Chunk("МАНУФАКТУРА УЮТА", normalFont));
            document.add(paragraph);
        } catch (DocumentException ex){
            LOGGER.info("error read PDF");
        } catch (IOException ex){
            LOGGER.error(ex.getMessage());
        }
    }
}
