# Running ROS2 on RISC-V

### Building ROS2 debian packages

ROS2 does not come with RISC-V debian packages. Therefore, it is necessary to rebuild the libraries. A development image (with more memory and more development packages) is used for ROS2:

    ./kas-container build kas-qemu.yml:kas/ros2-devel.yml
    export IMAGE_NAME=ros2-devel
    qemu-system-riscv64 -m 8G -M virt -cpu rv64 -smp 8 \
        -netdev user,id=vnet,hostfwd=:127.0.0.1:12345-:22 -device virtio-net-pci,netdev=vnet \
        -rtc base=localtime,clock=host \
        -drive file=build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64.ext4.img,if=none,format=raw,id=hd0 \
        -device virtio-blk-device,drive=hd0 -device loader,file=build/tmp/deploy/images/qemuriscv64/fw_jump.elf,addr=0x80200000 \
        -kernel build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64-vmlinux \
        -initrd build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64-initrd.img \
        -append "console=ttyS0 root=/dev/vda rw" -nographic

In QEMU, log in as root, build ros-foxy-ros-base (might need several hours), then build the demo and run it:
```
cd scripts
./ros_base_build.sh
./ros_base_debianize.sh
./demo_build.sh
./demo_debianize.sh
./demo_run.sh
```
In the process, 2 debian packages are built ```ros-foxy-base-bin_1.0-1_riscv64.deb``` and ```ros-foxy-demo-bin_1.0-1_riscv64.deb```. These debian packages have to be exported from QEMU, e.g. by copying them to the host from QEMU via SSH:
```
scp ~/*.deb <user>@10.0.2.2:
```

### Build and start ROS2 image
Copy the ROS2 debian packages (```ros-foxy-base-bin_1.0-1_riscv64.deb``` and ```ros-foxy-demo-bin_1.0-1_riscv64.deb```) in the folder ``` recipes-app/ros-foxy-base-bin/files/ ```. Then build and start the minimal ROS2 image:

    ./kas-container build kas-qemu.yml:kas/ros2.yml
    export IMAGE_NAME=base
    qemu-system-riscv64 -m 1G -M virt -cpu rv64 \
        -drive file=build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64.ext4.img,if=none,format=raw,id=hd0 \
        -device virtio-blk-device,drive=hd0 -device loader,file=build/tmp/deploy/images/qemuriscv64/fw_jump.elf,addr=0x80200000 \
        -kernel build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64-vmlinux \
        -initrd build/tmp/deploy/images/qemuriscv64/isar-image-${IMAGE_NAME}-debian-sid-ports-qemuriscv64-initrd.img \
        -append "console=ttyS0 root=/dev/vda rw" -nographic -snapshot

### Run the demos
Try the demos:

    source /root/demo_ws/install/setup.bash
    ros2 launch composition         composition_demo.launch.py
    <Ctrl-C>
    ros2 launch demo_nodes_cpp      add_two_ints.launch.py
    <Ctrl-C>
    ros2 launch demo_nodes_cpp      add_two_ints_async.launch.py
    <Ctrl-C>
    ros2 launch demo_nodes_cpp      talker_listener.launch.py
    <Ctrl-C>
    ros2 launch demo_nodes_cpp      talker_listener_best_effort.launch.py
    <Ctrl-C>
    ros2 launch dummy_robot_bringup dummy_robot_bringup.launch.py
    <Ctrl-C>
    ros2 launch lifecycle           lifecycle_demo.launch.py
    <Ctrl-C>
    ...