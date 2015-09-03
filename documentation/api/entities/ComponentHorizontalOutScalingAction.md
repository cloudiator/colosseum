# ComponentHorizontalOutScalingAction Actions
***

##Description
The ComponentHorizontalOutScalingAction entity represents the scaling action.

## GET /api/componentHorizontalOutScalingAction

###Description
Returns a list of ComponentHorizontalOutScalingAction types supported by the system.

###Request Parameters
None

###Response
A list of all ComponentHorizontalOutScalingAction entities stored in the database.

###Response Example
```
[
    {
        "externalReferences":[
            "some_external_reference"
        ],
        "amount":1,
        "min":2,
        "max":3,
        "count":4,
        "applicationComponent":1,
        "link":[
            {
                "href":"http://localhost:9000/api/componentHorizontalInScalingAction/131073",
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

## GET /api/componentHorizontalOutScalingAction/{id}

###Description

Returns the ComponentHorizontalOutScalingAction entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id      | The id of the ComponentHorizontalOutScalingAction.

###Response 
The ComponentHorizontalOutScalingAction entity identified by the given id.

###Response Example
```
{
    "externalReferences":[
        "some_external_reference"
    ],
    "amount":1,
    "min":2,
    "max":3,
    "count":4,
    "applicationComponent":1,
    "link":[
        {
            "href":"http://localhost:9000/api/componentHorizontalOutScalingAction/131073",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/componentHorizontalOutScalingAction

###Description

Creates a new ComponentHorizontalOutScalingAction entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
externalReferences | List of strings as external references.
amount | Amount of instances to scale.
min | Minimum instances of applicationComponent for this action.
max | Maximum instances of applicationComponent for this action.
count | Count variable to show how often this action has been triggered.
applicationComponent | The id of the applicationComponent that is referenced in this action.

###Request Example
```
{
    "externalReferences":[
        "some_external_reference"
    ],
    "amount":1,
    "min":2,
    "max":3,
    "count":4,
    "applicationComponent":1
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/componentHorizontalOutScalingAction/{id}

###Description

Updates the ComponentHorizontalOutScalingAction entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
id      | The id of the ComponentHorizontalOutScalingAction to update.
externalReferences | List of strings as external references.
amount | Amount of instances to scale.
min | Minimum instances of applicationComponent for this action.
max | Maximum instances of applicationComponent for this action.
count | Count variable to show how often this action has been triggered.
applicationComponent | The id of the applicationComponent that is referenced in this action.

###Request Example
```
PUT /api/componentHorizontalOutScalingAction/1
```
```
{
    "externalReferences":[
        "some_external_reference"
    ],
    "amount":1,
    "min":2,
    "max":3,
    "count":4,
    "applicationComponent":1
}
```

###Response

The updated entity. See GET /api/componentHorizontalOutScalingAction/{id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/componentHorizontalOutScalingAction/{id}

###Description

Deletes the ComponentHorizontalOutScalingAction entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id      | The id of the ComponentHorizontalOutScalingAction to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
