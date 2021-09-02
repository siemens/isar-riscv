# Copyright (c) Siemens AG, 2021
#
# Authors:
#  Guillaume Pais <guillaume.pais@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#

inherit dpkg-base

DESCRIPTION = "Scripts to deploy ROS2"

SRC_URI = "file://ros-foxy-base-bin_1.0-1_riscv64.deb;unpack=false \
           file://ros-foxy-demo-bin_1.0-1_riscv64.deb;unpack=false "

PROVIDES += "ros-foxy-base-bin ros-foxy-demo-bin"

do_dpkg_build() {
}
