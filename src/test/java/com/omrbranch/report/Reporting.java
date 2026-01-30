package com.omrbranch.report;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.omrbranch.utility.BaseClass;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

/**
 * =============================================================== Class Name :
 * Reporting Project : OMR Branch API Automation Framework
 *
 * Purpose: - Generates Masterthought JVM HTML report from Cucumber JSON output.
 * - Reads report paths, metadata, and environment from API Config.properties.
 *
 * Required Config Keys (API): - jsonpath -> target/Output.json - jvmPath ->
 * target - projectName -> OMR Branch API Automation - reportAuthor ->
 * Velmurugan - env -> dev / qa / uat / prod
 *
 * Author : Velmurugan
 * ===============================================================
 */
public class Reporting extends BaseClass {

  private static final Logger logger = LogManager.getLogger(Reporting.class);

  /**
   * Generates JVM HTML report using Masterthought cucumber-reporting.
   *
   * @param jsonFileReport JSON file path (optional). If null/empty, uses
   *                       Config.properties key: jsonpath
   */
  public static void generateJVMReport(String jsonFileReport) {

    logger.info("Starting JVM Report Generation for API Automation...");

    // -----------------------------------------------------------
    // 1) Resolve JSON report path (priority: param -> config)
    // -----------------------------------------------------------
    if (jsonFileReport == null || jsonFileReport.trim().isEmpty()) {
      jsonFileReport = getProjectPath() + File.separator + getJsonOutputPath();
      logger.info("Using JSON report path from config: {}", jsonFileReport);
    } else {
      logger.info("Using JSON report path from parameter: {}", jsonFileReport);
    }

    // -----------------------------------------------------------
    // 2) Resolve JVM report output directory
    // -----------------------------------------------------------
    String jvmBasePath = getProjectPath() + File.separator + getJvmReportPath();

    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    File reportOutputDir = new File(jvmBasePath + File.separator + "JVM_Report_" + timeStamp);

    if (!reportOutputDir.exists()) {
      reportOutputDir.mkdirs();
      logger.info("Created report directory: {}", reportOutputDir.getAbsolutePath());
    }

    // -----------------------------------------------------------
    // 3) Metadata from Config.properties
    // -----------------------------------------------------------
    String projectName = getProperty("projectName");
    if (projectName == null || projectName.trim().isEmpty()) {
      projectName = "OMR Branch API Automation";
    }

    String author = getProperty("reportAuthor");
    if (author == null || author.trim().isEmpty()) {
      author = "Velmurugan";
    }

    String environment = getEnv(); // dev / qa / uat / prod

    // -----------------------------------------------------------
    // 4) Masterthought Configuration + Classifications
    // -----------------------------------------------------------
    Configuration config = new Configuration(reportOutputDir, projectName);

    config.addClassifications("Project", projectName);
    config.addClassifications("Author", author);
    config.addClassifications("Environment", environment);
    config.addClassifications("Platform", System.getProperty("os.name"));
    config.addClassifications("Java Version", System.getProperty("java.version"));
    config.addClassifications("Execution Time", timeStamp);

    // -----------------------------------------------------------
    // 5) Build report
    // -----------------------------------------------------------
    List<String> jsonFiles = new ArrayList<>();
    jsonFiles.add(jsonFileReport.trim());

    logger.info("Generating JVM report for JSON file(s): {}", jsonFiles);

    ReportBuilder builder = new ReportBuilder(jsonFiles, config);
    builder.generateReports();

    // -----------------------------------------------------------
    // 6) Summary logs
    // -----------------------------------------------------------
    logger.info("------------------------------------------------------------");
    logger.info("JVM Report Generated Successfully!");
    logger.info("Location : {}", reportOutputDir.getAbsolutePath());
    logger.info("Project  : {}", projectName);
    logger.info("Author   : {}", author);
    logger.info("Env      : {}", environment);
    logger.info("------------------------------------------------------------");
  }
}
