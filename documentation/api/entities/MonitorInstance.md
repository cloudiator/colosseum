# Cloud Actions
***

##Description
The MonitorInstance entity represents a instantiated concrete monitoring process made of a monitor.

## GET /api/monitorInstance

###Description
Returns a list of MonitorInstance types supported by the system.

###Request Parameters
None

###Response
A list of all MonitorInstance entities stored in the database.

###Response Example
```
[
    {
        "externalReferences":[],
        "monitor":196612,
        "ipAddress":1,
        "virtualMachine":1,
        "component":null,
        "link":
            [
                {
                    "href":"http://localhost:9000/api/monitorInstance/196613","rel":"self"
                }
            ]
    }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/monitorInstance/{id}

###Description

Returns the MonitorInstance entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the MonitorInstance.

###Response 
The MonitorInstance entity identified by the given id.

###Response Example
```
{
    "externalReferences":[],
    "monitor":196612,
    "ipAddress":1,
    "virtualMachine":1,
    "component":null,
    "link":
        [
            {
                "href":"http://localhost:9000/api/monitorInstance/196613","rel":"self"
            }
        ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/monitorInstance

###Description

Creates a new MonitorInstance entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
externalReferences  | A list of external references.
monitor             | ID of the monitor that was instantiated
ipAddress           | ID of the IP address of the execution of the MonitorInstances
virtualMachine      | ID of the concerned VirtualMachine.
component           | ID of the concerned Component

###Request Example
```
{
    "externalReferences":[],
    "monitor":196612,
    "ipAddress":1,
    "virtualMachine":1,
    "component":null
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/monitorInstance/{id}

###Description

Updates the MonitorInstance entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
monitorInstance_id  | ID of the given MonitorInstance to update.
externalReferences  | A list of external references.
monitor             | ID of the monitor that was instantiated
ipAddress           | ID of the IP address of the execution of the MonitorInstances
virtualMachine      | ID of the concerned VirtualMachine.
component           | ID of the concerned Component

###Request Example
```
PUT /api/monitorInstance/1
```
```
{
    "externalReferences":[],
    "monitor":196612,
    "ipAddress":1,
    "virtualMachine":1,
    "component":null,
    "link":
        [
            {
                "href":"http://localhost:9000/api/monitorInstance/196613","rel":"self"
            }
        ]
}
```

###Response

The updated entity. See GET /api/monitorInstance/{id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/monitorInstance/{id}

###Description

Deletes the MonitorInstance entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the MonitorInstance to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
