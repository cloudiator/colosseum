# Credential Actions
***

## GET /api/credential

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all credential entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/credential/1",
            "rel":"self"
         }
      ],
      "user":"john",
      "secret":"secret"
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/credential/2",
            "rel":"self"
         }
      ],
      "user":"doe",
      "secret":"secret"
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/credential/{credential_id}

**Request Parameters**

Parameter      | Description
-------------  | -------------
credential_id  | The id of the credential.

**Request** This operation does not require a request body.

**Response** Shows the selected credential entity.

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/credential/1",
         "rel":"self"
      }
   ],
   "user":"john",
   "secret":"secret"
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/credential

**Request Parameters** none

**Request** The credential attributes user and secret.

```
{  
   "user":"john",
   "secret":"secret"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/credential/{credential_id}

**Request Parameters** 

Parameter      | Description
-------------  | -------------
credential_id  | The id of the credential to update.

**Request** The credential attributes user and secret.

```
{  
   "user":"john",
   "secret":"secret"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/credential/{credential_id}

**Request Parameters** 

Parameter      | Description
-------------  | -------------
credential_id  | The id of the credential to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)