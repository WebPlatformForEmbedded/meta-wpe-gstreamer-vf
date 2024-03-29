##########################################################################################
# THIS FILE WAS GENERATED by ./prepare-binaries.sh. PLEASE DO NOT MANUALLY MODIFY
# To modify this file, please use ./prepare-binaries.sh found in layer scripts dir.
##########################################################################################
SUMMARY = "Gstreamer1.0-wpe-plugins-brcm-binary prebuilt binaries"
DESCRIPTION = "Gstreamer1.0-wpe-plugins-brcm-binary contains prebuilt binaries for gstreamer1.0-wpe-plugins-brcm"
HOMEPAGE = "https://github.com/Metrological/meta-metrological-apps-vss"
SECTION = "wpe-binaries"
LICENSE = "CLOSED"

PROVIDES_append = " gstreamer1.0-wpe-plugins-brcm"

# inherit PV from original recipe
PV = "1.0+gitAUTOINC+5534aa56df-26da199-r0"
SRC_URI = " \
    file://gstreamer1.0-wpe-plugins-brcm-1.0+gitAUTOINC+5534aa56df-26da199-r0.tar.gz;subdir=binaries-source \
"
SRC_URI[md5sum] = "16b4059419cf5342a673b77ecf140d95"
SRC_URI[sha256sum] = "ad84c81a6ff3c3265283c1a3aca2151c63ccc002aa6704aa91f989ee493bff9a"

S = "${WORKDIR}/binaries-source"

FILES_SOLIBSDEV = ""

FILES_${PN}_append = " \
    ${libdir}/libbrcmsvpmeta.so \
    ${libdir}/gstreamer-1.0-wpe/libbrcmaudiosink.so \
    ${libdir}/gstreamer-1.0-wpe/libbrcmaudiodecoder.so \
    ${libdir}/gstreamer-1.0-wpe/libbrcmvidfilter.so \
    ${libdir}/gstreamer-1.0-wpe/libbrcmaudfilter.so \
    ${libdir}/gstreamer-1.0-wpe/libbrcmvideodecoder.so \
    ${libdir}/gstreamer-1.0-wpe/libbrcmgstutil.so \
    ${libdir}/gstreamer-1.0-wpe/libbrcmpcmsink.so \
    ${libdir}/gstreamer-1.0-wpe/libbrcmvideosink.so \
 "
FILES_${PN}-dev_append = " \
    ${includedir}/gstreamer-wpe/gst_brcm_svp_meta.h \
    ${includedir}/gst_brcm_svp_meta.h \
    ${libdir}/libbrcmsvpmeta.la \
    ${libdir}/gstreamer-1.0-wpe/libbrcmaudiodecoder.la \
    ${libdir}/gstreamer-1.0-wpe/libbrcmaudiosink.la \
    ${libdir}/gstreamer-1.0-wpe/libbrcmpcmsink.la \
    ${libdir}/gstreamer-1.0-wpe/libbrcmvideosink.la \
    ${libdir}/gstreamer-1.0-wpe/libbrcmvideodecoder.la \
    ${libdir}/gstreamer-1.0-wpe/libbrcmvidfilter.la \
    ${libdir}/gstreamer-1.0-wpe/libbrcmgstutil.la \
    ${libdir}/gstreamer-1.0-wpe/libbrcmaudfilter.la \
 "
FILES_${PN}-doc_append = " "
FILES_${PN}-locale_append = " "
FILES_${PN}-staticdev_append = " "

do_install_append() {
    rsync -rlptgD ${S}/ ${D}
}

RDEPENDS_${PN}_append = " \
     gstreamer1.0-wpe (>= 1.10.4) \
     gstreamer1.0-wpe-plugins-base-wpegstaudio-1.0 (>= 1.10.4) \
     gstreamer1.0-wpe-plugins-base-wpegsttag-1.0 (>= 1.10.4) \
     gstreamer1.0-wpe-plugins-base-wpegstvideo-1.0 (>= 1.10.4) \
 "
RPROVIDES_${PN}_append = " gstreamer1.0-wpe-plugins-brcm"

DEFAULT_PREFERENCE = "-1"

# Pre-build binaries are already stripped
INSANE_SKIP_${PN} += "already-stripped"

# We have no GNU_HASH in the elf binaries
INSANE_SKIP_${PN} += "ldflags"
