# meta-wpe-gstreamer-vf

This Yocto meta layer enables a seperate Gstreamer 1.10.x installation along side your existing Gstreamer for WPEWebKit. This may be required in case you have components dependent on (much) older Gstreamer installs and cannot upgrade without major impact. This will enable 2 Gstreamer installations side by side.

This layer will make the required changes to Gstreamer to install into `/usr/lib/gstreamer1.0-wpe/` and `libwpegst*` libraries to support it.

To verify the installation the binaries are prefixed with `wpe`. For example to test GST Launch:
```
	wpegst-launch playbin uri=<test>
```

Note that this is very experimental and mileage may vary based on the current status of your existing system. It is highly recommended to upgrade to 1.10 or newer to support WPEWebKit.
