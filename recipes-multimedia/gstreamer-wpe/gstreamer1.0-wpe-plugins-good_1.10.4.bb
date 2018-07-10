require gstreamer1.0-plugins-good.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe"

SRC_URI = " \
    http://gstreamer.freedesktop.org/src/gst-plugins-good/gst-plugins-good-${PV}.tar.xz \
    file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
    file://avoid-including-sys-poll.h-directly.patch \
    file://ensure-valid-sentinel-for-gst_structure_get.patch \
    file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
    file://0001-v4l2object-Also-add-videometa-if-there-is-padding-to.patch \
    file://0001-qtdemux-distinguish-TFDT-with-value-0-from-no-TFDT-a.patch \
    file://0005-souphttpsrc-cookie-jar-and-context-query-support.patch \
    file://0006-qtdemux-add-context-for-a-preferred-protection.patch \
    file://0007-qtdemux-dont-check-pushbased-edts.patch \
    file://0008-qtdemux-also-push-buffers-without-encryption-info-in.patch \
    file://0009-qtdemux-fix-assert-when-moof-contains-one-sample.patch \
    file://0010-matroskademux-Allow-Matroska-headers-to-be-read-more.patch \
    file://0011-matroskademux-Start-stream-time-at-zero.patch \
    file://0012-matroskademux-emit-no-more-pads-when-the-Tracks-elem.patch \
"
SRC_URI[md5sum] = "cc0cc13cdb07d4237600b6886b81f31d"
SRC_URI[sha256sum] = "8a86c61434a8c44665365bd0b3557a040937d1f44bf69caee4e9ea816ce74d7e"

S = "${WORKDIR}/gst-plugins-good-${PV}"
