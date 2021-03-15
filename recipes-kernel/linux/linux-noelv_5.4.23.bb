# Copyright (c) Siemens AG, 2021
#
# Authors:
#  Steffen Gerhold <steffen.gerhold@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#

require recipes-kernel/linux/linux-custom.inc

ARCHIVE_VERSION = "${@ d.getVar('PV')[:-2] if d.getVar('PV').endswith('.0') else d.getVar('PV') }"

SRC_URI += " \
    https://cdn.kernel.org/pub/linux/kernel/v5.x/linux-${ARCHIVE_VERSION}.tar.xz"

SRC_URI[sha256sum] = "3f28aacdf5deddfdf80bb949884699b96053a3548dc3434552d30dc0bc781eca"

S = "${WORKDIR}/linux-${ARCHIVE_VERSION}"