# CloudImage Actions
***

## GET /api/cloudImage

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all cloudImage entities stored in the database.

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

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/cloudImage/{cloudImage_id}

**Request Parameters**

Parameter        | Description
-------------    | -------------
cloudImage_id    | The id of the cloudImage.

**Request** This operation does not require a request body.

**Response** Shows the selected cloudImage entity.

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

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/cloudImage

**Request Parameters** none

**Request** The cloudImage attributes cloud, image and uuid.

```
{  
   "cloud":1,
   "image":2,
   "cloudUuid":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2"
}   
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/cloudImage/{cloudImage_id}

**Request Parameters** 

Parameter        | Description
-------------    | -------------
cloudImage_id    | The id of the cloudImage to update.

**Request** The cloudImage attributes cloud, image and uuid.

```
{  
   "cloud":1,
   "image":2,
   "cloudUuid":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/cloudImage/{cloudImage_id}

**Request Parameters** 

Parameter       | Description
-------------   | -------------
cloudImage_id   | The id of the cloudImage to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)