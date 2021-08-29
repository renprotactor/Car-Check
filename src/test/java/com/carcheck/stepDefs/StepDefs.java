package com.carcheck.stepDefs;

import com.carcheck.config.FileParser;
import com.carcheck.pages.Car;
import com.carcheck.pages.CarDetailsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.testng.asserts.SoftAssert;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StepDefs {


    CarDetailsPage carDetailsPage=new CarDetailsPage();
    List<String> inputFiles, outputFiles;
    List<String> allRegNos = new ArrayList<>();
    List<Car> cars = new ArrayList<>();


    @Given("User read the input files")
    public void user_read_the_input_files(io.cucumber.datatable.DataTable file) {
        inputFiles = file.asList();
        System.out.println(inputFiles);
    }

    @When("User extracted vehicle registration numbers based on patterns")
    public void user_extracted_vehicle_registration_numbers_based_on_patterns() {
        inputFiles.forEach(filePath -> {
            FileParser fileParser = new FileParser(filePath);
            List<String> regNos = fileParser.getRegNos();
            if (!regNos.isEmpty()) {
                allRegNos.addAll(regNos);
            }
        });
    }


    @Then("User compares results of registration numbers from {string} with output files")
    public void user_compares_results_of_registration_numbers_from_with_output_files(String url,DataTable file) {
        outputFiles = file.asList();
        outputFiles.forEach(filePath -> {
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

//            carDetailsPage.openURL("https://cartaxcheck.co.uk/free-car-check/?vrm=DN09HRM");
            carDetailsPage.openURL(url + "free-car-check/?vrm=" + registrationNo);
            Car actualCarDetails = carDetailsPage.getCarDetails();
            Car expectedCarDetails = cars.stream().filter(car -> car.getRegNo().equals(registrationNo)).findFirst().orElse(null);
            if (actualCarDetails == null) {
                softAssert.assertTrue(false, "Car registration no " + registrationNo + " not found at " + url);
            } else if (expectedCarDetails == null) {
                softAssert.assertTrue(false, "Car registration no " + registrationNo + " not found in output file");
            } else {
                //Assert Make
                softAssert.assertEquals(actualCarDetails.getMake(), expectedCarDetails.getMake(), "Make didn't matched for the car number " + registrationNo);
                //Assert Model
                softAssert.assertEquals(actualCarDetails.getModel(), expectedCarDetails.getModel(), "Model didn't matched for the car number " + registrationNo);
                //Assert Color
                softAssert.assertEquals(actualCarDetails.getColor(), expectedCarDetails.getColor(), "Color didn't matched for the car number " + registrationNo);
                //Assert Year
                softAssert.assertEquals(actualCarDetails.getYear(), expectedCarDetails.getYear(), "Year didn't matched for the car number " + registrationNo);
            }
        });


    }


}
