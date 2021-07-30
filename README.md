# Isar RISC-V

This [Isar](https://github.com/ilbers/isar) layer provides recipes and configurations to add support for RISC-V architecture.

## Building for QEMU

To build an image for QEMU and running it, docker and QEMU with RISC-V support are needed. On a Debian-based system, these can be installed using the following command:

    sudo apt install docker.io qemu-system-riscv64

Run the following command to start the build:

    ./kas-container build kas-qemu.yml

Alternative: build the development image for ROS 2:

    ./kas-container build kas-qemu.yml:kas/ros2-devel.yml

## Running in QEMU

Start the generated image in QEMU using this command:

    qemu-system-riscv64 -m 1G -M virt -cpu rv64 \
        -drive file=build/tmp/deploy/images/qemuriscv64/isar-image-base-debian-sid-ports-qemuriscv64.ext4.img,if=none,format=raw,id=hd0 \
        -device virtio-blk-device,drive=hd0 -device loader,file=build/tmp/deploy/images/qemuriscv64/fw_jump.elf,addr=0x80200000 \
        -kernel build/tmp/deploy/images/qemuriscv64/isar-image-base-debian-sid-ports-qemuriscv64-vmlinux \
        -initrd build/tmp/deploy/images/qemuriscv64/isar-image-base-debian-sid-ports-qemuriscv64-initrd.img \
        -append "console=ttyS0 root=/dev/vda rw" -nographic -snapshot

User name and password to log in are both "root".
