package stepDefinitions;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.eo.Se;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.OutputType;
import utils.DriverManager;
import utils.GlobalParams;
import utils.ServerManager;
import utils.VideoManager;

import java.io.IOException;
import java.sql.Driver;

public class Hooks {

    @Before
    public void initialize(Scenario scenario) throws Exception {
//        GlobalParams params = new GlobalParams();
//        params.initializeGlobalParams();
//        ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_"
//                + params.getDeviceName());
//        new ServerManager().startServer();
//        new DriverManager().initializeDriver();
        new VideoManager().startRecording();
    }

    @After
    public void quit(Scenario scenario) throws IOException {
        if(scenario.isFailed()){
            byte[] screenshot = new DriverManager().getDriver().getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png", scenario.getName());
        }
        new VideoManager().stopRecording(scenario.getName());
    }
}
