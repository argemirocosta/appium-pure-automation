Feature: Cart

  Scenario: Add a product to the cart
    When the user adds the first product to the cart
    Then the cart contains 1 item
