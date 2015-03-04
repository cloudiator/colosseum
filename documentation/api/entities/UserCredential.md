# UserCredential Actions
***
## Description
User credentials connects the credential information of the user with a cloud api, meaning
that those credentials will be used to authenticate the user at the given api.
***
## GET /api/userCredential

###Request Parameters
None.

### Response
A list of all userCredential entities stored in the database.

### Response Example
```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/userCredential/1",
            "rel":"self"
         }
      ],
      "cloudApi":1,
      "credential":1,
      "frontendUser":1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/userCredential/2",
            "rel":"self"
         }
      ],
      "cloudApi":1,
      "credential":3,
      "frontendUser":2
   }
]
```
### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/userCredential/{userCredential_id}

### Description
Retrieves the details of the user credential entity identified with the given id.

### Request Parameters

Parameter             | Description
-------------         | -------------
userCredential_id     | The id of the userCredential.

### Response
Shows the selected userCredential entity.

### Response Example
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/userCredential/1",
         "rel":"self"
      }
   ],
   "cloudApi":1,
   "credential":1,
   "frontendUser":1
}
```
### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## POST /api/userCredential

### Description
Creates a new user credential entity and returns it.

### Request Parameters

Parameter    | Description
------------ | --------------------------------
cloudApi     | The id of the api entity.
credential   | The id of the credential entity.
frontendUser | The id of the frontend user.

### Request Example
```
{  
   "cloudApi":1,
   "credential":1,
   "frontendUser":1
}   
```
### Response
The created entity. See /api/userCredential/{userCredential_id}

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## PUT /api/userCredential/{userCredential_id}

### Description
Updates the user credential entity located at the given id.

### Request Parameters

Parameter         | Description
----------------- | ----------------------------------------
userCredential_id | The id of the userCredential to update.
cloudApi          | The id of the api entity.
credential        | The id of the credential entity.
frontendUser      | The id of the frontend user.

### Request Example
```
PUT /api/userCredential/1
```
```
{  
   "cloudApi":1,
   "credential":1,
   "frontendUser":1
}
```

### Response
The updated entity. See /api/userCredential/{userCredential_id]

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/userCredential/{userCredential_id}

**Request Parameters** 

Parameter           | Description
-------------       | -------------
userCredential_id   | The id of the userCredential to delete.

### Response** 
No data.

### Response Codes 

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)