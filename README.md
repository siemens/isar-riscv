# Isar RISC-V

This [Isar](https://github.com/ilbers/isar) layer provides recipes and configurations to add support for RISC-V architecture.

## Building for QEMU

To build an image for QEMU and running it, docker and QEMU with RISC-V support are needed. On a Debian-based system, these can be installed using the following command:

    sudo apt install docker.io qemu-system-riscv64

Run the following command to start the build:

    ./kas-container build kas-qemu.yml

## Running in QEMU

Start the generated image in QEMU using this command:

    qemu-system-riscv64 -m 1G -M virt -cpu rv64 \
        -drive file=build/tmp/deploy/images/qemuriscv64/isar-image-base-debian-sid-ports-qemuriscv64.ext4.img,if=none,format=raw,id=hd0 \
        -device virtio-blk-device,drive=hd0 -device loader,file=build/tmp/deploy/images/qemuriscv64/fw_jump.elf,addr=0x80200000 \
        -kernel build/tmp/deploy/images/qemuriscv64/isar-image-base-debian-sid-ports-qemuriscv64-vmlinux \
        -initrd build/tmp/deploy/images/qemuriscv64/isar-image-base-debian-sid-ports-qemuriscv64-initrd.img \
        -append "console=ttyS0 root=/dev/vda rw" -nographic -snapshot

User name and password to log in are both "root".

## Building ROS2

Use the development image for ROS2:

    ./kas-container build kas-qemu.yml:kas/ros2-devel.yml

In QEMU build ros-foxy-ros-base, build and run the demo:
```
cd scripts
./ros_base_build.sh
./ros_base_debianize.sh
./demo_build.sh
./demo_debianize.sh
./demo_run.sh
```
In the process 2 debian packages are built ros-foxy-base-bin_1.0-1_riscv64.deb and ros-foxy-demo-bin_1.0-1_riscv64.deb. These debian packages shall be saved on a server and later on copied in the folder ``` recipes-app/ros-foxy-base-bin/files/ ```. After these steps are done the minimal ROS2 image can be created:

    ./kas-container build kas-qemu.yml:kas/ros2.yml
