@orderCreation
@orders
@severity=critical
Feature: Order creation

  Background:
    Given School library service is up and running

  Scenario: Successful order creation by adding one book to one person
    Given book "War and peace" with ISBN "978-3-16-148410-0" exists
    And schoolkid Andrey Volkov exists
    When adding a book "War and peace" for order for Andrey Volkov and it is taken until "2020-02-12"
    Then order is successfully created