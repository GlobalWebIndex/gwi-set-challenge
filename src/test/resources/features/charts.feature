Feature: Charts page functionality

  This feature contains tests for the UI testing of the Landing page of Charts application

  @UI
  Scenario: Validate Dashboard order options
    Given I have a charts set
    When I am in the landing page of the application
    Then I validate that all charts in the dashboard are displayed by NAME order
    When I select create chart button
    Then I validate that user is redirected to create chart page
    When I click the back button
    And I select the CREATED sorting option
    Then I validate that all charts in the dashboard are displayed by CREATED order
    When I select the MODIFIED sorting option
    Then I validate that all charts in the dashboard are displayed by MODIFIED order
    When I select the NAME sorting option
    Then I validate that all charts in the dashboard are displayed by NAME order

  @UI
  Scenario: Validate Dashboard Search option
    Given I have a charts set
    When I am in the landing page of the application
    Then I type "C" at the search option and validate that all the charts that match this pattern are displayed
    Then I clear the field
    Then I type "c" at the search option and validate that all the charts that match this pattern are displayed
    Then I type "hart " at the search option and validate that all the charts that match this pattern are displayed
    Then I type "1" at the search option and validate that all the charts that match this pattern are displayed
    Then I clear the field
    Then I type "z" at the search option and validate that no charts are displayed

  @API
  Scenario Outline: Validate get chart API
    Given I have a charts set
    When I create a get charts request
      | orderBy | <orderBy> |
      | order   | <order>   |
    Then I validate that the data retrieved for sorting options <order1>,<order2> are correctly sorted

    Examples:
      | orderBy       | order         | order1   | order2 |
      | FIELD_MISSING | FIELD_MISSING | NAME     | ASC    |
      | _             | FIELD_MISSING | NAME     | ASC    |
      | NULL          | FIELD_MISSING | NAME     | ASC    |
      | FIELD_MISSING | NULL          | NAME     | ASC   |
      | FIELD_MISSING | _             | NAME     | ASC    |
      | _             | _             | NAME     | ASC   |
      | FIELD_MISSING | asc           | NAME     | ASC    |
      | FIELD_MISSING | desc          | NAME     | DESC   |
      | name          | FIELD_MISSING | NAME     | ASC    |
      | name          | asc           | NAME     | ASC    |
      | name          | desc          | NAME     | DESC   |
      | dateCreated   | FIELD_MISSING | CREATED  | ASC    |
      | dateCreated   | asc           | CREATED  | ASC    |
      | dateCreated   | desc          | CREATED  | DESC   |
      | dateModified  | FIELD_MISSING | MODIFIED | DESC   |
      | dateModified  | asc           | MODIFIED | ASC    |
      | dateModified  | desc          | MODIFIED | DESC   |

  @API
  Scenario Outline: Validate get chart API error messages
    Given I have a charts set
    When I create a get charts request
      | orderBy | <orderBy> |
      | order   | <order>   |
    Then I validate that request fails with status <statusCode>
    Then I validate that error <error message> is displayed

    Examples:
      | orderBy       | order         | statusCode | error message                        |
      | FIELD_MISSING | ABCBD         | 400        | Please check your request parameters |
      | name          | ABCFC         | 400        | Please check your request parameters |
      | ABCDB         | FIELD_MISSING | 400        | Please check your request parameters |
      | ABCDH         | asc           | 400        | Please check your request parameters |
