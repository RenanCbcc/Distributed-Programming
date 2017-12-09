from Pyro4 import expose


class Account(object):
    def __init__(self):
        self._balance = 0.0

    @expose
    def pay(self,price):
        self._balance -=price

    @expose
    def deposit(self,cash):
        self._balance+=cash

    @expose
    def balance(self):
        return self._balance


class Shop(object):

    def __init__(self):
        self.accounts = {}
        self.clients = ["Warin Diodorus","Ermenrich Baugulf","Theotman Erasmos","Apollonios Unnr"]

    @expose
    def name(self):
        return "BuyAnythingOnline"

    @expose
    def logOn(self,name):
        if name in self.clients:
            self.accounts[name] = Account()
            print('logon %s' % name)
        else:
            self.clients.append(name)
            self.accounts[name] = Account()

    @expose
    def logOut(self,name):
        print('logout %s'%name)

    @expose
    def deposit(self,name,amount):
        try:
            return self.accounts[name].deposit(amount)
            print('%s deposit %.2f' % (name,amount))
        except KeyError:
            raise KeyError('unknown account')

    @expose
    def balance(self,name):
        try:
            return self.accounts[name].balance()
        except KeyError:
            raise KeyError('unknown account')

    @expose
    def allAccounts(self):
        accs = {}
        for name in self.accounts.keys():
            accs[name]= self.accounts[name].balance()
        return accs

    @expose
    def buy(self,name,price):
        balance = self.accounts[name].balance()
        self.accounts[name].pay(price)
        print('%s payed %.2f' % (name, price))