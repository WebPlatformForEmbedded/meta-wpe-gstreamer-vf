FILES_${PN} += "\
         ${libdir}/wpe/gstreamer-1.0/*.so \
         ${libdir}/wpe/*.so* \
         "

FILES_${PN}-dev += "\
         ${libdir}/wpe/*.a \
         ${libdir}/wpe/*.la \
         ${libdir}/pkgconfig/*.pc \
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
    find ${D}${libdir} -maxdepth 1 ! -name wpe ! -name pkgconfig -exec mv -v  {} ${D}${libdir}/wpe \;
    find ${D}${includedir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${includedir}/wpe \;
    find ${D}${libexecdir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${libexecdir}/wpe \;
    find ${D}${datadir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${datadir}/wpe \;
    # correct paths in pc files
    for f in `ls ${D}${libdir}/pkgconfig/*.pc` ; do sed -i -e "s;/lib;/lib/wpe;g" -e "s;/include;/include/wpe;g" -e "s;/share;/share/wpe;g" -e "s;/bin;/bin/wpe;g" $f; done
}

do_populate_sysroot () {
    install -d ${STAGING_LIBDIR}/wpe
    install -d ${STAGING_LIBDIR}/pkgconfig
    install -d ${STAGING_INCDIR}/wpe

    install -m 0755 ${D}${libdir}/wpe/*.so ${STAGING_LIBDIR}/wpe
    install -m 0644 ${D}${libdir}/pkgconfig/*.pc ${STAGING_LIBDIR}/pkgconfig
    cp -r -v ${D}${includedir}/wpe/* ${STAGING_INCDIR}/wpe
}
