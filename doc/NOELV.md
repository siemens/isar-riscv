# NOELV

## Required Build Artifacts

This Isar build configuration supports a setup where firmware image and device tree blob are loaded via debugger and the file system resides on an NFS server.

### Building Firmware Image and File System Archive

The following command starts the build for NOELV (and requires docker to be installed):

    ./kas-container build kas-noelv.yml

Alternatively, to include the pre-built ROS2 base package in the file system image, copy the ROS2 debian packages (```ros-foxy-base-bin_1.0-1_riscv64.deb``` and ```ros-foxy-demo-bin_1.0-1_riscv64.deb```) to the folder ```recipes-app/ros-foxy-base-bin/files/```. Add the respective kas configuration file to the build command as shown below. Information how to build the ROS2 base package for RISC-V are available [here](ROS2.md).

    ./kas-container build kas-noelv.yml:kas/ros2.yml

Relevant build artifacts are
  - the firmware image to be loaded via debugger: ```build/tmp/deploy/images/noelv/fw_payload.elf```
  - the file system tar archive to be extracted on the NFS server: ```build/tmp/deploy/images/noelv/isar-image-base-debian-sid-ports-noelv.tar.gz```

### Configuring IP Address of NOELV System and NFS Server

The kernel command line included in the device tree configuration needs to be adapted to make the kernel boot from NFS.

Install the device tree compiler

    apt install device-tree-compiler

Download the NOEL-XCKU [device tree configuration](https://www.gaisler.com/products/noel-v/20210208/NOEL-XCKU/noel-xcku-20210208.tar.gz) archive listed on [NOEL-XCKU website](https://www.gaisler.com/index.php/products/processors/noel-v-examples/noel-xcku) and extract it

```
wget https://www.gaisler.com/products/noel-v/20210208/NOEL-XCKU/noel-xcku-20210208.tar.gz
tar xf noel-xcku-20210208.tar.gz
```

Open file ```noel-xcku-ex4.dts``` in a text editor and locate section "chosen". Update value of key "bootargs" to match your NFS server setup. Example for configuration with fixed IP addresses:

```
chosen {
    bootargs = "root=/dev/nfs rw nfsroot=192.168.1.1:/mnt/nfs/noelv,v3 ip=192.168.1.123:::255.255.255.0:noelv earlycon=sbi console=ttyGR0,115200";
  };
```

Detailed information on NFS-related kernel command line parameters is available on [kernel.org](https://www.kernel.org/doc/html/latest/admin-guide/nfs/nfsroot.html).

Convert textual configuration to device tree blob using device tree compiler

    dtc -I dts -O dtb -o noel-xcku-ex4.dtb noel-xcku-ex4.dts

The resulting file ```noel-xcku-ex4.dtb``` can be loaded via debugger.

## Using APT to Install Additional Packages on a Live System

Two users are preconfigured: "root" and "riscv". For both, the password equals the user name.

When the system is running, APT can be used to install Debian packages. APT requires network connection to snapshot.debian.org to read the package list and install packages. If a DHCP server is used to configure networking for the NOELV system, internet access might already be available.

### Optional: Forwarding Internet Traffic to Proxy Server

If using an HTTP proxy server is mandatory to access the internet, forwarding a dedicated port from the NOELV system to the proxy server is typically a viable solution. This assumes that the proxy server is accessible from the machine used to connect to the NOELV system:
```
# set proxy
ssh riscv@$NOELV_IP -R 12345:$PROXY_NAME:$PROXY_PORT
```

### Update Package List and Install Package
```
ssh riscv@$NOELV_IP

# become root
noelv$ sudo -s

# optional: set proxy server
noelv$ export http_proxy="http://127.0.0.1:12345"
noelv$ export https_proxy=$http_proxy

# set system date (deviation from today's date might lead to issues such as certificate-related errors)
noelv$ date -s '2021-10-21 17:42'

# disable APT certificate check
noelv$ touch /etc/apt/apt.conf.d/99verify-peer.conf
noelv$ echo >>/etc/apt/apt.conf.d/99verify-peer.conf "Acquire { https::Verify-Peer false }"

# update APT package list, this might take some time
noelv$ apt update

# install additional packages, e.g. nano
noelv$ apt install nano
```
