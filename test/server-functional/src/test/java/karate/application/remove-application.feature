@application
Feature: Remove an application

  As an user
  I want to remove applications
  So that I can clean up deprecated software

Background:
  * url baseUrl

Scenario: Remove an existing application

  Given path '/rest/v1/application'
    And request { name: 'Testing application' }
  When method post
  Then status 201

  * def id = response.id

  Given path '/rest/v1/application/' + id
  When method delete
  Then status 200

  Given path '/rest/v1/application/' + id
  When method get
  Then status 404

Scenario: Remove a non existing application

  Given path '/rest/v1/application/no-existing-app'
  When method delete
  Then status 404
