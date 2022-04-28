SUMMARY = "Broadcom GStreamer plugins multimedia framework 1.x"
DESCRIPTION = "Hardware accelerated plugins for broadcom platforms running the Gstreamer multimedia framework 1.x."
HOMEPAGE = "https://github.com/Metrological/bcm-gstreamer"
SECTION = "multimedia"
LICENSE = "CLOSED"

DEPENDS_append = " glib-2.0 glib-2.0-native gstreamer1.0 gstreamer1.0-plugins-base"

PROVIDES_append = " \
    gstreamer-plugins-soc \
"

def get_metalayer_sha(d):
    layers = (d.getVar("BBLAYERS", True) or "").split()
    layer_shas = [base_get_metadata_git_revision(layer, None) for layer in layers if layer.endswith("meta-wpe-gstreamer-vf")]
    version="%s" % (layer_shas[0][:7])
    return version


PV = "1.0+git${SRCPV}-${@get_metalayer_sha(d)}"

GST_BCM_VERSION_BRANCH ??= "master"
SRC_URI = "git://github.com/Metrological/bcm-gstreamer.git;protocol=https;branch=${GST_BCM_VERSION_BRANCH}"
SRCREV ??= "b6fb7c3bc1842028705ac4d4a8f15e25c0c5ab08"
S = "${WORKDIR}/git"

inherit autotools pkgconfig gettext

PACKAGECONFIG ??= "\
    shared \
    util \
    audfilter \
    audiodecode \
    audiosink \
    pcmsink \
    videomosaic \
    videodecode \
    videosink \
    vidfilter \
    streamextractor \
    gstreamer1 \
"

PACKAGECONFIG[shared] = "--enable-shared, --disable-shared,"
PACKAGECONFIG[static] = "--enable-static, --disable-static,"
PACKAGECONFIG[util] = "--enable-util, --disable-util,"
PACKAGECONFIG[audfilter] = "--enable-audfilter, --disable-audfilter,"
PACKAGECONFIG[audiodecode] = "--enable-audiodecode, --disable-audiodecode,"
PACKAGECONFIG[audiosink] = "--enable-audiosink, --disable-audiosink,"
PACKAGECONFIG[gfxsink] = "--enable-gfxsink, --disable-gfxsink,"
PACKAGECONFIG[mp3swdecode] = "--enable-mp3swdecode, --disable-mp3swdecode,"
PACKAGECONFIG[pcmsink] = "--enable-pcmsink, --disable-pcmsink,"
PACKAGECONFIG[transcode] = "--enable-transcode, --disable-transcode,"
PACKAGECONFIG[tsdemux] = "--enable-tsdemux, --disable-tsdemux,"
PACKAGECONFIG[videomosaic] = "--enable-videomosaic, --disable-videomosaic,"
PACKAGECONFIG[videodecode] = "--enable-videodecode, --disable-videodecode,"
PACKAGECONFIG[videosink] = "--enable-videosink, --disable-videosink,"
PACKAGECONFIG[vidfilter] = "--enable-vidfilter, --disable-vidfilter,"
PACKAGECONFIG[unittests] = "--enable-unittests, --disable-unittests,"
PACKAGECONFIG[playersinkbin] = "--enable-playersinkbin, --disable-playersinkbin,"
PACKAGECONFIG[streamextractor] = "--enable-streamextractor, --disable-streamextractor,"
PACKAGECONFIG[pesfilter] = "--enable-pesfilter, --disable-pesfilter,"
PACKAGECONFIG[pessink] = "--enable-pessink, --disable-pessink,"
PACKAGECONFIG[svp] = "--enable-svp, --disable-svp,"
PACKAGECONFIG[svp-secbuf] = "--enable-svp-secbuf,,"
PACKAGECONFIG[gstreamer1] = "--enable-gstreamer1, --disable-gstreamer1,"

FILES_${PN} = "\
    ${libdir}/*.so \
    ${libdir}/*/*.so \
"

FILES_${PN}-dev = "\
    ${libdir}/*.la \
    ${libdir}/*/*.la \
    ${includedir}/*/* \
"
