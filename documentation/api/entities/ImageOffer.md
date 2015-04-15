# Image Offer Actions
***
# Description
The image offer entity describes properties of an operating system image offer.

## GET /api/imageOffer

### Request Parameters
None.

### Response
A list of all image offer entities stored in the database.

### Response Example
```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/imageOffer/1",
            "rel":"self"
         }
      ],
      "name":"Ubuntu-Cloud-Image",
      "operatingSystem":1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/imageOffer/2",
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

## GET /api/imageOffer/{imageOffer_id}

### Description
Returns a single image offer entity identified by {imageOffer_id}.

### Request Parameters

Parameter     | Description
------------- | -------------
imageOffer_id      | The id of the image.

### Response
The image offer entity identified by the given id.

### Response Example
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/imageOffer/1",
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

## POST /api/imageOffer

### Description
Creates a new image offer entity and returns the newly created entity.

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
The newly created image. See /api/imageOffer/{imageOffer_id}

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## PUT /api/imageOffer/{imageOffer_id}

### Description

Updates the image offer entity identified by the given id with the data of the request.

### Request Parameters** 

Parameter            | Description
-------------------- | --------------------------------
imageOffer_id        | The id of the image offer to update.
name                 | The name of the image.
operatingSystem      | The id of the operating system.

### Request Example
```
PUT /api/imageOffer/1
```
```
{  
   "name":"Ubuntu-Cloud-Image",
   "operatingSystem":1
}
```

### Response
The updated image offer entity. See /api/imageOffer/{imageOffer_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/imageOffer/{imageOffer_id}

### Description

Deletes the image offer entity with the given id.

### Request Parameters** 

Parameter       | Description
--------------- | -------------
imageOffer_id   | The id of the image offer to delete.

### Response
No data.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)