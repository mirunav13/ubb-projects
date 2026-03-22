#ifndef ORDEREDSET_H
#define ORDEREDSET_H

#include "Node.h"

template<typename T>
using Comparator = bool (*)(const T&, const T&);

template <typename T>
class OrderedSet {
private:
    Node<T>* head;
    Comparator<T> cmp;

public:
    OrderedSet(Comparator<T> cmpFunc) : head(nullptr), cmp(cmpFunc) {}
    OrderedSet(const OrderedSet& other);
    OrderedSet& operator=(const OrderedSet& other);
    ~OrderedSet();

    void add(const T& elem);
    bool ifExist(const T& elem) const;
    int size() const;
    T getElem(int poz) const;

    void remove(const T& elem);
};

#include "OrderedSet.tpp"

#endif // ORDEREDSET_H
