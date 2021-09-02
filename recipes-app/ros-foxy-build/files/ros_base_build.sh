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
  echo "Create a bundle for the ros_base"
}

if [[ "$1" == "-h" || ("$1" == "--help") ]]
then
  display_usage
  exit 0
fi


set -e #exit on error
set -x #echo on

pip3 install colcon-common-extensions
pip3 install vcstool

# find the path where this script is located
SCRIPTPATH=$( cd $(dirname $0) ; pwd -P )
WORKSPACE=$SCRIPTPATH/../ros2_base_ws
mkdir -p $WORKSPACE/src
vcs import $WORKSPACE/src < $SCRIPTPATH/ros_base.repos
cd $WORKSPACE
colcon build --continue-on-error --merge-install --install-base /opt/ros/foxy/
