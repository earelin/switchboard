@application
Feature: Query application list

  As an user
  I want to get the list of applications
  So that I can operate on those applications

Background:
  * url baseUrl
  * table applications
    | name                 | description                            |
    | 'Website'            | 'Public website'                       |
    | 'Mobile'             | 'Mobile application'                   |
    | 'Accounting'         | null                                   |
    | 'Content Management' | 'Content Management System'            |
    | 'Authoring'          | 'Content authoring application'        |
    | 'Mobile Authoring'   | 'Content authoring mobile application' |
    | 'Logging'            | null                                   |
    | 'Workflow Status'    | null                                   |
    | 'Project Management' | 'Project management tool'              |
    | 'Human Resources'    | 'Human Resources system'               |
  * callonce read('../utils/create-application.feature') applications
  * def applicationSchema =
    """
    {
      id: '#string',
      name: '#string',
      description: '##string',
      secret: '#string'
    }
    """

Scenario: Get a page of applications

  Given path '/rest/v1/application'
    And param page = 1
    And param size = 4
  When method get
  Then status 200
    And assert response.content.length == 4
    And match response ==
      """
      {
        content: '#[] applicationSchema',
        totalElements: '#number',
        totalPages: '#number',
        pageNumber: 1,
        pageSize: 4,
        first: false,
        last: false
      }
      """

Scenario: Get a sorted page of applications

  Given path '/rest/v1/application'
    And param page = 1
    And param size = 4
    And param sort = 'name:desc'
  When method get
  Then status 200
    And assert response.content.length == 4
    And match response ==
      """
      {
        content: '#[] applicationSchema',
        totalElements: '#number',
        totalPages: '#number',
        pageNumber: 1,
        pageSize: 4,
        first: false,
        last: false
      }
      """

Scenario: Get a sorted page of applications by an not existing property

  Given path '/rest/v1/application'
    And param page = 1
    And param size = 4
    And param sort = 'surname'
  When method get
  Then status 400

Scenario: Get an application page less than 0

  Given path '/rest/v1/application'
    And param page = -1
  When method get
  Then status 400

Scenario: Get an application size less than 1

  Given path '/rest/v1/application'
  And param size = 0
  When method get
  Then status 400
