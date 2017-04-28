package com.graduate.server.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.time.format.TextStyle;
import java.util.HashSet;








import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;


public class IndexBuild {
	private  static String inputPath="F:\\graduate\\data\\instance_processed\\";
	private  static String outPath=inputPath+"index";
	private  String entityPath="entity2id.txt";
	private  String abstractPath="entity2abstract.txt";
	private  HashSet<String> entitySet=new HashSet<String>();
	public IndexBuild(){
		CreateIndex();
	}
	public  void CreateIndex(){
		CreateAbstractIndex(inputPath+abstractPath);
		CreateEntityIndex(inputPath+entityPath);
	}
	private  void CreateAbstractIndex(String abstractPath) {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
	    indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
	    
		BufferedReader reader;
		IndexWriter indexWriter;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(abstractPath)), "UTF-8"));
			indexWriter = new IndexWriter(FSDirectory.open(Paths.get(outPath)), indexWriterConfig);
        	
			String tmpString = null;
			int count=0;
        	while((tmpString = reader.readLine()) != null){
        		count++;
        		entitySet.add(tmpString.split("\t")[0]);
            	indexWriter.addDocument(createAbstract(tmpString));
        	}
        	System.out.println("Abstract:"+count);
			reader.close();
			indexWriter.close();
		} catch (IOException e) {
			System.out.println("fuck"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	private void CreateEntityIndex(String entityPath){
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
	    indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
	    
		BufferedReader reader;
		IndexWriter indexWriter;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(entityPath)), "UTF-8"));
			indexWriter = new IndexWriter(FSDirectory.open(Paths.get(outPath)), indexWriterConfig);
        	int count=0;
			String tmpString = null;
        	while((tmpString = reader.readLine()) != null){
        		if(!entitySet.contains(tmpString.split("\t")[0])){
        			count++;
        			indexWriter.addDocument(createEntity(tmpString));
        		}
            	
        	}
        	System.out.println("Entity:"+count);
			reader.close();
			indexWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Document createAbstract(String tmpString) throws IOException{
		String[] tokens=tmpString.split("\t");
		String name=URLDecoder.decode(tokens[0].replaceAll("_", " "),"UTF-8");
		Document doc=new Document();
		if(name.equals("bryan gordon")) System.out.println("YES");
		doc.add(new Field("name",name,TextField.TYPE_STORED));
		doc.add(new Field("context",tokens[1],TextField.TYPE_STORED));
		return doc;
	}
	private Document createEntity(String tmpString) throws IOException{
		String[] tokens=tmpString.split("\t");
		String name=URLDecoder.decode(tokens[0].replaceAll("_", " "),"UTF-8");
		Document doc=new Document();
		doc.add(new Field("name",name,TextField.TYPE_STORED));
		return doc;
	}
	public static DirectoryReader getIndexReader(){
		DirectoryReader directoryReader = null;		
		try {
			directoryReader = DirectoryReader.open(FSDirectory.open(Paths.get(outPath)));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
		return directoryReader;
	}
}

