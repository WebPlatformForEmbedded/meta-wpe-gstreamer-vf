SUMMARY = "Gstreamer SOC specific plugins for Broadcom"
LICENSE = "CLOSED"
PV = "17.1+git${SRCPV}"

# Removed broadcom-refsw for the time being, should be added back again towards the end
DEPENDS = " gstreamer1.0-wpe gstreamer1.0-wpe-plugins-base mpg123 curl"
RDEPENDS_${PN} = "mpg123"

PROVIDES = "virtual/gst-plugins-playersinkbin"
RPROVIDES_${PN} = "virtual/gst-plugins-playersinkbin"

SRC_URI = "git://git@github.com/Metrological/gstreamer-plugins-soc.git;protocol=ssh;branch=17.1-rdkfix"
SRCREV = "1856b04e46d53da16cc5469279c0497fa13ce6d2"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

PACKAGECONFIG ?= "${@base_contains('WITH_SVP', 'y', 'svp', ' ', d)}"
PACKAGECONFIG[svp]  = "--enable-svp,,"

EXTRA_OECONF += " \
	--enable-gstreamer1 \
	--enable-shared \
	--with-pic \
	--disable-static \
	--enable-systemclock \
	--enable-audfilter \
	--enable-audiodecode \
	--enable-audiosink \
	--enable-videodecode \
	--enable-videosink \
	--enable-vidfilter \
	--disable-avi \
	--disable-flv \
	--disable-httpsrc \
	--disable-ivfparse \
	--disable-matroska \
	--disable-mp3swdecode \
	--disable-mp4demux \
	--disable-pcmsink \
	--disable-pesdemux \
	--disable-playback \
	--disable-qtdemux \
	--disable-transcode \
	--disable-tsdemux \
	--disable-tsparse \
	--disable-playersinkbin \
	--disable-gfxsink \
"

EXTRA_OECONF += 'REFSW_DIR="refsw"'

PACKAGES_DYNAMIC = "^libgst.*"

FILES_SOLIBSDEV = ""
SOLIBS = ".so"
INSANE_SKIP_${PN} += "dev-so"

FILES_${PN} += "${libdir}/gstreamer-*/*.so ${libdir}/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-*/*.la"
FILES_${PN}-dbg += "${libdir}/gstreamer-*/.debug/*"
FILES_${PN}-staticdev += "${libdir}/gstreamer-*/*.a "
FILES_${PN}-dbg += "/usr/bin/gst-unittests/.debug/*"

CFLAGS += "-std=gnu99"
CFLAGS += "-Wall"

