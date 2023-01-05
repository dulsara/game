# game
execute below command
/mvnw spring-boot:run or you can just run in your IDE default
app runs on localhost:8080/

One API end point for processing user bet and receive the response with Bet details and winning value

Request Type - GET

Request End Point - localhost:8080/api/bet

Request Body -

{
"number":"90",
"bet":"10"
}

Response Body - 

{
"number": 90,
"bet": 10.0,
"winValue": 99.0
}

If user input wrong request values, Response body can be as below

{
"timestamp": "2023-01-04T23:08:12.661619Z",
"message": "Bet Number should be in 1 - 100 range",
"details": "uri=/api/bet"
}

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

# Million Time Executions Test

Below Test class has been written for testing 1 million bet executions and calculate RTP value

game/src/test/java/com/dulsara/game/bet/execution/ExecutionTest.java

you can just run the test class and see the RTP value after the test execution as below


****** Total Bet : 499585.96 ******

******Total Win : 1649115.02 ******

****** RTP : 330.10% ******