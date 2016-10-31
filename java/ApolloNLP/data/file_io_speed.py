# -*- coding: utf-8 -*-
"""
Created on Mon Oct 31 13:49:53 2016

@author: rao
"""

from waffle import system

file_name = "/home/rao/workspace/coding-examples/java/ApolloNLP/data/CoreNatureDictionary.ngram.txt"

t = system.Timer()
t.start()
s = system.f_read(file_name)
t.end()

print(t.t_elapsed)