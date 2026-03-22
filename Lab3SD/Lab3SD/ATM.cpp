//
// Created by tifle on 24/03/2025.
//
#include "ATM.h"
#include <iostream>
#include <vector>
using namespace std;

bool cmpSuma(const Transaction &t1, const Transaction &t2){
    return (t1.getSuma() < t2.getSuma());
}
bool cmpLen(const Transaction &t1, const Transaction &t2){
    return (t1.getNrbanc() < t2.getNrbanc());
}
bool cmpData(const Transaction &t1, const Transaction &t2) {
    if (t1.getData().an < t2.getData().an) {
        return true;
    } else if (t1.getData().an > t2.getData().an) {
        return false;
    }

    if (t1.getData().luna < t2.getData().luna) {
        return true;
    } else if (t1.getData().luna > t2.getData().luna) {
        return false;
    }

    return t1.getData().zi < t2.getData().zi;
}


ATM::ATM() : transactions(new Multime<Transaction>(cmpData)) {
}


void ATM::sortData() {
    Multime<Transaction>* temp = new Multime<Transaction>(cmpData);
    for (int i = 0; i < transactions->size(); i++) {
        if (temp->ifExist(transactions->getElem(i))==false) {
            temp->add(transactions->getElem(i));
        }
    }
    transactions=temp;
}

void ATM::sortLen() {
    Multime<Transaction>* temp = new Multime<Transaction>(cmpLen);
    for (int i = 0; i < transactions->size(); i++) {
        if (temp->ifExist(transactions->getElem(i))==false) {
            temp->add(transactions->getElem(i));
        }
    }
    transactions=temp;
}

void ATM::sortSuma() {
    Multime<Transaction>* temp = new Multime<Transaction>(cmpSuma);
    for (int i = 0; i < transactions->size(); i++) {
        if (temp->ifExist(transactions->getElem(i))==false) {
            temp->add(transactions->getElem(i));
        }
        // temp->add(transactions->getElem(i));
    }
    transactions=temp;
}

void ATM::addTransaction(int suma, int transactionID, int zi, int luna, int an) {
    int valsuma=suma;
    pereche<int> utilizate[collection.size()];
    int k = 0;
    for (int i = 0; i < collection.size(); i++) {
        int valoare = collection.getAt(i);
        int numarDisponibil = collection.nrOccurrences(valoare);

        if (suma == 0) break;
        int numarFolosite = std::min(suma / valoare, numarDisponibil);

        if (numarFolosite > 0) {
            utilizate[k]={valoare, numarFolosite};
            k++;
            suma -= numarFolosite * valoare;
        }
    }

    if (suma > 0) {
        std::cout << "Fonduri insuficiente pentru aceasta tranzactie!\n";
    } else {
        int id=transactionID ;
        Transaction newTrans(id, valsuma, utilizate,k, zi, luna, an);
        if (transactions->ifExist(newTrans) == false) {
            transactions->add(newTrans);
            transactionId++;
            for (int i = 0; i < k; i++) {
                collection.remove(utilizate[i].elem, utilizate[i].frecv);
            }
        }
        else {
            std::cout << "Aceasta tranzactie exista deja\n";
        }
    }

}

void ATM::printBalance() {
    std::cout << "Sold ATM: \n";
    for (int i = 0; i < collection.size(); i++) {
        std::cout << "Bancnota: " << collection.getAt(i) << endl
                  << "Disponibile: " << collection.nrOccurrences(collection.getAt(i)) << "\n";
    }
}

void ATM::printTransactions() const {
    if (transactions->size()==0) {
        std::cout << "Nu au fost gasite tranzactii!\n";
    }
    else{
        // std::cout << "Istoric tranzactii: \n";
        // for (int i = 0; i < transactions->size(); i++) {
        //     transactions->getElem(i).printTransaction();
        // }}
        std::cout << "Istoric tranzactii: \n";
         for (int i = 0; i < transactions->size(); i++) {
             transactions->getElem(i).printTransaction();  // Afișează tranzacția
        }
}


}

void ATM::addCollection(int elem, int frecv) {
    collection.add(elem, frecv);
}
