//
// Created by tifle on 03/04/2025.
//

#ifndef MULTIME_H
#define MULTIME_H


template<typename T>
using Comparator = bool (*)(const T&, const T&);  //pointer la functie

template<typename T>
class Multime {
private:
    T* elems;
    int capacitate;
    int noElems;

    Comparator<T> cmp;  // folosește aliasul de tip

    void resize();
    int search(const T& e) const;

public:
    Multime(Comparator<T> cmpFunc);

    ~Multime();

    void add(const T& e);
    int remove(const T& e);
    bool ifExist(const T& e) const;
    int size() const;
    T getElem(int poz) const;
};

#include "Multime.tpp"

#endif //MULTIME_H
