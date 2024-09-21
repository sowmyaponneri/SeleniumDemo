
@tag
Feature: Purchase the order from Eccomerce website
  
  Background:
  Given I landed on Eccomerce Page
  @tag2
  Scenario Outline: Positive test of submitting the order
    Given Logged in with username <name> and password <password>
    When I add the product <productName> to Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed in ConfirmationPage

    Examples: 
      | name            | password      | productName |
      | ramso@gmail.com | Dec16mar12bm@ | ZARA COAT 3 |
      