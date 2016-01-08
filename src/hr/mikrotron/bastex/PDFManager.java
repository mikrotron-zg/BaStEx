package hr.mikrotron.bastex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**PDFManager is PDFBox wrapper used for PDF manipulation in BaStEx, 
 * primarily for text extraction
 * @author prexy
 * @version 0.1
 */
public class PDFManager {

	String filepath;
	PDFTextStripper pdfStripper;
	PDDocument pdDoc;
	COSDocument cosDoc;
	File file;

	public PDFManager(String filepath){
		this.filepath=filepath;
	}
	
	/**Extracts text from all pages in PDF file
	 * @return string containing extracted text
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String getText() throws FileNotFoundException, IOException{
		return getText(0,0);
	}
	
	
	/**Extracts text from PDF file, including start and end page
	 * @param startPage from 1 no n, if given 0 or less it equals to 1
	 * @param endPage from 1 to n, if given 0 or less it equals to last page
	 * @return string containing extracted text
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String getText(int startPage, int endPage) throws FileNotFoundException, IOException{
		
		//TODO check page numbers, throw exceptions if needed
		file=new File(filepath);
		PDFParser parser = new PDFParser(new RandomAccessFile(file,"r")); //for PDFBox 2.0 or later
		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		pdfStripper.setStartPage(startPage<=0 ? 1 : startPage);
		pdfStripper.setEndPage(endPage==0 ? pdDoc.getNumberOfPages() : endPage);
		return pdfStripper.getText(pdDoc);

	}
}
