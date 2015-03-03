# UserCredential Actions
***

## GET /api/userCredential

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all userCredential entities stored in the database.

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

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/userCredential/{userCredential_id}

**Request Parameters**

Parameter             | Description
-------------         | -------------
userCredential_id     | The id of the userCredential.

**Request** This operation does not require a request body.

**Response** Shows the selected userCredential entity.

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

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/userCredential

**Request Parameters** none

**Request** The userCredential attributes cloudApi, credential and frontendUser.

```
{  
   "cloudApi":1,
   "credential":1,
   "frontendUser":1
}   
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/userCredential/{userCredential_id}

**Request Parameters** 

Parameter            | Description
-------------        | -------------
userCredential_id    | The id of the userCredential to update.

**Request** The userCredential attributes cloudApi, credential and frontendUser.

```
{  
   "cloudApi":1,
   "credential":1,
   "frontendUser":1
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/userCredential/{userCredential_id}

**Request Parameters** 

Parameter           | Description
-------------       | -------------
userCredential_id   | The id of the userCredential to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)