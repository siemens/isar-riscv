# Copyright (c) Siemens AG, 2021
#
# Authors:
#  Steffen Gerhold <steffen.gerhold@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License. See
# COPYING.MIT file in the top-level directory.
#

inherit dpkg

SRC_URI = " \
    https://github.com/riscv/opensbi/archive/v0.5.tar.gz \
    file://rules"

SRC_URI[sha256sum] = "bc82f1e63663cafb7976b324d8a01263510cfd816063dc89e0ccffb9763fb1dd"

S = "${WORKDIR}/opensbi-0.5"

DEBIAN_BUILD_DEPENDS ?= "device-tree-compiler"
DEBIAN_DEPENDS ?= "linux"

do_prepare_build[cleandirs] += "${S}/debian"
do_prepare_build[depends] += "linux-noelv:do_dpkg_build"
do_prepare_build() {
    # Copy Linux kernel binary so it can be integrated into OpenSBI binary as payload during OpenSBI build.
    # FIXME: Is there a more elegant way to do this than copying the Linux binary?
    cp "${TMPDIR}/work/debian-sid-ports-riscv64/linux-noelv/5.4.23-r0/linux-5.4.23/build-full/vmlinux" "${S}/"

    deb_debianize
}

do_deploy_deb[dirs] += "${DEPLOY_DIR_IMAGE}"
do_deploy_deb_append() {
    cp -f ${S}/build/platform/gaisler/noelv/firmware/fw_payload.elf "${DEPLOY_DIR_IMAGE}/"
    cp -f ${S}/build/platform/gaisler/noelv/firmware/fw_payload.bin "${DEPLOY_DIR_IMAGE}/"
}

dpkg_runbuild_prepend() {
    export PLATFORM="gaisler/noelv"
    export FW_PAYLOAD_PATH="vmlinux"
}



