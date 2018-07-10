SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

FILES_${PN} += "${libdir}/wpe/*.so* \
                ${libdir}/wpe/gstreamer-1.0/*.so*"
                
FILES_${PN}-dev += "${libdir}/wpe/*.a \
                    ${libdir}/wpe/*.la \
                    ${libdir}/wpe/gstreamer-1.0/*.la \
                    ${libdir}/wpe/gstreamer-1.0/*.a \ 
                    ${libdir}/wpe/gstreamer-1.0/include \
                    ${libdir}/wpe/pkgconfig/*.pc \
                    ${libdir}/wpe/girepository-1.0/*.typelib \
                    ${libdir}/wpe/*.so* \
                    ${libdir}/wpe/gstreamer-1.0/*.so*"
                    
do_install_append () {
 install -d ${D}${bindir}/wpe
 install -d ${D}${libdir}/wpe
 find ${D}${bindir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${bindir}/wpe \;
 find ${D}${libdir} -maxdepth 1 ! -name wpe -exec mv -v  {} ${D}${libdir}/wpe \;
}


