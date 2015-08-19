# Credential Actions
***

###Description

The CloudCredential entity represents the user account for a specified cloud.

***

## GET /api/cloudCredential

###Description

Returns a list of Credential entities stored in the system.

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
            "href":"http://example.com:9000/cloudCredential/1",
            "rel":"self"
         }
      ],
      "user":"john",
      "secret":"secret",
      "cloud":1,
      "tenant":1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudCredential/2",
            "rel":"self"
         }
      ],
      "user":"doe",
      "secret":"secret",
      "cloud":1,
      "tenant":2
   }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/cloudCredential/{cloudCredential_id}

####Description

Returns the Credential entity identified by the given {credential_id}.

###Request Parameters

Parameter           | Description
------------------  | -------------
cloudCredential_id  | The id of the credential.

###Response

The Credential entity identified by the given id.

###Response Example

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/cloudCredential/1",
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

## POST /api/cloudCredential

###Description

Creates a new Credential entity. The new entity will be returned.

###Request Parameters

Parameter         | Description
----------------- | --------------
user              | The username.
secret            | The password.
cloud             | The cloud the credential belongs to.
tenant | The frontend group that has access to it.

###Request Example

```
{  
   "user":"john",
   "secret":"secret",
   "cloud":1,
   "tenant":1
}
```

###Response

The created entity. See GET /api/cloudCredential/{cloudCredential_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/cloudCredential/{cloudCredential_id}

###Description

Updates the Credential entity identified by the given id.

###Request Parameters 

Parameter           | Description
------------------- | -------------
cloudCredential_id  | The id of the credential to update.
user                | The username.
secret              | The password.
cloud               | The cloud the credential belongs to.
tenant   | The frontend group that has access to it.

###Request Example

PUT /api/cloudCredential/1

```
{  
   "user":"john",
   "secret":"secret",
   "cloud":1,
   "tenant":1
}

###Response

The updated entity. See GET /api/cloudCredential/{cloudCredential_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/cloudCredential/{cloudCredential_id}

###Description

Deletes the Credential entity identified by the given {cloudCredential_id}.

###Request Parameters

Parameter           | Description
------------------- | -------------
cloudCredential_id  | The id of the credential to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
