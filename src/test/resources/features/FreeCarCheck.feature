Feature: Verifying the vehicle registration

  Scenario: Validate the car registrations with input and output files
    Given User read the input files
      | ./src/test/resources/testInput/car_input.txt |
    When User extracted vehicle registration numbers based on patterns
    Then User compares results of registration numbers from "https://cartaxcheck.co.uk/" with output files
      | ./src/test/resources/testOutput/car_output.txt |