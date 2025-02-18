cmake_minimum_required(VERSION 3.27)
project(Lab_7_8)

set(CMAKE_CXX_STANDARD 17)

add_executable(Lab_7_8 main.cpp
        Apartament.cpp
        Apartament.h
        testeApartamente.cpp
        testeApartamente.h
        RepoSTL.cpp
        RepoSTL.h
        Service.cpp
        Service.h
        UI.cpp
        UI.h
)
