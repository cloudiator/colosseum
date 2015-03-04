# CloudApi Actions
***

##Description

The CloudAPI entity represents a specific endpoint which is associated
with a cloud and an API.

## GET /api/cloudApi

###Description

Returns a list of CloudAPI entities supported by the system.

###Request Parameters
None

###Response
A list of all CloudApi entities stored in the database.

###Response Example

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudApi/1",
            "rel":"self"
         }
      ],
      "api":2,
      "cloud":1,
      "endpoint":"https://neutron-host:9696"
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudApi/2",
            "rel":"self"
         }
      ],
      "api":1,
      "cloud":1,
      "endpoint":"https://neutron-host:8774"
   }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/cloudApi/{cloudApi_id}

###Description

Returns the CloudApi entity identified by the given {cloudApi_id}.

###Request Parameters

Parameter      | Description
-------------  | -------------
cloudApi_id    | The id of the cloudApi.

###Response
The CloudApi entity identified by the given id.

###Response Example

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/cloudApi/1",
         "rel":"self"
      }
   ],
   "api":2,
   "cloud":1,
   "endpoint":"https://neutron-host:9696"
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/cloudApi

###Description

Creates a new CloudApi entity. The new entity will be returned.

###Request Parameters

Parameter      | Description
-------------  | -------------
api            | The API associated with the CloudApi.
cloud          | The cloud associated with the CloudApi.
endpoint       | The endpoint url of the CloudApi.

###Request Example

```
{  
   "api":1,
   "cloud":1,
   "endpoint":"https://neutron-host:8774"
}
```

###Response

The created entity. See GET /api/cloudApi/{cloudApi_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/cloudApi/{cloudApi_id}

###Description

Updates the CloudApi entity identified by the given id.

###Request Parameters 

Parameter      | Description
-------------  | -------------
cloudApi_id    | The id of the cloudApi to update.
api            | The API associated with the CloudApi.
cloud          | The cloud associated with the CloudApi.
endpoint       | The endpoint url of the CloudApi.

###Request Example

PUT /api/cloudApi/1

```
{  
   "api":1,
   "cloud":1,
   "endpoint":"https://neutron-host:8774"
}
```

###Response

The updated entity. See GET /api/cloudApi/{cloudApi_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/cloudApi/{cloudApi_id}

###Description

Deletes the CloudApi entity identified by the given {cloudApi_id}.

###Request Parameters 

Parameter     | Description
------------- | -------------
cloudApi_id   | The id of the cloudApi to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)