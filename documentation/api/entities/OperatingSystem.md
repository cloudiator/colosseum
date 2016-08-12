# OperatingSystem Actions
***

##Description

The operating system entity represents the operating system used by an image.

***

## GET /api/os

###Description

Returns a list of operating system entities supported by the system.

###Request Parameters
None

###Response

A list of all operating system entities stored in the database.

###Response Example

```
[
  {
    "operatingSystemArchitecture":"AMD64",
    "operatingSystemFamily": "UBUNTU",
    "version":"8",
    "link":[
      {
        "href":"http://example.com:9000/api/os/1",
        "rel":"self"
      }
    ]
  },
  {
    "operatingSystemArchitecture":"I386",
    "operatingSystemFamily": "UBUNTU",
    "version":"7",
    "link":[
      {
        "href":"http://example.com:9000/api/os/2",
        "rel":"self"
      }
    ]
  }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/os/{os_id}

###Description

Returns the operating system entity identified by the given {os_id}.

###Request Parameters

Parameter        | Description
---------------- | -------------
os_id            | The id of the operating system.



###Response
The operating system entity identified by the given id.

###Response Example

```
{
  "operatingSystemArchitecture":"AMD64",
  "operatingSystemFamily": "UBUNTU",
  "version":"8",
  "link":[
    {
      "href":"http://example.com:9000/api/os/1",
      "rel":"self"
    }
  ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/os

###Description

Creates a new operating system entity. The new entity will be returned.

###Request Parameters

Parameter                   | Description
--------------------------- | ---------------------------------
operatingSystemArchitecture | The architecture of the os (I386, AMD64)
operatingSystemFamily       | The family of the operating system
version                     | The version of the operating system.

###Request Example

```
{
  "operatingSystemArchitecture":"I386",
  "operatingSystemFamily": "UBUNTU",
  "version":"7"
}
```

###Response

The created entity. See GET /api/os/{os_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/os/{os_id}

###Description

Updates the operating system entity identified by the given id.

###Request Parameters** 

Parameter                   | Description
--------------------------- | -----------------------------------------
os_id                       | The id of the os to update.
operatingSystemArchitecture | The architecture of the os (I386, AMD64)
operatingSystemFamily       | The family of the operating system
version                     | The version of the operating system.

###Request Example

PUT /api/os/1

```
{
  "operatingSystemArchitecture":"I386",
  "operatingSystemFamily": "UBUNTU",
  "version":"7"
}
```
###Response

The updated entity. See GET /api/os/{os_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/os/{os_id}

###Description

Deletes the oeprating system entity identified by the given {os_id}.

###Request Parameters 

Parameter       | Description
-------------   | -------------
os_id           | The id of the operating system to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
