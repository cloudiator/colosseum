# Image Actions
***

## GET /api/image

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all image entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/image/1",
            "rel":"self"
         }
      ],
      "name":"Ubuntu-Cloud-Image",
      "operatingSystem":1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/image/2",
            "rel":"self"
         }
      ],
      "name":"CentOS-Cloud-Image",
      "operatingSystem":1
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/image/{image_id}

**Request Parameters**

Parameter     | Description
------------- | -------------
image_id      | The id of the image.

**Request** This operation does not require a request body.

**Response** Shows the selected image entity.

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/image/1",
         "rel":"self"
      }
   ],
   "name":"Ubuntu-Cloud-Image",
   "operatingSystem":1
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/image

**Request Parameters** none

**Request** The image attributes name and operatingSystem.

```
{  
   "name":"Ubuntu-Cloud-Image",
   "operatingSystem":1
}    
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/image/{image_id}

**Request Parameters** 

Parameter     | Description
------------- | -------------
image_id      | The id of the image to update.

**Request** The image attributes name and operatingSystem.

```
{  
   "name":"Ubuntu-Cloud-Image",
   "operatingSystem":1
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/image/{image_id}

**Request Parameters** 

Parameter     | Description
------------- | -------------
image_id   | The id of the image to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)