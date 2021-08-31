package com.ericsson.eus.plcfg.plugin;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ericsson.eus.plcfg.plugin.constant.PlcfgConstants;

/**
 * 
 * FILE_NAME: LoadConfigMapProp.java
 * 
 * MODULE DESCRIPTION: F96456 :ATTPRE-96674
 *
 * @author eznraps, Date: Feb 2, 2021 4:15:16 PM 2021
 * 
 * @(c) COPYRIGHT 2021 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class LoadConfigMapProp {
	private Properties prop = new Properties();
	Timestamp timestamp;
	int i = 0;
	// static variable single_instance of type Singleton
	private static LoadConfigMapProp single_instance = null;
	private String configMapLoc = PlcfgConstants.CONFIG_MAP_PATH;
	private String dirName = PlcfgConstants.CONFIG_MAP_DIR_NAME;
	protected Log log = LogFactory.getLog(this.getClass().getName());

	/**
	 *
	 * Purpose: private constructor to make it singleton
	 *
	 * Date: Feb 2, 2021 4:16:18 PM 2021
	 * 
	 * US/D/F Number: F96456 :ATTPRE-96674
	 *
	 * @param result
	 * @param inputStream
	 */
	private LoadConfigMapProp() {
		super();
		try {
			Runnable run = () -> watchFile();
			loadProperties(configMapLoc);
			Executors.newSingleThreadExecutor().execute(run);
		} catch (IOException e) {
			log.error(e);
		}
	}

	// static method to create instance of Singleton class
	public static LoadConfigMapProp getInstance() {
		if (single_instance == null)
			single_instance = new LoadConfigMapProp();

		return single_instance;
	}

	/**
	 * 
	 * User: eznraps , Date: Feb 2, 2021 4:21:03 PM 2021
	 *
	 * Purpose: load properties
	 *
	 * US/D/F Number: F96456 :ATTPRE-96674
	 * 
	 * Return Type: void
	 *
	 * @throws IOException
	 */
	public void loadProperties(String propFileName) throws IOException {
		try (InputStream input = new FileInputStream(propFileName)) {
			Timestamp timestampNew = null;
			prop.load(input);
			log.debug("Properties loaded from " + propFileName);

			if (i > 0) {
				timestampNew = new Timestamp(System.currentTimeMillis());
			} else {
				timestamp = new Timestamp(System.currentTimeMillis());
				timestampNew = timestamp;
			}

			long milliseconds = timestampNew.getTime() - timestamp.getTime();
			int seconds = (int) milliseconds / 1000;

			int hours = seconds / 3600;
			int minutes = (seconds % 3600) / 60;
			seconds = (seconds % 3600) % 60;

			log.debug("ConfigMap file updated no:" + i + ", Since: OldTime: " + timestamp + " , NewTime: "
					+ timestampNew);
			log.debug("Diff in time: " + hours + "hrs, " + minutes + "minutes, " + seconds + "secs");
			log.debug("LoadConfigMapProp Property file updated content: " + prop);
			i = i + 1;
			timestamp = timestampNew;
		} catch (Exception e) {
			log.debug("Exception: " + e);
			log.error(e);
		}
	}

	/**
	 * 
	 * User: eznraps , Date: Feb 2, 2021 4:23:54 PM 2021
	 *
	 * Purpose: get properties value
	 *
	 * US/D/F Number: F96456 :ATTPRE-96674
	 * 
	 * Return Type: String
	 *
	 * @param propName
	 * @return
	 */
	public String getProp(String propName) {
		return prop.getProperty(propName) != null ? prop.getProperty(propName) : "null";
	}

	/**
	 * 
	 * User: eznraps , Date: Feb 2, 2021 5:20:57 PM 2021
	 *
	 * Purpose: Watch file for create, delete and modify
	 *
	 * US/D/F Number: F96456 :ATTPRE-96674
	 * 
	 * Return Type: void
	 *
	 */
	private void watchFile() {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get(dirName);
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

			log.debug("Watch Service registered for dir: " + dir.getFileName());

			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException ex) {
					log.debug("Exception : " + ex);
					Thread.currentThread().interrupt();
					return;
				}

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();

					log.debug("Watch service Event(Kind) Name >>>"+kind.name() + ": " + fileName);

					if (kind == ENTRY_MODIFY) {
						loadProperties(configMapLoc);
						log.debug(" The " + configMapLoc + " : file has changed!!!");
					}
				}

				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}

		} catch (IOException ex) {
			log.debug("Exception : " + ex);
			log.error(ex);
		}
	}
}
