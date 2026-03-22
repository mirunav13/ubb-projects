#include <iostream>
#include "ATM.h"

int main() {
    ATM atm;
    int optiune, suma;
    int transactionID=1;

    do {
        std::cout << "\nMeniu ATM:\n";
        std::cout << "1. Afiseaza sold\n";
        std::cout << "2. Adauga tranzactie\n";
        std::cout << "3. Afiseaza toate tranzactiile\n";
        std::cout << "4. Adauga bancnote\n";
        std::cout << "5. Sorteaza tranzactiile dupa data\n";
        std::cout << "6. Sorteaza tranzactiile dupa suma\n";
        std::cout << "7. Sorteaza tranzactiile dupa numarul bancnotelor\n";
        std::cout << "0. Iesire\n";
        std::cout << "Alege optiunea: ";
        std::cin >> optiune;

        switch (optiune) {
            case 1:
                atm.printBalance();
                break;
            case 2:
                int an, luna, zi;
                std::cout << "Introdu data tranzactiei: "<<"\n";
                std::cout << "An: ";
                std::cin >> an;
                std::cout << "Luna: ";
                std::cin >> luna;
                std::cout << "Zi: ";
                std::cin >> zi;
                // Data data = new Data{zi, luna, an};
                // Data data={.zi=zi, .luna=luna, .an=an};
                std::cout << "Introdu suma dorita: ";
                std::cin >> suma;
                atm.addTransaction(suma, transactionID, zi, luna,an);
                transactionID++;
                break;
            case 3:
                atm.printTransactions();
                break;
            case 4:
                int elem;
                int frecv;
                std::cout << "Introdu bancnota: ";
                std::cin >> elem;
                std::cout << "Introdu frecventa: ";
                std::cin >> frecv;
                atm.addCollection(elem, frecv);
                break;
            case 5:
                atm.sortData();
                break;
            case 6:
                atm.sortSuma();
                break;
            case 7:
                atm.sortLen();
                break;
            case 0:
                std::cout << "Iesire...\n";
                break;
            default:
                std::cout << "Optiune invalida! Incearca din nou.\n";
        }
    } while (optiune != 0);

    return 0;
}
