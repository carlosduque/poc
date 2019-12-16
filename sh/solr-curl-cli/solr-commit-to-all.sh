#!/bin/bash

# Solr
export SOLR_URLS="solr1 solr2 solr3"
export SOLR_CORES="core1 core2 core3 core4 core5 core6"

echo "Commiting to all Solr instances..."
for url in $SOLR_URLS; do
    for core in $SOLR_CORES; do
        echo "** Sending commit message to:  $url / $core "
        curl http://$url:8983/solr/$core/update -H 'Content-type:text/xml' --data-binary '<commit/>'
    done
done

