#ifndef TRANSACTION_H
#define TRANSACTION_H

#include "Collection.h"

struct Data {
    int zi, luna, an;
};

class Transaction {
private:
    int idTrans;
    int suma;
    Collection<int> banc;
    Data data;

public:
    Transaction();
    Transaction(int idVal, int sumaVal, const Collection<int>& bancVal, int zi, int luna, int an);
    Transaction(const Transaction& other);
    Transaction& operator=(const Transaction& other);

    int getIdTrans() const;
    int getSuma() const;
    int getNrbanc() const;
    Data getData() const;
    const Collection<int>& getBanc() const;

    void printTransaction() const;
    bool operator==(const Transaction& other) const;
};

#endif // TRANSACTION_H
