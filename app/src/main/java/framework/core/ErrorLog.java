package framework.core;

import java.util.ArrayList;
import android.util.Log;

public class ErrorLog {
	private ArrayList<String> outputFile;
	private static ErrorLog instance;

	private ErrorLog() {
		outputFile = new ArrayList<String>();
		outputFile.add("- V1.0");
	}

	public static ErrorLog get() {
		if(instance == null) {
			instance = new ErrorLog();
		} return instance;
	}

	public void print() {
		for(int i = 0; i < outputFile.size(); i++) {
			if(i == 0) {
				Log.e("Error Logger", outputFile.get(i));
			} else {
				Log.e("Error " + i, outputFile.get(i));
			}
		}
	}

	public void write(String data) {
		outputFile.add(data);
	}
}
