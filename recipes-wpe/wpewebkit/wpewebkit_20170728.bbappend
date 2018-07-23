FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-check-for-wpe-gstreamer.patch"


RDEPS_MEDIASOURCE = " \
    gstreamer1.0-wpe-plugins-good-isomp4 \
"

RDEPS_VIDEO = " \
    gstreamer1.0-wpe-plugins-base-app \
    gstreamer1.0-wpe-plugins-base-playback \
    gstreamer1.0-wpe-plugins-good-souphttpsrc \
"

RDEPS_WEBAUDIO = " \
    gstreamer1.0-wpe-plugins-good-wavparse \
"

# plugins-bad config option 'dash' -> gstreamer1.0-wpe-plugins-bad-dashdemux
# plugins-bad config option 'videoparsers' -> gstreamer1.0-wpe-plugins-bad-videoparsersbad

RDEPS_EXTRA = " \
    gstreamer1.0-wpe-plugins-base-audioconvert \
    gstreamer1.0-wpe-plugins-base-audioresample \
    gstreamer1.0-wpe-plugins-base-gio \
    gstreamer1.0-wpe-plugins-base-opus \
    gstreamer1.0-wpe-plugins-base-videoconvert \
    gstreamer1.0-wpe-plugins-base-videoscale \
    gstreamer1.0-wpe-plugins-base-volume \
    gstreamer1.0-wpe-plugins-base-typefindfunctions \
    gstreamer1.0-wpe-plugins-good-audiofx \
    gstreamer1.0-wpe-plugins-good-audioparsers \
    gstreamer1.0-wpe-plugins-good-autodetect \
    gstreamer1.0-wpe-plugins-good-avi \
    gstreamer1.0-wpe-plugins-good-deinterlace \
    gstreamer1.0-wpe-plugins-good-id3demux \
    gstreamer1.0-wpe-plugins-good-icydemux \
    gstreamer1.0-wpe-plugins-good-interleave \
    gstreamer1.0-wpe-plugins-good-matroska \
    gstreamer1.0-wpe-plugins-bad-dashdemux \
    gstreamer1.0-wpe-plugins-bad-hls \
    gstreamer1.0-wpe-plugins-bad-faad \
    gstreamer1.0-wpe-plugins-bad-mpegtsdemux \
    gstreamer1.0-wpe-plugins-bad-opusparse \
    gstreamer1.0-wpe-plugins-bad-smoothstreaming \
    gstreamer1.0-wpe-plugins-bad-videoparsersbad \
    gstreamer1.0-wpe-plugins-ugly-mpg123 \
"

RDEPS_EXTRA_append_rpi = " \
    gstreamer1.0-omx \
    gstreamer1.0-wpe-plugins-bad-opengl \
"