# Ip Address Actions
***
## Description
An ip address represents the ip address under which a virtual machine is available.
***
## GET /api/ip
###Description
Lists all ip addresses available in the system.

###Request Parameters
None

###Response
A list of all ip address entities stored in the database.

###Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/ip/1",
            "rel":"self"
         }
      ],
      "ip":"93.184.216.34",
      "ipType":"PUBLIC",
      "virtualMachine":1,
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/ip/2",
            "rel":"self"
         }
      ],
      "ip":"2001:0db8:85a3:08d3:1319:8a2e:0370:7344",
      "ipType":"PRIVATE",
      "virtualMachine":1,
   },
]
```
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/ip/{ip_id}

###Description
Shows the ip address entity identified by the given {ip_id}.

###Request Parameters
Parameter             | Description
-------------         | -------------
ip_id                 | The id of the ip address.

###Response
Shows the selected ip address entity.

###Response Example
```
{
  "links":[
     {
        "href":"http://example.com:9000/ip/1",
        "rel":"self"
     }
  ],
  "ip":"2001:0db8:85a3:08d3:1319:8a2e:0370:7344",
  "ipType":"PRIVATE",
  "virtualMachine":1,
},
```
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***
## POST /api/ip

###Description
Creates an ip address and returns it.

###Request Parameters

Parameter       | Description
--------------- | ----------------------------------------------------------
ip              | The ip address (either ipV4 or ipV6)
ipType          | The type of the address, either PUBLIC or PRIVATE.
virtualMachine  | The id of the related virtual machine.

###Request Example
```
{
   "ip":"2001:0db8:85a3:08d3:1319:8a2e:0370:7344",
   "ipType":"PRIVATE",
   "virtualMachine":1,
}
```

###Response
Returns the create ip address. See /api/ip/{ip_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***
## PUT /api/ip/{ip_id}

###Description
Updates the ip with the information given from the request.

###Request Parameters
Parameter       | Description
--------------- | -------------
ip_id           | The id of the ip address.
ip              | The ip address (either ipV4 or ipV6)
ipType          | The type of the address, either PUBLIC or PRIVATE.
virtualMachine  | The id of the related virtual machine.

###Request Example
```
PUT /api/ip/1
```
```
{
   "ip":"2001:0db8:85a3:08d3:1319:8a2e:0370:7344",
   "ipType":"PRIVATE",
   "virtualMachine":1,
}
```
###Response
The updated ip address. See GET /api/ip/{ip_id}

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***
## DELETE /api/ip/{ip_id}

###Request Parameters

Parameter           | Description
------------------- | ---------------------------------------
ip_id               | The id of the ip address to delete.

### Response
No data.

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)