#pragma once
#include "Collection.h"

template <typename T>
Collection<T>::~Collection() {
    while (head) {
        NodeType* tmp = head;
        head = head->next;
        delete tmp;
    }
}

template <typename T>
Collection<T>::Collection(const Collection<T>& other) : head(nullptr) {
    NodeType* current = other.head;
    while (current) {
        add(current->info.elem, current->info.frecv);
        current = current->next;
    }
}

template <typename T>
Collection<T>& Collection<T>::operator=(const Collection<T>& other) {
    if (this != &other) {
        while (head) {
            NodeType* tmp = head;
            head = head->next;
            delete tmp;
        }

        NodeType* current = other.head;
        while (current) {
            add(current->info.elem, current->info.frecv);
            current = current->next;
        }
    }
    return *this;
}

template <typename T>
void Collection<T>::add(T elem, int frecv) {
    NodeType** it = &head;

    while (*it && (*it)->info.elem > elem)
        it = &((*it)->next);

    if (*it && (*it)->info.elem == elem) {
        (*it)->info.frecv += frecv;
    } else {
        PerecheType p = {elem, frecv};
        NodeType* newNode = new NodeType(p, *it);
        *it = newNode;
    }
}

template <typename T>
void Collection<T>::remove(T elem, int frecv) {
    NodeType** it = &head;

    while (*it) {
        if ((*it)->info.elem == elem) {
            (*it)->info.frecv -= frecv;
            if ((*it)->info.frecv <= 0) {
                NodeType* tmp = *it;
                *it = (*it)->next;
                delete tmp;
            }
            return;
        }
        it = &((*it)->next);
    }
}

template <typename T>
int Collection<T>::nrOccurrences(T elem) const {
    NodeType* current = head;
    while (current) {
        if (current->info.elem == elem)
            return current->info.frecv;
        current = current->next;
    }
    return 0;
}

template <typename T>
int Collection<T>::size() const {
    int count = 0;
    NodeType* current = head;
    while (current) {
        count++;
        current = current->next;
    }
    return count;
}

template <typename T>
T Collection<T>::getAt(int poz) const {
    NodeType* current = head;
    int index = 0;
    while (current) {
        if (index == poz)
            return current->info.elem;
        current = current->next;
        index++;
    }
    return T();
}
