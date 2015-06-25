# ComposedMonitor Actions
***

##Description
The ComposedMonitor entity represents a Monitor that is aggregated from other monitors.

## GET /api/composedMonitor

###Description
Returns a list of ComposedMonitor types supported by the system.

###Request Parameters
None

###Response
A list of all ComposedMonitor entities stored in the database.

###Response Example
```
[
    {
        "flowOperator":"MAP",
        "function":"AVG",
        "quantifier":1,
        "schedule":1,
        "window":1,
        "monitors":[1],
        "scalingActions":[131073],
        "link":
        [
            {
                "href":"http://localhost:9000/api/composedMonitor/8",
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

## GET /api/composedMonitor/{id}

###Description

Returns the ComposedMonitor entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the ComposedMonitor.

###Response 
The ComposedMonitor entity identified by the given id.

###Response Example
```
{
    "flowOperator":"MAP",
    "function":"AVG",
    "quantifier":1,
    "schedule":1,
    "window":1,
    "monitors":[1],
    "scalingActions":[131073],
    "link":
    [
        {
            "href":"http://localhost:9000/api/composedMonitor/8",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/composedMonitor

###Description

Creates a new ComposedMonitor entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
flowOperator | The Flow Operator for the monitor. Values: [MAP, REDUCE]
function    | The function for the monitor. Values: [AVG, SUM,...]
quantifier | The id of the Formula Quantifier.
schedule | The id of the schedule.
window | The id of the window.
monitors | A list of ids of the scaling monitors.
scalingActions | A list of ids of the scaling actions.


###Request Example
```
{
    "flowOperator":"MAP",
    "function":"AVG",
    "quantifier":1,
    "schedule":1,
    "window":1,
    "monitors":[1],
    "scalingActions":[131073]
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/composedMonitor/{id}

###Description

Updates the ComposedMonitor entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
id            | The id of the ComposedMonitor to update.
flowOperator | The Flow Operator for the monitor. Values: [MAP, REDUCE]
function    | The function for the monitor. Values: [AVG, SUM,...]
quantifier | The id of the Formula Quantifier.
schedule | The id of the schedule.
window | The id of the window.
monitors | A list of ids of the scaling monitors.
scalingActions | A list of ids of the scaling actions.


###Request Example
```
PUT /api/composedMonitor/1
```
```
{
    "flowOperator":"MAP",
    "function":"AVG",
    "quantifier":1,
    "schedule":1,
    "window":1,
    "monitors":[1],
    "scalingActions":[131073]
}
```

###Response

The updated entity. See GET /api/composedMonitor/{id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/composedMonitor/{id}

###Description

Deletes the ComposedMonitor entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the ComposedMonitor to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
