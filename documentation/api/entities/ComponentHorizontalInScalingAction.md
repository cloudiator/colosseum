# ComponentHorizontalInScalingAction Actions
***

##Description
The ComponentHorizontalInScalingAction entity represents the scaling action.

## GET /api/componentHorizontalInScalingAction

###Description
Returns a list of ComponentHorizontalInScalingAction types supported by the system.

###Request Parameters
None

###Response
A list of all ComponentHorizontalInScalingAction entities stored in the database.

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

## GET /api/componentHorizontalInScalingAction/{id}

###Description

Returns the ComponentHorizontalInScalingAction entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id      | The id of the ComponentHorizontalInScalingAction.

###Response 
The ComponentHorizontalInScalingAction entity identified by the given id.

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
            "href":"http://localhost:9000/api/componentHorizontalInScalingAction/131073",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/componentHorizontalInScalingAction

###Description

Creates a new ComponentHorizontalInScalingAction entity. The new entity will be returned.

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

## PUT /api/componentHorizontalInScalingAction/{id}

###Description

Updates the ComponentHorizontalInScalingAction entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
id      | The id of the ComponentHorizontalInScalingAction to update.
externalReferences | List of strings as external references.
amount | Amount of instances to scale.
min | Minimum instances of applicationComponent for this action.
max | Maximum instances of applicationComponent for this action.
count | Count variable to show how often this action has been triggered.
applicationComponent | The id of the applicationComponent that is referenced in this action.

###Request Example
```
PUT /api/componentHorizontalInScalingAction/1
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

The updated entity. See GET /api/componentHorizontalInScalingAction/{id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/componentHorizontalInScalingAction/{id}

###Description

Deletes the ComponentHorizontalInScalingAction entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id      | The id of the ComponentHorizontalInScalingAction to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
