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

# find the path where this script is located
SCRIPTPATH=$( cd $(dirname $0) ; pwd -P )
DEB_PACKAGE_VERSION=1.0-1
DEB_PACKAGE_NAME=ros-foxy-base-bin
DEB_PACKAGE_ARCH=riscv64
DEB_PATH=/root/${DEB_PACKAGE_NAME}_${DEB_PACKAGE_VERSION}_${DEB_PACKAGE_ARCH}/


mkdir -p ${DEB_PATH}/DEBIAN

CONTROL_FILE=$DEB_PATH/DEBIAN/control
echo Package: $DEB_PACKAGE_NAME > $CONTROL_FILE 
echo Version: $DEB_PACKAGE_VERSION >> $CONTROL_FILE
echo Architecture: $DEB_PACKAGE_ARCH >> $CONTROL_FILE
echo "Maintainer: xxx <xxx@siemens.com>" >> $CONTROL_FILE
echo Section: misc >> $CONTROL_FILE
echo Priority: optional >> $CONTROL_FILE
echo Description: The base for ROS2 compiled for RISC-V >> $CONTROL_FILE

mv /opt $DEB_PATH
dpkg-deb --build $DEB_PATH
