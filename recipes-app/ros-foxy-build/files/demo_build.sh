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
  echo "Build the ROS demo"
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
source /opt/ros/foxy/setup.bash
WORKSPACE=$SCRIPTPATH/../demo_ws
mkdir -p $WORKSPACE/src
vcs import $WORKSPACE/src < $SCRIPTPATH/demo.repos
cd $WORKSPACE
colcon build --continue-on-error 
