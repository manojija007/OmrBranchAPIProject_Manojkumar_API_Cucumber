package com.omrbranch.runner;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.omrbranch.report.Reporting;
import com.omrbranch.utility.BaseClass;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

/**
 * =============================================================== Class Name :
 * TestRunnerClass Project : OMR Branch API Automation Framework
 *
 * Purpose: - Executes Cucumber scenarios based on tags - Generates JSON report
 * - Triggers JVM report generation after execution
 *
 * Author : Velmurugan
 * ===============================================================
 */
		@RunWith(Cucumber.class)
		@CucumberOptions(
		features = "src/test/resources/features",
		glue = { "com.omrbranch.stepdefinition", "com.omrbranch.hooks" },
		tags = "@regression",
		snippets = SnippetType.CAMELCASE, 
		dryRun = false,
		monochrome = false, 
		publish = true, 
		stepNotifications = true, 
		plugin = {"pretty", "json:target/Output.json" }
		)
		
public class TestRunnerClass extends BaseClass {

  /** Logger for Runner-level logging */
  private static final Logger logger = LogManager.getLogger(TestRunnerClass.class);

  /**
   * Generates JVM report after all scenarios execution.
   */
  @AfterClass
  public static void afterClass() throws FileNotFoundException, IOException {

    logger.info("Cucumber execution completed. Generating JVM report...");

    // jsonpath is read from Config.properties → target/Output.json
    String jsonReportPath = getProjectPath() + File.separator + getProperty("jsonpath");

    Reporting.generateJVMReport(jsonReportPath);

    logger.info("JVM report generated successfully at: {}", jsonReportPath);
  }
}
