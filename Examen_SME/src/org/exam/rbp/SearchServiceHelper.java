package org.exam.rbp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class SearchServiceHelper {
	
	private SearchServiceHelper(){
	}

	public static List<String> leerArchivo(String pathFile){
		if(pathFile==null||"".equals(pathFile)){
			System.out.println("Invalid path!");
			return null;
		}
		File file=new File(pathFile);
		if(!file.isFile()&&!file.canRead()){
			System.out.println("Not file or cannot read!");
			return null;
		}
		FileReader freader=null;
		try {
			freader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		BufferedReader reader=new BufferedReader(freader);
		List<String> listUrls=new ArrayList<String>();
		try {
			String line = reader.readLine();
			while(line!=null){
				listUrls.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return listUrls;
	}
}