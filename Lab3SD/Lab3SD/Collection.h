#ifndef COLLECTION_COLECTION_H
#define COLLECTION_COLECTION_H

template <typename T>
struct pereche {
    T elem;
    int frecv;
};

template <typename T>
class Collection {
private:
    pereche<T>* elems;
    int len;
    int capacity;

    void resize();  // metodă privată pentru redimensionare

public:
    Collection(); // Constructor
    ~Collection(); // Destructor

    void add(T elem, int frecv);
    void remove(T elem, int valfrecv);
    bool search(T valoare);
    int size();
    int nrOccurrences(T elem);
    void destroy();
    T getAt(int position);
};
#include"Collection.tpp"

#endif //COLLECTION_COLECTION_H
