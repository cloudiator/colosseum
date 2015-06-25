# TimeWindow Actions
***

##Description
The TimeWindow entity represents the TimeWindow.

## GET /api/timeWindow

###Description
Returns a list of TimeWindow types supported by the system.

###Request Parameters
None

###Response
A list of all timeWindow entities stored in the database.

###Response Example
```
[
    {
        "interval":5,
        "timeUnit":"MINUTES",
        "link":
        [
            {
                "href":"http://localhost:9000/api/timeWindow/1",
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

## GET /api/timeWindow/{timeWindow_id}

###Description

Returns the TimeWindow entity identified by the given {timeWindow_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
timeWindow_id      | The id of the timeWindow.

###Response 
The TimeWindow entity identified by the given id.

###Response Example
```
{
    "interval":5,
    "timeUnit":"MINUTES",
    "link":
    [
        {
            "href":"http://localhost:9000/api/timeWindow/1",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/timeWindow

###Description

Creates a new TimeWindow entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
interval      | Amount of time units.
timeUnit      | The actual unit.

###Request Example
```
{
    "interval":5,
    "timeUnit":"MINUTES"
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/timeWindow/{timeWindow_id}

###Description

Updates the TimeWindow entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
timeWindow_id      | The id of the timeWindow to update.
interval           | Amount of time units.
timeUnit           | The actual unit.

###Request Example
```
PUT /api/timeWindow/1
```
```
{
    "interval":5,
    "timeUnit":"MINUTES"
}
```

###Response

The updated entity. See GET /api/timeWindow/{timeWindow_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/timeWindow/{timeWindow_id}

###Description

Deletes the TimeWindow entity identified by the given {timeWindow_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
timeWindow_id      | The id of the timeWindow to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
