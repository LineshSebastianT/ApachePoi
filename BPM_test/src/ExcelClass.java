import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 */
/**
 * @author linesh
 *
 */
public class ExcelClass {

	static ArrayList <PatientRecord> caseListFinal = new ArrayList <PatientRecord>();

	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void readXls(String filename)
	{
		try {

			//Transfer excel to ArrayList :: START//
			InputStream ExcelFileToRead = new FileInputStream(filename);
			HSSFWorkbook  wb = new HSSFWorkbook(ExcelFileToRead);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row; 
			ArrayList <PatientRecord> caseList = new ArrayList <PatientRecord>();   
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext())
			{
				row= (HSSFRow) rows.next();
				PatientRecord newPatient = new PatientRecord();
				for(int r=1;r<=1;r++){
					newPatient.eventNum = row.getRowNum()+1;
					newPatient.caseIdentifier =  (long) row.getCell(0).getNumericCellValue();
					newPatient.unixStart = (int) row.getCell(1).getNumericCellValue();
					newPatient.unixEnd = (int) row.getCell(2).getNumericCellValue();
					newPatient.activityName = row.getCell(3).getStringCellValue();
					newPatient.startDate = row.getCell(4).getStringCellValue();
					newPatient.endDate = row.getCell(5).getStringCellValue();
					caseList.add(newPatient);
				}
			}
			//Transfer excel to ArrayList :: END//
			ExcelFileToRead.close();

			//Recent BloodDraw count :: START//
			int drawCount;
			for(int i = 0;i<caseList.size();i++){
				drawCount=0;

				int recentTime = caseList.get(i).unixStart - 20 * 60 * 1000;

				for(int r = 0;r<caseList.size();r++){
					if((caseList.get(r).getUnixEnd() >= recentTime) && (caseList.get(r).getUnixEnd() < caseList.get(i).unixStart)){
						if(caseList.get(r).getActivityName().equalsIgnoreCase("BloodDraw")){
							++drawCount;
						}
						else{
							continue;
						}
					}
					else{
						continue;
					}
				}
				caseList.get(i).setBloodDrawCount(drawCount);
			}
			//Recent BloodDraw count :: END//

			long caseEndtime = 0;
			ArrayList <PatientRecord> caseListTemp;
			for(int i = 0;i<caseList.size();i++){
				caseListTemp = new ArrayList <PatientRecord>();
				long currentCase = caseList.get(i).getCaseIdentifier();
				for(int r = 0;r<caseList.size();r++){
					if(caseList.get(r).getCaseIdentifier()==currentCase){
						caseListTemp.add(caseList.get(r));					
					}
					else{
						continue;
					}
				}
				caseList.removeAll(caseListTemp);
				for(int j=1;j < caseListTemp.size();j++){
					if(caseListTemp.get(j-1).getUnixEnd()<caseListTemp.get(j).getUnixEnd()){
						caseEndtime = caseListTemp.get(j).getUnixEnd();
						continue;
					}
					else{
						caseEndtime = caseListTemp.get(j-1).getUnixEnd();
						continue;
					}
				}

				for(int k =0; k<caseListTemp.size();k++){
					long diff =  caseEndtime - caseListTemp.get(k).getUnixStart();
					caseListTemp.get(k).setRemainingTime((double)(diff/60));
				}
				caseListFinal.addAll(caseListTemp);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("||Exception @ readXls method");
			e.printStackTrace();
		}

	}

	public static void writeXls(String filename) {
		try
		{

			String sheetName = "Output Sheet";//name of sheet

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName) ;
			for (int r=0;r < caseListFinal.size(); r++ )
			{
				HSSFRow rowData = sheet.createRow(r);

				rowData.createCell(0).setCellValue(caseListFinal.get(r).getEventNum());
				rowData.createCell(1).setCellValue(caseListFinal.get(r).getCaseIdentifier());
				rowData.createCell(2).setCellValue(caseListFinal.get(r).getUnixStart());
				rowData.createCell(3).setCellValue(caseListFinal.get(r).getUnixEnd());
				rowData.createCell(4).setCellValue(caseListFinal.get(r).getActivityName());
				rowData.createCell(5).setCellValue(caseListFinal.get(r).getStartDate());
				rowData.createCell(6).setCellValue(caseListFinal.get(r).getEndDate());
				rowData.createCell(7).setCellValue(caseListFinal.get(r).getRemainingTime());
				rowData.createCell(8).setCellValue(caseListFinal.get(r).getBloodDrawCount());
			}
			int rows=sheet.getLastRowNum();
			sheet.shiftRows(0,rows,1); 
			HSSFRow rowHeader = sheet.createRow(0);
			rowHeader.createCell(0).setCellValue("Event Number");
			rowHeader.createCell(1).setCellValue("Case Identifier");
			rowHeader.createCell(2).setCellValue("Unix Start time");
			rowHeader.createCell(3).setCellValue("Unix End time");
			rowHeader.createCell(4).setCellValue("Activity Name");
			rowHeader.createCell(5).setCellValue("Event Start Date");
			rowHeader.createCell(6).setCellValue("Event End Date");
			rowHeader.createCell(7).setCellValue("Remaining Time (in Mins)");
			rowHeader.createCell(8).setCellValue("Blood Draw Count");
			FileOutputStream fileOut = new FileOutputStream(filename);

			//write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception @ writeXls");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "D:/College Masters - UoT/My Workspace/java/BPM_test/test_data.xls";
		System.out.println("Program :: Start :: Input File = "+filename);
		String outFile = "D:/College Masters - UoT/My Workspace/java/BPM_test/outData.xls";
		readXls(filename);
		writeXls(outFile);
		System.out.println("Program :: End :: OutPut File = "+outFile);
	}

}
