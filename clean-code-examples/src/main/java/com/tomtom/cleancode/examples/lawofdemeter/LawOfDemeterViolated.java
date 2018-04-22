package com.tomtom.cleancode.examples.lawofdemeter;

import static org.junit.Assert.assertEquals;

public class LawOfDemeterViolated {

    class Goods {

        AccountsReceivable ar;

        public Goods(AccountsReceivable ar) {
        }

        void purchase(Customer customer) {
            Money m = customer.getWallet().getMoney();
            ar.recordSale(this, m);
        }
    }

    class GoodsTest {

        void testPurchase() {
            AccountsReceivable ar = new AccountsReceivable();
            Goods goods = new Goods(ar);
            Money money = new Money(450, "INR");
            Wallet wallet = new Wallet(money);
            Customer customer = new Customer(wallet);
            goods.purchase(customer);
            assertEquals(450, ar.getSales());
        }
    }

    class AccountsReceivable {

        void recordSale(Goods g, Money m) {

        }

        public Object getSales() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    class Customer {

        Wallet wallet;

        public Customer(Wallet wallet2) {
            // TODO Auto-generated constructor stub
        }

        public Wallet getWallet() {
            return wallet;
        }
    }

    class Wallet {

        Money m;

        public Wallet(Money money) {
            // TODO Auto-generated constructor stub
        }

        Money getMoney() {
            return m;
        }
    }

    class Money {

        public Money(int i, String string) {
            // TODO Auto-generated constructor stub
        }

    }
}