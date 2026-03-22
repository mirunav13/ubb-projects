#ifndef ATM_H
#define ATM_H

#include "Collection.h"
#include "Transaction.h"
#include "OrderedSet.h"

class ATM {
private:
    Collection<int> collection;
    OrderedSet<Transaction>* transactions;
    int transactionId;

public:
    ATM();
    ~ATM();

    void addTransaction(int suma, int transactionID, int zi, int luna, int an);
    void printBalance();
    void printTransactions() const;
    void addCollection(int elem, int frecv);

    void sortSuma();
    void sortLen();
    void sortData();
};

#endif // ATM_H
