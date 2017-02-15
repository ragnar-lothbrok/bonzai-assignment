package com.assignment;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LatLonPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;

public class LuceneSpatial {

	public static void main(String[] args) throws IOException {

		String indexDir = "/home/anujjalan/lucene_test";
		IndexWriter indexWriter = null;
		try {
			Path path = Paths.get(indexDir);
			Directory directory = FSDirectory.open(path);
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(OpenMode.CREATE);
			indexWriter = new IndexWriter(directory, indexWriterConfig);
		} catch (Exception e) {
		}

		Document document = new Document();
		document.add(new StringField("brand", "anujjalan", Store.YES));
		document.add(new LatLonPoint("geoloc", 6.821994, 79.886208));
		indexWriter.addDocument(document);
		Document document1 = new Document();
		document1.add(new StringField("brand", "anujjalan1", Store.YES));
		document1.add(new LatLonPoint("geoloc", 7.831994, 79.884117));
		indexWriter.addDocument(document1);

		indexWriter.commit();
		indexWriter.close();
		DirectoryReader reader = DirectoryReader.open(new MMapDirectory(Paths.get(indexDir)));
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		TopDocs docs = null;
		try {
			Query query = LatLonPoint.newDistanceQuery("geoloc", 6.9270790, 79.8612430, 50000);
			docs = indexSearcher.search(query, 5);

		} catch (Exception e) {
		}
		//
		for (ScoreDoc doc : docs.scoreDocs) {
			Document doc2 = indexSearcher.doc(doc.doc);
			System.out.println(doc2.get("brand"));
		}

	}

}
