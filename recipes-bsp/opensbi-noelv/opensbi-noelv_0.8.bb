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
    https://github.com/riscv/opensbi/archive/v0.8.tar.gz \
    file://0001-lib-utils-serial-Add-support-for-Gaisler-APBUART.patch \
    file://rules "

SRC_URI[sha256sum] = "17e048ac765e92e15f7436b604452614cf88dc2bcbbaab18cdc024f3fdd4c575"

S = "${WORKDIR}/opensbi-0.8"

DEBIAN_BUILD_DEPENDS ?= "device-tree-compiler"
DEBIAN_DEPENDS ?= "linux"

do_prepare_build[cleandirs] += "${S}/debian"
do_prepare_build[depends] += "linux-noelv:do_dpkg_build"
do_prepare_build() {
    # Copy Linux kernel binary so it can be integrated into OpenSBI binary as payload during OpenSBI build.
    # FIXME: Is there a more elegant way to do this than copying the Linux binary?
    cp "${TMPDIR}/work/debian-sid-ports-riscv64/linux-noelv/5.10.25-r0/linux-5.10.25/build-full/arch/riscv/boot/Image" "${S}/"    

    deb_debianize
}

do_deploy_deb[dirs] += "${DEPLOY_DIR_IMAGE}"
do_deploy_deb_append() {
    cp -f ${S}/build/platform/generic/firmware/fw_payload.elf "${DEPLOY_DIR_IMAGE}/"
    cp -f ${S}/build/platform/generic/firmware/fw_payload.bin "${DEPLOY_DIR_IMAGE}/"
}

dpkg_runbuild_prepend() {    
    export PLATFORM="generic"
    export FW_PAYLOAD_PATH="Image"
    export FW_TEXT_START=0x0
    export PLATFORM_RISCV_ABI="lp64d"
}



