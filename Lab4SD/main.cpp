#include <iostream>
#include "ATM.h"
using namespace std;

int main() {
    ATM atm;
    int optiune, suma, transactionID = 1;

    do {
        cout << "\n<<MENIU>>\n";
        cout << "1. Afiseaza colectia de bancnote\n";
        cout << "2. Realizati o tranzactie\n";
        cout << "3. Afiseaza toate tranzactiile\n";
        cout << "4. Adauga bancnote\n";
        cout << "5. Sorteaza tranzactiile dupa data\n";
        cout << "6. Sorteaza tranzactiile dupa suma\n";
        cout << "7. Sorteaza tranzactiile dupa numarul bancnotelor\n";
        cout << "8. Iesire\n";
        cout << "Introduceti optiunea dorita: ";
        cin >> optiune;

        switch (optiune) {
            case 1: atm.printBalance(); break;
            case 2: {
                int zi, luna, an;
                cout << "Data (zi luna an): ";
                cin >> zi >> luna >> an;
                cout << "Suma: ";
                cin >> suma;
                atm.addTransaction(suma, transactionID++, zi, luna, an);
                break;
            }
            case 3: atm.printTransactions(); break;
            case 4: {
                int elem, frecv;
                cout << "Valoare bancnota: ";
                cin >> elem;
                cout << "Numar bancnote: ";
                cin >> frecv;
                atm.addCollection(elem, frecv);
                break;
            }
            case 5: atm.sortData(); break;
            case 6: atm.sortSuma(); break;
            case 7: atm.sortLen(); break;
            case 8: cout << "La revedere!\n"; break;
            default: cout << "Optiune invalida!\n";
        }
    } while (optiune != 8);

    return 0;
}
