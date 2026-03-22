cmake_minimum_required(VERSION 3.30)
project(Lab4SD)

set(CMAKE_CXX_STANDARD 20)

add_executable(Lab4SD main.cpp
        ATM.cpp
        ATM.h
        Collection.h
        Transaction.cpp
        Transaction.h
        OrderedSet.h
        Node.h)
