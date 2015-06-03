﻿# ComponentHorizontalInScalingAction Actions
***

##Description
The ComponentHorizontalInScalingAction entity represents the action to execute once a scaling rule has been activated.

## GET /api/componentHorizontalInScalingAction

###Description
Returns a list of ComponentHorizontalInScalingAction.

###Request Parameters
None

###Response
A list of all ComponentHorizontalInScalingAction entities stored in the database.

###Response Example
```


TODO
COPY FROM BROWSER


```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/componentHorizontalInScalingAction/{id}

###Description

Returns the ComponentHorizontalInScalingAction entity identified by the given {componentHorizontalInScalingAction_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the ComponentHorizontalInScalingAction.

###Response 
The ComponentHorizontalInScalingAction entity identified by the given id.

###Response Example
```


TODO


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
TODO

###Request Example
```


TODO


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
id            | The id of the ComponentHorizontalInScalingAction to update.
TODO

###Request Example
```
PUT /api/componentHorizontalInScalingAction/1
```
```

TODO


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
id            | The id of the ComponentHorizontalInScalingAction to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)