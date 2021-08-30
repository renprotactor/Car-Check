Feature: Retrieve the car details using vehicle registration

  Scenario: Validate the car details from site using input and output files
    Given User read the input file
      | ./src/test/resources/testInput/car_input.txt |
    When User extracts vehicle registration numbers from the input file
    Then User compares results of registration numbers from "https://cartaxcheck.co.uk/" against the  output file
      | ./src/test/resources/testOutput/car_output.txt |