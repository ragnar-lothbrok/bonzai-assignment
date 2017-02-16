package com.assignment.lucene.Impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LatLonPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.assignment.exceptions.IndexWriteException;
import com.assignment.exceptions.LatLongRangeException;
import com.assignment.exceptions.NoCabFoundException;
import com.assignment.lucene.api.GeoLocatorService;
import com.assignment.lucene.dtos.Location;
import com.assignment.lucene.dtos.Vehicle;

@Service
public class GeoLocatorServiceImpl implements GeoLocatorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocatorServiceImpl.class);

	@Autowired
	private IndexWriter indexWriter;

	@Autowired
	private IndexSearcher indexSearcher;

	@Value("${lucene.import.filename}")
	private String sampleDataFile;

	@Value("${lucene.fetched.neighbour.count}")
	private Integer fetchedCount;

	volatile private boolean isWriteHappend = false;

	@Autowired
	private ApplicationContext appContext;

	/**
	 * This is dummy data pumped in indexer.
	 */
	@PostConstruct
	public void fillData() {
		BufferedReader bufferedReader = null;
		try {
			Resource resource = new ClassPathResource(sampleDataFile);
			bufferedReader = new BufferedReader(new FileReader(resource.getFile()));
			String line = bufferedReader.readLine();
			line = bufferedReader.readLine();
			List<Vehicle> vehicleList = new ArrayList<Vehicle>();
			while (line != null) {
				String str[] = line.replaceAll("\"", "").split(",");
				Vehicle vehicle = new Vehicle();
				Location location = new Location();
				location.setLatitude(Float.parseFloat(str[1]));
				location.setLongitude(Float.parseFloat(str[2]));
				vehicle.setLocation(location);
				vehicle.setVehicleNumber(str[3] + "-" + str[0]);
				if (vehicleList.size() == 100) {
					break;
				}
				line = bufferedReader.readLine();
				vehicleList.add(vehicle);
			}
			addLocations(vehicleList);
		} catch (Exception e) {
			LOGGER.error("Exception occured while filling data from file {} ", e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LOGGER.error("Exception occured while closing buf reader {} ", e);
				}
			}
		}
	}

	/**
	 * This will add/update vehicle location in indexer.
	 */
	@Override
	public Boolean addLocations(List<Vehicle> vehicles) throws RuntimeException {
		try {
			LOGGER.info("Going to add vehicle locations = {} ", (vehicles != null ? vehicles.size() : 0));
			if (vehicles.size() > 0) {
				for (Vehicle vehicle : vehicles) {
					if (vehicle.getLocation() != null && vehicle.getLocation().getLatitude() != null
							&& vehicle.getLocation().getLongitude() != null) {
						Document document = new Document();
						document.add(new StringField("vehicleId", vehicle.getVehicleNumber(), Store.YES));
						document.add(new LatLonPoint("geoloc", vehicle.getLocation().getLatitude(), vehicle.getLocation().getLongitude()));
						indexWriter.updateDocument(new Term("vehicleId", vehicle.getVehicleNumber()), document);
					}
				}
			}
			indexWriter.commit();
			isWriteHappend = true;
		}catch(IllegalArgumentException e){
			LOGGER.error("Exception occured while adding document to index = {} ", e);
			throw new LatLongRangeException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception occured while adding document to index = {} ", e);
			throw new IndexWriteException();
		}
		return true;
	}

	/**
	 * This method will accept location of user and range within we want to search.
	 */
	@Override
	public List<Vehicle> getVehicleInRange(Location location, Double range) throws NoCabFoundException {
		indexSearcher = (isWriteHappend ? appContext.getBean(IndexSearcher.class) : indexSearcher);
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		TopDocs docs = null;
		try {
			if (location != null && location.getLatitude() != null && location.getLongitude() != null && range != null) {
				Query query = LatLonPoint.newDistanceQuery("geoloc", location.getLatitude(), location.getLongitude(), range);
				docs = indexSearcher.search(query, fetchedCount);
				for (ScoreDoc doc : docs.scoreDocs) {
					Document doc2 = indexSearcher.doc(doc.doc);
					Vehicle vehicle = new Vehicle();
					vehicle.setVehicleNumber(doc2.get("vehicleId"));
					vehicles.add(vehicle);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while fetching document from index = {} ", e);
		}
		if (vehicles.size() == 0) {
			throw new NoCabFoundException();
		}
		isWriteHappend = false;
		return vehicles;
	}

	/**
	 * This will add/update single vehicle position in indexer.
	 */
	@Override
	public Boolean addLocation(Vehicle vehicle) {
		try {
			if (vehicle != null && vehicle.getLocation() != null && vehicle.getLocation().getLatitude() != null
					&& vehicle.getLocation().getLongitude() != null) {
				Document document = new Document();
				document.add(new StringField("vehicleId", vehicle.getVehicleNumber(), Store.YES));
				document.add(new LatLonPoint("geoloc", vehicle.getLocation().getLatitude(), vehicle.getLocation().getLongitude()));
				indexWriter.updateDocument(new Term("vehicleId", vehicle.getVehicleNumber()), document);
			}
			indexWriter.commit();
		} catch (Exception e) {
			LOGGER.error("Exception occured while adding document to index = {} ", e);
			return false;
		}
		return true;
	}
}
