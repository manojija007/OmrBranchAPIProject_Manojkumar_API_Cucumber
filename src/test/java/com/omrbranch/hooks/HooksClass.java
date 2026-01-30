package com.omrbranch.hooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.omrbranch.utility.BaseClass;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class HooksClass extends BaseClass {

  private static final Logger logger = LogManager.getLogger(HooksClass.class);

  // =====================================================
  // BEFORE EACH SCENARIO
  // =====================================================
  @Before
  public void beforeScenario(Scenario scenario) {

    logger.info("----------------------------------------------------");
    logger.info("STARTING SCENARIO : {}", scenario.getName());
    logger.info("TAGS              : {}", scenario.getSourceTagNames());
    logger.info("----------------------------------------------------");

    // Reset request & response
    clear();
  }

  // =====================================================
  // AFTER EACH SCENARIO
  // =====================================================
  @After
  public void afterScenario(Scenario scenario) {

    if (scenario.isFailed()) {
      logger.error("SCENARIO FAILED : {}", scenario.getName());

      // Log response if available
      if (response != null) {
        response.then().log().all();
      }
    } else {
      logger.info("SCENARIO PASSED : {}", scenario.getName());
    }

    logger.info("----------------------------------------------------");
  }
}
