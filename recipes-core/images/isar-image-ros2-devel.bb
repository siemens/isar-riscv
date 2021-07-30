# Copyright (c) Siemens AG, 2021
#
# Authors:
#  Guillaume Pais <guillaume.pais@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#
require recipes-core/images/isar-image-base.bb

IMAGE_PREINSTALL += "apt-utils \
                     build-essential \
                     ca-certificates \
                     cmake \
                     conntrack \
                     curl \
                     dh-python \
                     dhcpcd5 \
                     ebtables \
                     ethtool \
                     git \
                     gnupg2 \
                     ifupdown \
                     iptables \
                     iproute2 \
                     iputils-ping \
                     libasio-dev \
                     libbullet-dev \
                     libconsole-bridge-dev \
                     libtinyxml2-dev \
                     libeigen3-dev \
                     locales \
                     lsb-release \
                     net-tools \
	             openssh-client \
                     python3-dev \
                     python3-distlib \
                     python3-empy \
                     python3-lark \
                     python3-notify2 \
                     python3-numpy \
                     python3-pip \
                     python3-pytest-cov \
                     python3-setuptools \
                     samba \
                     socat \
                     systemd \
                     vim \
                     wget \
                     "

# Extra space for rootfs in MB
ROOTFS_EXTRA = "8192"
