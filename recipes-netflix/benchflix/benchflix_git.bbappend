FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


DEPENDS_remove = "gstreamer1.0 gstreamer1.0-plugins-base"

DEPENDS_append = "\
    gstreamer1.0-wpe \
    gstreamer1.0-wpe-plugins-base \
"
