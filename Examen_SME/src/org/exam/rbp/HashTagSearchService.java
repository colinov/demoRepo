package org.exam.rbp;

import java.util.ArrayList;
import java.util.List;

public class HashTagSearchService implements SearchService{

	@Override
	public boolean search(String pathFile) {
		System.out.println("Start search");
		//leer archivo
		List<String> listUrls=SearchServiceHelper.leerArchivo(pathFile);
		if(listUrls==null||listUrls.isEmpty()){
			return false;
		}
		//iniciar buscadores
		List<SearchServiceWorker> listWoker=new ArrayList<>();
		for(String baseUrl:listUrls){
			SearchServiceWorker worker=new SearchServiceWorker(baseUrl, SearchConstants.HASHTAG_PATTERN,
					SearchConstants.HASHTAG_FOLDER);
			worker.start();
			listWoker.add(worker);
		}
		
		for(SearchServiceWorker worker:listWoker){
			System.out.println("worker "+worker.getName()+" fin="+worker.isDone());
		}
		
		System.out.println("End search");
		return true;
	}
}