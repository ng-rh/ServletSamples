if len(sys.argv) != 4:
    print "Invalid number of arguments!"
    print "USAGE: wsadmin.sh -lang jython -profile lib.py -f stopAndUninstall.py cellName nodeName serverName appName"
    sys.exit(101)

cellName = sys.argv[0]
nodeName = sys.argv[1]
serverName = sys.argv[2]
appName = sys.argv[3]

stopAndUninstallAppIfInstalled( cellName, nodeName, serverName, appName)
saveAndSynchronize()