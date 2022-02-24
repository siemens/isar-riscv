# Copyright (c) Siemens AG, 2022
#
# Authors:
#  Steffen Gerhold <steffen.gerhold@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#

# Workaround for ttyS0 timeout issue during start-up: 
# [ TIME ] Timed out waiting for device /dev/ttyS0.
# [DEPEND] Dependency failed for Serial Getty on ttyS0.
#
# This workaround is needed to reach login prompt.
# To be removed after the root cause is fixed.

DESCRIPTION = "Workaround for ttyS0 timeout issue during start-up"

SRC_URI = "file://serial-getty@.service"

inherit dpkg-raw

do_install() {
    install -v -d ${D}/etc/systemd/system/
    install -v -m 644 ${WORKDIR}/serial-getty@.service ${D}/etc/systemd/system/
}
