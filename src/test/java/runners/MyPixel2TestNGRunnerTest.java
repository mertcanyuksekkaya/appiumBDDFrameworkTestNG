package runners;

import io.cucumber.testng.CucumberOptions;



@CucumberOptions(
        plugin = {"pretty"
        , "html:target/PIXEL_2/cucumber"
        , "summary"
        , "de.monochromata.cucumber.report.PrettyReports:target/device-reports/PIXEL_2"}
        ,features = {"src/test/resources"}
        ,glue = {"stepDefinitions"}
        ,dryRun=false
        ,monochrome=true
        ,strict=true
        ,tags = {"@test"}
        )
public class MyPixel2TestNGRunnerTest extends RunnerBase {


}