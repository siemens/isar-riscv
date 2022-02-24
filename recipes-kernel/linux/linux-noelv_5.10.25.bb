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
    https://cdn.kernel.org/pub/linux/kernel/v5.x/linux-${ARCHIVE_VERSION}.tar.xz \
    file://0001-serial-apbuart-Move-port-configuration-into-probe-fu.patch \
    file://0002-serial-apbuart-Remove-SPARC-dependency.patch \
    file://0003-serial-apbuart-Get-index-of-serial-line-from-DT-alia.patch \
    file://0004-net-ethernet-greth-Remove-SPARC-dependency.patch \
    file://0005-greth-Do-not-reset-GRETH-when-EDCL-is-active.patch \
    file://0006-RISC-V-Add-fence-after-setting-SATP.patch \
    file://0007-net_can-grcanfd-support-20210610.patch \
    file://0008-net-greth-temporary-fix-for-little-endian-swapped-de.patch \
    file://0009-grlib-drvpkg-20220206.patch \
    file://0010-add_grlib_tree_5.10.patch \
    file://riscv_defconfig"

SRC_URI[sha256sum] = "930ae76b9a3b64b98802849aca332d17a706f20595de21e1ae729b55ee461add"

S = "${WORKDIR}/linux-${ARCHIVE_VERSION}"

KERNEL_DEFCONFIG = "riscv_defconfig"

