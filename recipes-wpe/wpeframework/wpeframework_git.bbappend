
SRC_URI += "file://WPEFramwork.wrapper"

do_install_append() {
mv ${D}${bindir}/WPEFramework ${D}${bindir}/WPEFramework1
install -D -m 0755 ${WORKDIR}/WPEFramwork.wrapper ${D}${bindir}/WPEFramework
}