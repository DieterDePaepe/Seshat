#Setting up a non-persistent MongoDB

```$ docker run --name mongo-db -d mongo:3.2.4```

#Storing a template using curl

    $ cat template.json
    {
      "data": {
        "type": "template",
        "attributes": {
          "name": "My first template",
          "fields": [
            {
              "name": "Time",
              "datatype": "DATETIME"
            }
          ]
        }
      }
    }

    $ curl -XPOST --data @template.json localhost:8080/templates