@application
Feature: Update an application name and description

As an user
I want to update application name and description
So that I can update the application information

Background:
  * url baseUrl

Scenario: Update application name and description

  Given path '/rest/v1/application'
    And request { name: 'Some application' }
  When method post
  Then status 201

  * def id = response.id

  Given path '/rest/v1/application/' + id
    And request { name: 'Updated name', description: 'Updated description' }
  When method put
  Then status 200
    And match response ==
      """
      {
        id: '#(id)',
        name: 'Updated name',
        description: 'Updated description',
        secret: '#string'
      }
      """

  Given path '/rest/v1/application/' + id
  When method get
  Then status 200
    And match response ==
      """
      {
        id: '#(id)',
        name: 'Updated name',
        description: 'Updated description',
        secret: '#string'
      }
      """

Scenario: Update application with an empty name

  Given path '/rest/v1/application'
    And request { name: 'Some application' }
  When method post
  Then status 201

  * def id = response.id

  Given path '/rest/v1/application/' + id
    And request { name: '   ' }
  When method put
  Then status 400

Scenario: Update a non existing application

  Given path '/rest/v1/application/not-existing-app'
    And request { name: 'Updated name', description: 'Updated description' }
  When method put
  Then status 404
