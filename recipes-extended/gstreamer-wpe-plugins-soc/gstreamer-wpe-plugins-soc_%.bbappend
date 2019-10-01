patch_make_files () {
        if [ ! -f ${S}/patch_make_files.done ]; then
        for f in `find ${S} -name Makefile.in -o -name Makefile.am -o -name configure.ac`; do \
            sed -i \
            -e "s;\[gstreamer-;\[wpe-gstreamer-;g" \
            -e "s;plugindir=.*;plugindir=\"\\\\$\(libdir\)/gstreamer\-\$\{GST_MAJORMINOR\}\-wpe\";g" \
            $f; \
        done
        touch ${S}/patch_make_files.done
        fi
}

disable_opus () {
        # Force youtube to play mp4a
        if [ ! -f ${S}/disable_opus.done ]; then
        for f in `find ${S} -name *.c -o -name *.h -type f`; do \
            sed -i \
            -e "s;case baudio_format_opus.*;;g" \
            -e "s;\"audio/x-opus\;\ \";;g" \
            $f; \
        done
        touch ${S}/disable_opus.done
        fi
}

do_configure[prefuncs] += " patch_make_files"

# activate by putting "BRCM_SINK_DISABLE_OPUS_SUPPORT = "1" " in you local.conf
do_configure[prefuncs] += "${@bb.utils.contains('BRCM_SINK_DISABLE_OPUS_SUPPORT', '1', ' disable_opus', ' ', d)}"

EXTRA_OECONF_append=" --datadir=${datadir}/gstreamer-wpe"
EXTRA_OECONF_append=" --datarootdir=${datadir}/gstreamer-wpe"
#EXTRA_OECONF_append=" --sysconfdir=${sysconfdir}/gstreamer-wpe"
EXTRA_OECONF_append=" --includedir=${includedir}/gstreamer-wpe"

# Needed for libocdm svp adapter
do_install_append() {
	install -m 0644 ${S}/svpmeta/src/gst_brcm_svp_meta.h ${D}/${includedir}
}
