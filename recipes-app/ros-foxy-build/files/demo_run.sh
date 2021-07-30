#! /bin/bash

# Copyright (c) Siemens AG, 2021
#
# Authors:
#  Guillaume Pais <guillaume.pais@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#


display_usage() {
  echo "Run the demo"
}

if [[ "$1" == "-h" || ("$1" == "--help") ]]
then
  display_usage
  exit 0
fi


set -e #exit on error
set -x #echo on

# find the path where this script is located
SCRIPTPATH=$( cd $(dirname $0) ; pwd -P )
WORKSPACE=$SCRIPTPATH/../demo_ws
source $WORKSPACE/install/setup.bash

export RMW_IMPLEMENTATION=rmw_cyclonedds_cpp

ros2 run demo_nodes_py listener &
ros2 run demo_nodes_py talker

