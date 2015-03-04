# CloudLocation Actions
***

##Description

The CloudLocation entity represents a specific location which is associated with a cloud and a location.

***
## GET /api/cloudLocation

###Description

Returns a list of CloudLocations entities supported by the system.

###Request Parameters
None

###Response

A list of all CloudLocation entities stored in the database.

###Response Example


```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudLocation/1",
            "rel":"self"
         }
      ],
      "cloud":1,
      "location":1,
      "cloudUuid":"regionOne"
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudLocation/2",
            "rel":"self"
         }
      ],
      "cloud":1,
      "location":1,
      "cloudUuid":"regionOne"
   }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/cloudLocation/{cloudLocation_id}

###Description

Returns the CloudLocation entity identified by the given {cloudLocation_id}.

###Request Parameters

Parameter        | Description
-------------    | -------------
cloudLocation_id    | The id of the cloudLocation.

###Response

The CloudLocation entity identified by the given id.

###Response Example

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/cloudLocation/1",
         "rel":"self"
      }
   ],
   "cloud":1,
   "location":1,
   "cloudUuid":"regionOne"
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/cloudLocation

###Description

Creates a new CloudLocation entity. The new entity will be returned.

###Request Parameters

Parameter        | Description
-------------    | -------------
cloud            | The cloud associated with the CloudLocation.
location         | The location associated with the CloudLocation.
cloudUuid        | The UUID of the CloudLocation.

###Request Example

```
{  
   "cloud":1,
   "location":1,
   "cloudUuid":"regionOne"
}
```

###Response

The created entity. See GET /api/cloudLocation/{cloudLocation_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/cloudLocation/{cloudLocation_id}

###Description

Updates the CloudLocation entity identified by the given id.

###Request Parameters

Parameter           | Description
-------------       | -------------
cloudLocation_id    | The id of the cloudLocation to update.
cloud               | The cloud associated with the CloudLocation.
location            | The location associated with the CloudLocation.
cloudUuid           | The UUID of the CloudLocation.

###Request Example 

```
{  
   "cloud":1,
   "location":1,
   "cloudUuid":"regionOne"
}
```

###Response

The updated entity. See GET /api/cloudLocation/{cloudLocation_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/cloudLocation/{cloudLocation_id}

###Description

Deletes the CloudLocation entity identified by the given {cloudLocation_id}.

###Request Parameters

Parameter          | Description
-------------      | -------------
cloudLocation_id   | The id of the cloudLocation to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)