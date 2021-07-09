
RPROVIDES_${PN}_append = "\ 
  ${@bb.utils.contains("PACKAGECONFIG", "faad", "gstreamer1.0-wpe-plugins-bad-faad", "", d)} \
  ${@bb.utils.contains("PACKAGECONFIG", "hls", "gstreamer1.0-wpe-plugins-bad-hls", "", d)} \
"
