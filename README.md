# RequestbinTest
Test project for https://github.com/bozarslan/Requestbin

# Test Framework Structure

The tests are implemented to be executed against Requestbin service running on https://floating-brushlands-81939.herokuapp.com/ 


Objects that RequestBin service uses are implemented as DTOs which are located under src/main/java/dto for
  - better code readability
  - easier test implementation as no need to deal with Json strings and the interaction will be done with Java objects only
    - Jackson is used to serialize/disserialize Java objects to/from Json strings

The client that calls the apis of RequestBin service is implemented in a service class which is src/main/java/service/BinService.java
  - better code readability
  - responsibility of calling the apis is extracted from the test methods/classes
  - minimize code duplication
    - this is something which can be improved as there is duplicated code within BinService as well
  - easier to maintain in case there is any change in api signitures
  - RestAssured is used as the test library to send requests and parse responses to/from Requestbin apis
  
There is only one test class implemented at the moment which is src/test/java/TestWebhooks.java that checks if webhooks are received successfully when the webhook url is called and the request content is saved correctly
  - The scenario assumed while designing this scenario is as followed:
  Given a Stuart customer Buse's Flower Shop want to send a delivery to it's customer Sandra
  And delivery has the information of sender as Buse's Flower Shop, receiver as Sandra, address as Sandra's address
  When Buse's Flower Shop sends her request to Stuart using RequestBin service by calling its webhook url
  Then RequestBin service receives Buse's Flower Shop request and saves the request and delivery information to be processes by Stuart's other services
  
  - Test steps implemented are as followed:
    1. Create a new bin in BeforeEach
        - This step can even be moved to BeforeAll as creating one bin would be sufficient for testing all the apis of Requestbin service and bin creation is not really the test
      goal as we want to verify the webhook functionality
    2. Send a webhook to the bin with content having delivery information
        - Deliver information is sent as body of the request but could be sent in the url params as well. As the requirement was not specified, I selected using body
    3. Get the list of requests of the bin and verify if the last webhook is received
    4. Get the request that is sent and verify the content to see if delivery information is saved
   - Java is used as the programming language and JUnit5 is used as a test framework
   
   # To Run the Test
   
   Gradle is used as a build tool for the project.
    - Clone the project
    - Build the project with gradle
    - Run TestWebhooks.java
    
   Missing GitHub Actions setup to run the test as part of a CI/CD pipeline.
