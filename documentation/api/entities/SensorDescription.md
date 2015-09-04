# SensorDescription Actions
***

##Description
The SensorDescription entity represents the SensorDescription.

## GET /api/sensorDescription

###Description
Returns a list of SensorDescription types supported by the system.

###Request Parameters
None

###Response
A list of all sensorDescription entities stored in the database.

###Response Example
```
[
    {
        "className":"de.uniulm.omi.cloudiator.visor.monitoring.sensors.CpuUsageSensor",
        "metricName":"cpu_usage",
        "isVmSensor":true,
        "link":
        [
            {
                "href":"http://localhost:9000/api/sensorDescription/1",
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

## GET /api/sensorDescription/{sensorDescription_id}

###Description

Returns the SensorDescription entity identified by the given {sensorDescription_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
sensorDescription_id      | The id of the sensorDescription.

###Response 
The SensorDescription entity identified by the given id.

###Response Example
```
{
    "className":"de.uniulm.omi.cloudiator.visor.monitoring.sensors.CpuUsageSensor",
    "metricName":"cpu_usage",
    "isVmSensor":true,
    "link":
    [
        {
            "href":"http://localhost:9000/api/sensorDescription/1",
            "rel":"self"
        }
    ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/sensorDescription

###Description

Creates a new SensorDescription entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
className          | The name of the class for deploying the sensor.
metricName          | The name of the metric.
isVmSensor          | Determines if the sensor is bound to the VM or component.

###Request Example
```
{
    "className":"de.uniulm.omi.cloudiator.visor.monitoring.sensors.CpuUsageSensor",
    "metricName":"cpu_usage",
    "isVmSensor":true
}
```

###Response Codes

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/sensorDescription/{sensorDescription_id}

###Description

Updates the SensorDescription entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
sensorDescription_id      | The id of the sensorDescription to update.
className          | The name of the class for deploying the sensor.
metricName          | The name of the metric.
isVmSensor          | Determines if the sensor is bound to the VM or component.

###Request Example
```
PUT /api/sensorDescription/1
```
```
{
    "className":"de.uniulm.omi.cloudiator.visor.monitoring.sensors.CpuUsageSensor",
    "metricName":"cpu_usage",
    "isVmSensor":true
}
```

###Response

The updated entity. See GET /api/sensorDescription/{sensorDescription_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/sensorDescription/{sensorDescription_id}

###Description

Deletes the SensorDescription entity identified by the given {sensorDescription_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
sensorDescription_id      | The id of the sensorDescription to delete.

###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
