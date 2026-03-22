#include "Transaction.h"

#include <iostream>
using namespace std;

Transaction::Transaction() {
    this->idTrans = 0;
    this->suma = 0;
    this->banc = nullptr;
    this->len = 0;
    // this->data = new Data;
    this->data.zi=0;
    this->data.luna=0;
    this->data.an=0;

}

Transaction::~Transaction() {
    delete[] banc;
}

Transaction::Transaction(int idVal, int sumaVal, pereche<int> * bancVal, int lenVal, int zi, int luna, int an) {
    this->idTrans = idVal;
    this->suma = sumaVal;
    this->len = lenVal;
    // this->data = dataVal;
    // this->data = new Data;
    // std::cout<<"inainte"<<zi<<" "<<luna<<" "<<an<<endl;
    this->data.an=an;
    this->data.luna=luna;
    this->data.zi = zi;
    // std::cout<<"dupa"<<data.zi<<" "<<data.luna<<" "<<data.an<<endl;
    this->banc = new pereche<int>[lenVal];
    for (int i = 0; i < lenVal; i++) {
        this->banc[i] = bancVal[i];
    }
}

void Transaction::printTransaction() const {
    std::cout << "ID Tranzactie: " << idTrans<<" " ;
    std::cout<< "Data inregistrarii: "<< this->data.zi <<"_"<<this->data.luna<<"_"<<this->data.an;
    std::cout << ", Suma: " << suma << "\n";
    std::cout << "Bancnote utilizate: ";
    for (int i = 0; i < len; i++) {
        if (banc[i].frecv > 0) {
            std::cout <<banc[i].frecv << " x " << banc[i].elem << " ; ";
        }
    }
    std::cout << "\n";


    // std::cout<<"print transaction 1";
    // // std::cout<<data->an;
    // if (data != nullptr) {
    //     std::cout << "ID Tranzactie: " << idTrans << " ";
    //     std::cout<<"print transaction 2";
    //     std::cout << "Data inregistrarii: " << data.zi << "_" << data.luna << "_" << data.an;
    //     std::cout<<"print transaction 3";
    // } else {
    //     std::cout << "Data invalida!" << std::endl;
    // }
    // std::cout << ", Suma: " << suma << "\n";
    // std::cout << "Bancnote utilizate: ";
    // for (int i = 0; i < len; i++) {
    //     if (banc[i].frecv > 0) {
    //         std::cout << banc[i].frecv << " x " << banc[i].elem << " ; ";
    //     }
    // }
    // std::cout << "\n";
}



pereche<int> Transaction::getBanc() const {
    return *banc;
}

Transaction::Transaction(const Transaction& other) {
    idTrans = other.idTrans;
    suma = other.suma;
    len = other.len;
    data = other.data;

    banc = new pereche<int>[len];
    for (int i = 0; i < len; i++) {
        banc[i] = other.banc[i];
    }
}

Transaction& Transaction::operator=(const Transaction& other) {
    if (this != &other) {
        delete[] banc;
        idTrans = other.idTrans;
        suma = other.suma;
        len = other.len;
        data= other.data;

        banc = new pereche<int>[len];
        for (int i = 0; i < len; i++) {
            banc[i] = other.banc[i];
        }
    }
    return *this;
}

int Transaction::getNrbanc() const{
    int suma = 0;
    for (int i=0 ; i < len ; i++) {
        suma+=banc[i].frecv;
    }
}

int Transaction::getSuma() const {
    return suma;
}
int Transaction::getLen() const {
    return len;
}

Data Transaction::getData() const {
    return data;
}


