
print "Initializing shared definitions ..."

_modules = [
            'sys',
            'time',
            're',
            'glob',
            'os',
            'os.path',
            'getopt',
            'traceback',
           ]

# A lot of modules aren't available in WAS 602.
# Log an import failure, but continue on so that scripts can
# still call functions that don't use these modules.
for module in _modules:
  try:
    locals()[module] = __import__(module, {}, {}, [])
  except ImportError:
    print 'Error importing %s.' % module

def _splitlines(s):
  rv = [s]
  if '\r' in s:
    rv = s.split('\r\n')
  elif '\n' in s:
    rv = s.split('\n')
  if rv[-1] == '':
    rv = rv[:-1]
  return rv

def nodeHasServerEntry( node_id, servertype ):
    serverEntries = _splitlines(AdminConfig.list( 'ServerEntry', node_id ))
    for serverEntry in serverEntries:
        sType = AdminConfig.showAttribute( serverEntry, "serverType" )
        if sType == servertype:
            return 1
    return 0

def saveAndSynchronize():
    """Save and Sync config to all nodes - return 0 on success, non-zero on error"""
    print "Saving configuration changes ..."
    changes = _splitlines(AdminConfig.queryChanges())
    for change in changes:
        print " %s"%(change)
    print "Save Mode: %s"%(AdminConfig.getSaveMode())
    AdminConfig.save()
    print "Save complete!"
    returncode = 0
    nodes = _splitlines(AdminConfig.list( 'Node' ))
    for node in nodes:
        if nodeHasServerEntry( node, 'NODE_AGENT' ):
            nodename = AdminConfig.showAttribute(node, 'name')
            print "Getting the NodeSync object for node %s"%(nodename)
            Sync1 = AdminControl.completeObjectName( "type=NodeSync,node=%s,*" % nodename )
            if Sync1:
                print "Synchronizing configuration changes with node %s"%(nodename)
                rc = AdminControl.invoke( Sync1, 'sync' )
                if rc != 'true':  # failed
                    print "Failed to synchronize node %s"%(nodename)
                    returncode = 1
            else:
                print "WARNING: Unable to get NodeSync object for node %s - is node agent running?"%(nodename)
                returncode = 2
    if returncode != 0:
        print "Node synchronization FAILED"
    return returncode

def uninstallAppIfInstalled( appName ): 
    stopAndUninstallAppIfInstalled(None, None, None, appName)

def stopAndUninstallAppIfInstalled( cellName, nodeName, serverName, appName ):
    installedApps = _splitlines(AdminApp.list())
    for app in installedApps:
        if app == appName:
            print "Uninstalling the %s application ..."%(appName)
            try:
                if cellName != None:
                    print "Stopping the %s application ..."%(appName)
                    appManager = AdminControl.queryNames('cell='+cellName+',node='+nodeName+',type=ApplicationManager,process='+serverName+',*')
                    AdminControl.invoke(appManager, 'stopApplication', appName)
            except:
                print "Could not stop application"
            AdminApp.uninstall(appName)

def installApp( earPath, appName, options ): 
    print "Attempting to install an application"
    print "Application Name : %s"%(appName)
    print "EAR path         : %s"%(earPath)
    print "Install Options  : %s"%(options)
    print "(If the application is already installed, it will first be uninstalled)"
    uninstallAppIfInstalled(appName)
    AdminApp.install(earPath, options)

def waitUntilAppIsReady( appName ):
    seconds = 10
    waited = 0
    timeout = 300
    while waited<timeout and not AdminApp.isAppReady(appName):
        print "Waiting %s seconds so the system is ready to start the %s application"%(seconds, appName)
        time.sleep( seconds )
        waited+=seconds
    if waited<20:
        print "Waiting %s seconds so the system is ready to start the %s application"%(20-waited, appName)
        time.sleep(20-waited)

def startApplication( cellname, nodename, servername, appname ):
    print "Starting an application"
    print "App Name    : %s"%(appname)
    print "Cell Name   : %s"%(cellname)
    print "Node Name   : %s"%(nodename)
    print "Server Name : %s"%(servername)
    appManager = AdminControl.queryNames('cell=%s,node=%s,type=ApplicationManager,process=%s,*' %(cellname,nodename,servername))
    AdminControl.invoke(appManager, 'startApplication', appname)
        
def stopApplication( cellname, nodename, servername, appname ):
    print "Stopping an application"
    print "App Name    : %s"%(appname)
    print "Cell Name   : %s"%(cellname)
    print "Node Name   : %s"%(nodename)
    print "Server Name : %s"%(servername)
    appManager = AdminControl.queryNames('cell=%s,node=%s,type=ApplicationManager,process=%s,*' %(cellname,nodename,servername))
    AdminControl.invoke(appManager, 'stopApplication', appname)

print "Shared definitions initialized!"
