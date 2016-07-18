cmake_minimum_required(VERSION 3.3)
project(c_benchmark)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp)
add_executable(c_benchmark ${SOURCE_FILES})