# CloudLocation Actions
***

## GET /api/cloudLocation

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all cloudLocation entities stored in the database.

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

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/cloudLocation/{cloudLocation_id}

**Request Parameters**

Parameter        | Description
-------------    | -------------
cloudLocation_id    | The id of the cloudLocation.

**Request** This operation does not require a request body.

**Response** Shows the selected cloudLocation entity.

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

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/cloudLocation

**Request Parameters** none

**Request** The cloudLocation attributes cloud, location and uuid.

```
{  
   "cloud":1,
   "location":1,
   "cloudUuid":"regionOne"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/cloudLocation/{cloudLocation_id}

**Request Parameters** 

Parameter           | Description
-------------       | -------------
cloudLocation_id    | The id of the cloudLocation to update.

**Request** The cloudLocation attributes cloud, location and uuid.

```
{  
   "cloud":1,
   "location":1,
   "cloudUuid":"regionOne"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/cloudLocation/{cloudLocation_id}

**Request Parameters** 

Parameter          | Description
-------------      | -------------
cloudLocation_id   | The id of the cloudLocation to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)