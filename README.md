# Projector-Management-System

## [API Documentation](https://app.swaggerhub.com/apis-docs/personal488/Projectors/1.0.0#/) is put on SwaggerHub now.

### Brief introduction of the project
Hi, this is a projector reservation system. Now currently, we support four basic operations: 
1. get information of all projectors we have

2. Reserve a projector during certain period
For example, if you send a post request and request body is
```json
{
    "date": "2018-11-11",
    "startTimeInDay": "10:00:00",
    "endTimeInDay": "11:00:00"
}
```
If there is a projector available During the start time and end time, then a reservation details will be returned, for example
```json
{
  "id": 1,
  "projector": {
    "id": 1,
    "name": "P1"
  },
  "duration": {
    "startTime": "2018-11-11T18:00:00.000+0000",
    "endTime": "2018-11-11T19:00:00.000+0000",
    "localStartTime": "2018-11-11 10:00:00",
    "localEndTime": "2018-11-11 11:00:00"
  }
}
```
But if there is no projector available during that time, return all other available durations on the same day and next day.
```json
[
    {
        "startTime": "2018-11-11T08:00:00.000+0000",
        "endTime": "2018-11-11T18:00:00.000+0000",
        "localStartTime": "2018-11-11 00:00:00",
        "localEndTime": "2018-11-11 10:00:00"
    },
    {
        "startTime": "2018-11-11T19:00:00.000+0000",
        "endTime": "2018-11-13T07:59:59.000+0000",
        "localStartTime": "2018-11-11 11:00:00",
        "localEndTime": "2018-11-12 23:59:59"
    }
]
```
If there is no projector can be reserved in these two days, then an error message will be returned. 

3. Get the details of a reservation

4. Cancel the reservation.

### Implementation details
Written in Java (Spring Boot).
Currently the data is stored in the memory (I use HashMaps for storing and retrieving data), no database is used so DAO is not implemented. But in future, the database can be added based on project needs.