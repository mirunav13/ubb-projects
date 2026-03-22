//
// Created by tifle on 03/04/2025.
//

#include <iostream>
#include "Multime.h"

template<typename T>
Multime<T>::Multime(Comparator<T> cmpFunc) {
    capacitate = 10;
    noElems = 0;
    elems = new T[capacitate];
    cmp = cmpFunc;
}

template<typename T>
Multime<T>::~Multime() {
    delete[] elems;
}

template<typename T>
void Multime<T>::resize() {
    capacitate *= 2;
    T* newElems = new T[capacitate];
    for (int i = 0; i < noElems; i++) {
        newElems[i] = elems[i];
    }
    delete[] elems;
    elems = newElems;
}

template<typename T>
int Multime<T>::search(const T& e) const {
    int st = 0, dr = noElems - 1, poz = 0;
    while (st <= dr) {
        int m = (st + dr) / 2;
        if (!cmp(elems[m], e) && !cmp(e, elems[m])) // egal
            return m;
        if (cmp(elems[m], e)) {
            poz = m + 1;
            st = m + 1;
        } else {
            dr = m - 1;
        }
    }
    return poz;
}

template<typename T>
bool Multime<T>::ifExist(const T& e) const {
    int st = 0, dr = noElems - 1;
    while (st <= dr) {
        int m = (st + dr) / 2;
        if (!cmp(elems[m], e) && !cmp(e, elems[m])) // egal
            return true;
        if (cmp(elems[m], e))
            st = m + 1;
        else
            dr = m - 1;
    }
    return false;
}

template<typename T>
void Multime<T>::add(const T& e) {
    if (ifExist(e)) return;

    if (noElems == capacitate)
        resize();

    int poz = search(e);
    for (int i = noElems; i > poz; --i)
        elems[i] = elems[i - 1];
    elems[poz] = e;
    noElems++;
}

template<typename T>
int Multime<T>::remove(const T& e) {
    if (!ifExist(e))
        return 0;

    int poz = search(e);
    for (int i = poz; i < noElems - 1; ++i)
        elems[i] = elems[i + 1];
    noElems--;
    return 1;
}

template<typename T>
int Multime<T>::size() const {
    return noElems;
}

template<typename T>
T Multime<T>::getElem(int poz) const {
    if (poz < 0 || poz >= noElems) {
        return T();
    }
    return elems[poz];
}
