//
// Created by tifle on 24/03/2025.
//
#ifndef ATM_H
#define ATM_H

#include "Collection.h"
#include "Transaction.h"
#include "Multime.h"

class ATM {
private:
    Collection<int> collection;
    Multime<Transaction> *transactions;
    int transactionId;

public:
    ATM();
    void addTransaction(int suma, int transactionID, int zi, int luna, int an);
    void printBalance();
    void printTransactions() const;
    void addCollection(int elem, int frecv);
    void sortSuma();
    void sortLen();
    void sortData();
};

#endif // ATM_H
