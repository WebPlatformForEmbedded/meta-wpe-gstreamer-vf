FILES_${PN} += "\
         ${libdir}/wpe/gstreamer-1.0/*.so \
         ${libdir}/wpe/*.so* \
         "

FILES_${PN}-dev += "\
         ${libdir}/wpe/*.a \
         ${libdir}/wpe/*.la \
         ${libdir}/wpe/pkgconfig/*.pc \
         ${libdir}/wpe/girepository-1.0/*.typelib \
         ${datadir}/wpe \
         "
                   
FILES_SOLIBSDEV = ""
SOLIBS = ".so"
INSANE_SKIP_${PN} += "dev-so"
                   
do_install_append () {
 install -d ${D}${bindir}/wpe
 install -d ${D}${libdir}/wpe
 install -d ${D}${includedir}/wpe
 install -d ${D}${libexecdir}/wpe
 install -d ${D}${datadir}/wpe
 find ${D}${bindir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${bindir}/wpe \;
 find ${D}${libdir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${libdir}/wpe \;
 find ${D}${includedir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${includedir}/wpe \;
 find ${D}${libexecdir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${libexecdir}/wpe \;
 find ${D}${datadir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${datadir}/wpe \;
}


