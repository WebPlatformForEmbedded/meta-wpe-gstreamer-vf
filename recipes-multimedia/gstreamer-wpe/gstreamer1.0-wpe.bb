SUMMARY = "Prebuild GStreamer 1.0 multimedia framework"
DESCRIPTION = "Prebuild GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"
LICENSE = "LGPLv2+"


SRC_URI = "file://gstreamer-wpe.tar \
            file://COPYING \
"

S = "${WORKDIR}/gstreamer-wpe-${PV}"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=6762ed442b3822387a51c92d928ead0d"

FILES_SOLIBSDEV = ""
SOLIBS = ".so"
INSANE_SKIP_${PN} += "dev-so ldflags already-stripped build-deps"

do_install(){
cp -av ${WORKDIR}/usr ${WORKDIR}/image
#pushd ${WORKDIR}
#for d in `find  usr/ -type d`; do echo $d; done
#for f in `find  usr/libexec -type f`; do echo $f ; done
#popd
}

FILES_${PN} += "\
         ${libdir}/wpe/gstreamer-1.0/*.so \
         ${libdir}/wpe/*.so* \
         "

FILES_${PN}-dev += "\
         ${libdir}/wpe/gstreamer-1.0/include/gst/*.h \
         ${libdir}/wpe/gstreamer-1.0/*.la \
         ${libdir}/wpe/*.la \
         "