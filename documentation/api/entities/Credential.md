# Credential Actions
***

###Description

The Credential entity represents an User of the system which is identified by user and secret.

***

## GET /api/credential

###Description

Returns a list of Credential entities supported by the system.

###Request Parameters
None

###Response

A list of all Credential entities stored in the database.

###Response Example

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

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/credential/{credential_id}

####Description

Returns the Credential entity identified by the given {credential_id}.

###Request Parameters

Parameter      | Description
-------------  | -------------
credential_id  | The id of the credential.

###Response

The Credential entity identified by the given id.

###Response Example

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

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)


***

## POST /api/credential

###Description

Creates a new Credential entity. The new entity will be returned.

###Request Parameters

Parameter      | Description
-------------  | -------------
user           | The username.
secret         | The password.

###Request Example

```
{  
   "user":"john",
   "secret":"secret"
}
```

###Response

The created entity. See GET /api/credential/{credential_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/credential/{credential_id}

###Description

Updates the Credential entity identified by the given id.

###Request Parameters 

Parameter      | Description
-------------  | -------------
credential_id  | The id of the credential to update.
user           | The username.
secret         | The password.

###Request Example

PUT /api/credential/1

```
{  
   "user":"john",
   "secret":"secret"
}
```

###Response

The updated entity. See GET /api/credential/{credential_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/credential/{credential_id}

###Description

Deletes the Credential entity identified by the given {credential_id}.

###Request Parameters

Parameter      | Description
-------------  | -------------
credential_id  | The id of the credential to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)