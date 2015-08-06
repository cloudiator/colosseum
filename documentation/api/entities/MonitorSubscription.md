# Cloud Actions
***

##Description
The MonitorSubscription entity represents...

## GET /api/monitorSubscription

###Description
Returns a list of MonitorSubscriptions handled by the system.

###Request Parameters
None

###Response
A list of all MonitorSubscription entities stored in the database.

###Response Example
```
TODO
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/monitorSubscription/{id}

###Description

Returns the MonitorSubscription entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the MonitorSubscription.

###Response
The MonitorSubscription entity identified by the given id.

###Response Example
```
TODO
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/monitorSubscription

###Description

Creates a new MonitorSubscription entity. The new entity will be returned.

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

## PUT /api/monitorSubscription/{id}

###Description

Updates the MonitorSubscription entity identified by the given id.

**Request Parameters**

Parameter     | Description
------------- | -------------
TODO

###Request Example
```
PUT /api/monitorSubscription/1
```
```
TODO
```

###Response

The updated entity. See GET /api/monitorSubscription/{id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/monitorSubscription/{id}
## DELETE /api/monitorSubscription/{id}

###Description

Deletes the MonitorSubscription entity identified by the given {id}.

###Request Parameters

Parameter     | Description
------------- | -------------
id            | The id of the MonitorSubscription to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
