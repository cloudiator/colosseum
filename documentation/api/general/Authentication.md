# Authentication Actions
***

## POST /api/login

**Request Parameters**

Parameter     | Description
------------- | -------------
email         | The email address of the user you want to authenticate.
password      | The password of the user you want to authenticate.

**Request** 

```
{
   "email":"john.doe@example.com",
   "password":"admin"
}
```

**Response** An access token, for future use with the system

```
{
   "createdOn":1420636102686,
   "expiresAt":1420636402686,
   "token":"dadj16lv9vgl6o57cd9jl2h4klfp70901p53ev5t774cqclabo1006aagv417f7fi6lsvipubqussdhqkm83kn8mak7h914t2d9d9rajoth97urhj0e74sb0mbv065gnj43ruha4vm2qp39dqhopqne29iboau5p6o499tqg6p32fdjmhrp9dv62d442vefrspjtdfqukcf0j",
   "userId":1
}
```

**Response Parameters**

Parameter     | Description
------------- | -------------
createdOn     | The unix timestamp when the token was generated.
expiresAt     | The unix timestamp when the token expires.
token         | The access token.
userId        | The id of the authenticated user.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 400 (Bad Request)

## Future use of authentication token

The token and the user id must be provided in the header for each further request to the system, that requires authentication.

**Request Headers**

Parameter     | Description
------------- | -------------
X-Auth-Token  | A valid, non-expired token.
X-Auth-UserId | The id of the user.