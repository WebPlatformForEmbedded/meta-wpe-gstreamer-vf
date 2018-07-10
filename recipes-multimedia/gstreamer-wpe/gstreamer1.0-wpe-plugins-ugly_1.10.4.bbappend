                   
do_install_append () {
 install -d ${D}${bindir}/wpe
 install -d ${D}${libdir}/wpe
 find ${D}${bindir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${bindir}/wpe \;
 find ${D}${libdir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${libdir}/wpe \;
}


