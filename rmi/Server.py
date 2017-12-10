from __future__ import print_function
import Pyro4.naming as naming
import Pyro4
import shop

ns = Pyro4.naming.locateNS()
daemon = Pyro4.core.Daemon()

uri = daemon.register(shop.Shop())
ns.register("distribuited.shop.Shop", uri)
print(list(ns.list(prefix="distribuited.shop").keys()))
daemon.requestLoop()

