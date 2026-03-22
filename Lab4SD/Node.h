#ifndef NODE_H
#define NODE_H

template <class E>
class Node {
    E info;
    Node<E>* next;

public:
    Node(E info, Node<E>* next = nullptr) : info(info), next(next) {}

    const E& getInfo() const { return info; }
    Node<E>* getNext() const { return next; }

    void setNext(Node<E>* nxt) { next = nxt; }

    template <typename T>
    friend class Collection;

    template <typename T>
    friend class OrderedSet;
};

#endif // NODE_H

