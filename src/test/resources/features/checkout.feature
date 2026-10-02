Feature: Checkout

  Scenario: Complete a purchase
    Given the user adds the first product to the cart
    When the user checks out with valid shipping and payment details
    Then the order is confirmed
