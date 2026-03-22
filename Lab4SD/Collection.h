#ifndef COLLECTION_H
#define COLLECTION_H

#include "Node.h"

template <typename T>
struct Pereche {
    T elem;
    int frecv;
};

template <typename T>
class Collection {
private:
    using PerecheType = Pereche<T>;
    using NodeType = Node<PerecheType>;

    NodeType* head;

public:
    Collection() : head(nullptr) {}
    Collection(const Collection& other);
    Collection& operator=(const Collection& other);
    ~Collection();

    void add(T elem, int frecv);
    void remove(T elem, int frecv);
    int nrOccurrences(T elem) const;
    int size() const;
    T getAt(int poz) const;
};

#include "Collection.tpp"

#endif // COLLECTION_H
