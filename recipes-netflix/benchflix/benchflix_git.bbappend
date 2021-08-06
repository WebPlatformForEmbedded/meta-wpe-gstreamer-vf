FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


DEPENDS_remove = "gstreamer1.0 gstreamer1.0-plugins-base"

DEPENDS_append = "\
    gstreamer1.0-wpe \
    gstreamer1.0-wpe-plugins-base \
"

SRC_URI_remove = "git://git@github.com/Metrological/benchflix.git;protocol=ssh;branch=2.0.7"
SRC_URI_append = " git://git@github.com/Metrological/benchflix.git;protocol=ssh;branch=2.0.8"
SRCREV_remove = "fcc77fb2bbc6b3416f308435edbb9cd2354f2a07"
SRCREV_append = " adcba2d843543cafbbe91692152e566c60e0dde3"
SRC_URI += "file://wpe-gstreamer.patch"