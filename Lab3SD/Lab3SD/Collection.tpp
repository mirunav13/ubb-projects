#pragma once
#include "Collection.h"
#include <algorithm>

template <typename T>
Collection<T>::~Collection() {
    delete[] elems;
}

template <typename T>
void Collection<T>::resize() {
    if (capacity == 0) capacity = 1;
    this->capacity *= 2;

    pereche<T>* temp = new pereche<T>[this->capacity];
    std::copy(this->elems, this->elems + this->len, temp);

    delete[] this->elems;
    this->elems = temp;
}

template <typename T>
Collection<T>::Collection() {
    this->capacity = 1;
    this->elems = new pereche<T>[this->capacity];
    this->len = 0;
}

template <typename T>
void Collection<T>::add(T elem, int frecv) {
    for (int i = 0; i < this->len; ++i)
        if (elem == elems[i].elem) {
            elems[i].frecv += frecv;
            return;
        }
    if (capacity == this->len)
        resize();
    int poz = search(elem);
    for (int i = frecv; i > poz; --i)
        elems[i] = elems[i - 1];
    elems[poz] = pereche<T>{elem, frecv};
    this->len++;
}

template <typename T>
void Collection<T>::remove(T elem, int valfrecv) {
    for (int i = 0; i < this->len; i++) {
        if (elems[i].elem == elem) {
            if (elems[i].frecv - valfrecv == 0) {
                for (int j = i + 1; j < this->len; j++) {
                    elems[j - 1] = elems[j];
                }
                len--;
            } else {
                elems[i].frecv -= valfrecv;
            }
        }
    }
}

template <typename T>
bool Collection<T>::search(T elem) {
    for (int i = 0; i < this->len; i++)
        if (elem == elems[i].elem)
            return true;
    return false;
}

template <typename T>
int Collection<T>::size() {
    return len;
}

template <typename T>
int Collection<T>::nrOccurrences(T elem) {
    for (int i = 0; i < len; i++)
        if (elem == elems[i].elem)
            return elems[i].frecv;
    return 0;
}

template <typename T>
T Collection<T>::getAt(int position) {
    return elems[position].elem;
}