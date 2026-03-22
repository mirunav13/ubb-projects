#pragma once
#include "OrderedSet.h"

template <typename T>
OrderedSet<T>::~OrderedSet() {
    while (head) {
        Node<T>* tmp = head;
        head = head->next;
        delete tmp;
    }
}

template <typename T>
OrderedSet<T>::OrderedSet(const OrderedSet<T>& other) : head(nullptr), cmp(other.cmp) {
    Node<T>* current = other.head;
    while (current) {
        add(current->info);
        current = current->next;
    }
}

template <typename T>
OrderedSet<T>& OrderedSet<T>::operator=(const OrderedSet<T>& other) {
    if (this != &other) {
        while (head) {
            Node<T>* tmp = head;
            head = head->next;
            delete tmp;
        }

        cmp = other.cmp;

        Node<T>* current = other.head;
        while (current) {
            add(current->info);
            current = current->next;
        }
    }
    return *this;
}

template <typename T>
bool OrderedSet<T>::ifExist(const T& elem) const {
    Node<T>* current = head;
    while (current) {
        if (!cmp(current->info, elem) && !cmp(elem, current->info)) //if (current->info == elem)
            return true;
        current = current->next;
    }
    return false;
}

template <typename T>
void OrderedSet<T>::add(const T& elem) {
    if (ifExist(elem))
        return;

    Node<T>** it = &head;

    while (*it && cmp((*it)->info, elem))
        it = &((*it)->next);

    Node<T>* newNode = new Node<T>(elem, *it);
    *it = newNode;
}

template <typename T>
int OrderedSet<T>::size() const {
    int count = 0;
    Node<T>* current = head;
    while (current) {
        count++;
        current = current->next;
    }
    return count;
}

template <typename T>
T OrderedSet<T>::getElem(int poz) const {
    Node<T>* current = head;
    int index = 0;
    while (current) {
        if (index == poz)
            return current->info;
        current = current->next;
        index++;
    }
    return T();
}

template <typename T>
void OrderedSet<T>::remove(const T& elem) {
    Node<T>** it = &head;

    while (*it) {
        if (!cmp((*it)->info, elem) && !cmp(elem, (*it)->info)) {
            Node<T>* toDelete = *it;
            *it = (*it)->next;
            delete toDelete;
            return;
        }
        it = &((*it)->next);
    }
}

