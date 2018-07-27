SUMMARY = "OpenMAX IL plugins for GStreamer"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
SECTION = "multimedia"

LICENSE = "LGPLv2.1"
LICENSE_FLAGS = "commercial"

DEPENDS = "gstreamer1.0-wpe gstreamer1.0-wpe-plugins-base gstreamer1.0-wpe-plugins-bad"

inherit autotools pkgconfig gettext gtk-doc upstream-version-is-even

acpaths = "-I ${S}/common/m4 -I ${S}/m4"


GSTREAMER_1_0_OMX_TARGET ?= "bellagio"
GSTREAMER_1_0_OMX_CORE_NAME ?= "${libdir}/libomxil-bellagio.so.0"

EXTRA_OECONF += "--disable-valgrind --with-omx-target=${GSTREAMER_1_0_OMX_TARGET}"

python __anonymous () {
    omx_target = d.getVar("GSTREAMER_1_0_OMX_TARGET", True)
    if omx_target in ['generic', 'bellagio']:
        # Bellagio headers are incomplete (they are missing the OMX_VERSION_MAJOR,#
        # OMX_VERSION_MINOR, OMX_VERSION_REVISION, and OMX_VERSION_STEP macros);
        # appending a directory path to gst-omx' internal OpenMAX IL headers fixes this
        d.appendVar("CFLAGS", " -I${S}/omx/openmax")
    elif omx_target == "rpi":
        # Dedicated Raspberry Pi OpenMAX IL support makes this package machine specific
        d.setVar("PACKAGE_ARCH", d.getVar("MACHINE_ARCH"))
}

set_omx_core_name() {
	sed -i -e "s;^core-name=.*;core-name=${GSTREAMER_1_0_OMX_CORE_NAME};" "${D}${sysconfdir}/xdg/gstomx.conf"
}

do_install[postfuncs] += " set_omx_core_name "

FILES_${PN} += "${libdir}/gstreamer-1.0-wpe/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0-wpe/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-1.0-wpe/*.a"

RDEPENDS_${PN} = "libomxil"

patch_make_files () {
        if [ ! -f ${S}/patch_make_files.done ]; then
        sed -i -e s/\ gstreamer/\ wpe-gstreamer/g ${S}/common/m4/gst-check.m4
        sed -i -e s/prefix\ gstreamer/prefix\ wpe-gstreamer/g  ${S}/configure.ac
        # correct plugin install location
        sed -i -e "s;/gstreamer-\$GST_API_VERSION;/gstreamer-\$\{GST_API_VERSION\}-wpe;g" ${S}/common/m4/gst-plugindir.m4
        #rename libs
        for f in `find ${S} -name Makefile.in -o -name Makefile.am -o -name configure.ac`; do \
          sed -i \
              -e s/libgst/libwpegst/g \
              -e s/lgst/lwpegst/g \
              -e s/girepository/giwperepository/g \
              -e s/gir-1.0/girwpe-1.0/g \
              $f ; \
        done
        touch ${S}/patch_make_files.done
        fi
}

do_configure[prefuncs] += " patch_make_files"

EXTRA_OECONF_append=" --datadir=${datadir}/gstreamer-wpe"
EXTRA_OECONF_append=" --datarootdir=${datadir}/gstreamer-wpe"
#EXTRA_OECONF_append=" --sysconfdir=${sysconfdir}/gstreamer-wpe"
EXTRA_OECONF_append=" --includedir=${includedir}/gstreamer-wpe"
