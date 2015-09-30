# Cloud Actions
***

##Description
The Cloud entity represents possible cloud providers like amazon or flexiant.

## GET /api/cloud

###Description
Returns a list of Cloud types supported by the system.

###Request Parameters
None

###Response
A list of all cloud entities stored in the database.

###Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/cloud/1",
            "rel":"self"
         }
      ],
      "name":"amazon",
      "endpoint":"https://amazon.de:9696",
      "api":2
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/cloud/2",
            "rel":"self"
         }
      ],
      "name":"flexiant",
      "endpoint":"https://flexiant.net:8774",
      "api":1
   }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/cloud/{cloud_id}

###Description

Returns the Cloud entity identified by the given {cloud_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
cloud_id      | The id of the cloud.

###Response 
The Cloud entity identified by the given id.

###Response Example
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/cloud/1",
         "rel":"self"
      }
   ],
   "name":"amazon",
   "endpoint":"https://amazon.de:9696",
   "api":2
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/cloud

###Description

Creates a new Cloud entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
name          | The name of the cloud.
endpoint      | The endpoint of the api
api           | The api used for requests to this clouds.

###Request Example
```
{  
   "name":"amazon",
   "endpoint":"https://amazon.de:9696",
   "api":2
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/cloud/{cloud_id}

###Description

Updates the Cloud entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
cloud_id      | The id of the cloud to update.
endpoint      | The endpoint of the api
api           | The api used for requests to this clouds.

###Request Example
```
PUT /api/cloud/1
```
```
{  
   "name":"Flexiant",
   "endpoint":"https://amazon.de:9696",
   "api":2
}
```

###Response

The updated entity. See GET /api/cloud/{cloud_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/cloud/{cloud_id}

###Description

Deletes the Cloud entity identified by the given {cloud_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
cloud_id      | The id of the cloud to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
