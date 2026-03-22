#include "Transaction.h"
#include <iostream>
using namespace std;

Transaction::Transaction() : idTrans(0), suma(0), data{0, 0, 0} {}

Transaction::Transaction(int idVal, int sumaVal, const Collection<int>& bancVal, int zi, int luna, int an)
    : idTrans(idVal), suma(sumaVal), banc(bancVal), data{zi, luna, an} {}

Transaction::Transaction(const Transaction& other)
    : idTrans(other.idTrans), suma(other.suma), banc(other.banc), data(other.data) {}

Transaction& Transaction::operator=(const Transaction& other) {
    if (this != &other) {
        idTrans = other.idTrans;
        suma = other.suma;
        banc = other.banc;
        data = other.data;
    }
    return *this;
}

int Transaction::getIdTrans() const { return idTrans; }

int Transaction::getSuma() const { return suma; }

int Transaction::getNrbanc() const {
    int total = 0;
    for (int i = 0; i < banc.size(); i++) {
        total += banc.nrOccurrences(banc.getAt(i));
    }
    return total;
}

Data Transaction::getData() const { return data; }

const Collection<int>& Transaction::getBanc() const { return banc; }

void Transaction::printTransaction() const {
    cout << "ID: " << idTrans << ", Data: " << data.zi << "." << data.luna << "." << data.an
         << ", Suma: " << suma << " RON\nBancnote utilizate: ";
    for (int i = 0; i < banc.size(); i++) {
        int frecv = banc.nrOccurrences(banc.getAt(i));
        if (frecv > 0) {
            cout << frecv << " x " << banc.getAt(i) << " lei; ";
        }
    }
    cout << "\n";
}

bool Transaction::operator==(const Transaction& other) const {
    if (idTrans != other.idTrans || suma != other.suma ||
        data.an != other.data.an || data.luna != other.data.luna || data.zi != other.data.zi)
        return false;

    if (banc.size() != other.banc.size())
        return false;

    for (int i = 0; i < banc.size(); i++) {
        if (banc.getAt(i) != other.banc.getAt(i) ||
            banc.nrOccurrences(banc.getAt(i)) != other.banc.nrOccurrences(banc.getAt(i)))
            return false;
    }
    return true;
}
