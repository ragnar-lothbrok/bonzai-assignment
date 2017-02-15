package com.assignment.lucene;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuceneConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(LuceneConfiguration.class);

	@Value("${lucene.index.dir}")
	private String indexDir;

	@Bean
	public IndexWriter getIndexWriter() {
		IndexWriter indexWriter = null;
		try {
			Path path = Paths.get(indexDir);
			Directory directory = FSDirectory.open(path);
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			indexWriter = new IndexWriter(directory, indexWriterConfig);
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating index writer = {} ", e);
		}
		return indexWriter;
	}

	@Bean
	public IndexSearcher getIndexSearcher() {
		IndexSearcher indexSearcher = null;
		try {
			Path path = Paths.get(indexDir);
			Directory directory = MMapDirectory.open(path);
			IndexReader indexReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(indexReader);
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating searcher {}", e);
		}
		return indexSearcher;
	}
}
