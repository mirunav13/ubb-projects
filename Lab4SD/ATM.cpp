#include "ATM.h"
#include <iostream>
using namespace std;

bool cmpId(const Transaction& t1, const Transaction& t2) {
    return t1.getIdTrans() < t2.getIdTrans();
}

bool cmpSuma(const Transaction &t1, const Transaction &t2) {
    return t1.getSuma() < t2.getSuma();
}

bool cmpLen(const Transaction &t1, const Transaction &t2) {
    return t1.getNrbanc() < t2.getNrbanc();
}

bool cmpData(const Transaction &t1, const Transaction &t2) {
    if (t1.getData().an != t2.getData().an)
        return t1.getData().an < t2.getData().an;
    if (t1.getData().luna != t2.getData().luna)
        return t1.getData().luna < t2.getData().luna;
    return t1.getData().zi < t2.getData().zi;
}

ATM::ATM() : transactions(new OrderedSet<Transaction>(cmpId)), transactionId(1) {}

ATM::~ATM() {
    delete transactions;
}

void ATM::addTransaction(int suma, int transactionID, int zi, int luna, int an) {
    int sumaInitiala = suma;
    Collection<int> copie = collection;
    Collection<int> bancnoteFolosite;

    for (int i = 0; i < copie.size(); i++) {
        int valoare = copie.getAt(i);
        int disponibile = copie.nrOccurrences(valoare);

        if (suma == 0) break;

        int folosite = min(suma / valoare, disponibile);

        if (folosite > 0) {
            bancnoteFolosite.add(valoare, folosite);
            suma -= folosite * valoare;
            copie.remove(valoare, folosite);
        }
    }

    if (suma > 0) {
        cout << "Fonduri insuficiente pentru aceasta tranzactie!\n";
        return;
    }

    Transaction t(transactionID, sumaInitiala, bancnoteFolosite, zi, luna, an);

    if (transactions->ifExist(t)) {
        std::cout << "Aceasta tranzactie exista deja.\n";
        return;
    }

    collection = copie;

    transactions->add(t);

    cout << "Tranzactia a fost efectuata cu succes:\n";
    for (int i = 0; i < bancnoteFolosite.size(); i++) {
        cout << bancnoteFolosite.nrOccurrences(bancnoteFolosite.getAt(i))
             << " x " << bancnoteFolosite.getAt(i) << " RON\n";
    }
}

void ATM::printBalance() {
    cout << "Bancnote disponibile in ATM:\n";
    for (int i = 0; i < collection.size(); i++) {
        cout << collection.getAt(i) << " lei x " << collection.nrOccurrences(collection.getAt(i)) << "\n";
    }
}

void ATM::printTransactions() const {
    if (transactions->size() == 0) {
        cout << "Nu au fost gasite tranzactii!\n";
    } else {
        cout << "Istoric tranzactii:\n";
        for (int i = 0; i < transactions->size(); i++) {
            transactions->getElem(i).printTransaction();
        }
    }
}

void ATM::addCollection(int elem, int frecv) {
    collection.add(elem, frecv);
}

void ATM::sortData() {
    OrderedSet<Transaction>* temp = new OrderedSet<Transaction>(cmpData);
    for (int i = 0; i < transactions->size(); i++) {
        temp->add(transactions->getElem(i));
    }
    delete transactions;
    transactions = temp;
    printTransactions();
}

void ATM::sortLen() {
    OrderedSet<Transaction>* temp = new OrderedSet<Transaction>(cmpLen);
    for (int i = 0; i < transactions->size(); i++) {
        temp->add(transactions->getElem(i));
    }
    delete transactions;
    transactions = temp;
    printTransactions();
}

void ATM::sortSuma() {
    OrderedSet<Transaction>* temp = new OrderedSet<Transaction>(cmpSuma);
    for (int i = 0; i < transactions->size(); i++) {
        temp->add(transactions->getElem(i));
    }
    delete transactions;
    transactions = temp;
    printTransactions();
}
