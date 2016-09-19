#!/bin/bash

# Solr
export SOLR_URLS="mckvdatpdb08 mckvdatpdb09 mckvdatpdb10"
export SOLR_CORES="core1 core2 core3 core4 core5 core6"

echo "Sending the request to all Solr instances..."
for url in $SOLR_URLS; do
    for core in $SOLR_CORES; do
        echo "** Sending request message to:  $url/$core "
	curl "http://$url:8983/solr/$core/select/?q=*:*&rows=0&wt=json"
    done
done

