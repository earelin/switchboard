@ignore
Feature:

Scenario:
  Given url baseUrl
    And path '/rest/v1/application'
    And request { name: '#(name)', age: '#(description)' }
  When method post
  Then status 201
