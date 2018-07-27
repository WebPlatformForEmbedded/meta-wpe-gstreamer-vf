SUMMARY = "GStreamer 1.0 multimedia framework"
DESCRIPTION = "GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"
LICENSE = "LGPLv2+"

DEPENDS = "glib-2.0 glib-2.0-native libcap libxml2 bison-native flex-native"

inherit autotools pkgconfig gettext upstream-version-is-even gobject-introspection gtk-doc

# This way common/m4/introspection.m4 will come first
# (it has a custom INTROSPECTION_INIT macro, and so must be used instead of our common introspection.m4 file)
acpaths = "-I ${S}/common/m4 -I ${S}/m4"

SRC_URI_append = " \
    file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
"

PACKAGECONFIG ??= ""

PACKAGECONFIG[debug] = "--enable-debug,--disable-debug"
PACKAGECONFIG[tests] = "--enable-tests,--disable-tests"
PACKAGECONFIG[valgrind] = "--enable-valgrind,--disable-valgrind,valgrind,"
PACKAGECONFIG[gst-tracer-hooks] = "--enable-gst-tracer-hooks,--disable-gst-tracer-hooks,"
PACKAGECONFIG[unwind] = "--with-unwind,--without-unwind,libunwind"

EXTRA_OECONF = " \
    --disable-dependency-tracking \
    --disable-docbook \
    --disable-examples \
"

CACHED_CONFIGUREVARS += "ac_cv_header_valgrind_valgrind_h=no"

# musl libc generates warnings if <sys/poll.h> is included directly
CACHED_CONFIGUREVARS += "ac_cv_header_sys_poll_h=no"

PACKAGES += "${PN}-bash-completion"

FILES_${PN} += "${libdir}/gstreamer-1.0-wpe/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0-wpe/*.la ${libdir}/gstreamer-1.0-wpe/*.a ${libdir}/gstreamer-1.0/include"
FILES_${PN}-bash-completion += "${datadir}/bash-completion/completions/ ${datadir}/bash-completion/helpers/gst*"

RRECOMMENDS_${PN}_qemux86 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"
RRECOMMENDS_${PN}_qemux86-64 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"

delete_pkg_m4_file() {
        # This m4 file is out of date and is missing PKG_CONFIG_SYSROOT_PATH tweaks which we need for introspection
        rm "${S}/common/m4/pkg.m4" || true
        rm -f "${S}/common/m4/gtk-doc.m4"
}

# gstreamer is not using system-wide makefiles (which we patch in gtkdoc recipe,
# but its own custom ones, which we have to patch here
patch_gtk_doc_makefiles() {
        # Patch the gtk-doc makefiles so that the qemu wrapper is used to run transient binaries
        # instead of libtool wrapper or running them directly
        # Also substitute a bogus plugin scanner, as trying to run the real one is causing issues during build on x86_64.
        sed -i \
           -e "s|GTKDOC_RUN =.*|GTKDOC_RUN = \$(top_builddir)/gtkdoc-qemuwrapper|" \
           -e "s|\$(GTKDOC_EXTRA_ENVIRONMENT)|\$(GTKDOC_EXTRA_ENVIRONMENT) GST_PLUGIN_SCANNER_1_0=\$(top_builddir)/libs/gst/helpers/gst-plugin-scanner-dummy|" \
           ${S}/common/gtk-doc*mak
}

patch_pc_files () {
        # Deal with pc files by patching the sources
        if [ ! -f ${S}/patch_pc_files.done  ]; then
        for f in `ls ${S}/pkgconfig/gstreamer*.pc.in`; \
        do  \
          file=`echo $f | sed "s/.*\///"` ; \
          pc=`echo $f | sed -r "s/.+\/(.+)\..+/\1/"` ; \
          sed -i \
              -e "s;pluginsdir=.*;pluginsdir=\@PLUGINDIR\@;g" \
              -e "s;girdir=.*;girdir=${datadir}/girwpe-1.0;g" \
              -e "s;typelibdir=.*;typelibdir=${libdir}/giwperepository-1.0;g" \
              -e "s;datarootdir=.*;datarootdir=${prefix}/share/gstreamer-wpe;g" \
              -e "s;helpersdir=.*;;g" \
              -e "s;completionsdir=.*;;g" \
              -e "s;\ gstreamer-;\ wpe-gstreamer-;g" \
              -e "s/lgst/lwpegst/g" \
              $f ; \
          mv ${f} ${S}/pkgconfig/wpe-${file} ; \
        done
        sed -i -e "s;pkgconfig/gstreamer;pkgconfig/wpe-gstreamer;g"  ${S}/configure.ac
        sed -i -e s/gstreamer/wpe-gstreamer/g  ${S}/pkgconfig/Makefile.in
        sed -i -e s/gstreamer/wpe-gstreamer/g  ${S}/pkgconfig/Makefile.am
        # correct plugin install location
        sed -i -e "s;/gstreamer-\$GST_API_VERSION;/gstreamer-\$\{GST_API_VERSION\}-wpe;g" ${S}/common/m4/gst-plugindir.m4
        #rename libs
        for f in `find ${S} -name Makefile.in -o -name Makefile.am -o -name configure.ac`; do \
          sed -i \
              -e s/libgst/libwpegst/g \
              -e s/lgst/lwpegst/g \
              -e s/girepository/giwperepository/g \
              -e s/gir-1.0/girwpe-1.0/g \
              $f ; \
        done
        touch ${S}/patch_pc_files.done
        fi
}

do_configure[prefuncs] += " delete_pkg_m4_file patch_gtk_doc_makefiles patch_pc_files"

FILES_${PN}-dev += "\
         ${datadir}/gstreamer-wpe \
         ${datadir}/girwpe-1.0 \
         "

FILES_${PN} += "\
        ${libdir}/giwperepository-1.0 \
        "

do_compile_prepend() {
        export GIR_EXTRA_LIBS_PATH="${B}/gst/.libs:${B}/libs/gst/base/.libs"
}

do_install_append () {
  if [ -d "${D}${datadir}/man" ]; then
    rm -rf  "${D}${datadir}/man"
  fi
  if [ -d "${D}${datadir}/aclocal" ]; then
    rm -rf  "${D}${datadir}/aclocal"
  fi
  if [ -d "${D}${datadir}/bash-completion" ]; then
    rm -rf  "${D}${datadir}/bash-completion"
  fi
}

EXTRA_OECONF_append=" --datarootdir=${datadir}/gstreamer-wpe"
EXTRA_OECONF_append=" --sysconfdir=${sysconfdir}/gstreamer-wpe"
EXTRA_OECONF_append=" --includedir=${includedir}/gstreamer-wpe"
EXTRA_OECONF_append=" --program-prefix wpe"
