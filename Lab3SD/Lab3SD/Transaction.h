#ifndef TRANSACTION_H
#define TRANSACTION_H
#include "Collection.h"

struct Data{
    int zi, luna, an;
};

class Transaction {
private:
    int idTrans;
    int suma;
    pereche<int> * banc;
    int len;
    struct Data data;

public:
    Transaction(int idVal, int sumaVal, pereche<int> * bancVal, int lenVal, int zi, int luna, int an);
    Transaction();
    Transaction(const Transaction& t);
    Transaction& operator=(const Transaction& t);
    ~Transaction();

    int getSuma() const;
    int getLen() const;
    void printTransaction() const;
    pereche<int> getBanc() const;
    int getNrbanc() const;

    Data getData() const;
};

#endif // TRANSACTION_H

