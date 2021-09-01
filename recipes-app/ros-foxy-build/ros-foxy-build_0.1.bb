# Copyright (c) Siemens AG, 2021
#
# Authors:
#  Guillaume Pais <guillaume.pais@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#

DESCRIPTION = "Scripts to build ROS2 with demo"

SRC_URI = "file://demo_build.sh \
           file://demo.repos \
           file://demo_run.sh \
           file://demo_debianize.sh \
           file://ros_base_build.sh \
           file://ros_base_debianize.sh \
           file://ros_base.repos"

inherit dpkg-raw

do_install() {
	install -v -d ${D}/root
	install -v -d ${D}/root/scripts
	install -v -m 0755 ${WORKDIR}/demo_build.sh         ${D}/root/scripts
	install -v -m 0755 ${WORKDIR}/demo_debianize.sh     ${D}/root/scripts
	install -v -m 0644 ${WORKDIR}/demo.repos            ${D}/root/scripts
	install -v -m 0755 ${WORKDIR}/demo_run.sh           ${D}/root/scripts
	install -v -m 0755 ${WORKDIR}/ros_base_build.sh     ${D}/root/scripts
	install -v -m 0755 ${WORKDIR}/ros_base_debianize.sh ${D}/root/scripts
	install -v -m 0644 ${WORKDIR}/ros_base.repos        ${D}/root/scripts
}
