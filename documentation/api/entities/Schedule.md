# Schedule Actions
***

##Description
The Schedule entity represents a schedule.

## GET /api/schedule

###Description
Returns a list of Schedule types supported by the system.

###Request Parameters
None

###Response
A list of all schedule entities stored in the database.

###Response Example
```
[
    {
        "interval":1,
        "timeUnit":"MINUTES",
        "link":
        [
            {
                "href":"http://localhost:9000/api/schedule/1",
                "rel":"self"
            }
        ]
    }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/schedule/{schedule_id}

###Description

Returns the Schedule entity identified by the given {schedule_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
schedule_id      | The id of the Schedule.

###Response 
The Cloud entity identified by the given id.

###Response Example
```
{
    "interval":1,
    "timeUnit":"MINUTES",
    "link":
    [
        {
            "href":"http://localhost:9000/api/schedule/1",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/schedule

###Description

Creates a new Schedule entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
interval      | The amount of time units.
timeUnit      | The actual unit.

###Request Example
```
{
    "interval":1,
    "timeUnit":"MINUTES"
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/schedule/{schedule_id}

###Description

Updates the Cloud entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
schedule_id      | The id of the schedule to update.
interval      | The amount of time units.
timeUnit      | The actual unit.

###Request Example
```
PUT /api/schedule/1
```
```
{
    "interval":1,
    "timeUnit":"MINUTES"
}
```

###Response

The updated entity. See GET /api/schedule/{schedule_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/schedule/{schedule_id}

###Description

Deletes the Cloud entity identified by the given {Schedule_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
schedule_id   | The id of the schedule to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
