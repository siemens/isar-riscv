# Isar RISC-V

This [Isar](https://github.com/ilbers/isar) layer provides recipes and configurations to add support for RISC-V architecture.

Dedicated files in [doc](doc) folder contain information how to [build for and run on NOEL-V platform](doc/NOELV.md), how to [include ROS2](doc/ROS2.md) and how to [create an SDK](doc/SDK.md)

## Building for QEMU

To build an image for QEMU and running it, docker and QEMU with RISC-V support are needed. On a Debian-based system, these can be installed using the following command:

    sudo apt install docker.io qemu-system-riscv64

Run the following command to start the build:

    ./kas-container build kas-qemu.yml

The QEMU build by default uses the Linux kernel from the Debian repositories. If you need to use the same kernel as in the NOEL-V build, please include a second .yml file as shown below.

    ./kas-container build kas-qemu.yml:kas/force-noelv-kernel.yml

## Running in QEMU

Start the generated image in QEMU using this command:

    export IMAGE_NAME=base
    qemu-system-riscv64 -m 1G -M virt -cpu rv64 \
        -netdev user,id=vnet,hostfwd=:127.0.0.1:12345-:22 -device virtio-net-pci,netdev=vnet \
        -drive file=build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64.ext4.img,if=none,format=raw,id=hd0 \
        -device virtio-blk-device,drive=hd0 \
        -device loader,file=build/tmp/deploy/images/qemuriscv64/fw_jump.elf,addr=0x80200000 \
        -kernel build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64-vmlinux \
        -initrd build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64-initrd.img \
        -append "console=ttyS0 root=/dev/vda rw" -nographic -snapshot

Two users are preconfigured: "root" and "riscv". For both, the password equals the user name.

### SSH Access to QEMU VM

The QEMU invocation shown above forwards host port 12345 to VM port 22. Accessing the VM via SSH is possible by executing

```$ ssh -p 12345 riscv@localhost```


