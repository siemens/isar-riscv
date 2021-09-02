# Copyright (c) Siemens AG, 2021
#
# Authors:
#  Guillaume Pais <guillaume.pais@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#
require recipes-core/images/isar-image-base.bb

IMAGE_INSTALL += "ros-foxy-base-bin \
                  ros-foxy-demo-bin"

# Extra space for rootfs in MB
ROOTFS_EXTRA = "1024"

