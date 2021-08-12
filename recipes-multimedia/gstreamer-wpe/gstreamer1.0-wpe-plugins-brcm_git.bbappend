FILESEXTRAPATHS_prepend := "${THISDIR}/files/gstreamer1.0-wpe-plugins-brcm:"

DEPENDS_remove = "gstreamer1.0"
DEPENDS = "gstreamer1.0-wpe gstreamer1.0-wpe-plugins-base glib-2.0 glib-2.0-native"

PACKAGECONFIG_append = "svp"

SRC_URI_remove = "git://git@github.com/Metrological/bcm-gstreamer.git;protocol=ssh;branch=${GST_BCM_VERSION_BRANCH}"
SRC_URI_append = " \
    git://code.rdkcentral.com/r/collaboration/rdk/components/generic/gst-plugins-soc/soc/broadcom;protocol=https;branch=master \
    file://0001-Do-not-minimize-composition-on-decoder-startup.patch \
    file://0002-Revert-BCMCZ-121-remove-gstsvpmeta.h.patch \
    file://0003-Adjust-condition-for-freezing-the-system-clock.patch \
    file://0004-Changes-to-STC-for-audio-glitches.patch \
    file://0005-Respond-to-position-query-in-bytes-from-pcm-sink.patch \
"

SRCREV = "6c27372afb3ed20eab1e6d35ba54225e778f39f8"

patch_pc_files () {
    for f in `find ${S} -name Makefile.in -o -name Makefile.am -o -name configure.ac`; do \
        sed -i \
        -e "s;\[gstreamer-;\[wpe-gstreamer-;g" \
        -e "s;plugindir=.*;plugindir=\"\\\\$\(libdir\)/gstreamer\-\$\{GST_MAJORMINOR\}\-wpe\";g" \
        $f; \
    done
	sed -i -e "s;gst-plugin-scanner;wpegst-plugin-scanner;g" ${S}/configure.ac
    sed -i -e "s;gst-ptp-helper;wpegst-ptp-helper;g" ${S}/configure.ac
    sed -i -e "s;-lgstvideo-1.0;-lwpegstvideo-1.0;g" ${S}/reference/videodecode/src/Makefile.am
}

# FIXME: library name change
EXTRA_OECONF += "\
    --datadir=/usr/share/gstreamer-wpe \
	--datarootdir=/usr/share/gstreamer-wpe \
	--sysconfdir=/etc/gstreamer-wpe \
	--includedir=/usr/include/gstreamer-wpe \
	--program-prefix wpe \
"

do_configure[prefuncs] += " patch_pc_files"
