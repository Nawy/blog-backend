#!/bin/sh
docker build . -t blog-test
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 8080:8080 blog-test"
