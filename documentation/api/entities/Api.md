# API Actions
***

## Description

The API entity represents an API type (like aws-ec2 or openstack-nova) supported by the system.
***

## GET /api/api

###Description
Returns a list of API types supported by the system.

###Request Parameters
None

###Response
A list of all api entities stored in the database.

###Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/api/1",
            "rel":"self"
         }
      ],
      "name":"aws-ec2",
      "internalProviderName":"aws-ec2"
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/api/api/2",
            "rel":"self"
         }
      ],
      "name":"openstack-nova",
      "internalProviderName":"openstack-nova"
   }
]
```
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/api/{api_id}

###Description
Returns the API type identified by the given {api_id}.

###Request Parameters
Parameter     | Description
------------- | -------------
api_id        | The id of the api.

###Response
The API entity identified by the given id.

###Response Example
```
{
   "links":[
      {
         "href":"http://example.com:9000/api/api/1",
         "rel":"self"
      }
   ],
   "name":"aws-ec2",
   "internalProviderName":"aws-ec2"
}
```
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/api

###Description
Creates a new API entity. The new entity will be returned.

###Request Parameters
Parameter            | Description
-------------------- | ------------------------------------
name                 | The name of the api.
internalProviderName | The internal name of the provider.

###Request Example
```
{
    "name":"openstack-nova",
    "internalProviderName":"openstack-nova"
}
```
###Response 
The created entity. See GET /api/api/{api_id}
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)
***
## PUT /api/api/{api_id}

###Description
Updates the API type identified by the given id.

###Request Parameters
Parameter            | Description
-------------------- | -------------
api_id               | The id of the api to update.
name                 | The name of the API.
internalProviderName | The internal name of the provider.

###Request Example
```
PUT /api/api/1
```
```
{
    "name":"openstack-nova",
    "internalProviderName":"openstack-nova"
}
```
###Response
The updated entity. See GET /api/api/{api_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)
***
## DELETE /api/api/{api_id}

###Description
Deletes the API entity identified by the given {api_id}.

###Request Parameters
Parameter     | Description
------------- | -------------
api_id        | The id of the api to delete.

###Response
No data.

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)