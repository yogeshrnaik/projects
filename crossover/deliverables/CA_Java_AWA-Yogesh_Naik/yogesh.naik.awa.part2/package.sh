#!/bin/bash
THIS_DIR="$(basename $( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd ))"
TARBALLNAME=${THIS_DIR}.tgz
mvn -q clean
pushd ..
tar zcf ${TARBALLNAME} ${THIS_DIR}/pom.xml ${THIS_DIR}/src ${THIS_DIR}/package.sh
popd

