# samsara-core

Real-time event stream processing pipeline for Samsara Analytics

## How to build it

This projects uses [lein-bin plugin](https://github.com/Raynes/lein-bin) which
is required to be installed.

```
lein do clean, bin
```

The `lein-bin` will produce an executable uberjar in `./target/samsara-core`.

## How to run it

```
-----------------------------------------------
   _____
  / ___/____ _____ ___  _________ __________ _
  \__ \/ __ `/ __ `__ \/ ___/ __ `/ ___/ __ `/
 ___/ / /_/ / / / / / (__  ) /_/ / /  / /_/ /
/____/\__,_/_/ /_/ /_/____/\__,_/_/   \__,_/

  Samsara CORE
-----------------------------| v0.1.0- |-------

SYNOPSIS
       ./samsara-core -c config.edn

DESCRIPTION
       Starts a processing pipeline node which will start
       consuming messages from the Ingestion-API sent to
       Kafka and produce a richer stream of event back
       into Kafka.

  OPTIONS:

  -c --config config-file.edn [REQUIRED]
       Configuration file to set up the processing node.
       example: './config/config.edn'

  -h --help
       this help page

```

The configuration is a EDN file with the following format.

```Clojure
{:topics
   {:job-name "Samsara"
    :input-topic "ingestion"
    :output-topic "events"
    ;; a CSV list of hosts and ports (and optional path)
    :zookeepers "127.0.0.1:2181"
    ;; a CSV list of host and ports of kafka brokers
    :brokers "127.0.0.1:9092"
    :offset :smallest}
}
```

## How to build and run the Docker container

A `Dockerfile` is available for this project. To build it just run:

```
lein do clean, bin
docker build -t samsara/samsara-core .
```
Then to run the container:

```
# it is important that you pass the `-i` option to create a stdin chanel
docker run -tdi -p 15000:15000 -v /tmp/samsara-core:/logs \
        --link=samsaradockerimages_kafka_1:kafka_1 \
        --link=samsaradockerimages_kafka_2:kafka_2 \
        --link=samsaradockerimages_kafka_3:kafka_3 \
        --link=samsaradockerimages_zookeeper_1:zookeeper_1 \
        --link=samsaradockerimages_zookeeper_2:zookeeper_2 \
        --link=samsaradockerimages_zookeeper_3:zookeeper_3 \
        samsara/samsara-core
```

The linked containers will then be used for configuration autodiscovery.

This will expose port `15000` on your docker host. It will mount
a volume to expose the container logs as `/tmp/samsara-core`.

You should be able to point your browser to [http://127.0.0.1:15000]
and log in with `admin` / `samsara` to access supervisord web console.

**NOTE: if you are running on OSX with `boot2docker` the host on wich
will the ports be exposed won't be the local host but the
`boot2docker` virtual machine which by default is on `192.168.59.103`
(check your $DOCKER_HOST).** This means that instead of accessing
[http://127.0.0.1:15000] you will have to access
[http://192.168.59.103:15000] and that the mounted volume
(`/tmp/samsara-core`) will be on the `boot2docker` virtual machine.
To access them you can log into `boot2docker` with the following
command: `boot2docker ssh`.


## Tracking of metrics

To track metrics I use [TRACKit!](https://github.com/samsara/trackit)
and we expose the following metrics:

The `<job-name>` refers to the name gave in the configuration file
to the particular job (as `(-> config :job :job-name)`). This is
useful if multiple jobs are consuming the same topic with different
pipelines in order to be able to distinguish them in the monitoring
tool.
The `<topic>` refers to the kafka topic which you are consuming, by
default is `ingestion`.

```
# counters to track the total size of
# proceccesed data in bytes

pipeline.<job-name>.<topic>.in.total-size.count
pipeline.<job-name>.<topic>.out.total-size.count


# Track distribution of the message size

pipeline.<job-name>.<topic>.in.size
pipeline.<job-name>.<topic>.out.size

# for the above metrics these details are tracked

       count = number of messages processed
         min = min size
         max = max size
        mean = mean size
      stddev = standard deviation is size
      median = median size
         p75 <= various percentiles on size
         p95 <=           ''
         p98 <=           ''
         p99 <=           ''
        p999 <=           ''


# Tracking processing time and rate for
# pipeline processing : internal pipeline
# overall processing : including marshalling/unmarshalling
# global store : time and rate to restore the state (single kv-entry)
# local store : time and rate to restore the state (single kv-entry)


pipeline.<job-name>.<topic>.overall-processing.time
pipeline.<job-name>.<topic>.pipeline-processing.time
pipeline.<job-name>.stores.global.<dimension>.restore.time
pipeline.<job-name>.stores.local.<topic>.restore.time

             count = number of event processed
         mean_rate = mean rate x second
           m1_rate = rate x second over last 1 minute
           m5_rate = rate x second over last 5 minutes
          m15_rate = rate x second over last 15 minutes
               min = min execution time
               max = max execution time
              mean = mean execution time
            stddev = standard deviation on execution time
            median = mean execution time
              p75 <= various percentiles on execution time
              p95 <=               ''
              p98 <=               ''
              p99 <=               ''
             p999 <=               ''

```

These metrics can be pushed to any of the following systems: **graphite, grafana, ganglia, statsd, riemann.**

## License

Copyright © 2015-2017 Samsara's authors.

Distributed under the Apache License v 2.0 (http://www.apache.org/licenses/LICENSE-2.0)
