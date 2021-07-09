DEPENDS_remove = "gstreamer1.0"
DEPENDS = "gstreamer1.0-wpe gstreamer1.0-wpe-plugins-base glib-2.0 glib-2.0-native"

PACKAGECONFIG_append = "svp"

GST_BCM_VERSION_BRANCH = "development/vss-test"
SRCREV = "f306636792d230aeaa046dd0e04efd7069cbf6eb"

patch_pc_files () {
    for f in `find ${S} -name Makefile.in -o -name Makefile.am -o -name configure.ac`; do \
        sed -i \
        -e "s;\[gstreamer-;\[wpe-gstreamer-;g" \
        -e "s;plugindir=.*;plugindir=\"\\\\$\(libdir\)/gstreamer\-\$\{GST_MAJORMINOR\}\-wpe\";g" \
        $f; \
    done
	sed -i -e "s;gst-plugin-scanner;wpegst-plugin-scanner;g" ${S}/configure.ac
    sed -i -e "s;gst-ptp-helper;wpegst-ptp-helper;g" ${S}/configure.ac
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
