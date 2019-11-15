SUMMARY = "Gstreamer SOC specific plugins for Broadcom"
LICENSE = "CLOSED"
PV = "18.1+git${SRCPV}"

DEPENDS = " gstreamer1.0-wpe gstreamer1.0-wpe-plugins-base mpg123 curl broadcom-refsw"
RDEPENDS_${PN} = "mpg123"

PROVIDES = "virtual/gst-wpe-plugins-playersinkbin"
RPROVIDES_${PN} = "virtual/gst-wpe-plugins-playersinkbin"

SRC_URI = "git://git@github.com/Metrological/gstreamer-plugins-soc.git;protocol=ssh;branch=18.1-rdkv-20180416"
SRCREV = "98ec02b612f2051ac449c7fdea11a430eed07255"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

PACKAGECONFIG ?= "${@bb.utils.contains('WITH_SVP', 'y', 'svp', ' ', d)}"
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

