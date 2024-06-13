if len(sys.argv) != 5:
    print "Invalid number of arguments!"
    print "USAGE: wsadmin.sh -lang jython -profile lib.py -f installAndStart.py cellName nodeName serverName appName earPath"
    sys.exit(101)

cellName = sys.argv[0]
nodeName = sys.argv[1]
serverName = sys.argv[2]
appName = sys.argv[3]
earPath = sys.argv[4]
options = '[-appname '+appName+' -node '+nodeName+' -cell '+cellName+' -server '+serverName+' -MapWebModToVH [[.* .* default_host]]]'

stopAndUninstallAppIfInstalled( cellName, nodeName, serverName, appName)
installApp(earPath, appName, options)
saveAndSynchronize()
waitUntilAppIsReady(appName)
startApplication( cellName, nodeName, serverName, appName )