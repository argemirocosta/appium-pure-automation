Feature: Login

  Scenario: Log in with valid credentials
    When the user logs in with valid credentials
    Then the product catalog is displayed
