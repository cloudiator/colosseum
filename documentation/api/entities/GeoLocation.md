# GeoLocation Actions
***

##Description

The geo location represents a geographical location.

***
## GET /api/geo

###Description

Returns a list of geo. locations entities supported by the system.

###Request Parameters
None

###Response

A list of all geo. location entities stored in the database.

###Response Example


```
[
  {
    "region":"EU",
    "city":"Frankfurt",
    "country":"Germany",
    "iso3166":"de",
    "locationLatitude":-1.4521000385284424,
    "locationLongitude":-1.7853200435638428,
    "link":[
      {
        "href":"http://localhost:9000/api/geo/1",
        "rel":"self"
      }
    ]
  },
  {
    "region":"Asia",
    "city":"Sydney",
    "country":"Australia",
    "iso3166":"au",
    "locationLatitude":-33.849998474121094,
    "locationLongitude":151.1999969482422,
    "link":[
      {
        "href":"http://localhost:9000/api/geo/2",
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

## GET /api/geo/{geo_id}

###Description

Returns the geo. location entity identified by the given {geo_id}.

###Request Parameters

Parameter        | Description
-------------    | -------------
geo_id           | The id of the geo. location.

###Response

The geo. location entity identified by the given id.

###Response Example

```
{
  "region":"Asia",
  "city":"Sydney",
  "country":"Australia",
  "iso3166":"au",
  "locationLatitude":-33.849998474121094,
  "locationLongitude":151.1999969482422,
  "link":[
    {
      "href":"http://localhost:9000/api/geo/2",
      "rel":"self"
    }
  ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/geo

###Description

Creates a new location entity. The new entity will be returned.

###Request Parameters

Parameter         | Description
----------------- | -------------
region            | The geographical region of the location.
city              | The city.
country           | The country.
iso3166           | The iso3166-Alpha2 code of the country.
locationLatitude  | The latitude of the location.
locationLongitude | The longitude of the location.

###Request Example

```
{
  "region":"Asia",
  "city":"Sydney",
  "country":"Australia",
  "iso3166":"au",
  "locationLatitude":-33.85,
  "locationLongitude":151.2
}
```

###Response

The created entity. See GET /api/geo/{geo_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/geo/{geo_id}

###Description

Updates the geo. location entity identified by the given id.

###Request Parameters

Parameter         | Description
----------------- | -------------
geo_id            | The id of the geo. location to update.
region            | The geographical region of the location.
city              | The city.
country           | The country.
iso3166           | The iso3166-Alpha2 code of the country.
locationLatitude  | The latitude of the location.
locationLongitude | The longitude of the location.

###Request Example 
```
PUT /api/geo/2
```
```
{
  "region":"Asia",
  "city":"Sydney",
  "country":"Australia",
  "iso3166":"au",
  "locationLatitude":-33.85,
  "locationLongitude":151.2
}
```

###Response

The updated entity. See GET /api/geo/{geo_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/geo/{geo_id}

###Description

Deletes the geo. location entity identified by the given {geo_id}.

###Request Parameters

Parameter | Description
--------- | -------------
geo_id    | The id of the geo. location to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)