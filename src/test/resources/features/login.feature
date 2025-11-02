# Feature: Login functionality
# This feature covers positive and negative login scenarios for the application.

Feature: User Login

  Background:
    Given the user is on the login page

  @smoke @positive
  Scenario: Successful login with valid credentials
    When the user enters valid username and password
    And clicks the login button
    Then the user should be redirected to the dashboard
    And a welcome message should be displayed

  @negative
  Scenario Outline: Unsuccessful login with invalid credentials
    When the user enters username "<username>" and password "<password>"
    And clicks the login button
    Then an error message "<error_message>" should be displayed

    Examples:
      | username       | password       | error_message                      |
      | invaliduser    | Invalid@1234   | Invalid username or password.     |
      | testuser       | wrongpassword  | Invalid username or password.     |
      |                | Test@1234      | Username cannot be empty.          |
      | testuser       |               | Password cannot be empty.          |
