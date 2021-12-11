<h1 align="center">OurAirports2Json</h1>
<h3 align="center">Web service for JSON responsing of airports data from https://ourairports.com/</h3>

<p align="center">
<a href="https://github.com/alexmaryin/ourairports_json/actions/workflows/gradle.yml"><img src="https://github.com/alexmaryin/ourairports_json/actions/workflows/gradle.yml/badge.svg"></a>
<a href="https://github.com/alexmaryin/ourairports_json/releases"><img src="https://img.shields.io/github/release/alexmaryin/ourairports_json.svg"></a>
<a href="https://en.wikipedia.org/wiki/Representational_state_transfer"><img src="https://img.shields.io/badge/interface-REST-brightgreen.svg"></a>
</p>

<h4 align="center">
Allows to receive World airports information in JSON format originated from https://ourairports.com
</h4>
<hr style="height:1px;border-width:0;color:gray">

<h3>Best way to try API online and discover it is <a href="https://app.swaggerhub.com/apis-docs/alexmaryin/OurAirports/1.0">Swagger documentation</a></h3>

## Disclaimer
*Please don't use this API as an officially supported information for the purposes of real flight-planning. Information grabbed here has been created and maintains
by whole-the-world community and may contains out of dated details for airports, radio frequencies and runways conditions.*

## Base URL

Deployed on Amazon Cloud `http://ec2-13-51-70-132.eu-north-1.compute.amazonaws.com`

Delpoyed on Heroku Cloud `https://ourairports-json.herokuapp.com`

## Authentication

API doesn't require any authentication.

## Routes

### Airports

Return Airport with radio frequencies and runways details.

* Get airports JSON details `/api/v1/airports/:icao` where `icao` is 4-letter ICAO code of airport

### Runways

Return runways details for airport with specified ICAO.

* Get runways JSON array for concerned airport `/api/v1/runways/:icao` where `icao` is 4-letter ICAO code of airport

### Frequencies

Return radio details for airport with specified ICAO.

* Get radio JSON array for concerned airport `/api/v1/frequencies/:icao` where 'icao' is 4-letter ICAO code of airport

### Response status

* `200 OK` if success
* `400 Bad request` if icao code isn't correct
* `404 Not found` if API database hasn't any details of required airport

## Schemas

### Airport
```json
"Airport": {
          "icao": {
            "type": "string",
          },
          "type": {
            "type": "string",
            "enum": ["closed_airport", "heliport", "large_airport", "medium_airport", "seaplane_base", "small_airport"],
          },
          "name": {
            "type": "string",
          },
          "latitude": {
            "type": "number",
          },
          "longitude": {
            "type": "number",
          },
          "elevation": {
            "type": "integer",
            "format": "int32",
          },
          "webSite": {
            "type": "string",
          },
          "wiki": {
            "type": "string",
          },
          "frequencies": [
            "items": "Frequency"
          ],
          "runways": [
            "items": "Runway"
          ]
        }
```
Airport type may have one of the following values:
* closed_airport
* heliport
* large_airport
* medium_airport
* seaplane_base
* small_airport

### Runways
```json
"Runway": {
          "lengthFeet": {
            "type": "integer",
            "format": "int32",
          },
          "widthFeet": {
            "type": "integer",
            "format": "int32",
          },
          "surface": {
            "type": "string",
            "enum": ["ASP", "TURF", "CON", "GRS","GRE", "WATER","UNK"],
          },
          "closed": {
            "type": "boolean"
          },
          "lowNumber": {
            "type": "string",
          },
          "lowElevationFeet": {
            "type": "integer",
            "format": "int32",
          },
          "lowHeading": {
            "type": "integer",
            "format": "int32",
          },
          "highNumber": {
            "type": "string",
          },
          "highElevationFeet": {
            "type": "integer",
            "format": "int32",
          },
          "highHeading": {
            "type": "integer",
            "format": "int32",
          }
        }
```
Surface type may have one of the following values:
* ASP - asphalt
* TURF - turf
* CON - concrete
* GRS - grass
* GRE - gravel
* WATER - water
* UNK - unknown

### Frequency
```json
"Frequency": {
          "type": {
            "type": "string",
            "enum": ["TML", "CTAF", "CTR", "AFIS", "APP", "RMP", "ARR", "ATIS", "UNIC", "DEL", "CTR", "DEP", "FIRE", "GND", "TWR", "UNKNOWN"],
          },
          "description": {
            "type": "string",
          },
          "valueMhz": {
            "type": "number",
            "format": "float",
          }
        }
```
Frequency type may have one of the following values:
* TML - terminal area
* CTAF - flight advisory service
* CTR - radar/control area
* UNKNOWN
* AFIS - flight information service
* APP - approach
* RMP - apron
* ARR - arrival
* ATIS - weather observation automatic service
* UNIC - unicom
* DEL - delivery
* DEP - departure
* FIRE - emergency service
* GND - ground
* TWR - tower

## License

Copyright 2021, Alex Maryin

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:


The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.


THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.







