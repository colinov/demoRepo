package org.exam.rbp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SearchServiceWorker extends Thread{

	private Boolean isDone;
	private String baseUrl;
	private String patternSearch;
	private String folder;
	
	public SearchServiceWorker(String baseUrl,String patternSearch,String folder){
		super(baseUrl);
		this.isDone=null;
		this.baseUrl=baseUrl;
		this.patternSearch=patternSearch;
		this.folder=folder;
	}
	
	public void run(){
		//leer contenido
		Map<String, String> mapHashTag=new HashMap<>();
		URL url=null;
		try {
			url=new URL(baseUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(url!=null){
			URLConnection conn;
			BufferedReader inputReader=null;
			StringBuilder sb=new StringBuilder();
			try {
				conn=url.openConnection();
				InputStreamReader input=new InputStreamReader(conn.getInputStream());
				inputReader=new BufferedReader(input);
				String line=inputReader.readLine();
				while(line!=null){
					sb.append(line);
					line=inputReader.readLine();
					if(line!=null){
						sb.append(line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				try {
					if(inputReader!=null){
						inputReader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//buscar contenido
			Pattern pattern=Pattern.compile(patternSearch);
			Document document=Jsoup.parse(sb.toString());
			Elements elems=document.getElementsMatchingText(pattern);
			
			if(elems!=null){
				System.out.println("elements.size="+elems.size());
				for(int i=0;i<elems.size();i++){
					String text=elems.get(i).text();
					String[] listHashTag=pattern.split(text);
					System.out.println("listHashTag,length="+listHashTag.length);
					for(String hashTag:listHashTag){
						text=StringUtils.replaceOnce(text, hashTag, "|");
					}
					StringTokenizer tok=new StringTokenizer(text, "|");
					while(tok.hasMoreTokens()){
						mapHashTag.put(tok.nextToken(), "");
					}
				}
				System.out.println("mapHashTag="+mapHashTag);
			}else{
				System.out.println("Not found!");
			}
		}
		//Escribir resultado
		String nameFile=url.getHost().replace(".", "-");
		File fileOut=new File(folder+nameFile+".txt");
		PrintStream printer=null;
		System.out.println("Path="+fileOut.getAbsolutePath());
		try {
			printer=new PrintStream(fileOut);
			for(String key:mapHashTag.keySet()){
				printer.println(key);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			printer.close();
		}
		isDone=true;
	}
	
	public boolean isDone(){
		while(isDone==null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return isDone;
	}
}
