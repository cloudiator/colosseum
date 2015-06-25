# RawMonitor Actions
***

##Description
The RawMonitor entity represents the raw monitors.

## GET /api/rawMonitor

###Description
Returns a list of raw monitor types supported by the system.

###Request Parameters
None

###Response
A list of all raw monitor entities stored in the database.

###Response Example
```
[
    {
        "application":1,
        "component":1,
        "componentInstance":null,
        "cloud":null,
        "sensorDescription":1,
        "schedule":2,
        "link":
        [
            {
                "href":"http://localhost:9000/api/rawMonitor/1",
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

## GET /api/rawMonitor/{rawMonitor_id}

###Description

Returns the Cloud entity identified by the given {rawMonitor_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
rawMonitor_id      | The id of the raw monitor.

###Response 
The raw monitor entity identified by the given id.

###Response Example
```
{
    "application":1,
    "component":1,
    "componentInstance":null,
    "cloud":null,
    "sensorDescription":1,
    "schedule":2,
    "link":
    [
        {
            "href":"http://localhost:9000/api/rawMonitor/1",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/rawMonitor

###Description

Creates a new RawMonitor entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
rawMonitor_id | The id of the raw monitor.
application | The id of application that is defined as filter.
component | The id of the component that is defined as filter.
componentInstance | The id of the componentInstance that is defined as filter.
cloud | The id of the cloud that is defined as filter.
sensorDescription | The id of the sensor description.
schedule | The id of the schedule.

###Request Example
```
{
    "application":1,
    "component":1,
    "componentInstance":null,
    "cloud":null,
    "sensorDescription":1,
    "schedule":2
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/rawMonitor/{rawMonitor_id}

###Description

Updates the Cloud entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
application | The id of application that is defined as filter.
component | The id of the component that is defined as filter.
componentInstance | The id of the componentInstance that is defined as filter.
cloud | The id of the cloud that is defined as filter.
sensorDescription | The id of the sensor description.
schedule | The id of the schedule.

###Request Example
```
PUT /api/rawMonitor/1
```
```
{
    "application":1,
    "component":1,
    "componentInstance":null,
    "cloud":null,
    "sensorDescription":1,
    "schedule":2
}
```

###Response

The updated entity. See GET /api/rawMonitor/{rawMonitor_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/cloud/{rawMonitor_id}

###Description

Deletes the RawMonitor entity identified by the given {rawMonitor_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
cloud_id      | The id of the cloud to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
