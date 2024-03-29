# Creating an SDK

The cross-compile toolchain built and used by ISAR can also be used outside of ISAR. Beside the dependencies mentioned for the QEMU build above, patchelf is also required:

    sudo apt install docker.io qemu-system-riscv64 patchelf

To create an SDK archive, start an ISAR build for QEMU and include the sdk.yml file as additional parameter:

    ./kas-container build kas-qemu.yml:kas/opt/sdk.yml

Choose an SDK installation directory (named $MY_SDK_PATH here) and extract the created SDK archive:

    tar xf build/tmp/deploy/images/qemuriscv64/sdk-debian-sid-ports-riscv64.tar.xz -C $MY_SDK_PATH/

Relocate the toolchain:

```
cd $MY_SDK_PATH/sdk-debian-sid-ports-riscv64
./relocate-sdk.sh
``` 

Optional: test the toolchain by compiling e.g. a hello world example:

    $MY_SDK_PATH/sdk-debian-sid-ports-riscv64/usr/bin/riscv64-linux-gnu-gcc -o hello hello.c

Optional: run the hello world executable within QEMU (see [README](../README.md) how to build an image for QEMU and start it):

```
# copy executable from host system
qemu$ scp $USER@10.0.2.2:path/to/hello .
# run it
qemu$ ./hello
```

