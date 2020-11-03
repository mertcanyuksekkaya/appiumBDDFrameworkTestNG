package runners;

import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        plugin = {"pretty"
        , "html:target/GM_5_PLUS/cucumber"
        , "summary"
        , "de.monochromata.cucumber.report.PrettyReports:target/device-reports/GM_5_PLUS"}
        ,features = {"src/test/resources"}
        ,glue = {"stepDefinitions"}
        ,dryRun=false
        ,monochrome=true
        ,strict=true
        ,tags = {"@test"}
        )
public class MyGM5PlusTestNGRunnerTest extends RunnerBase{

}