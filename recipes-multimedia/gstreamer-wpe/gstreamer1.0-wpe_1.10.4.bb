require gstreamer1.0-wpe.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

SRC_URI = " \
    http://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz \
    file://deterministic-unwind.patch \
    file://0001-typefind-min-1k.patch \
    file://0002-small-robustness-fixes.patch \
    file://0003-protection-added-function-to-filter-system-ids.patch \
    file://0004-protection-Add-a-new-definition-for-unspecified-syst.patch \
    file://0005-protection-Fix-the-string-to-define-unspecified-syst.patch \
"
SRC_URI[md5sum] = "7c91a97e4a2dc81eafd59d0a2f8b0d6e"
SRC_URI[sha256sum] = "50c2f5af50a6cc6c0a3f3ed43bdd8b5e2bff00bacfb766d4be139ec06d8b5218"

S = "${WORKDIR}/gstreamer-${PV}"


# fix gir build error
do_compile_prepend () {
    cp ${B}/pkgconfig/wpe-gstreamer.pc ${B}/pkgconfig/gstreamer-1.0.pc
}

do_install_prepend () {
    if [ -f "${B}/pkgconfig/gstreamer-1.0.pc" ]; then
        rm ${B}/pkgconfig/gstreamer-1.0.pc
    fi
}

do_install_append () {
    if [ -d "${D}${libdir}/gstreamer-1.0" ]; then
        mv -v ${D}${libdir}/gstreamer-1.0 ${D}${libdir}/gstreamer-1.0-wpe
    fi
}