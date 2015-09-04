# FormulaQuantifier Actions
***

##Description
The FormulaQuantifier entity represents a quantifier for a formula that defines the amount of output MonitorInstances to a Monitor.

## GET /api/formulaQuantifier

###Description
Returns a list of FormulaQuantifier types supported by the system.

###Request Parameters
None

###Response
A list of all FormulaQuantifier entities stored in the database.

###Response Example
```
[
    {
        "relative":true,
        "value":1.0,
        "link":
        [
            {
                "href":"http://localhost:9000/api/formulaQuantifier/1",
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

## GET /api/formulaQuantifier/{id}

###Description

Returns the FormulaQuantifier entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the cloud.

###Response 
The FormulaQuantifier entity identified by the given id.

###Response Example
```
{
    "relative":true,
    "value":1.0,
    "link":
    [
        {
            "href":"http://localhost:9000/api/formulaQuantifier/1",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/formulaQuantifier

###Description

Creates a new FormulaQuantifier entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
relative            | Determines if the value is relative.
value            | The value.

###Request Example
```
{
    "relative":true,
    "value":1.0
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/formulaQuantifier/{id}

###Description

Updates the FormulaQuantifier entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
id            | The id of the FormulaQuantifier.
relative            | Determines if the value is relative.
value            | The value.

###Request Example
```
PUT /api/formulaQuantifier/1
```
```
{
    "relative":true,
    "value":1.0
}
```

###Response

The updated entity. See GET /api/formulaQuantifier/{id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/formulaQuantifier/{id}

###Description

Deletes the FormulaQuantifier entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
TODO

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
