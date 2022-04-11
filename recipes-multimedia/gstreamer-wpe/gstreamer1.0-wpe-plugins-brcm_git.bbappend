FILESEXTRAPATHS_prepend := "${THISDIR}/files/gstreamer1.0-wpe-plugins-brcm:"

DEPENDS_remove = "gstreamer1.0"
DEPENDS = "gstreamer1.0-wpe gstreamer1.0-wpe-plugins-base glib-2.0 glib-2.0-native"

PACKAGECONFIG_append = "svp"

SRC_URI_remove = "git://git@github.com/Metrological/bcm-gstreamer.git;protocol=ssh;branch=${GST_BCM_VERSION_BRANCH}"
SRC_URI_append = " \
    git://code.rdkcentral.com/r/collaboration/rdk/components/generic/gst-plugins-soc/soc/broadcom;protocol=https;branch=master \
    file://0001-BCMCZ-377-Do-not-reset-position-on-first-frame.patch \
    file://0002-BCMCZ-379-Refactor-position-query-handling.patch \
    file://0003-BCMCZ-376-BCOM-6082-Potential-video-buffer-flushed-o.patch \
    file://0004-BCMCZ-378-audfilter-svp-optimization.patch \
    file://0005-Revert-BCMCZ-121-remove-gstsvpmeta.h.patch \
    file://0006-Configure-sinks-to-use-the-async-mode.patch \
"

SRCREV = "30b63420cc4867728e2d260706a1bf96263bef30"

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

do_install_append () {
    install -m 0644 ${S}/reference/svpmeta/src/gst_brcm_svp_meta.h ${D}/${includedir}
}

FILES_${PN}-dev += "\
    ${includedir}/* \
"
