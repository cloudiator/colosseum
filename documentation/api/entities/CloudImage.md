# CloudImage Actions
***

##Description

The CloudImage entity represents a specific image which is associated with a cloud and an image.

***

## GET /api/cloudImage

###Description

Returns a list of CloudImages entities supported by the system.

###Request Parameters
None

###Response

A list of all CloudImage entities stored in the database.

###Response Example

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudImage/1",
            "rel":"self"
         }
      ],
      "cloud":1,
      "image":2,
      "cloudUuid":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2"
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudImage/2",
            "rel":"self"
         }
      ],
      "cloud":1,
      "image":3,
      "cloudUuid":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2"
   }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/cloudImage/{cloudImage_id}

###Description

Returns the CloudImage entity identified by the given {cloudImage_id}.

###Request Parameters

Parameter        | Description
-------------    | -------------
cloudImage_id    | The id of the cloudImage.



###Response
The CloudImage entity identified by the given id.

###Response Example

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/cloudImage/1",
         "rel":"self"
      }
   ],
   "cloud":1,
   "image":2,
   "cloudUuid":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2"
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/cloudImage

###Description

Creates a new CloudImage entity. The new entity will be returned.

###Request Parameters

Parameter        | Description
-------------    | -------------
cloud            | The cloud associated with the CloudImage.
image            | The image associated with the CloudImage.
cloudUuid        | The UUID of the CloudImage.

###Request Example

```
{  
   "cloud":1,
   "image":2,
   "cloudUuid":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2"
}   
```

###Response

The created entity. See GET /api/cloudImage/{cloudImage_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/cloudImage/{cloudImage_id}

###Description

Updates the CloudImage entity identified by the given id.

###Request Parameters** 

Parameter        | Description
-------------    | -------------
cloudImage_id    | The id of the cloudImage to update.
cloud            | The cloud associated with the CloudImage.
image            | The image associated with the CloudImage.
cloudUuid        | The UUID of the CloudImage.

###Request Example

PUT /api/cloudImage/1

```
{  
   "cloud":1,
   "image":2,
   "cloudUuid":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2"
}
```
###Response

The updated entity. See GET /api/cloudImage/{cloudImage_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/cloudImage/{cloudImage_id}

###Description

Deletes the CloudImage entity identified by the given {cloudImage_id}.

###Request Parameters 

Parameter       | Description
-------------   | -------------
cloudImage_id   | The id of the cloudImage to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)