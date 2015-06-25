# ConstantMonitor Actions
***

##Description
The ConstantMonitor entity represents a Monitor just with a constant value.

## GET /api/constantMonitor

###Description
Returns a list of ConstantMonitor types supported by the system.

###Request Parameters
None

###Response
A list of all ConstantMonitor entities stored in the database.

###Response Example
```
[
    {
        "value":20.0,
        "link":
        [
            {
                "href":"http://localhost:9000/api/constantMonitor/131074",
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

## GET /api/constantMonitor/{id}

###Description

Returns the ConstantMonitor entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the ConstantMonitor.

###Response 
The ConstantMonitor entity identified by the given id.

###Response Example
```
{
    "value":20.0,
    "link":
    [
        {
            "href":"http://localhost:9000/api/constantMonitor/131074",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/constantMonitor

###Description

Creates a new ConstantMonitor entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
value   | The value of the constant monitor.

###Request Example
```
{
    "value":20.0
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/constantMonitor/{id}

###Description

Updates the ConstantMonitor entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
id   | The id of the constant monitor.
value   | The value of the constant monitor.

###Request Example
```
PUT /api/constantMonitor/1
```
```
{
    "value":20.0
}
```

###Response

The updated entity. See GET /api/constantMonitor/{id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/constantMonitor/{id}

###Description

Deletes the ConstantMonitor entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the ConstantMonitor to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
