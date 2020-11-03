package runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.*;
import utils.DriverManager;
import utils.GlobalParams;
import utils.ServerManager;


@CucumberOptions(
        plugin = {"pretty"
        , "html:target/Pixel4/cucumber"
        , "summary"
        , "de.monochromata.cucumber.report.PrettyReports:target/Pixel4/cucumber-html-reports"}
        ,features = {"src/test/resources"}
        ,glue = {"stepDefinitions"}
        ,dryRun=false
        ,monochrome=true
        ,strict=true
        ,tags = {"@test"}
        )
public class MyPixel4TestNGRunnerTest{

        private TestNGCucumberRunner testNGCucumberRunner;

        @Parameters({"platformName", "udid", "deviceName", "systemPort",
                "chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort"})
        @BeforeClass(alwaysRun = true)
        public void setUpClass(String platformName,
                               String udid,
                               String deviceName,
                               @Optional("Android") String systemPort,
                               @Optional("Android") String chromeDriverPort,
                               @Optional("iOS") String wdaLocalPort,
                               @Optional("iOS") String webkitDebugProxyPort) throws Exception {

                ThreadContext.put("ROUTINGKEY", platformName + "_" + deviceName);

                GlobalParams globalParams = new GlobalParams();
                globalParams.setPlatformName(platformName);
                globalParams.setUDID(udid);
                globalParams.setDeviceName(deviceName);
                switch (platformName){
                        case "Android":
                                globalParams.setSystemPort(systemPort);
                                globalParams.setChromeDriverPort(chromeDriverPort);
                                break;
                        case "IOS":
                                globalParams.setWdaLocalPort(wdaLocalPort);
                                globalParams.setWebkitDebugProxyPort(webkitDebugProxyPort);
                                break;
                }
                new ServerManager().startServer();
                new DriverManager().initializeDriver();
                testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        }

        @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
        public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) throws Throwable {
                testNGCucumberRunner.runScenario(pickle.getPickle());
        }

        @DataProvider
        public Object[][] scenarios() {
                return testNGCucumberRunner.provideScenarios();
        }

        @AfterClass(alwaysRun = true)
        public void tearDownClass() {
                DriverManager driverManager = new DriverManager();
                if(driverManager.getDriver() != null){
                        driverManager.getDriver().quit();
                        driverManager.setDriver(null);
                }
                ServerManager serverManager = new ServerManager();
                if (serverManager.getServer() != null) {
                        serverManager.getServer().stop();
                }
                if (testNGCucumberRunner != null){
                        testNGCucumberRunner.finish();
                }
        }
}