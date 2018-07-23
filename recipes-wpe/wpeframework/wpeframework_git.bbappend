FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://WPEFramework.wrapper"

do_install_append() {
	mv ${D}${bindir}/WPEFramework ${D}${bindir}/WPEFramework1
	install -D -m 0755 ${WORKDIR}/WPEFramework.wrapper ${D}${bindir}/WPEFramework
}