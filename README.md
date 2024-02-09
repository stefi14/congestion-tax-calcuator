Welcome the Volvo Cars Congestion Tax Calculator assignment.

Use the IDE of your preference run the project and go to http://localhost:8080/swagger-ui/
and test congestion-tax-calculator-controller GET /congestiont/calculate tax click on it and then try it out with different inputs such as:
* 
* Car with city 1
{
"id": 1,
"lisencePlate": "1234",
"vehicleType": "car",
"city": 1,
"dates": [
"2013-02-08T12:43:58",
"2013-02-07T12:43:58",
"2013-02-08T13:23:58",
"2013-02-09T12:43:58"
]
}

* Car with city 2
{
"id": 1,
"lisencePlate": "1234",
"vehicleType": "car",
"city": 2,
"dates": [
"2013-02-08T12:43:58",
"2013-02-07T12:43:58",
"2013-02-08T13:23:58",
"2013-02-09T12:43:58"
]
}


{
"id": 1,
"lisencePlate": "45656",
"vehicleType": "motorcycle",
"city": 2,
"dates": [
"2013-02-08T12:43:58",
"2013-02-07T12:43:58",
"2013-02-08T13:23:58",
"2013-02-09T12:43:58"
]
}