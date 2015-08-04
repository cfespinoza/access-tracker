package com.caflores.access_tracker.exporter;

import org.json.JSONObject;

public interface ExportService {
	
	public void exportXLS(JSONObject data);
	public void exportCSV(JSONObject data);
	public void exportXLS(JSONObject data, String fileName);
	public void exportCSV(JSONObject data, String fileName);
	
}
