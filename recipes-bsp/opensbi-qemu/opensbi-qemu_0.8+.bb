# Copyright (c) Siemens AG, 2020
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#

inherit dpkg

SRC_URI = " \
    git://github.com/riscv/opensbi.git;protocol=https \
    file://rules"
SRCREV = "a04c46506a6e5bd7425bd63430645a6c85145b10"

S = "${WORKDIR}/git"

do_prepare_build[cleandirs] += "${S}/debian"
do_prepare_build() {
    deb_debianize

    echo "build/platform/generic/firmware/fw_jump.elf /usr/lib/opensbi/qemu/" > ${S}/debian/install
}

do_deploy_deb[dirs] += "${DEPLOY_DIR_IMAGE}"
do_deploy_deb_append() {
    cp -f ${S}/build/platform/generic/firmware/fw_jump.elf "${DEPLOY_DIR_IMAGE}/"
}

dpkg_runbuild_prepend() {
    export PLATFORM="generic"    
}
