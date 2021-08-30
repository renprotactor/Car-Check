package com.carcheck.stepDefs;

import com.carcheck.config.FileParser;
import com.carcheck.pages.Car;
import com.carcheck.pages.CarDetailsPage;
import com.carcheck.utils.BasePage;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StepDefs {
    CarDetailsPage carDetailsPage = new CarDetailsPage();
    List<String> inputFile, outputFile;
    List<String> allRegNos = new ArrayList<>();
    List<Car> cars = new ArrayList<>();

    @Given("User read the input file")
    public void user_read_the_input_file(DataTable inputFile) {
        this.inputFile = inputFile.asList();
    }

    @When("User extracts vehicle registration numbers from the input file")
    public void user_extracts_vehicle_registration_numbers_from_the_input_file() {
        inputFile.forEach(filePath -> {
            FileParser fileParser = new FileParser(filePath);
            List<String> regNos = fileParser.getRegNos();
            if (!regNos.isEmpty()) {
                allRegNos.addAll(regNos);
            }
        });
    }

    @Then("User compares results of registration numbers from {string} against the  output file")
    public void user_compares_results_of_registration_numbers_from_against_the_output_file(String url, DataTable outputFile) {
        this.outputFile = outputFile.asList();
        this.outputFile.forEach(filePath -> {
            FileParser fileParser = new FileParser(filePath);
            List<String> vehicleDetails = fileParser.getLines();
            if (!vehicleDetails.isEmpty()) {
                vehicleDetails.remove(0);
                vehicleDetails.forEach(vehicle -> {
                    List<String> details = Arrays.asList(vehicle.split(","));
                    Car car = new Car(details.get(0), details.get(1), details.get(2),
                            details.get(3), details.get(4));
                    cars.add(car);
                });
            }
        });

        SoftAssert softAssert = new SoftAssert();
        allRegNos.forEach(regNo -> {

            String registrationNo = regNo.replaceAll("\\s", "");
            carDetailsPage.openURL(url + "free-car-check/?vrm=" + registrationNo);
            Car actualCarDetails = carDetailsPage.getCarDetails();
            Car expectedCarDetails = cars.stream().filter(car -> car.getRegNo().equals(registrationNo)).findFirst().orElse(null);
            if (actualCarDetails == null) {
                softAssert.assertTrue(false, "Car registration no " + registrationNo + " not found at " + url);
            } else if (expectedCarDetails == null) {
                softAssert.assertTrue(false, "Car registration no " + registrationNo + " not found in the output file");
            } else {
                //Asserting Make
                softAssert.assertEquals(actualCarDetails.getMake(), expectedCarDetails.getMake(), "Make did not match for the car registration number " + registrationNo);
                //Asserting Model
                softAssert.assertEquals(actualCarDetails.getModel(), expectedCarDetails.getModel(), "Model did not match for the car registration number " + registrationNo);
                //Asserting Color
                softAssert.assertEquals(actualCarDetails.getColor(), expectedCarDetails.getColor(), "Color did not match for the car registration number " + registrationNo);
                //Asserting Year
                softAssert.assertEquals(actualCarDetails.getYear(), expectedCarDetails.getYear(), "Year did not match for the car registration number " + registrationNo);
            }
        });
        softAssert.assertAll();
    }

    @After
    public void tearDownStep() {
        if (BasePage.driver != null) {
            BasePage.driver.close();
            BasePage.driver.quit();
        }
    }
}
