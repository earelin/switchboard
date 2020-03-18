@application
Feature: Create an application

  As an user
  I want to create application
  So that I can define its feature flags

Background:
  * url baseUrl

Scenario: Create an application with name and description

  Given path '/rest/v1/application'
    And request { name: 'Website', description: 'Public website' }
  When method post
  Then status 201
    And match response == { id: '#string', name: 'Website', description: 'Public website', secret: '#string' }

  * def id = response.id
  * def secret = response.secret

  Given path '/rest/v1/application/' + id
  When method get
  Then status 200
    And match response == { id: '#(id)', name: 'Website', description: 'Public website', secret: '#(secret)' }

Scenario: Creating an application with empty name

  Given path '/rest/v1/application'
    And request { name: '   ' }
  When method post
  Then status 400

Scenario: Creating an application without name

  Given path '/rest/v1/application'
    And request { }
  When method post
  Then status 400
