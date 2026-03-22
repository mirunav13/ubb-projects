cmake_minimum_required(VERSION 3.30)
project(Lab3SD)

set(CMAKE_CXX_STANDARD 20)

add_executable(Lab3SD main.cpp
        ATM.cpp
        ATM.h
        Collection.tpp
        Collection.h
        Transaction.cpp
        Transaction.h
        Multime.h
        Multime.tpp
)
