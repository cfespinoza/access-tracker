package com.caflores.access_tracker.exporter.implementation;

import org.json.JSONObject;
import java.io.File; 
import java.util.Date; 
import jxl.*; 
import jxl.write.*; 

import com.caflores.access_tracker.exporter.ExportService;

public class Exporter implements ExportService{
	
	private final String DEFAULT_NAME = "OutputXLS";
	private WritableWorkbook workbook = Workbook.createWorkbook( new File(getExternalFilesDir(null), DEFAULT_NAME));
	
	
	@Override
	public void exportXLS(JSONObject data) {
		// TODO Auto-generated method stub
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
		
		Label label = new Label(0, 2, "A label record"); 
		sheet.addCell(label); 
		
		Number number = new Number(3, 4, 3.1459); 
		sheet.addCell(number);
		
		// All sheets and cells added. Now write out the workbook 
		workbook.write(); 
		workbook.close();
	}

	@Override
	public void exportCSV(JSONObject data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportXLS(JSONObject data, String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportCSV(JSONObject data, String fileName) {
		// TODO Auto-generated method stub
		
	}

	private WritableSheet getSheet(int position){
		
	}
	
}