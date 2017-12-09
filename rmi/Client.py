from __future__ import print_function
import Pyro4.naming
import Pyro4

class Client(object):

    def __init__(self,name,cash):
        self.name = name
        self.cash = cash


    def deposit_Cash(self,Shop):
        print("\n*** %s is doing shopping with %s:"\
              %(self.name,Shop.name()))

        print("Log on")
        Shop.logOn(self.name)
        print("Deposit money %s"%self.cash)
        Shop.deposit(self.name,self.cash)
        print("Balance = %.2f" % Shop.balance(self.name))
        print("Deposit money %s" %self.cash)
        Shop.deposit(self.name,50)
        print("Balance = %.2f" % Shop.balance(self.name))
        Shop.logOut(self.name)
        print("Log out")

    def buying_Book(self, Shop):
        print("\n*** %s is doing shopping with %s:"% (self.name, Shop.name()))

        print("Log on")
        Shop.logOn(self.name)
        print("Deposit money %s" % self.cash)
        Shop.deposit(self.name, self.cash)
        print("Balance = %.2f" % Shop.balance(self.name))
        print("%s is buying a book for %s$"\
              %(self.name,37))
        Shop.buy(self.name,37)
        print("Log out")
        Shop.logOut(self.name)


if __name__ == '__main__':
    ns = Pyro4.naming.locateNS()
    uri = ns.lookup("distribuited.shop.Shop")
    print(uri)
    Shop = Pyro4.core.Proxy(uri)
    George = Client('George',50)
    Warin = Client('Warin Diodorus',100)

    George.buying_Book(Shop)
    Warin.deposit_Cash(Shop)
    print("")
    print("")
    print("")

    print("The accounts in the %s:" % Shop.name())
    accounts = Shop.allAccounts()
    for name in accounts.keys():
        print(" %s: %.2f"\
              %(name,accounts[name]))