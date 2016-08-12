# Image Actions
***

##Description

The image entity represents a specific image which is associated with a cloud and an image.

***

## GET /api/image

###Description

Returns a list of images entities supported by the system.

###Request Parameters
None

###Response

A list of all image entities stored in the database.

###Response Example

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/image/1",
            "rel":"self"
         }
      ],
      "name":"Ubuntu Server 14.04",
      "cloud":1,
      "remoteId":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2",
      "location":1,
      "cloudCredentials": [
        1
      ],      
      "operatingSystem": 1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/image/2",
            "rel":"self"
         }
      ],
      "name": "CentOS",
      "cloud":1,
      "remoteId":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2",
      "location":1,
      "cloudCredentials": [
        1
      ],  
      "operatingSystem": 2
   }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/image/{image_id}

###Description

Returns the image entity identified by the given {image_id}.

###Request Parameters

Parameter        | Description
-------------    | -------------
image_id         | The id of the image.



###Response
The image entity identified by the given id.

###Response Example

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/image/1",
         "rel":"self"
      }
   ],
   "name":"Ubuntu Server 14.04",
   "cloud":1,
   "remoteId":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2",
   "location":1,
   "cloudCredentials": [
     1
   ],     
   "operatingSystem": 1
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/image

###Description

Creates a new image entity. The new entity will be returned.

###Request Parameters

Parameter        | Description
-------------    | -------------
name             | The name of the image.
cloud            | The cloud associated with the image.
remoteId        | The UUID of the image.
location        | The location at which the image is available.
cloudCredentials | The cloud credentials used for retrieving the image.
operatingSystem  | The operating system used by the image.

###Request Example

```
{  
   "name":"Ubuntu Server 14.04",
   "cloud":1,
   "remoteId":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2",
   "location":1,
   "cloudCredentials": [
     1
   ],     
   "operatingSystem": 1
}   
```

###Response

The created entity. See GET /api/image/{image_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/image/{image_id}

###Description

Updates the image entity identified by the given id.

###Request Parameters** 

Parameter        | Description
-------------    | -------------
image_id         | The id of the image to update.
name             | The name of the image.
cloud            | The cloud associated with the image.
remoteId        | The UUID of the image.
location        | The location at which the image is available.
cloudCredentials | The cloud credentials used for retrieving the image.
operatingSystem  | The operating system used by the image.

###Request Example

PUT /api/image/1

```
{  
   "name":"Ubuntu Server 14.04",
   "cloud":1,
   "remoteId":"1bbdd137-9eed-494b-b4eb-70a5fbe285d2",
   "location":1,
   "cloudCredentials": [
     1
   ],
   "operatingSystem": 1
}
```
###Response

The updated entity. See GET /api/image/{image_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/image/{image_id}

###Description

Deletes the image entity identified by the given {image_id}.

###Request Parameters 

Parameter       | Description
-------------   | -------------
image_id        | The id of the image to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
