SUMMARY = "Plugins for the GStreamer multimedia framework 1.x"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"

DEPENDS = "gstreamer1.0-wpe glib-2.0-native"

inherit autotools pkgconfig upstream-version-is-even gobject-introspection gtk-doc

acpaths = "-I ${S}/common/m4 -I ${S}/m4"

LIBV = "1.0"
require gst-plugins-package.inc

# Orc enables runtime JIT compilation of data processing routines from Orc
# bytecode to SIMD instructions for various architectures (currently SSE, MMX,
# MIPS, Altivec and NEON are supported).

GSTREAMER_ORC ?= "orc"

PACKAGECONFIG[debug] = "--enable-debug,--disable-debug"
PACKAGECONFIG[orc] = "--enable-orc,--disable-orc,orc orc-native"
PACKAGECONFIG[valgrind] = "--enable-valgrind,--disable-valgrind,valgrind"

export ORCC = "${STAGING_DIR_NATIVE}${bindir}/orcc"

EXTRA_OECONF = " \
    --disable-examples \
"

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

python do_rename_pc_files() {
    pkgconfigDir = d.expand('${B}/pkgconfig')
    print('pkgconfig: ' + pkgconfigDir)
    pcFiles = os.listdir(pkgconfigDir) or []

    for file in pcFiles:
        if file[:9] == 'gstreamer':
            print('Renaming: ' + file)
            os.rename( os.path.join(pkgconfigDir, file), os.path.join(pkgconfigDir, 'wpe-' + file) )
}

# addtask do_rename_pc_files after do_compile before do_install

patch_pc_files () {
        # Deal with pc files by patching the sources
        if [ ! -f ${S}/patch_pc_files.done ]; then
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
        sed -i -e "s;pkgconfig/gstreamer;pkgconfig/wpe-gstreamer;g" \
               -e "s;toolsdir\ gstreamer-;toolsdir\ wpe-gstreamer-;g" ${S}/configure.ac
        sed -i -e s/gstreamer/wpe-gstreamer/g ${S}/pkgconfig/Makefile.in
        sed -i -e s/gstreamer/wpe-gstreamer/g ${S}/pkgconfig/Makefile.am
        # This is for the package so it will pickup the correct pc files from sysroot.
        sed -i -e s/\ gstreamer/\ wpe-gstreamer/g ${S}/common/m4/gst-check.m4
        sed -i -e s/prefix\ gstreamer/prefix\ wpe-gstreamer/g  ${S}/configure.ac
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
         ${includedir}/gstreamer-wpe \
         ${datadir}/girwpe-1.0 \
         "

FILES_${PN} += "\
        ${libdir}/giwperepository-1.0 \
        ${datadir}/* \
        "

PACKAGES_DYNAMIC = "^${PN}-.*"

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

EXTRA_OECONF_append=" --datadir=${datadir}/gstreamer-wpe"
EXTRA_OECONF_append=" --datarootdir=${datadir}/gstreamer-wpe"
EXTRA_OECONF_append=" --sysconfdir=${sysconfdir}/gstreamer-wpe"
EXTRA_OECONF_append=" --includedir=${includedir}/gstreamer-wpe"
EXTRA_OECONF_append=" --program-prefix wpe"
