# Image Actions
***
# Description
The image entity describes abstract properties of an operating system image.

## GET /api/image

### Request Parameters
None.

### Response
A list of all image entities stored in the database.

### Response Example
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
### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/image/{image_id}

### Description
Returns a single image entity identified by {image_id}.

### Request Parameters

Parameter     | Description
------------- | -------------
image_id      | The id of the image.

### Response
The image entity identified by the given id.

### Response Example
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
### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## POST /api/image

### Description
Creates a new image entity and returns the newly created entity.

### Request Parameters

Parameter       | Description
--------------- | -------------
name            | The name of the image.
operatingSystem | The id of the operating system.

### Request Example
```
{  
   "name":"Ubuntu-Cloud-Image",
   "operatingSystem":1
}    
```
### Response
The newly created image. See /api/image/{image_id}

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## PUT /api/image/{image_id}

### Description

Updates the image entity identified by the given id with the data of the request.

### Request Parameters** 

Parameter       | Description
--------------- | --------------------------------
image_id        | The id of the image to update.
name            | The name of the image.
operatingSystem | The id of the operating system.

### Request Example
```
PUT /api/image/1
```
```
{  
   "name":"Ubuntu-Cloud-Image",
   "operatingSystem":1
}
```

### Response
The updated image entity. See /api/image/{image_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/image/{image_id}

### Description

Deletes the image entity with the given id.

### Request Parameters** 

Parameter     | Description
------------- | -------------
image_id   | The id of the image to delete.

### Response
No data.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)